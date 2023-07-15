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
        System.out.println("此时运行的是expire命令");
        if(setArgs.size()<=1){
            MultiWriteHandler.setClient("至少需要两个参数");
            return;
        }
        delayHash delayHash = new delayHash();
        // 设置键的过期时间
        String key = setArgs.get(0);
        String expire = setArgs.get(1);
        delayHash.setKeyExpiration(key, Long.parseLong(expire));
    }
}
