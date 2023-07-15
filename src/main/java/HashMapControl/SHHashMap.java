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
            //导入
            ObjectInputStream ois = null;
//            try {
            ois = new ObjectInputStream(new FileInputStream(file));
            //从文件中读取数据
            hmh = (HashMap<String, HashMap<String,String>>) ois.readObject();
            ois.close();
        }else{
            hmh = new HashMap<String, HashMap<String,String>>();
        }
        return hmh;
    }

    //    这里就写一个写进去文件的方法，理解成手动刷盘
// 写入文件
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
                    System.out.println("Data3文件创建成功。");
                    if (hmh != null) {
                        oos.writeObject(hmh);
                        oos.flush();
                    }
                } else {
                    System.out.println("Data3创建文件失败");
                }
            } catch (IOException e) {
                System.out.println("Data3创建文件时出现异常：" + e.getMessage());
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
