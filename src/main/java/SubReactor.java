import Command.Command;
import HashMapControl.SSHashMap;
import Io.MultiWriteHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class SubReactor {
    private Selector selector;
    private volatile boolean stop;
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    public SubReactor() throws IOException, ClassNotFoundException {
//        ������ʱ���ȡ�����ļ�����Ĺ�ϣ
        HashMap<String, String> hm = SSHashMap.input();
        System.out.println(hm);
        try {
            selector = SelectorProvider.provider().openSelector();
            stop = false;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * ����Reactor�е�Channelע�ᵽ��Reactor�е�selector
     */
    public void register(SocketChannel sc) {
        try {
            sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("��reactor��ʼ�����ˡ���������");
                System.out.println("��reactor�߳�:" + Thread.currentThread().getName());
                System.out.println("-----------------------------------------------------------");

                while (!stop) {
                    try {
                        selector.select(1000);
                        Set<SelectionKey> selectedKeys = selector.selectedKeys();
                        Iterator<SelectionKey> it = selectedKeys.iterator();
                        SelectionKey key = null;
                        while (it.hasNext()) {
                            key = it.next();
                            it.remove();
                            try {
                                disptach(key);
                            } catch (Exception e) {
                                if (key != null) {
                                    key.cancel();
                                    if (key.channel() != null)
                                        key.channel().close();
                                }
                            }
                        }
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        });

    }

    private void disptach(SelectionKey key) {
        /**
         * ��Reactorֻ���Ķ���д�¼�
         */
        if (key.isValid()) {
            if (key.isReadable()) {
                //����� (ר��д����..)
                readData(key);
            }
            if (key.isWritable()) {
                new MultiWriteHandler(key).run();
            }
        }
    }

    //��ȡ�ͻ�����Ϣ
    private void readData(SelectionKey key) {
        SocketChannel channel = null;

        try {
            //ȡ��������channle
            //�õ�channel
            channel = (SocketChannel) key.channel();
            //����buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int count = channel.read(buffer);
            //�ѻ�����������ת���ַ���
            String msg = new String(buffer.array()).replaceAll("\u0000", "");
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("ҵ���߼������߳�: " + Thread.currentThread().getName());
                    //����count��ֵ������
                    if (count > 0) {
                        //�������Ϣ
                        System.out.println("�ӿͻ����յ�: " + msg);
                        CommandExtract commandExtract   = new CommandExtract();
                        Command command = commandExtract.Extract(msg);
                        if (command!=null) {
                            command.execute();
                        }
                        HashMap<String, String> hm = SSHashMap.getSSHashMap();
                        try {
//                            ��������þ��Ǵӿͻ����յ���Ϣ���ֱ��д�룬��������ĳɶ�ʱ����



                            SSHashMap.output(hm);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("-----------------------------------------------------------");
                    }
                }
            });
            //�������Ŀͻ���ת����Ϣ(ȥ���Լ�), ר��дһ������������
            sendInfoToOtherClients(msg, channel);
        } catch (Exception e) {
            try {
                System.out.println(channel.getRemoteAddress() + " ������..");
                //ȡ��ע��
                key.cancel();
                //�ر�ͨ��
                channel.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    //ת����Ϣ�������ͻ�(ͨ��)
    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {
        System.out.println("������ת����Ϣ��...");
        System.out.println("������ת�����ݸ��ͻ����߳�: " + Thread.currentThread().getName());

        //���� ����ע�ᵽselector �ϵ� SocketChannel,���ų� self
        for (SelectionKey key : selector.keys()) {
            //ͨ�� key  ȡ����Ӧ�� SocketChannel
            Channel targetChannel = key.channel();

            //�ų��Լ�
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                //ת��
                SocketChannel dest = (SocketChannel) targetChannel;
                //��msg �洢��buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //��buffer ������д�� ͨ��
                dest.write(buffer);
            }
        }
    }
}

