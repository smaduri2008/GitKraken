import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestCallable {

    public static void main(String[] args) {
		long startTime = System.nanoTime();


        ExecutorService executor = Executors.newFixedThreadPool(1000);

        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {

            Callable<Integer> task = () -> {
                int num = 0;
                for (int j = 0; j < 1_000_000; j++) {
                    num += 1;
                }
                return num;
            };

            futures.add(executor.submit(task));
        }

        int total = 0;

        for (Future<Integer> f : futures) {
            try {
                total += f.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        executor.shutdown();

        System.out.println("The total is " + total);



		long endTime = System.nanoTime();

        double duration = (endTime - startTime) / 1_000_000_000.0;

        System.out.println("The total is " + total);
        System.out.println("Duration with ExecutorService/Callable: " + duration + " seconds");

        startTime = System.nanoTime();
        int timer = 0;
        for (int i = 1; i <= 1_000_000_000; i++) {

            timer += 1;

        }
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1_000_000_000.0;

        System.out.println("Duration single-thread: " + duration + " seconds");
        System.out.println("Timer total: " + timer);

    }
}
