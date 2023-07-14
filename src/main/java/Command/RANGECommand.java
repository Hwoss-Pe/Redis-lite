package Command;
import HashMapControl.SLHashMap;
import Io.MultiWriteHandler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class RANGECommand implements Command{
    private List<String> setArgs;
    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }

    public RANGECommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }

    public RANGECommand() {
    }

    @Override
    public void execute() {
        System.out.println("��ʱ���е���range����");
        if(setArgs.size()<=2) {
            MultiWriteHandler.setClient("������Ҫ��������");
            return ;
        }
        HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
        String key = setArgs.get(0);
        int start = Integer.parseInt(setArgs.get(1));
        int end = Integer.parseInt(setArgs.get(2));
        int length = end-start ;
        LinkedList<String> linkedList = hml.get(key);
        if(linkedList==null) {
            MultiWriteHandler.setClient("û������");
        }
        if (start<0||end<0||length > linkedList.size()||length<0||end > linkedList.size()-1) {
            MultiWriteHandler.setClient("�Ƿ����룬���������ڻ���Խ��");//���������Ƿ�
        } else {
            for (int i = start; i <= end; i++) {
                MultiWriteHandler.setClient(linkedList.get(i)+" ");
                //ȥ��ȡ���ϵĶ�Ӧֵ��Ȼ�����Ҫ����Ĵ����¼����н��з���
            }
        }
    }
}

