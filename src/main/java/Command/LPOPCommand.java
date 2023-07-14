package Command;

import HashMapControl.SHHashMap;
import HashMapControl.SLHashMap;
import Io.MultiWriteHandler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class LPOPCommand implements Command{
    private List<String> setArgs;
    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }

    public LPOPCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }

    public LPOPCommand() {
    }

    @Override
    public void execute() {
        if(setArgs.size()<1) {
            MultiWriteHandler.setClient("������Ҫһ������");
            return;
        }
        System.out.println("��ʱ���е���lpop����");
        HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
        String key = setArgs.get(0);
        LinkedList<String> linkedList = hml.get(key);
        if(linkedList==null||linkedList.size()==0){
            System.out.println("key������߸ü���Ϊ��");
            System.out.println("0��ʾɾ��ʧ��");
        }else{
            String removing = linkedList.removeFirst();
              hml.put(key,linkedList);
              SLHashMap.setHml(hml);
              MultiWriteHandler.setClient(removing);
              System.out.println("1��ʾɾ���ɹ�");
        }
    }
}