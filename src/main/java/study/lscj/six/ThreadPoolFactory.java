package study.lscj.six;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolFactory{
    
    public static ExecutorService newFixedThreadPool(int nThreads){
        return new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>()){
                    @Override
                    protected void afterExecute(Runnable r, Throwable t) {
                        if (t == null && r instanceof Future<?>) {
                            try {
                                Future<?> future = (Future<?>) r;
                                if (future.isDone())
                                    future.get();
                            } catch (CancellationException ce) {
                                t = ce;
                            } catch (ExecutionException ee) {
                                t = ee.getCause();
                            } catch (InterruptedException ie) {
                                Thread.currentThread().interrupt(); // ignore/reset
                            }
                        }
                        if (t != null)
                            t.printStackTrace();
                    }
        };
    }
}
