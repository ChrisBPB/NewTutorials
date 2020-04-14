package tutorial;

import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GroundItem;
import org.powerbot.script.rt4.Npc;

import java.util.ArrayList;
import java.util.concurrent.Callable;


public class Loot extends Task {

    int cowhide = 1739;
    Tile cow_tile = Tile.NIL;
    ArrayList<Tile> cowLootTile = new ArrayList<Tile>();

    public Loot(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        if(ctx.players.local().interacting().valid() &&
                !ctx.players.local().interacting().tile().equals(cow_tile) &&
                !ctx.players.local().interacting().inMotion() &&
                ctx.players.local().speed()==0
        ){
            cow_tile = ctx.players.local().interacting().tile();

            int length = 2; //length of a cow

            Npc cow = (Npc) ctx.players.local().interacting();
            Tile newTile = new Tile(cow_tile.x()-1, cow_tile.y()-1, cow_tile.floor());
            cowLootTile.add(newTile);

            System.out.println("We just added tile: " + newTile + " The cows tile was " + cow.tile());

        }

        boolean lootExists = false;
        for(Tile t : cowLootTile){
            if(!ctx.groundItems.select().at(t).id(cowhide).isEmpty()){
                lootExists = true;
            }
        }

        return cowLootTile!=null && lootExists &&
                !ctx.players.local().interacting().valid() &&
                !ctx.inventory.isFull();
    }

    @Override
    public void execute() {
        System.out.println("Looter");
        ArrayList<Tile> toRemove = new ArrayList<Tile>();

        for(Tile t : cowLootTile){
            if(!ctx.groundItems.select().at(t).id(cowhide).isEmpty()){

                GroundItem hide = ctx.groundItems.select().at(t).id(cowhide).poll();
                hide.interact("Take", hide.name());

                Callable<Boolean> booleanCallable = new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return !hide.valid();
                    }
                };
                Condition.wait(booleanCallable, 300, 10);

                toRemove.add(t);

            }
        }
        //System.out.println("We removed " + toRemove.size() + " tiles from our list");
        cowLootTile.removeAll(toRemove);
    }
}
