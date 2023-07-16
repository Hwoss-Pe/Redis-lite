package Command;

import HashMapControl.HashsetMap;
import Io.MultiWriteHandler;
import HashMapControl.SHHashMap;
import HashMapControl.SLHashMap;
import HashMapControl.SSHashMap;
import Io.OutputCheck;
import log.AppendFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class BGSAVECommand implements Command {

    @Override
    public void setArgs(List<String> list) {
        // �������������
    }

    @Override
    public void execute() {
        Thread saveThread = new Thread(() -> {
            try {
                OutputCheck.output();
                MultiWriteHandler.setClient("��̨����ɹ�");
            } catch (IOException e) {
                e.printStackTrace();
                MultiWriteHandler.setClient("��̨����ʧ��");
            }
        });
        saveThread.start();
    }
}
