package HashMapControl;

import Io.properties;
import log.LogPrint;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public class HashsetMap {
    static String SetMap ;
    static HashMap<String, HashSet<String>> hms = new HashMap<String, HashSet<String>>();
    public static HashMap<String, HashSet<String>> input() {
        try {
            SetMap= properties.property("HashsetMap");
            File file = new File(SetMap);
            if (file.exists()) {
                //导入
                ObjectInputStream ois = null;
                ois = new ObjectInputStream(new FileInputStream(file));
                //从文件中读取数据
                hms = (HashMap<String, HashSet<String>>) ois.readObject();
                ois.close();
            }
        } catch (IOException e) {
            LogPrint.logger.error("读入失败",e);
        } catch (ClassNotFoundException e) {
            LogPrint.logger.error("找不到类property",e);
        }
        return hms;
    }

    // 写入文件
    public static void output(HashMap<String, HashSet<String>> hms)  {
        File file = new File(SetMap);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(SetMap));
        } catch (IOException e) {
            LogPrint.logger.error("获取文件流失败",e);
        }
        if (file.exists()) {
            if (hms != null) {
                try {
                    oos.writeObject(hms);
                    oos.flush();
                } catch (IOException e) {
                    LogPrint.logger.error("读入失败",e);
                }
            }
        } else {
            try {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("Data4文件创建成功。");
                    if (hms != null) {
                        oos.writeObject(hms);
                        oos.flush();
                        oos.close();
                    }
                } else {
                    System.out.println("Data4创建文件失败");
                }
            } catch (IOException e) {
                LogPrint.logger.error("读入文件Data4异常",e);
            }
        }
    }
    public static HashMap<String, HashSet<String>> getSetMap(){
        return hms;
    }
    public static void setHms(HashMap<String, HashSet<String>> setHashMap) {
        hms = setHashMap;
    }
}
