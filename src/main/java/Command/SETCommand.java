package Command;
import Io.MultiWriteHandler;
import HashMapControl.SSHashMap;

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
        if(setArgs.size()<=1){
            MultiWriteHandler.setClient("������Ҫ��������");
            return;
        }
        String key = setArgs.get(0);
        String value = setArgs.get(1);
        System.out.println("��ʱ���е���set����");
        HashMap<String, String> hm = SSHashMap.getSSHashMap();
        hm.put(key, value);
        SSHashMap.setHm(hm);
        MultiWriteHandler.setClient("1");
    }

}
