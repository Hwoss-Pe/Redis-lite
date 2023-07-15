package Command;

import Io.MultiWriteHandler;
import Time.delayHash;

import java.util.List;

public class DDLCommand implements Command{
    private List<String> setArgs;

    public DDLCommand() {
    }

    public DDLCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }

    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }

    @Override
    public void execute() {
        System.out.println("此时运行的是ddl命令");
        if(setArgs.size()==0){
            MultiWriteHandler.setClient("至少需要一个参数");
            return ;
        }
        String key = setArgs.get(0);
        delayHash DelayHash = new delayHash();
        long remainingTime = DelayHash.getKeyTtl(key);
        MultiWriteHandler.setClient(remainingTime+"");
    }
}
