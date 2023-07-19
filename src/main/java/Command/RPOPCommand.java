package Command;

import HashMapControl.SHHashMap;
import HashMapControl.SLHashMap;
import Io.MultiWriteHandler;
import Protocolutils.Protocol;

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
        Protocol protocol = new Protocol();
        String s ;
        System.out.println("��ʱ���е���rpop����");
        if(setArgs.size()<1) {
            s = protocol.encodeServer("", "401");
        }else {
            HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
            String key = setArgs.get(0);
            LinkedList<String> linkedList = hml.get(key);
            if(linkedList==null||linkedList.size()==0){
                s = protocol.encodeServer("key������߸ü���Ϊ��", "404");
            }else{
                String removing = linkedList.removeLast();
                hml.put(key,linkedList);
                SLHashMap.setHml(hml);
                s = protocol.encodeServer(removing, "200");
            }
        }
        MultiWriteHandler.setClient(s);
    }
}
