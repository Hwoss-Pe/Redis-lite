package Command;

import HashMapControl.SLHashMap;
import Io.MultiWriteHandler;
import Protocolutils.Protocol;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class LENCommand implements Command {
    private List<String> setArgs;

    public LENCommand() {
    }
    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }

    @Override
    public void execute() {
        Protocol protocol = new Protocol();
        String s ;
        System.out.println("此时运行的是len命令");
        if (setArgs.size()==0){
            s = protocol.encodeServer("", "401");
        }else {
            String key = setArgs.get(0);
            int size;
            HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
            if(hml.containsKey(key)){
                LinkedList<String> linkedList = hml.get(key);
                if(linkedList==null){
                    size = 0;
                }
                else{
                    size = linkedList.size();
                }
                s = protocol.encodeServer(size+"", "200");
            }else{
                s = protocol.encodeServer("", "501");
            }
        }
        MultiWriteHandler.setClient(s);
    }
}
