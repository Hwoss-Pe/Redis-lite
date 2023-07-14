package Command;

import HashMapControl.SHHashMap;
import HashMapControl.SLHashMap;
import Io.MultiWriteHandler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class RPOPCommand implements Command{
    private List<String> setArgs;
    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }

    public RPOPCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }

    public RPOPCommand() {
    }

    @Override
    public void execute() {
        if(setArgs.size()<1) {
            MultiWriteHandler.setClient("������Ҫһ������");
            return;
        }
        System.out.println("��ʱ���е���rpop����");
        HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
        String key = setArgs.get(0);
        LinkedList<String> linkedList = hml.get(key);
        if(linkedList==null||linkedList.size()==0){
            System.out.println("key������߸ü���Ϊ��");
            System.out.println("0��ʾɾ��ʧ��");
        }else{
            String removing = linkedList.removeLast();
            hml.put(key,linkedList);
            SLHashMap.setHml(hml);
            MultiWriteHandler.setClient(removing);
            System.out.println("1��ʾɾ���ɹ�");
        }
    }
}
