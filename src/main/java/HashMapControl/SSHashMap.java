package HashMapControl;

import Io.properties;
import log.LogPrint;

import java.io.*;
import java.util.HashMap;

public class SSHashMap {
//    static String HashMapAddress = "..\\Test444\\Datas1.bin";
    static String HashMapAddress;
    static  HashMap<String, String> hm = new HashMap<>();

    public static HashMap<String, String> input()  {
        try {
            HashMapAddress= properties.property("SSHashMap");
            File file = new File(HashMapAddress);
            if (file.exists()) {
                //����
                ObjectInputStream ois = null;
                ois = new ObjectInputStream(new FileInputStream(file));
                //���ļ��ж�ȡ����
                hm = (HashMap<String, String>) ois.readObject();
                ois.close();
            }
        } catch (IOException e) {
            LogPrint.logger.error("�ļ�IO������",e);
        } catch (ClassNotFoundException e) {
            LogPrint.logger.error("�Ҳ����ļ�Data1",e);
        }
        return hm;
    }



    //    �����дһ��д��ȥ�ļ��ķ����������ֶ�ˢ��
// д���ļ�
    public static void output(HashMap<String, String> hm)  {
        File file = new File(HashMapAddress);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(HashMapAddress));
        } catch (IOException e) {
            LogPrint.logger.error("�ļ�IO����������",e);
        }
        if (file.exists()) {
            try {
                if (hm != null) {
                    oos.writeObject(hm);
                    oos.flush();
                }
            } catch (IOException e) {
                LogPrint.logger.error("д���ļ�Data1ʧ��",e);
            }
        } else {
                try {
                    boolean created = file.createNewFile();
                    if (created) {
                        System.out.println("Data�ļ������ɹ���");
                        if (hm != null) {
                            oos.writeObject(hm);
                            oos.flush();
                            oos.close();
                        }
                    } else {
                        System.out.println("Data�����ļ�ʧ��");
                    }
                } catch (IOException e) {
                    LogPrint.logger.error("�����ļ�Data�����쳣",e);
                }
            }

        }
        public static HashMap<String,String> getSSHashMap(){
                return hm;
        }
        public static void setHm(HashMap<String,String> ssHashMap) {
                hm = ssHashMap;
        }
    }

