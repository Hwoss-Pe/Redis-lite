package Command;
import HashMapControl.SHHashMap;
import Io.MultiWriteHandler;
import HashMapControl.SSHashMap;
import Protocolutils.Protocol;

import java.util.HashMap;
import java.util.List;

public class HGETCommand implements Command {
    private List<String> setArgs;
    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }

    public HGETCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }

    public HGETCommand() {
    }

    @Override
    public void execute() {
        Protocol protocol = new Protocol();
        String s ;
        String key1 = setArgs.get(0);
        String key2 = setArgs.get(1);
        System.out.println("此时运行的是hget命令");
        if(setArgs.size()<=1){
            s = protocol.encodeServer("", "401");

        }else {
            HashMap<String, HashMap<String, String>> hmh = SHHashMap.getSHHashMap();
            if (hmh.containsKey(key1)) {
                HashMap<String, String> hm = hmh.get(key1);
                String value = hm.get(key2);
                s = protocol.encodeServer(value, "200");
            } else {
                s = protocol.encodeServer("", "501");
            }
        }
        MultiWriteHandler.setClient(s);
    }

}
