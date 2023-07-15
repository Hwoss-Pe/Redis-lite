package Time;

import HashMapControl.HashsetMap;
import HashMapControl.SHHashMap;
import HashMapControl.SLHashMap;
import HashMapControl.SSHashMap;
import Io.MultiWriteHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class AutoSave {

    public void execute() {
        Thread saveThread = new Thread(() -> {
            try {
                HashMap<String, String> hm = SSHashMap.getSSHashMap();
                HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
                HashMap<String, HashMap<String, String>> hmh = SHHashMap.getSHHashMap();
                HashMap<String, HashSet<String>> hms = HashsetMap.getSetMap();
                SSHashMap.output(hm);
                SLHashMap.output(hml);
                SHHashMap.output(hmh);
                HashsetMap.output(hms);
                System.out.println("后台保存成功");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("后台保存失败");
            }
        });
        saveThread.start();
    }
}
