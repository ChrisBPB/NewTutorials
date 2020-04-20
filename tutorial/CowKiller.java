package tutorial;


import org.powerbot.script.*;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Npc;

import java.awt.*;
import java.util.ArrayList;


@Script.Manifest(name="Hello World", description="Tutorial")

public class CowKiller extends PollingScript<ClientContext> implements PaintListener
{

    ArrayList<Task> tasks = new ArrayList<Task>();

    @Override
    public void start() {
        System.out.println("Hello World");
        Fight fight = new Fight(ctx);
        Loot loot = new Loot(ctx);
        Walk walk = new Walk(ctx);
        Bank bank = new Bank(ctx);
        tasks.add(fight);
        tasks.add(loot);
        tasks.add(walk);
        tasks.add(bank);
    }

    @Override
    public void poll() {
       for(Task t : tasks){
            if(t.activate()){
                t.execute();
            }
        }
    }

    @Override
    public void repaint(Graphics graphics) {
        graphics.setColor(new Color(0, 0, 0, 180));
        graphics.fillRect(0, 0, 150, 100);

        graphics.setColor(new Color(255,255,255));
        graphics.drawRect(0, 0, 150, 100);

        graphics.drawString("Cow Killer Tutorial", 20, 20);
    }
}