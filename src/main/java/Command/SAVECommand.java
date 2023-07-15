package Command;


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

public class SAVECommand implements Command {
//�ֶ�ǿ�ƽ������������������ִ��д��
    @Override
    public void setArgs(List<String> list) {

    }

    public SAVECommand() {
    }

    @Override
    public void execute() {
        System.out.println("��ʱ���е���save����");
        try {
            HashMap<String, String> hm = SSHashMap.getSSHashMap();
            HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
            HashMap<String, HashMap<String, String>> hmh = SHHashMap.getSHHashMap();
            HashMap<String, HashSet<String>> hms = HashsetMap.getSetMap();
            SSHashMap.output(hm);
            SLHashMap.output(hml);
            SHHashMap.output(hmh);
            HashsetMap.output(hms);
            MultiWriteHandler.setClient("����ɹ�");
        } catch (IOException e) {
            e.printStackTrace();
            MultiWriteHandler.setClient("����ʧ��");
        }


    }
}
