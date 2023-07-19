package Command;
import Io.MultiWriteHandler;
import Io.OutputCheck;
import Protocolutils.Protocol;
import Time.LogPrint;

import java.io.IOException;
import java.util.List;

public class BGSAVECommand implements Command {
    @Override
    public void setArgs(List<String> list) {
        // 该命令无需参数
    }

    @Override
    public void execute() {
        Thread saveThread = new Thread(() -> {
            OutputCheck.output();
            Protocol protocol = new Protocol();
            String s = protocol.encodeServer("OK", "200");
            MultiWriteHandler.setClient(s);
        });
        saveThread.start();
    }
}
