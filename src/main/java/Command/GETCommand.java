package Command;
import Io.MultiWriteHandler;
import HashMapControl.SSHashMap;

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
        if(setArgs.size()<1){
            MultiWriteHandler.setClient("������Ҫһ������");
            return ;
        }
        String key = setArgs.get(0);
        System.out.println("��ʱ���е���get����");

        HashMap<String, String> hm = SSHashMap.getSSHashMap();
        if(hm.containsKey(key)){
            String value = hm.get(key);
            MultiWriteHandler.setClient(value+"\n");
        }else{
            MultiWriteHandler.setClient("�Ҳ�����ǰ��key");
        }
    }

}
