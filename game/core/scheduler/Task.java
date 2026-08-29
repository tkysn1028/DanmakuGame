package game.core.scheduler;

public class Task {
    public Coroutine co;
    public int wait = 0;
    public Task(Coroutine co) {
        this.co = co;
    }
    
}
