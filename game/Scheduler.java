package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Scheduler {
    private final List<Task> tasks = new ArrayList<>();

    public void add(Coroutine co) {
        tasks.add(new Task(co));
    }

    public void tick() {
        for (Iterator<Task> it = tasks.iterator(); it.hasNext(); ) {
            Task t = it.next();
            if (--t.wait > 0) continue;
            int r = t.co.resume();
            if (r < 0) it.remove();
            else t.wait = Math.max(1, r);
        }
    }
}