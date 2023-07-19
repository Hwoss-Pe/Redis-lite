package HashMapControl;

import Io.properties;
import log.LogPrint;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public class HashsetMap {
    static String SetMap ;
    static HashMap<String, HashSet<String>> hms = new HashMap<String, HashSet<String>>();
    public static HashMap<String, HashSet<String>> input() {
        try {
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
        } catch (IOException e) {
            LogPrint.logger.error("����ʧ��",e);
        } catch (ClassNotFoundException e) {
            LogPrint.logger.error("�Ҳ�����property",e);
        }
        return hms;
    }

// д���ļ�
    public static void output(HashMap<String, HashSet<String>> hms)  {
        File file = new File(SetMap);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(SetMap));
        } catch (IOException e) {
            LogPrint.logger.error("��ȡ�ļ���ʧ��",e);
        }
        if (file.exists()) {
            if (hms != null) {
                try {
                    oos.writeObject(hms);
                    oos.flush();
                } catch (IOException e) {
                    LogPrint.logger.error("����ʧ��",e);
                }
            }
        } else {
            try {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("Data4�ļ������ɹ���");
                    if (hms != null) {
                        oos.writeObject(hms);
                        oos.flush();
                        oos.close();
                    }
                } else {
                    System.out.println("Data4�����ļ�ʧ��");
                }
            } catch (IOException e) {
                LogPrint.logger.error("�����ļ�Data4�쳣",e);
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
