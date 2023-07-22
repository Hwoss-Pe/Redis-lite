package Io;

import log.LogPrint;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiWriteHandler {
    private static  SelectionKey key = null;
    private final ByteBuffer buffer;

    public MultiWriteHandler(SelectionKey key) {
//        用过key来获取对应的通道
        this.key = key;
        this.buffer = ByteBuffer.allocate(1024);
    }

    public void run() {
//        多线程运行来发送数据给客户端
        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        这里有服务端通信延迟
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    SocketChannel channel = (SocketChannel) key.channel();
                    Scanner scanner = new Scanner(System.in);
                    while (true) {
                        if (scanner.hasNextLine()) {
                            String message = scanner.nextLine();
//                            这里主动阻塞数据防止循环一直发送
                            buffer.clear();
                            buffer.put(message.getBytes());
                            buffer.flip();
//是                             设置可读
                            while (buffer.hasRemaining()) {
                                channel.write(buffer);
                            }

                            System.out.println("发送给客户端信息: " + message);
                        }
                    }
                } catch (IOException e) {
                    LogPrint.logger.error("SocketChannel创建出现异常或者IO出错",e);
                }
            }
        });
    }


    public static void setClient(String message) {
        try {
            if(key != null) {
//                同一个key去获取通道
                SocketChannel channel = (SocketChannel) key.channel();
//                Charset charset = StandardCharsets.UTF_8;
                Charset charset = Charset.forName("GBK");
//                设置发送信息的编码

                channel.write(charset.encode(message));
            }
        } catch (IOException e) {
            LogPrint.logger.error("SocketChannel的key出错",e);
        }
    }
}
