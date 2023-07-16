package Io;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class inputCheck {
        public static  String input() {
            String persistenceMethod = "dataPersistence"  ;
//            默认采用数据持久化
            try {
                Properties properties = new Properties();
                FileInputStream fileInputStream = new FileInputStream("..\\Test444\\config.properties");
                properties.load(fileInputStream);
                persistenceMethod = properties.getProperty("persistenceMethod");
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return persistenceMethod;
        }
    }

