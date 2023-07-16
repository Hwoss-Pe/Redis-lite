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
        // 该命令无需参数
    }

    @Override
    public void execute() {
        Thread saveThread = new Thread(() -> {
            try {
                OutputCheck.output();
                MultiWriteHandler.setClient("后台保存成功");
            } catch (IOException e) {
                e.printStackTrace();
                MultiWriteHandler.setClient("后台保存失败");
            }
        });
        saveThread.start();
    }
}
