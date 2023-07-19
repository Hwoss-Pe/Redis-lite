package Command;
import HashMapControl.SLHashMap;
import Io.MultiWriteHandler;
import Protocolutils.Protocol;

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
        Protocol protocol = new Protocol();
        String s ;
        if(setArgs.size()<=2) {
            s = protocol.encodeServer("", "401");
        }else {
            HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
            String key = setArgs.get(0);
            int start = Integer.parseInt(setArgs.get(1));
            int end = Integer.parseInt(setArgs.get(2));
            int length = end - start;
            LinkedList<String> linkedList = hml.get(key);
            if (linkedList == null) {
                s = protocol.encodeServer("û������", "404");
            }
            if (start < 0 || end < 0 || length > linkedList.size() || length < 0 || end > linkedList.size() - 1) {
                        s = protocol.encodeServer("�Ƿ����룬���������ڻ���Խ��", "404");
            } else {
                StringBuilder str = new StringBuilder();
                for (int i = start; i <= end; i++) {
                    str.append(linkedList.get(i)).append(" ");
                    //ȥ��ȡ���ϵĶ�Ӧֵ��Ȼ�����Ҫ����Ĵ����¼����н��з���
                }
                s = protocol.encodeServer(str.toString(), "200");
            }
        }
        MultiWriteHandler.setClient(s);
    }
}

