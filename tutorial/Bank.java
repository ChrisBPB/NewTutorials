package tutorial;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;

import java.util.concurrent.Callable;

public class Bank extends Task {
    public Bank(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.inventory.isFull() &&
                ctx.bank.nearest().tile().distanceTo(ctx.players.local())<5;
    }

    @Override
    public void execute() {

        if(ctx.bank.opened()){
            ctx.bank.depositInventory();
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !ctx.inventory.isFull();
                }
            }, 300, 10);
        } else {
            if(ctx.bank.inViewport()){

                ctx.bank.open();
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.bank.opened();
                    }
                }, 300, 10);

            } else {
                ctx.camera.turnTo(ctx.bank.nearest());
            }
        }

    }
}
