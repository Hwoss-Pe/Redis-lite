import Command.SAVECommand;
import Io.InputCheck2;
import Io.MultiWriteHandler;
import Command.CommandExtract;
import Protocolutils.Protocol;
import Time.LogPrint;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class SubReactor {
    private Selector selector;
    private volatile boolean stop;
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    public SubReactor()  {

        InputCheck2.input();
        try {
            selector = SelectorProvider.provider().openSelector();
            stop = false;
        } catch (IOException e) {
            LogPrint.logger.error("������reactor�����쳣",e);
            System.exit(1);
        }
    }


//     ����Reactor�е�Channelע�ᵽ��Reactor�е�selector
    public void register(SocketChannel sc) {
        try {
            sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        } catch (ClosedChannelException e) {
            LogPrint.logger.error("ע��selector�����쳣",e);
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
                                disPatch(key);
                            } catch (Exception e) {
                                if (key != null) {
                                    key.cancel();
                                    if (key.channel() != null)
                                        key.channel().close();
                                }
                            }
                        }
                    } catch (Throwable t) {
                        LogPrint.logger.error("reactor�����쳣",t);
                    }
                }
            }
        });

    }

    private void disPatch(SelectionKey key) {
//        ��Reactorֻ���Ķ���д�¼�

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
            //ȡ��������channel
            //�õ�channel
            channel = (SocketChannel) key.channel();
            //����buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int count = channel.read(buffer);
            //�ѻ�����������ת���ַ���
            String msg = new String(buffer.array()).replaceAll("\u0000", "");
            Protocol protocol = new Protocol();
            msg= protocol.decodeServer(msg);
            String finalMsg = msg;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("ҵ���߼������߳�: " + Thread.currentThread().getName());
                    //����count��ֵ������
                    if (count > 0) {
                        //�������Ϣ
                        System.out.println("�ӿͻ����յ�: " + finalMsg);
                        CommandExtract commandExtract   = new CommandExtract();
                       commandExtract.Extract(finalMsg);
                        System.out.println("-----------------------------------------------------------");
                    }
                }
            });
            //�������Ŀͻ���ת����Ϣ(ȥ���Լ�), ר��дһ������������
            sendInfoToOtherClients(msg, channel);
        } catch (Exception e) {
            try {
                assert channel != null;
                System.out.println(channel.getRemoteAddress() + " ������..");

//                ���ߺ��Զ����б��棬�ǿͻ����˳���ʱ����������Ȼ����б��棬�ڿͻ���Ҳд��һ�α�֤�ɹ�
                SAVECommand saveCommand = new SAVECommand();
                saveCommand.execute();



                //ȡ��ע��
                key.cancel();
                //�ر�ͨ��
                channel.close();
            } catch (IOException e2) {
                LogPrint.logger.error("�ر�ͨ�������쳣",e);
            }
        }
    }

    //ת����Ϣ�������ͻ�(ͨ��)
    private void sendInfoToOtherClients(String msg, SocketChannel self)  {
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
                try {
                    dest.write(buffer);
                } catch (IOException e) {
                    LogPrint.logger.error("��buffer������д��ͨ�������쳣",e);
                }
            }
        }
    }
}

