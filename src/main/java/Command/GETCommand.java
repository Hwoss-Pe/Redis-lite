package Command;
import Io.MultiWriteHandler;
import HashMapControl.SSHashMap;
import Protocolutils.Protocol;

import java.util.HashMap;
import java.util.List;

public class GETCommand implements Command {
    private List<String> setArgs;
    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }

    public GETCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }

    public GETCommand() {
    }

    @Override
    public void execute() {
        System.out.println("此时运行的是get命令");
        Protocol protocol = new Protocol();
        String s ;
        if(setArgs.size()<1){
           s =  protocol.encodeServer("", "401");
        }else{
            String key = setArgs.get(0);
            HashMap<String, String> hm = SSHashMap.getSSHashMap();
            if(hm.containsKey(key)){
                String value = hm.get(key);
                s=  protocol.encodeServer(value, "200");
            }else{
                s=  protocol.encodeServer("", "501");
            }
        }
        MultiWriteHandler.setClient(s);
    }
}
