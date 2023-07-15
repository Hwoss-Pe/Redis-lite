package Command;

import Io.MultiWriteHandler;
import Time.delayHash;

import java.util.List;

public class EXPIRECommand implements Command{
    private List<String> setArgs;

    public EXPIRECommand() {
    }

    public EXPIRECommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }

    @Override
    public void setArgs(List<String> list ) {
        this.setArgs = list;
    }

    @Override
    public void execute() {
        System.out.println("��ʱ���е���expire����");
        if(setArgs.size()<=1){
            MultiWriteHandler.setClient("������Ҫ��������");
            return;
        }
        delayHash delayHash = new delayHash();
        // ���ü��Ĺ���ʱ��
        String key = setArgs.get(0);
        String expire = setArgs.get(1);
        delayHash.setKeyExpiration(key, Long.parseLong(expire));
    }
}
