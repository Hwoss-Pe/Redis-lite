import Command.Command;
import Io.MultiWriteHandler;

import java.util.ArrayList;
import java.util.List;

public class CommandExtract {
//    ִ�а�����Ķ�������
    public Command Extract(String userInput)  {
        List<String> argList = new ArrayList<>();
        String[] split = userInput.split("˵��");
        //����õ��Ĳ��������ֵ
//        try{
//
//        }catch(ArrayIndexOutOfBoundsException e){
//
//        }
        String[] s = split[1].split(" ");
        String newString = s[0].trim();
        String[] strings =split[1].split("\\s+");
//        �����һ��ʼ��֤�����ȥ
        for (int i = 1; i < strings.length; i++) {
            argList.add(strings[i]);
        }
        newString = "Command."+newString+"Command";
        Class<?> aClass = null;
        try {
            aClass = Class.forName(newString);
            if(Command.class.isAssignableFrom(aClass)){
//                MultiWriteHandler.setClient("����ִ��"+"\n");
                try {
//        �������Ч�������ô���������Ǵ��������Ҵ������
                    Command command = (Command) aClass.getDeclaredConstructor().newInstance();
                    command.setArgs(argList);
                    return command;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            MultiWriteHandler.setClient("������������");
        }
        return null;
//        ע�ⷵ�ص�ָ�������ܲ����ڣ�Ҳ���Ǵ����ָ���ʱ��
    }
}
