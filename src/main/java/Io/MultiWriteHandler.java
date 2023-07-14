package Io;

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
        this.key = key;
        this.buffer = ByteBuffer.allocate(1024);
    }

    public void run() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
//       记录bug，由于scanner会造成客户端输入阻塞，导致需要在服务端回馈数据后才会显示客户端发送的数据，
//        因此这里再去采用多线程的方式去进行输入，可以保证两边的正常通信，这样做仍然存在服务端先行输入但是缺找不到通道的情况，出现在多个客户端的时候问题
//        需要等待客户端输入后才可以
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    SocketChannel channel = (SocketChannel) key.channel();
                    Scanner scanner = new Scanner(System.in);

                    while (true) {
                        if (scanner.hasNextLine()) {
                            String message = scanner.nextLine();
                            buffer.clear();
                            buffer.put(message.getBytes());
                            buffer.flip();

                            while (buffer.hasRemaining()) {
                                channel.write(buffer);
                            }

                            System.out.println("发送给客户端信息: " + message);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static void setClient(String message) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                    SocketChannel channel = (SocketChannel) key.channel();
                try {
                    Charset charset = Charset.forName("GBK");
                    channel.write(charset.encode(message));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
