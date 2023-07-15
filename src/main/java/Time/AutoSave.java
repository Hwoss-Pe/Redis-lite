package Time;

import HashMapControl.SHHashMap;
import HashMapControl.SLHashMap;
import HashMapControl.SSHashMap;
import Io.MultiWriteHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AutoSave {

    public void execute() {
        Thread saveThread = new Thread(() -> {
            try {
                HashMap<String, String> hm = SSHashMap.input();
                HashMap<String, LinkedList<String>> hml = SLHashMap.input();
                HashMap<String, HashMap<String, String>> hmh = SHHashMap.getSHHashMap();
                SSHashMap.output(hm);
                SLHashMap.output(hml);
                SHHashMap.output(hmh);
                System.out.println("��̨����ɹ�");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("��̨����ʧ��");
            }
        });
        saveThread.start();
    }
}
