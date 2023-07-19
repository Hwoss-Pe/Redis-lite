package HashMapControl;

import Io.properties;
import log.LogPrint;

import java.io.*;
import java.util.HashMap;

public class SSHashMap {
//    static String HashMapAddress = "..\\Test444\\Datas1.bin";
    static String HashMapAddress;
    static  HashMap<String, String> hm = new HashMap<>();

    public static HashMap<String, String> input()  {
        try {
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
        } catch (IOException e) {
            LogPrint.logger.error("文件IO流出错",e);
        } catch (ClassNotFoundException e) {
            LogPrint.logger.error("找不到文件Data1",e);
        }
        return hm;
    }



    //    这里就写一个写进去文件的方法，理解成手动刷盘
// 写入文件
    public static void output(HashMap<String, String> hm)  {
        File file = new File(HashMapAddress);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(HashMapAddress));
        } catch (IOException e) {
            LogPrint.logger.error("文件IO流出现问题",e);
        }
        if (file.exists()) {
            try {
                if (hm != null) {
                    oos.writeObject(hm);
                    oos.flush();
                }
            } catch (IOException e) {
                LogPrint.logger.error("写入文件Data1失败",e);
            }
        } else {
                try {
                    boolean created = file.createNewFile();
                    if (created) {
                        System.out.println("Data文件创建成功。");
                        if (hm != null) {
                            oos.writeObject(hm);
                            oos.flush();
                            oos.close();
                        }
                    } else {
                        System.out.println("Data创建文件失败");
                    }
                } catch (IOException e) {
                    LogPrint.logger.error("创建文件Data出现异常",e);
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

