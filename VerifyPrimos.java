import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class VerifyPrimos {

    public static void main(String[] args) {
        try {
            List<Integer> numbers = readNumbersFromFile("entrada.txt");

            // Verificação com 1 thread
            long startTime = System.nanoTime();
            List<Integer> primos1Thread = findPrimosSequential(numbers);
            long endTime = System.nanoTime();
            long duration1Thread = (endTime - startTime) / 1000000; // Convertendo para milissegundos
            writePrimosToFile(primos1Thread, "output_1_thread.txt", duration1Thread);

            // Verificação com 5 threads
            startTime = System.nanoTime();
            List<Integer> primos5Threads = findPrimosParallel(numbers, 5);
            endTime = System.nanoTime();
            long duration5Threads = (endTime - startTime) / 1000000; // Convertendo para milissegundos
            writePrimosToFile(primos5Threads, "output_5_threads.txt", duration5Threads);

            // Verificação com 10 threads
            startTime = System.nanoTime();
            List<Integer> primos10Threads = findPrimosParallel(numbers, 10);
            endTime = System.nanoTime();
            long duration10Threads = (endTime - startTime) / 1000000; // Convertendo para milissegundos
            writePrimosToFile(primos10Threads, "output_10_threads.txt", duration10Threads);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Integer> readNumbersFromFile(String inputFile) throws IOException {
        List<Integer> numbers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                numbers.add(Integer.parseInt(line.trim()));
            }
        }
        return numbers;
    }

    private static List<Integer> findPrimosSequential(List<Integer> numbers) {
        List<Integer> primos = new ArrayList<>();
        for (int num : numbers) {
            if (isPrimos(num)) {
                primos.add(num);
            }
        }
        return primos;
    }

    private static List<Integer> findPrimosParallel(List<Integer> numbers, int numThreads) {
        List<Integer> primos = new ArrayList<>();
        int chunkSize = numbers.size() / numThreads;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            final int start = i * chunkSize;
            final int end = (i == numThreads - 1) ? numbers.size() : (i + 1) * chunkSize;
            Thread thread = new Thread(() -> {
                for (int j = start; j < end; j++) {
                    if (isPrimos(numbers.get(j))) {
                        synchronized (primos) {
                            primos.add(numbers.get(j));
                        }
                    }
                }
            });
            thread.start();
            threads.add(thread);
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return primos;
    }

    static boolean isPrimos(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    private static void writePrimosToFile(List<Integer> primos, String outputFile, long duration) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            writer.println("TEMPO DE FUNCIONAMENTO: " + duration + " milissegundos");
            for (int prime : primos) {
                writer.println(prime);
            }
        }
    }
}
