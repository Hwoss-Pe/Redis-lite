package Command;
import Io.MultiWriteHandler;
import Io.OutputCheck;
import Time.LogPrint;

import java.io.IOException;
import java.util.List;

public class BGSAVECommand implements Command {
    @Override
    public void setArgs(List<String> list) {
        // �������������
    }

    @Override
    public void execute() {
        Thread saveThread = new Thread(() -> {
            OutputCheck.output();
            MultiWriteHandler.setClient("��̨����ɹ�");
        });
        saveThread.start();
    }
}
