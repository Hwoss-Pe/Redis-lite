package Io;

import HashMapControl.HashsetMap;
import HashMapControl.SHHashMap;
import HashMapControl.SLHashMap;
import HashMapControl.SSHashMap;
import log.AppendFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Properties;

public class InputCheck {
    //   先创建空集合进行保存
    private HashMap<String, HashSet<String>> hms = new HashMap<String, HashSet<String>>();
    private HashMap<String, HashMap<String,String>> hmh = new HashMap<>();
    private HashMap<String, LinkedList<String>> hml = new HashMap<>();
    private  HashMap<String, String> hm = new HashMap<>();



    public static void input()  {
        String persistenceMethod = "dataPersistence";
//            默认采用数据持久化
        persistenceMethod =  properties.property("persistenceMethod");
//        读取配置文件词条
        if (persistenceMethod.equals("dataPersistence")) {
//            实现二进制数据持久化
            HashMap<String, String> hm = SSHashMap.input();
            HashMap<String, LinkedList<String>> hml = SLHashMap.input();
            HashMap<String, HashMap<String, String>> hmh = SHHashMap.input();
            HashMap<String, HashSet<String>> hms = HashsetMap.input();
//            更新类的哈希
            SSHashMap.setHm(hm);
            SLHashMap.setHml(hml);
            SHHashMap.setHmh(hmh);
            HashsetMap.setHms(hms);
        } else if (persistenceMethod.equals("appendLogFile")) {
//            这边调用追加日志文件方法
            AppendFile.loadCommands();
        }else if (persistenceMethod.equals("both")){
//            如果是一样的就默认采用二进制持久化的方法
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
