package Command;
        import Io.MultiWriteHandler;
        import HashMapControl.SSHashMap;
        import java.util.HashMap;
        import java.util.List;
public class delCommand implements Command {
    //    ɾ���ı���������key��value����null
    private List<String> setArgs;
    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }

    public delCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }

    public delCommand() {
    }

    @Override
    public void execute() {
        if(setArgs.size()<1){
            MultiWriteHandler.setClient("������Ҫ��������");
        }
        String key = setArgs.get(0);
        System.out.println("��ʱ���е���del����");
        HashMap<String, String> hm = SSHashMap.getSSHashMap();
        hm.put(key, null);
        MultiWriteHandler.setClient("ɾ����key��value�ɹ�");
        SSHashMap.setHm(hm);
    }
}
