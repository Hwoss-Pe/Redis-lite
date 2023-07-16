package HashMapControl;

import Io.MultiWriteHandler;
import Io.inputCheck;
import Io.properties;

import java.io.*;
import java.nio.charset.MalformedInputException;
import java.util.HashMap;
import java.util.HashSet;

import static log.AppendFile.loadCommands;

public class HashsetMap {
//    static String SetMap = "..\\Test444\\Datas4.bin";
    static String SetMap ;
    static HashMap<String, HashSet<String>> hms = new HashMap<String, HashSet<String>>();
    public static HashMap<String, HashSet<String>> input() throws IOException, ClassNotFoundException {
        SetMap= properties.property("HashsetMap");
            File file = new File(SetMap);
            if (file.exists()) {
                //����
                ObjectInputStream ois = null;
                ois = new ObjectInputStream(new FileInputStream(file));
                //���ļ��ж�ȡ����
                hms = (HashMap<String, HashSet<String>>) ois.readObject();
                ois.close();
            }
        return hms;
    }

// д���ļ�
    public static void output(HashMap<String, HashSet<String>> hms) throws IOException {
        File file = new File(SetMap);
        ObjectOutputStream oos = null;
        oos = new ObjectOutputStream(new FileOutputStream(SetMap));
        if (file.exists()) {
            if (hms != null) {
                oos.writeObject(hms);
                oos.flush();
            }
        } else {
            try {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("Data4�ļ������ɹ���");
                    if (hms != null) {
                        oos.writeObject(hms);
                        oos.flush();
                    }
                } else {
                    System.out.println("Data4�����ļ�ʧ��");
                }
            } catch (IOException e) {
                System.out.println("Data4�����ļ�ʱ�����쳣��" + e.getMessage());
            }
        }
    }
    public static HashMap<String, HashSet<String>> getSetMap(){
        return hms;
    }
    public static void setHms(HashMap<String, HashSet<String>> setHashMap) {
        hms = setHashMap;
    }
}
