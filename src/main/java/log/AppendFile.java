package log;

import java.io.*;
import Command.CommandExtract;
import Io.properties;
import Time.LogPrint;

public class AppendFile {
//
//    每次手动保存和自动保存和后台保存的时候就把日志文件清空
//            然后输入的命令只要符合就追加写进入
//    那么到时就会有一堆文件采用换行来隔开，每次换行代表一个命令
//    恢复的时候就从文件读取，然后用换行分开每句命令最后挨个执行，清空也要清空日志

     static   String  LOGFILE;
     static {
         LOGFILE = properties.property("appendFile");
     }
     static String commands = "";
    // 清空日志文件
    public static void clearLogFile() {
                commands="";
    }

    // 将命令追加到日志文件中
    public static void saveCommand() {
        try (FileWriter writer = new FileWriter(LOGFILE, true)) {
            writer.write(commands);
            clearLogFile();
        } catch (IOException e) {
            LogPrint.logger.error("手动save保存命令出错",e);
        }
    }

    // 从日志文件中读取命令并且执行
    public static void loadCommands() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOGFILE))) {
            String line;
            CommandExtract extract = new CommandExtract();
            while ((line = reader.readLine()) != null) {
                String[] commands = line.split("\\*");
                for (String command : commands) {
                    if(!command.equals("save"))
                   extract.Command(command);
                }
                System.out.println("执行恢复成功");
            }
            clearLogFile();
        } catch (IOException e) {
            LogPrint.logger.error("读取缓存数据出错",e);
        }
    }
    public  static  void tempFile(String command){
//        这里就存取那些缓存的命令
         commands = commands+command+"*";
    }
}
