package HashMapControl;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class SSHashMap {
    static String HashMapAddress = "..\\Test444\\Datas.property";
    static  HashMap<String, String> hm = null;

    public static HashMap<String, String> input() throws IOException, ClassNotFoundException {
        File file = new File(HashMapAddress);
//        File file1 = new File("..\\Test444\\a.log");
//        PrintStream pt = new PrintStream(new FileOutputStream(file1,true));
        if (file.exists()) {
            //导入
            ObjectInputStream ois = null;
//            try {
            ois = new ObjectInputStream(new FileInputStream(file));
            //从文件中读取数据
            hm = (HashMap<String, String>) ois.readObject();
            ois.close();
        }else{
            hm = new HashMap<String, String>();
        }
        return hm;
    }

    //    这里就写一个写进去文件的方法，理解成手动刷盘
// 写入文件
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
                        System.out.println("Data文件创建成功。");
                        if (hm != null) {
                            oos.writeObject(hm);
                            oos.flush();
                        }
                    } else {
                        System.out.println("Data创建文件失败");
                    }
                } catch (IOException e) {
                    System.out.println("Data创建文件时出现异常：" + e.getMessage());
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

