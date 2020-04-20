package tutorial;

import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import java.util.concurrent.Callable;

public class Walk extends Task {

    public static final Tile[] path = {new Tile(3253, 3267, 0), new Tile(3250, 3266, 0), new Tile(3250, 3263, 0), new Tile(3250, 3260, 0), new Tile(3250, 3257, 0), new Tile(3250, 3254, 0), new Tile(3252, 3251, 0), new Tile(3255, 3249, 0), new Tile(3257, 3246, 0), new Tile(3258, 3243, 0), new Tile(3259, 3240, 0), new Tile(3259, 3237, 0), new Tile(3259, 3234, 0), new Tile(3259, 3231, 0), new Tile(3258, 3228, 0), new Tile(3255, 3226, 0), new Tile(3252, 3226, 0), new Tile(3249, 3226, 0), new Tile(3246, 3226, 0), new Tile(3243, 3226, 0), new Tile(3240, 3226, 0), new Tile(3238, 3223, 0), new Tile(3235, 3222, 0), new Tile(3232, 3219, 0), new Tile(3229, 3218, 0), new Tile(3226, 3218, 0), new Tile(3223, 3218, 0), new Tile(3220, 3218, 0), new Tile(3217, 3218, 0), new Tile(3215, 3215, 0), new Tile(3215, 3212, 0), new Tile(3212, 3211, 0), new Tile(3209, 3211, 0), new Tile(3206, 3209, 0), new Tile(3205, 3209, 1), new Tile(3205, 3209, 2), new Tile(3205, 3212, 2), new Tile(3205, 3215, 2), new Tile(3206, 3218, 2), new Tile(3209, 3220, 2)};
    Area cowField = new Area(
            new Tile(3242, 3298, 0),
            new Tile(3246, 3279, 0),
            new Tile(3253, 3278, 0),
            new Tile(3253, 3255, 0),
            new Tile(3265, 3255, 0),
            new Tile(3265, 3296, 0)
    );

    Walker walker = new Walker(ctx);

    public Walk(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return (ctx.bank.nearest().tile().distanceTo(ctx.players.local())>4 && ctx.inventory.isFull()) ||
                (!ctx.inventory.isFull() && !cowField.contains(ctx.players.local()));
    }

    @Override
    public void execute() {
        if(ctx.movement.destination().equals(Tile.NIL) || ctx.movement.destination().distanceTo(ctx.players.local())<5){

            if(ctx.inventory.isFull()){
                if(path[0].distanceTo(ctx.players.local())>5 && cowField.contains(ctx.players.local())){

                    Tile currentTile = ctx.players.local().tile();

                    ctx.movement.step(path[0]);

                    Callable<Boolean> booleanCallable = new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return !ctx.players.local().tile().equals(currentTile);
                        }
                    };

                    Condition.wait(booleanCallable);
                }

                //inventory IS full
                Tile currentTile = ctx.players.local().tile();
                walker.walkPath(path);

                Callable<Boolean> booleanCallable = new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return !ctx.players.local().tile().equals(currentTile);
                    }
                };
                Condition.wait(booleanCallable);

            } else if(!cowField.contains(ctx.players.local())){
                //don't need inventory check here as we are using the ELSE so inventory CANT be full here anyway
                Tile currentTile = ctx.players.local().tile();
                walker.walkPathReverse(path);

                Callable<Boolean> booleanCallable = new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return !ctx.players.local().tile().equals(currentTile);
                    }
                };
                Condition.wait(booleanCallable);

            }

        }

    }
}
