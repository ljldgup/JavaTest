package Reactive;

import io.reactivex.Observable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

;

public class MyPublisher implements Publisher<String> {
    public Set<Subscriber<? super String>> subscribers= new HashSet<>();

    public MyPublisher() throws IOException {
        /*
        ServerSocket server = new ServerSocket(80);
        System.out.println("start server");
        Socket socket=null;

        while(true){
            socket = server.accept ();
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //读取客户端发送来的消息
            br.lines().map(s -> s.split( " "))
                    .flatMap(Arrays::stream)
                    .forEach(str->{
                subscribers.stream().forEach(s -> s.onNext(str));
            });
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write("fuck");
            bw.flush();
        }
         */

    }



    @Override
    public void subscribe(Subscriber<? super String> s) {

        while (true) {
            s.onNext("i");
        }
    }

    public static void main(String[] args) throws IOException {
        Observable.fromPublisher( new MyPublisher()).map(s -> "get:" + s).subscribe(System.out::println);
    }
}
