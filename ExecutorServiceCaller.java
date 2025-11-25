import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceCaller{

    public static void main(String[] args) {

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
    }
}
