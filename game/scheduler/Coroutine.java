package game.scheduler;

import java.util.concurrent.SynchronousQueue;

public class Coroutine {
    private static final Object TOKEN = new Object();
    private final SynchronousQueue<Integer> out = new SynchronousQueue<>();
    private final SynchronousQueue<Object> in = new SynchronousQueue<>();
    private final Thread thread;
    private boolean started = false;
    private boolean done = false;

    interface Yielder {
        void pause(int frames) throws InterruptedException;
    }

    interface Body {
        void run(Yielder y) throws InterruptedException;
    }

    public Coroutine(Body body) {
        thread = new Thread(() -> {
            try {
                body.run((frames) -> {
                    out.put(frames);
                    in.take();
                });
            } catch (InterruptedException e) {
                return;                     
            }
            try {
                out.put(-1);                
            } catch (InterruptedException ignored) {

            }
        });
        thread.setDaemon(true);             
    }
    
    public int resume() {
        if (done) return -1;
        try {
            if (!started) {
                started = true;
                thread.start();
            } else {
                in.put(TOKEN);              
            }
            int r = out.take();             
            if (r < 0) done = true;
            return r;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            done = true;
            return -1;
        }
    }
}
