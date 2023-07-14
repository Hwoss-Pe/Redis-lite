package Command;

import Io.MultiWriteHandler;

import java.util.List;

public class PINGCommand implements Command{

    public PINGCommand() {
    }

    @Override
    public void setArgs(List<String> list) {

    }

    @Override
    public void execute() {
        MultiWriteHandler.setClient("pong");
    }
}
