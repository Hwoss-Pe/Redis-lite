package Command;

import java.util.HashMap;
import java.util.List;

public interface Command {
    void setArgs(List<String> list);
//    ��������Ĳ���
    void execute();
//    ע�ⷽ���ķ���ֵ
}
