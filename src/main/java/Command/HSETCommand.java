package Command;
import HashMapControl.SHHashMap;
import Io.MultiWriteHandler;
import HashMapControl.SSHashMap;

import java.util.HashMap;
import java.util.List;

public class HSETCommand implements Command {
    private List<String> setArgs;
    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }

    public HSETCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }

    public HSETCommand() {
    }

    @Override
    public void execute() {
        if(setArgs.size()<=2){
            MultiWriteHandler.setClient("��Ҫ��������");
            return;
        }
        String key1 = setArgs.get(0);
        String key2 = setArgs.get(1);
        String value = setArgs.get(2);
        System.out.println("��ʱ���е���hset����");
        HashMap<String ,String> hm = new HashMap<>();
        hm.put(key2,value);
        HashMap<String, HashMap<String, String>> hmh = SHHashMap.getSHHashMap();
        hmh.put(key1,hm);
        SHHashMap.setHmh(hmh);
        MultiWriteHandler.setClient("1");
    }

}
