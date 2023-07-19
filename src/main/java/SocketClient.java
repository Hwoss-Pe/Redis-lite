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
    //定义相关的属性
    private static  String HOST ; // 服务器的ip
    private static   int PORT ; //服务器端口
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    //构造器, 完成初始化工作
    public SocketClient()  {
        HOST = properties.property("HOST");
        String PORTStr = properties.property("PORT");
        PORT = Integer.parseInt(PORTStr);

        try {
            selector = Selector.open();
            //连接服务器
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            //设置非阻塞
            socketChannel.configureBlocking(false);
            //将channel 注册到selector
            socketChannel.register(selector, SelectionKey.OP_READ);
            //得到username
            username = socketChannel.getLocalAddress().toString().substring(1);
        } catch (IOException e) {
            LogPrint.logger.error("SocketChannel创建出现异常或者IO出错",e);
        }
        System.out.println(username + ">");

    }

    //向服务器发送消息
    public void sendInfo(String info) {

        info = username + " 说：" + info;
//        写入前包装一下，这里就要写一个通过协议的方法
        Protocol protocol = new Protocol();
        info = protocol.encodeClient(info);
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        }catch (IOException e) {
            LogPrint.logger.error("SocketChannel通道出现异常",e);
        }
    }

//    读取从服务器端回复的消息
    public void readInfo() {
        try {
            int readChannels = selector.select();
            if(readChannels > 0) {//有可以用的通道
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {

                    SelectionKey key = iterator.next();
                    if(key.isReadable()) {
                        //得到相关的通道
                        SocketChannel sc = (SocketChannel) key.channel();
                        //得到一个Buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        //读取
                        sc.read(buffer);
                        //把读到的缓冲区的数据转成字符串
                        String msg = new String(buffer.array());
                        Protocol protocol = new Protocol();
                         msg = protocol.decodeClient(msg);
                      msg = new String(msg.replaceAll("\u0000", ""));
                        System.out.println(msg);
                    }
                }
                iterator.remove(); //删除当前的selectionKey, 防止重复操作
            } else {
                System.out.println("没有可以用的通道...");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args)  {

        //启动我们客户端
        SocketClient chatClient = new SocketClient();



        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            SAVECommand saveCommand = new SAVECommand();
            saveCommand.execute();
//           这个实现只能用在调试，到现在没看懂，也可能是我的设计的通道模式是等待断开导致程序是异常退出所以没法关闭钩子运行
//            因此在是服务端进行监听，如果失去连接就实现保存
//            如果想检测手动保存的功能将这段代码和服务器里面的一起注释在SubReactor里面
        }));
        new Thread(() -> {
            while (true) {
                chatClient.readInfo();
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e) {
                    LogPrint.logger.error("线程并发异常",e);
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
