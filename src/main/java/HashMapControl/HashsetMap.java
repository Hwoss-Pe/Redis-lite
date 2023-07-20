package HashMapControl;

import Io.properties;
import log.LogPrint;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public class HashsetMap {
//    为了方便调用。采用都是static方法的进行操作，在其他指令的时候就可以获取修改，然后再设置回来
    static String SetMap ;
    static HashMap<String, HashSet<String>> hms = new HashMap<String, HashSet<String>>();
    public static HashMap<String, HashSet<String>> input() {
        try {
//            从配置文件里面读取这个的路径
            SetMap= properties.property("HashsetMap");
            File file = new File(SetMap);
//            创建文件读取
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

    // 写入文件    //    这里就写一个写进去文件的方法，理解成手动刷盘
    public static void output(HashMap<String, HashSet<String>> hms)  {
        File file = new File(SetMap);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(SetMap));
        } catch (IOException e) {
//            获取文件失败
            LogPrint.logger.error("获取文件流失败",e);
        }
        if (file.exists()) {
            if (hms != null) {
                try {
                    oos.writeObject(hms);
                    oos.flush();
                } catch (IOException e) {
//                    日志文件写入
                    LogPrint.logger.error("读入失败",e);
                }
            }
        } else {
            try {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("Data4文件创建成功。");
                    if (hms != null) {
//                       调用流的写入就可以了
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
//    获取当前类的哈希的方法
    public static HashMap<String, HashSet<String>> getSetMap(){
        return hms;
    }
//    更新当前类的哈希的方法
    public static void setHms(HashMap<String, HashSet<String>> setHashMap) {
        hms = setHashMap;
    }
}
