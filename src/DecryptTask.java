
import org.apache.commons.codec.digest.Crypt;

import java.lang.Thread;

public class DecryptTask implements Runnable {
    private int startYear;
    private int endYear;
    private String hash;
    Boolean found;

    public DecryptTask(int startYear, int endYear, String hash, Boolean found) {
        this.startYear = startYear;
        this.endYear = endYear;
        this.hash = hash;
        this.found = found;
    }

    public void run() {
        int d = 1;
        int m = 1;
        int y = this.startYear;
        while (!this.found && y <= endYear) {
            while (!this.found && m <= 12) {
                while (!this.found && d <= 31) {
                    String dd = String.format("%02d", d);
                    String mm = String.format("%02d", m);
                    String yyyy = Integer.toString(y);
                    String psw = yyyy + mm + dd;
                    String hashTest = Crypt.crypt(psw, "parallel");
                    if (this.hash.equals(hashTest)) {
                        this.found = true;
                        System.out.println("Thread: " + Thread.currentThread().getId());
                        System.out.println("Psw generated: " + psw + " finds, crypt: " + hashTest);
                    }
                    d++;
                }
                d = 1;
                m++;
            }
            m = 1;
            y++;
        }
    }
}
