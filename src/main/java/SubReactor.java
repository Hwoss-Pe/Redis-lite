import Command.SAVECommand;
import Io.InputCheck;
import Io.MultiWriteHandler;
import Command.CommandExtract;
import Protocolutils.Protocol;
import log.LogPrint;

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

        InputCheck.input();
        try {
            selector = SelectorProvider.provider().openSelector();
            stop = false;
        } catch (IOException e) {
            LogPrint.logger.error("创建子reactor出现异常",e);
            System.exit(1);
        }
    }


//     将主Reactor中的Channel注册到从Reactor中的selector
    public void register(SocketChannel sc) {
        try {
            sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        } catch (ClosedChannelException e) {
            LogPrint.logger.error("注册selector出现异常",e);
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
                        LogPrint.logger.error("reactor出现异常",t);
                    }
                }
            }
        });

    }

    private void disPatch(SelectionKey key) {
//        从Reactor只关心读和写事件

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
            //取到关联的channel
            //得到channel
            channel = (SocketChannel) key.channel();
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int count = channel.read(buffer);
            //把缓存区的数据转成字符串
            String msg = new String(buffer.array()).replaceAll("\u0000", "");
            Protocol protocol = new Protocol();
            msg= protocol.decodeServer(msg);
            String finalMsg = msg;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("业务逻辑处理线程: " + Thread.currentThread().getName());
                    //根据count的值做处理
                    if (count > 0) {
                        //输出该消息
                        System.out.println("从客户端收到: " + finalMsg);
                        CommandExtract commandExtract   = new CommandExtract();
                       commandExtract.Extract(finalMsg);
                        System.out.println("-----------------------------------------------------------");
                    }
                }
            });
            //向其它的客户端转发消息(去掉自己), 专门写一个方法来处理
            sendInfoToOtherClients(msg, channel);
        } catch (Exception e) {
            try {
                assert channel != null;
                System.out.println(channel.getRemoteAddress() + " 离线了..");

//                离线后自动进行保存，是客户端退出的时候服务器检测然后进行保存，在客户端也写了一次保证成功
                SAVECommand saveCommand = new SAVECommand();
                saveCommand.execute();

                //取消注册
                key.cancel();
                //关闭通道
                channel.close();
            } catch (IOException e2) {
                LogPrint.logger.error("关闭通道出现异常",e);
            }
        }
    }

    //转发消息给其它客户(通道)
    private void sendInfoToOtherClients(String msg, SocketChannel self)  {
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
                try {
                    dest.write(buffer);
                } catch (IOException e) {
                    LogPrint.logger.error("将buffer的数据写入通道出现异常",e);
                }
            }
        }
    }
}

