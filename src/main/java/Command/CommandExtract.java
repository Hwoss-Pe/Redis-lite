package Command;

import Io.MultiWriteHandler;
import log.LogPrint;
import log.AppendFile;

import java.util.ArrayList;
import java.util.List;

public class CommandExtract {
//    ִ�а�����Ķ�������
    public Command Extract(String userInput)  {
        String[] split = userInput.split("˵��");
        Command command = null;
        try {
            command = Command(split[1]);
        } catch (Exception e) {
            LogPrint.logger.error("���������ִ���",e);
        }
        return command;
//        ע�ⷵ�ص�ָ�������ܲ����ڣ�Ҳ���Ǵ����ָ���ʱ��
    }

    public Command Command (String commandStr) {
        List<String> argList = new ArrayList<>();
        String[] s = commandStr.split(" ");
//        ������ǻ�ȡ�������set
        String newString = s[0].trim();
//        ����ǻ�ȡ����
        String[] strings =commandStr.split("\\s+");
//        �����һ��ʼ��֤�����ȥ
        for (int i = 1; i < strings.length; i++) {
            argList.add(strings[i]);
        }
        newString = newString.toUpperCase();
        newString = "Command."+newString+"Command";
        Class<?> aClass = null;
        try {
            aClass = Class.forName(newString);
            if(Command.class.isAssignableFrom(aClass)){
                try {
//        �������Ч�������ô���������Ǵ��������Ҵ������������д��׷���ļ�
                    AppendFile.tempFile(commandStr);
                    //����Ϸ��Ͱ�����浽׷���ļ�
                    Command command = (Command) aClass.getDeclaredConstructor().newInstance();
                    command.setArgs(argList);
                    command.execute();
                    return command;
                } catch (Exception e) {
                    LogPrint.logger.error("����ִ��ʧ��",e);
                }
            }
        } catch (ClassNotFoundException e) {
            MultiWriteHandler.setClient("������������");
        }
        return null;
    }
}
