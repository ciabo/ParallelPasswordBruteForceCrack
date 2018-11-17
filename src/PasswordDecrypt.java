import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class PasswordDecrypt {

    public static void main(String[] args) throws IOException {
        int count = 0;
        long start = System.currentTimeMillis(); // for sequential program is better to use nanoTime than currentTimeMills()(that is wall-clock time)
        String fileName="./PswDb/db100.txt";
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path);
        ArrayList<String> psws = new ArrayList<>();
        ArrayList<String> hashes = new ArrayList<>();
        while(scanner.hasNextLine()){
            //process each line
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            psws.add(parts[0]);
            hashes.add(parts[1]);
        }
        scanner.close();
        int size=psws.size();
        System.out.println("Num psw: " + size);
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Boolean found=false;
        for(int i=0;i<size;i++){
            found=false;
            List<Callable<Object>> todo = new ArrayList<Callable<Object>>();
            for(int j=1940;j<=2010;j=j+20){
                DecryptTask task=new DecryptTask(j,j+20,hashes.get(i),found);
                todo.add(Executors.callable(task));
            }
            try {
                List<Future<Object>> futures = executor.invokeAll(todo);
            }catch (Exception e){

            }
        }
        executor.shutdown();
        long finish = System.currentTimeMillis();
        long timeelaps = finish-start;
        System.out.println();
        System.out.println("Psw decrypted: " + size + " Time elapsed: " + timeelaps +"ms " + timeelaps/1000 +"s");
    }

}

