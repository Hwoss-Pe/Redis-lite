package HashMapControl;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

public class SLHashMap {
    static String SLHashMapAddress = "..\\Test444\\Datas2.property";
    static HashMap<String, LinkedList<String>> hml = null;
    public static HashMap<String, LinkedList<String>> input() throws IOException, ClassNotFoundException {
        File file = new File(SLHashMapAddress);
        if (file.exists()) {
            //����
            ObjectInputStream ois = null;
//            try {
            ois = new ObjectInputStream(new FileInputStream(file));
            //���ļ��ж�ȡ����
            hml = (HashMap<String, LinkedList<String>>) ois.readObject();
            ois.close();
        }else{
            hml = new HashMap<String, LinkedList<String>>();
        }
        return hml;
    }

    //    �����дһ��д��ȥ�ļ��ķ����������ֶ�ˢ��
// д���ļ�
    public static void output(HashMap<String, LinkedList<String>> hml) throws IOException {
        File file = new File(SLHashMapAddress);
        ObjectOutputStream oos = null;
        oos = new ObjectOutputStream(new FileOutputStream(SLHashMapAddress));
        if (file.exists()) {
            if (hml != null) {
                oos.writeObject(hml);
                oos.flush();
            }
        } else {
            try {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("Data2�ļ������ɹ���");
                    if (hml != null) {
                        oos.writeObject(hml);
                        oos.flush();
                    }
                } else {
                    System.out.println("Data2�����ļ�ʧ��");
                }
            } catch (IOException e) {
                System.out.println("Data2�����ļ�ʱ�����쳣��" + e.getMessage());
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
