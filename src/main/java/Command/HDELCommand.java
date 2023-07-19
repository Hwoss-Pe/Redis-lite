package Command;
import HashMapControl.SHHashMap;
import Io.MultiWriteHandler;
import HashMapControl.SSHashMap;
import Protocolutils.Protocol;

import java.util.HashMap;
import java.util.List;
public class HDELCommand implements Command {
    //    ɾ���ı���������key��value����null
    private List<String> setArgs;
    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }

    public HDELCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }

    public HDELCommand() {
    }

    @Override
    public void execute() {
        System.out.println("��ʱ���е���del����");
        HashMap<String, HashMap<String, String>> hmh = SHHashMap.getSHHashMap();
        Protocol protocol = new Protocol();
        String s ;
        if(setArgs.size()<1){
            s =  protocol.encodeServer("", "401");
            return;
        }
         else if(setArgs.size()==1){
            String key = setArgs.get(0);
//            ɾ������������ϣ
            HashMap<String, String> hm = hmh.get(key);
            if(hm==null){
                s =  protocol.encodeServer("", "501");
            }else {
                hm.clear();
                hmh.put(key,hm);
            }

        }
         else if(setArgs.size()==2){
            String key1 = setArgs.get(0);
            String key2 = setArgs.get(1);
//             ɾ�����ǹ�ϣ�����value
            HashMap<String, String> hm = hmh.get(key1);
            hm.put(key2,null);
        }
        s =  protocol.encodeServer("", "200");
         MultiWriteHandler.setClient(s);
        SHHashMap.setHmh(hmh);
    }
}
