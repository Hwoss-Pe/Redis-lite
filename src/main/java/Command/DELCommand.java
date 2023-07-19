package Command;
        import Io.MultiWriteHandler;
        import HashMapControl.SSHashMap;
        import Protocolutils.Protocol;

        import java.util.HashMap;
        import java.util.List;
public class DELCommand implements Command {
    //    删除的本质上设置key的value等于null
    private List<String> setArgs;
    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }

    public DELCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }

    public DELCommand() {
    }

    @Override
    public void execute() {
        System.out.println("此时运行的是del命令");
        String s ;
        Protocol protocol = new Protocol();
        if(setArgs.size()<1){
             s = protocol.encodeServer("", "401");
        }else {
            String key = setArgs.get(0);
            HashMap<String, String> hm = SSHashMap.getSSHashMap();
            if(hm.containsKey(key)){
                hm.put(key, null);
                SSHashMap.setHm(hm);
                s = protocol.encodeServer("", "200");
            }else {
                s = protocol.encodeServer("", "401");
            }
        }
        MultiWriteHandler.setClient(s);
    }
}
