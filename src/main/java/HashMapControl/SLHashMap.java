package HashMapControl;

import Io.properties;
import log.LogPrint;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

public class SLHashMap {
    static String SLHashMapAddress;
    static HashMap<String, LinkedList<String>> hml = new HashMap<>();
    public static HashMap<String, LinkedList<String>> input() {
        try {
            SLHashMapAddress= properties.property("SLHashMap");
            File file = new File(SLHashMapAddress);
            if (file.exists()) {
                //����
                ObjectInputStream ois = null;
                ois = new ObjectInputStream(new FileInputStream(file));
                //���ļ��ж�ȡ����
                hml = (HashMap<String, LinkedList<String>>) ois.readObject();
                ois.close();
            }
        } catch (IOException e) {
            LogPrint.logger.error("��ȡIO������",e);
        } catch (ClassNotFoundException e) {
            LogPrint.logger.error("�Ҳ����ļ�Data2",e);
        }
        return hml;
    }

    //    �����дһ��д��ȥ�ļ��ķ����������ֶ�ˢ��
// д���ļ�
    public static void output(HashMap<String, LinkedList<String>> hml)  {
        File file = new File(SLHashMapAddress);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(SLHashMapAddress));
        } catch (IOException e) {
            LogPrint.logger.error("�ļ�IO������",e);
        }
        if (file.exists()) {
            try {
                if (hml != null) {
                    oos.writeObject(hml);
                    oos.flush();
                }
            } catch (IOException e) {
                LogPrint.logger.error("д���ļ�Data2ʧ��",e);
            }
        } else {
            try {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("Data2�ļ������ɹ���");
                    if (hml != null) {
                        oos.writeObject(hml);
                        oos.flush();
                        oos.close();
                    }
                } else {
                    System.out.println("Data2�����ļ�ʧ��");
                }
            } catch (IOException e) {
                LogPrint.logger.error("Data2���������쳣",e);
            }
        }
    }
    public static HashMap<String,LinkedList<String>> getSLHashMap(){
        return hml;
    }
    public static void setHml(HashMap<String,LinkedList<String>> slHashMap) {
        hml = slHashMap;
    }
}
