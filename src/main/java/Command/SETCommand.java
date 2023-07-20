package Command;
import Io.MultiWriteHandler;
import HashMapControl.SSHashMap;
import Protocolutils.Protocol;

import java.util.HashMap;
import java.util.List;

public class SETCommand implements Command {
    private List<String> setArgs;
    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }

    public SETCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }

    public SETCommand() {
    }

    @Override
    public void execute() {
        Protocol protocol = new Protocol();
        String s;
        if(setArgs.size()<=1){
            s = protocol.encodeServer("", "401");
        }else{
            String key = setArgs.get(0);
            String value = setArgs.get(1);
            System.out.println("此时运行的是set命令");
            HashMap<String, String> hm = SSHashMap.getSSHashMap();
            hm.put(key, value);
            SSHashMap.setHm(hm);
            s = protocol.encodeServer("", "200");
        }
        MultiWriteHandler.setClient(s);
    }

}
