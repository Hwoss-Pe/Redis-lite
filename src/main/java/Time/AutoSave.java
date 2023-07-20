package Time;

import HashMapControl.HashsetMap;
import HashMapControl.SHHashMap;
import HashMapControl.SLHashMap;
import HashMapControl.SSHashMap;
import Io.MultiWriteHandler;
import Io.OutputCheck;
import log.AppendFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static log.AppendFile.saveCommand;

public class AutoSave {

    public void execute() {
        Thread saveThread = new Thread(() -> {
            OutputCheck.output();
            System.out.println("后台保存成功");
        });
        saveThread.start();
    }
}
