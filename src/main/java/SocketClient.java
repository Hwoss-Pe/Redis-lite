import Command.SAVECommand;
import Io.properties;
import Protocolutils.Protocol;
import Time.LogPrint;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;


public class SocketClient {
    //������ص�����
    private static  String HOST ; // ��������ip
    private static   int PORT ; //�������˿�
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    //������, ��ɳ�ʼ������
    public SocketClient()  {
        HOST = properties.property("HOST");
        String PORTStr = properties.property("PORT");
        PORT = Integer.parseInt(PORTStr);

        try {
            selector = Selector.open();
            //���ӷ�����
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            //���÷�����
            socketChannel.configureBlocking(false);
            //��channel ע�ᵽselector
            socketChannel.register(selector, SelectionKey.OP_READ);
            //�õ�username
            username = socketChannel.getLocalAddress().toString().substring(1);
        } catch (IOException e) {
            LogPrint.logger.error("SocketChannel���������쳣����IO����",e);
        }
        System.out.println(username + ">");

    }

    //�������������Ϣ
    public void sendInfo(String info) {

        info = username + " ˵��" + info;
//        д��ǰ��װһ�£������Ҫдһ��ͨ��Э��ķ���
        Protocol protocol = new Protocol();
        info = protocol.encodeClient(info);
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        }catch (IOException e) {
            LogPrint.logger.error("SocketChannelͨ�������쳣",e);
        }
    }

//    ��ȡ�ӷ������˻ظ�����Ϣ
    public void readInfo() {
        try {
            int readChannels = selector.select();
            if(readChannels > 0) {//�п����õ�ͨ��
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {

                    SelectionKey key = iterator.next();
                    if(key.isReadable()) {
                        //�õ���ص�ͨ��
                        SocketChannel sc = (SocketChannel) key.channel();
                        //�õ�һ��Buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        //��ȡ
                        sc.read(buffer);
                        //�Ѷ����Ļ�����������ת���ַ���
                        String msg = new String(buffer.array());
                        Protocol protocol = new Protocol();
                         msg = protocol.decodeClient(msg);
                      msg = new String(msg.replaceAll("\u0000", ""));
                        System.out.println(msg);
                    }
                }
                iterator.remove(); //ɾ����ǰ��selectionKey, ��ֹ�ظ�����
            } else {
                System.out.println("û�п����õ�ͨ��...");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args)  {

        //�������ǿͻ���
        SocketClient chatClient = new SocketClient();



        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            SAVECommand saveCommand = new SAVECommand();
            saveCommand.execute();
//           ���ʵ��ֻ�����ڵ��ԣ�������û������Ҳ�������ҵ���Ƶ�ͨ��ģʽ�ǵȴ��Ͽ����³������쳣�˳�����û���رչ�������
//            ������Ƿ���˽��м��������ʧȥ���Ӿ�ʵ�ֱ���
//            ��������ֶ�����Ĺ��ܽ���δ���ͷ����������һ��ע����SubReactor����
        }));
        new Thread(() -> {
            while (true) {
                chatClient.readInfo();
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e) {
                    LogPrint.logger.error("�̲߳����쳣",e);
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            System.out.println(HOST+":"+ PORT+">");
            String s = scanner.nextLine();
            chatClient.sendInfo(s);
        }








    }
}
