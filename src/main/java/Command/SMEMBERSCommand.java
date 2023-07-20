package Command;

import HashMapControl.HashsetMap;
import Io.MultiWriteHandler;
import Protocolutils.Protocol;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SMEMBERSCommand implements Command{
    private List<String> setArgs;

    public SMEMBERSCommand() {
    }

    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }
    public SMEMBERSCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }
    @Override
    public void execute() {
        Protocol protocol = new Protocol();
        String s ;
        System.out.println("此时运行的是smembers命令");
        if(setArgs.size()==0){
            s = protocol.encodeServer("", "401");
        }else {
            HashMap<String, HashSet<String>> hms = HashsetMap.getSetMap();
            String key = setArgs.get(0);
            if(hms.containsKey(key)){
                String result = "";
                HashSet<String> hs = hms.get(key);
                for (String str : hs) {
                    result = result+" "+str;
                }
                s = protocol.encodeServer(result, "200");
            }else{
                s = protocol.encodeServer("", "501");
            }
        }
        MultiWriteHandler.setClient(s);
    }
}
