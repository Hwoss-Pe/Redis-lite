import log.LogPrint;

public class ProgramServer {
    //   模拟一个应用程序来调用api进行操作,调用方法就是直接创建对应的类
//    然后在通过方法的形式调用服务器和客户端开启
    public static void main(String[] args) {
        SocketServer.run();
    }
}
