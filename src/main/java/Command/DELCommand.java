package Command;
        import Io.MultiWriteHandler;
        import HashMapControl.SSHashMap;
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
        if(setArgs.size()<1){
            MultiWriteHandler.setClient("������Ҫһ������");
            return;
        }
        String key = setArgs.get(0);
        HashMap<String, String> hm = SSHashMap.getSSHashMap();
        hm.put(key, null);
        MultiWriteHandler.setClient("ɾ����key��value�ɹ�");
        SSHashMap.setHm(hm);
    }
}
