package HashMapControl;

import Io.MultiWriteHandler;
import Io.inputCheck;
import Io.properties;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import static log.AppendFile.loadCommands;

public class SSHashMap {
//    static String HashMapAddress = "..\\Test444\\Datas1.bin";
    static String HashMapAddress;
    static  HashMap<String, String> hm = new HashMap<>();

    public static HashMap<String, String> input() throws IOException, ClassNotFoundException {
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
            return hm;
    }





    //    �����дһ��д��ȥ�ļ��ķ����������ֶ�ˢ��
// д���ļ�
    public static void output(HashMap<String, String> hm) throws IOException {
        File file = new File(HashMapAddress);
        ObjectOutputStream oos = null;
        oos = new ObjectOutputStream(new FileOutputStream(HashMapAddress));
        if (file.exists()) {
            if (hm != null) {
                oos.writeObject(hm);
                oos.flush();
            }
        } else {
                try {
                    boolean created = file.createNewFile();
                    if (created) {
                        System.out.println("Data�ļ������ɹ���");
                        if (hm != null) {
                            oos.writeObject(hm);
                            oos.flush();
                        }
                    } else {
                        System.out.println("Data�����ļ�ʧ��");
                    }
                } catch (IOException e) {
                    System.out.println("Data�����ļ�ʱ�����쳣��" + e.getMessage());
                }
            }
                 oos.close();
        }
        public static HashMap<String,String> getSSHashMap(){
                return hm;
        }
        public static void setHm(HashMap<String,String> ssHashMap) {
                hm = ssHashMap;
        }
    }

