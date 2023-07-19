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
                //导入
                ObjectInputStream ois = null;
                ois = new ObjectInputStream(new FileInputStream(file));
                //从文件中读取数据
                hml = (HashMap<String, LinkedList<String>>) ois.readObject();
                ois.close();
            }
        } catch (IOException e) {
            LogPrint.logger.error("获取IO流错误",e);
        } catch (ClassNotFoundException e) {
            LogPrint.logger.error("找不到文件Data2",e);
        }
        return hml;
    }

    //    这里就写一个写进去文件的方法，理解成手动刷盘
// 写入文件
    public static void output(HashMap<String, LinkedList<String>> hml)  {
        File file = new File(SLHashMapAddress);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(SLHashMapAddress));
        } catch (IOException e) {
            LogPrint.logger.error("文件IO流出错",e);
        }
        if (file.exists()) {
            try {
                if (hml != null) {
                    oos.writeObject(hml);
                    oos.flush();
                }
            } catch (IOException e) {
                LogPrint.logger.error("写入文件Data2失败",e);
            }
        } else {
            try {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("Data2文件创建成功。");
                    if (hml != null) {
                        oos.writeObject(hml);
                        oos.flush();
                        oos.close();
                    }
                } else {
                    System.out.println("Data2创建文件失败");
                }
            } catch (IOException e) {
                LogPrint.logger.error("Data2创建出现异常",e);
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
