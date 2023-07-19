package Command;
import HashMapControl.SLHashMap;
import Io.MultiWriteHandler;
import HashMapControl.SSHashMap;
import Protocolutils.Protocol;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
public class LDELCommand implements Command {
    //    删除的本质上设置key的value等于null
    private List<String> setArgs;
    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }

    public LDELCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }

    public LDELCommand() {
    }

    @Override
    public void execute() {
        System.out.println("此时运行的是ldel命令");
        Protocol protocol = new Protocol();
        String s ;
        if(setArgs.size()<1){
            s = protocol.encodeServer("", "401");
        }else {
            String key = setArgs.get(0);
            HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
            if(hml.containsKey(key)){
                LinkedList<String> linkedList = hml.get(key);
                if(linkedList!=null){
                    linkedList.clear();
                    s = protocol.encodeServer("", "200");
                }else{
                    s = protocol.encodeServer("", "404");
                }
            }else{
                s = protocol.encodeServer("", "501");
            }
            SLHashMap.setHml(hml);
            MultiWriteHandler.setClient(s);
        }



    }
}
