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
//       ��¼bug������scanner����ɿͻ�������������������Ҫ�ڷ���˻������ݺ�Ż���ʾ�ͻ��˷��͵����ݣ�
//        ���������ȥ���ö��̵߳ķ�ʽȥ�������룬���Ա�֤���ߵ�����ͨ�ţ���������Ȼ���ڷ�����������뵫��ȱ�Ҳ���ͨ��������������ڶ���ͻ��˵�ʱ������
//        ��Ҫ�ȴ��ͻ��������ſ���
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
