package HashMapControl;

import Io.properties;
import Time.LogPrint;

import java.io.*;
import java.util.HashMap;

public class SHHashMap {
    static String SHHashMapAddress ;
    static HashMap<String, HashMap<String,String>> hmh = new HashMap<>();
    public static HashMap<String, HashMap<String,String>> input()  {
        try {
            SHHashMapAddress= properties.property("SHHashMap");
            File file = new File(SHHashMapAddress);
            if (file.exists()) {
                //����
                ObjectInputStream ois = null;
                ois = new ObjectInputStream(new FileInputStream(file));
                //���ļ��ж�ȡ����
                hmh = (HashMap<String, HashMap<String,String>>) ois.readObject();
                ois.close();
            }
        } catch (IOException e) {
            LogPrint.logger.error("��ȡIO������",e);
        } catch (ClassNotFoundException e) {
            LogPrint.logger.error("�Ҳ����ļ�Data3",e);
        }
        return hmh;
    }

    //    �����дһ��д��ȥ�ļ��ķ����������ֶ�ˢ��
// д���ļ�
    public static void output(HashMap<String, HashMap<String,String>> hmh)  {
        File file = new File(SHHashMapAddress);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(SHHashMapAddress));
        } catch (IOException e) {
            LogPrint.logger.error("�Ҳ����ļ�",e);
        }
        if (file.exists()) {
            try {
                if (hmh != null) {
                    oos.writeObject(hmh);
                    oos.flush();
                }
            } catch (IOException e) {
                LogPrint.logger.error("д��Data3ʧ��",e);
            }
        } else {
            try {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("Data3�ļ������ɹ���");
                    if (hmh != null) {
                        oos.writeObject(hmh);
                        oos.flush();
                        oos.close();
                    }
                } else {
                    System.out.println("Data3�����ļ�ʧ��");
                }
            } catch (IOException e) {
                LogPrint.logger.error("Data3�ļ���������",e);
            }
        }
    }
    public static HashMap<String,HashMap<String,String>> getSHHashMap(){
        return hmh;
    }
    public static void setHmh(HashMap<String,HashMap<String,String>> shHashMap) {
        hmh = shHashMap;
    }
}
