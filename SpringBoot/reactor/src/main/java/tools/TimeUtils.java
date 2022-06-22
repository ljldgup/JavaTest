package tools;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeUtils {
    public static int dateDelta(LocalDateTime date) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        System.out.println(date);
        return Duration.between(date, now).getNano();
    }

    public static void printTime(String title) {
        System.out.println(title + ":" + LocalDateTime.now());
    }

    public static void main(String[] args) throws InterruptedException {
        LocalDateTime past = LocalDateTime.now();
        Thread.sleep(1000);
        System.out.println(dateDelta(past));
    }
}
