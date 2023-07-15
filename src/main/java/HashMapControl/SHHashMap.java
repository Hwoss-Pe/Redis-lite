package HashMapControl;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

public class SHHashMap {
    static String SHHashMapAddress = "..\\Test444\\Datas3.bin";
    static HashMap<String, HashMap<String,String>> hmh = null;
    public static HashMap<String, HashMap<String,String>> input() throws IOException, ClassNotFoundException {
        File file = new File(SHHashMapAddress);
        if (file.exists()) {
            //����
            ObjectInputStream ois = null;
//            try {
            ois = new ObjectInputStream(new FileInputStream(file));
            //���ļ��ж�ȡ����
            hmh = (HashMap<String, HashMap<String,String>>) ois.readObject();
            ois.close();
        }else{
            hmh = new HashMap<String, HashMap<String,String>>();
        }
        return hmh;
    }

    //    �����дһ��д��ȥ�ļ��ķ����������ֶ�ˢ��
// д���ļ�
    public static void output(HashMap<String, HashMap<String,String>> hmh) throws IOException {
        File file = new File(SHHashMapAddress);
        ObjectOutputStream oos = null;
        oos = new ObjectOutputStream(new FileOutputStream(SHHashMapAddress));
        if (file.exists()) {
            if (hmh != null) {
                oos.writeObject(hmh);
                oos.flush();
            }
        } else {
            try {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("Data3�ļ������ɹ���");
                    if (hmh != null) {
                        oos.writeObject(hmh);
                        oos.flush();
                    }
                } else {
                    System.out.println("Data3�����ļ�ʧ��");
                }
            } catch (IOException e) {
                System.out.println("Data3�����ļ�ʱ�����쳣��" + e.getMessage());
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
