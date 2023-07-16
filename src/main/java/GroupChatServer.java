import Io.properties;
import Time.AutoSave;
import Time.LogPrint;
import Time.delayHash;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

class GroupChatServer{
    //��������
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static  int PORT ;
    private SubReactor subReactor;

    //������
    //��ʼ������
    public GroupChatServer() {
        String PORTStr = null;
        PORTStr = properties.property("PORT");
        if(PORTStr != null){
            PORT = Integer.parseInt(PORTStr);
        }else{
            System.out.println("�˿����ó���");
        }
        try {
            //�õ�ѡ����
            selector = Selector.open();
            //ServerSocketChannel
            listenChannel =  ServerSocketChannel.open();
            //�󶨶˿�
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //���÷�����ģʽ
            listenChannel.configureBlocking(false);
            //����listenChannel ע�ᵽselector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (IOException e) {
            LogPrint.logger.error("listenChannel�����쳣",e);
        }
    }
    //����
    public void listen() {
        System.out.println("�����߳���reactor: " + Thread.currentThread().getName());
        System.out.println("-----------------------------------------------------------");
        try {
            //ѭ������
            while (true) {
                int count = selector.select();
//                ͨ��������������ҷ��ص�ǰͨ��������������ֱ��������һ��ע�ᵽ Selector �ϵ� Channel ������
                if(count > 0) {//���¼�����
                    //�����õ�selectionKey ����
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        //ȡ��selectionkey
                        SelectionKey key = iterator.next();

                        //������accept
                        if(key.isAcceptable()) {
//                            �ж�ͨ���Ƿ��Ѿ�����
                             //��Reactor����
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            //��ʾ
                            System.out.println(sc.getRemoteAddress() + " ���� ");
                            System.out.println("-----------------------------------------------------------");
                            /**
                             * ��Reactorֻ����Accept�¼�
                             */
                            if(subReactor==null){
                                Selector selector = key.selector();
                                SelectionKey sk = sc.register(selector, SelectionKey.OP_READ);
                            }else {
                                subReactor.register(sc);
                            }
                        }
                        //��ǰ��key ɾ������ֹ�ظ�����
                        iterator.remove();
                    }
                } else {
                    System.out.println("�ȴ�....");
                }
            }

        }catch (Exception e) {
            LogPrint.logger.error("listen�����쳣",e);
        }
    }
//�����reactor
    public void addSub(SubReactor subReactor){
        this.subReactor=subReactor;
        this.subReactor.run();
    }

    public static void main(String[] args)  {
        //��������������
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.addSub(new SubReactor());
        delayHash delayHash = new delayHash();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                AutoSave autoSave = new AutoSave();
                autoSave.execute();
            }
        };
        // �ڳ����������ӳ�10����ִ�ж�ʱ����Ȼ��ÿ��һ����ִ��һ��
        timer.schedule(task, 10000, 60*1000);
        groupChatServer.listen();

    }
}



