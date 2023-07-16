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
                //导入
                ObjectInputStream ois = null;
                ois = new ObjectInputStream(new FileInputStream(file));
                //从文件中读取数据
                hmh = (HashMap<String, HashMap<String,String>>) ois.readObject();
                ois.close();
            }
        } catch (IOException e) {
            LogPrint.logger.error("获取IO流出错",e);
        } catch (ClassNotFoundException e) {
            LogPrint.logger.error("找不到文件Data3",e);
        }
        return hmh;
    }

    //    这里就写一个写进去文件的方法，理解成手动刷盘
// 写入文件
    public static void output(HashMap<String, HashMap<String,String>> hmh)  {
        File file = new File(SHHashMapAddress);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(SHHashMapAddress));
        } catch (IOException e) {
            LogPrint.logger.error("找不到文件",e);
        }
        if (file.exists()) {
            try {
                if (hmh != null) {
                    oos.writeObject(hmh);
                    oos.flush();
                }
            } catch (IOException e) {
                LogPrint.logger.error("写入Data3失败",e);
            }
        } else {
            try {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("Data3文件创建成功。");
                    if (hmh != null) {
                        oos.writeObject(hmh);
                        oos.flush();
                        oos.close();
                    }
                } else {
                    System.out.println("Data3创建文件失败");
                }
            } catch (IOException e) {
                LogPrint.logger.error("Data3文件创建出错",e);
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
