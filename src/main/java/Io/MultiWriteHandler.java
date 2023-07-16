package Io;

import Time.LogPrint;

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
//        �����з����ͨ���ӳ�
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

                            System.out.println("���͸��ͻ�����Ϣ: " + message);
                        }
                    }
                } catch (IOException e) {
                    LogPrint.logger.error("SocketChannel���������쳣����IO����",e);
                }
            }
        });
    }


    public static void setClient(String message) {
                try {
                    if(key != null) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        Charset charset = Charset.forName("GBK");
                        channel.write(charset.encode(message));
                    }
                } catch (IOException e) {
                    LogPrint.logger.error("SocketChannel��key����",e);
                }
    }
}
