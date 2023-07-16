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
                //导入
                ObjectInputStream ois = null;
                ois = new ObjectInputStream(new FileInputStream(file));
                //从文件中读取数据
                hm = (HashMap<String, String>) ois.readObject();
                ois.close();
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
                 oos.close();
        }
        public static HashMap<String,String> getSSHashMap(){
                return hm;
        }
        public static void setHm(HashMap<String,String> ssHashMap) {
                hm = ssHashMap;
        }
    }

