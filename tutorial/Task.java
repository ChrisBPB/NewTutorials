package tutorial;

import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

public abstract class Task extends ClientAccessor {

    public Task(ClientContext ctx) {
        super(ctx);
    }

    public abstract boolean activate();
    public abstract void execute();

}