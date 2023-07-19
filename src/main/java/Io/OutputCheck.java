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
    //   �����ͬ��input�������һ�������ȥ���󶨵ģ�
    public static void output()  {
        String persistenceMethod = "dataPersistence";
//            Ĭ�ϲ������ݳ־û�
        try {
            Properties properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream("..\\Test444\\src\\main\\resources\\config.properties");
            properties.load(fileInputStream);
            persistenceMethod = properties.getProperty("persistenceMethod");
            fileInputStream.close();
        } catch (IOException e) {
            LogPrint.logger.error("���������ļ��쳣",e);
        }
        if (persistenceMethod.equals("dataPersistence")) {
            HashMap<String, String> hm = SSHashMap.getSSHashMap();
            HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
            HashMap<String, HashMap<String, String>> hmh = SHHashMap.getSHHashMap();
            HashMap<String, HashSet<String>> hms = HashsetMap.getSetMap();
            SSHashMap.output(hm);
            SLHashMap.output(hml);
            SHHashMap.output(hmh);
            HashsetMap.output(hms);
        } else if (persistenceMethod.equals("appendLogFile")) {
            AppendFile.saveCommand();
        }else if (persistenceMethod.equals("both")){
            HashMap<String, String> hm = SSHashMap.getSSHashMap();
            HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
            HashMap<String, HashMap<String, String>> hmh = SHHashMap.getSHHashMap();
            HashMap<String, HashSet<String>> hms = HashsetMap.getSetMap();
            SSHashMap.output(hm);
            SLHashMap.output(hml);
            SHHashMap.output(hmh);
            HashsetMap.output(hms);
            System.out.println("������־һ��");
        }else{
            MultiWriteHandler.setClient("�����ļ��־û���������");
        }
    }
}
