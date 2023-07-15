import Time.AutoSave;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

class GroupChatServer{
    //定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 10086;
    private SubReactor subReactor;

    //构造器
    //初始化工作
    public GroupChatServer() {
        try {
            //得到选择器
            selector = Selector.open();
            //ServerSocketChannel
            listenChannel =  ServerSocketChannel.open();
            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞模式
            listenChannel.configureBlocking(false);
            //将该listenChannel 注册到selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //监听
    public void listen() {
        System.out.println("监听线程主reactor: " + Thread.currentThread().getName());
        System.out.println("-----------------------------------------------------------");
        try {
            //循环处理
            while (true) {
                int count = selector.select();
//                通道会进行阻塞并且返回当前通道就绪的数量，直到至少有一个注册到 Selector 上的 Channel 就绪。
                if(count > 0) {//有事件处理
                    //遍历得到selectionKey 集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        //取出selectionkey
                        SelectionKey key = iterator.next();

                        //监听到accept
                        if(key.isAcceptable()) {
//                            判断通道是否已经连接
                             //主Reactor监听
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            //提示
                            System.out.println(sc.getRemoteAddress() + " 上线 ");
                            System.out.println("-----------------------------------------------------------");
                            /**
                             * 主Reactor只关心Accept事件
                             */
                            if(subReactor==null){
                                Selector selector = key.selector();
                                SelectionKey sk = sc.register(selector, SelectionKey.OP_READ);
                            }else {
                                subReactor.register(sc);
                            }
                        }
                        //当前的key 删除，防止重复处理
                        iterator.remove();
                    }
                } else {
                    System.out.println("等待....");
                }
            }

        }catch (Exception e) {
            e.printStackTrace();

        }finally {
            //发生异常处理....

        }
    }
    /**
     * 添加子Reactor
     * @param subReactor
     * @return
     */
    public void addSub(SubReactor subReactor){
        this.subReactor=subReactor;
        this.subReactor.run();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //创建服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.addSub(new SubReactor());
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                AutoSave autoSave = new AutoSave();
                autoSave.execute();
            }
        };
        // 在程序启动后延迟两秒钟执行定时任务，然后每隔一分钟执行一次
        timer.schedule(task, 2000, 60*1000);
        groupChatServer.listen();

    }
}



