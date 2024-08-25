package org.example.database.service;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreader {
    public final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void runUserTask(Runnable task) {
        executorService.submit(task);
    }

    public void shutdown() {
        executorService.shutdown();
    }


//    public void generateAndSaveUserHistoryAsync(LocalDateTime date) {
//        runUserTask(() -> generateAndSaveUserHistory(date));
//    }

}
