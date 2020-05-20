package IO.AIO;

public class TimeClient {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8010;
        new Thread(new AsyncTimeClientHandler(host, port), "AIO-AsyncTimeClientHandler-001").start();
    }
}
