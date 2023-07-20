package Command;
import Io.MultiWriteHandler;
import Io.OutputCheck;
import Protocolutils.Protocol;

import java.util.List;

public class BGSAVECommand implements Command {
    @Override
    public void setArgs(List<String> list) {
        // 该命令无需参数
    }

    @Override
    public void execute() {
//        后台保存的方法，区别于正常的保存，这个采用多线程式进行保存，不会阻塞主线程
        Thread saveThread = new Thread(() -> {
//            调用输出的方法进行相对应的存储
            OutputCheck.output();
//            创建相对的协议，后面的类不在赘述
            Protocol protocol = new Protocol();
//            思路就是加密协议发给客户端，客户端再对这个协议进行解析获取对应的data
            String s = protocol.encodeServer("OK", "200");
            MultiWriteHandler.setClient(s);
        });
        saveThread.start();
    }
}
