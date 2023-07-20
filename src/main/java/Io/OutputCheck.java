package Io;

import HashMapControl.HashsetMap;
import HashMapControl.SHHashMap;
import HashMapControl.SLHashMap;
import HashMapControl.SSHashMap;
import log.LogPrint;
import log.AppendFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Properties;

public class OutputCheck {
    //   输出是一起输出出去，绑定的；
    public static void output()  {
        String persistenceMethod = "dataPersistence";
//            默认采用数据持久化
        try {
//            读取配置文件词条
            Properties properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream("..\\Test444\\src\\main\\resources\\config.properties");
            properties.load(fileInputStream);
            persistenceMethod = properties.getProperty("persistenceMethod");
            fileInputStream.close();
        } catch (IOException e) {
            LogPrint.logger.error("读入配置文件异常",e);
        }
        if (persistenceMethod.equals("dataPersistence")) {
//            数据存储持久化
            HashMap<String, String> hm = SSHashMap.getSSHashMap();
            HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
            HashMap<String, HashMap<String, String>> hmh = SHHashMap.getSHHashMap();
            HashMap<String, HashSet<String>> hms = HashsetMap.getSetMap();
            SSHashMap.output(hm);
            SLHashMap.output(hml);
            SHHashMap.output(hmh);
            HashsetMap.output(hms);
        } else if (persistenceMethod.equals("appendLogFile")) {
//            追加日志文件持久化
            AppendFile.saveCommand();
        }else if (persistenceMethod.equals("both")){
//            默认采用数据持久化
            HashMap<String, String> hm = SSHashMap.getSSHashMap();
            HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
            HashMap<String, HashMap<String, String>> hmh = SHHashMap.getSHHashMap();
            HashMap<String, HashSet<String>> hms = HashsetMap.getSetMap();
            SSHashMap.output(hm);
            SLHashMap.output(hml);
            SHHashMap.output(hmh);
            HashsetMap.output(hms);
            System.out.println("两种日志一起");
        }else{
            MultiWriteHandler.setClient("配置文件持久化方法出错");
        }
    }
}
