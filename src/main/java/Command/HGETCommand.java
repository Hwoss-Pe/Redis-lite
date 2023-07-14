package Command;
import HashMapControl.SHHashMap;
import Io.MultiWriteHandler;
import HashMapControl.SSHashMap;

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
        if(setArgs.size()<=1){
            MultiWriteHandler.setClient("��Ҫ��������");
            return ;
        }
        String key1 = setArgs.get(0);
        String key2 = setArgs.get(1);
        System.out.println("��ʱ���е���hget����");

        HashMap<String, HashMap<String, String>> hmh = SHHashMap.getSHHashMap();
        if(hmh.containsKey(key1)){
            HashMap<String, String> hm = hmh.get(key1);
            String value = hm.get(key2);
            MultiWriteHandler.setClient(value+"\n");
        }else{
            MultiWriteHandler.setClient("�Ҳ�����ǰ��key");
        }
    }

}