package Command;
        import Io.MultiWriteHandler;
        import HashMapControl.SSHashMap;
        import Protocolutils.Protocol;

        import java.util.HashMap;
        import java.util.List;
public class DELCommand implements Command {
    //    ɾ���ı���������key��value����null
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
        System.out.println("��ʱ���е���del����");
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
