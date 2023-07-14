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
//        开机的时候读取三个文件里面的哈希
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
     * 将主Reactor中的Channel注册到从Reactor中的selector
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
                System.out.println("从reactor开始启动了。。。。。");
                System.out.println("从reactor线程:" + Thread.currentThread().getName());
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
         * 从Reactor只关心读和写事件
         */
        if (key.isValid()) {
            if (key.isReadable()) {
                //处理读 (专门写方法..)
                readData(key);
            }
            if (key.isWritable()) {
                new MultiWriteHandler(key).run();
            }
        }
    }

    //读取客户端消息
    private void readData(SelectionKey key) {
        SocketChannel channel = null;

        try {
            //取到关联的channle
            //得到channel
            channel = (SocketChannel) key.channel();
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int count = channel.read(buffer);
            //把缓存区的数据转成字符串
            String msg = new String(buffer.array()).replaceAll("\u0000", "");
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("业务逻辑处理线程: " + Thread.currentThread().getName());
                    //根据count的值做处理
                    if (count > 0) {
                        //输出该消息
                        System.out.println("从客户端收到: " + msg);
                        CommandExtract commandExtract   = new CommandExtract();
                        Command command = commandExtract.Extract(msg);
                        if (command!=null) {
                            command.execute();
                        }
                        HashMap<String, String> hm = SSHashMap.getSSHashMap();
                        try {
//                            这里的设置就是从客户端收到信息后就直接写入，这里后续改成定时任务



                            SSHashMap.output(hm);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("-----------------------------------------------------------");
                    }
                }
            });
            //向其它的客户端转发消息(去掉自己), 专门写一个方法来处理
            sendInfoToOtherClients(msg, channel);
        } catch (Exception e) {
            try {
                System.out.println(channel.getRemoteAddress() + " 离线了..");
                //取消注册
                key.cancel();
                //关闭通道
                channel.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    //转发消息给其它客户(通道)
    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中...");
        System.out.println("服务器转发数据给客户端线程: " + Thread.currentThread().getName());

        //遍历 所有注册到selector 上的 SocketChannel,并排除 self
        for (SelectionKey key : selector.keys()) {
            //通过 key  取出对应的 SocketChannel
            Channel targetChannel = key.channel();

            //排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                //转型
                SocketChannel dest = (SocketChannel) targetChannel;
                //将msg 存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将buffer 的数据写入 通道
                dest.write(buffer);
            }
        }
    }
}

