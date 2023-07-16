package log;

import java.io.*;
import Command.CommandExtract;
import Io.properties;
import Time.LogPrint;

public class AppendFile {
//
//    ÿ���ֶ�������Զ�����ͺ�̨�����ʱ��Ͱ���־�ļ����
//            Ȼ�����������ֻҪ���Ͼ�׷��д����
//    ��ô��ʱ�ͻ���һ���ļ����û�����������ÿ�λ��д���һ������
//    �ָ���ʱ��ʹ��ļ���ȡ��Ȼ���û��зֿ�ÿ��������󰤸�ִ�У����ҲҪ�����־

     static   String  LOGFILE;
     static {
         LOGFILE = properties.property("appendFile");
     }
     static String commands = "";
    // �����־�ļ�
    public static void clearLogFile() {
                commands="";
    }

    // ������׷�ӵ���־�ļ���
    public static void saveCommand() {
        try (FileWriter writer = new FileWriter(LOGFILE, true)) {
            writer.write(commands);
            clearLogFile();
        } catch (IOException e) {
            LogPrint.logger.error("�ֶ�save�����������",e);
        }
    }

    // ����־�ļ��ж�ȡ�����ִ��
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
                System.out.println("ִ�лָ��ɹ�");
            }
            clearLogFile();
        } catch (IOException e) {
            LogPrint.logger.error("��ȡ�������ݳ���",e);
        }
    }
    public  static  void tempFile(String command){
//        ����ʹ�ȡ��Щ���������
         commands = commands+command+"*";
    }
}
