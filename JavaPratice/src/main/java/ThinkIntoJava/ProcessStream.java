package ThinkIntoJava;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ProcessStream {
 	//Reader Writer 对应字符， stream对应字节
	public static void enter(Process ps) {
		BufferedWriter in = new BufferedWriter(new OutputStreamWriter(ps.getOutputStream()));
		//netstat命令本身 会导致阻塞
		String commands = "dir\n ps\n ipconfig\n 124\n netstat\n";
		for(String command : commands.split(" ")) {
			try {
				in.write(command);
				//BufferedWriter, 缓冲区写入需要flush
				in.flush();
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		
	}
	
	public static void out(Process ps) {
		String rst;
		BufferedReader out = new BufferedReader(new InputStreamReader(ps.getInputStream()));
		
		//该种写法异常时会跳出循环
		try {
			while(true) {
				//由于cmd最后一行没有结尾，单个进程中直接使用readLine，无法读到，会阻塞，所以采用两个进程读写 
				rst = out.readLine();
				System.out.println(rst);
				//每打印一次将运行权利交给输入线程
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		} 
	}
	public static void main(String[] args) throws IOException {
		//使用ProcessBuilder的start方法
		Process process = new ProcessBuilder("cmd.exe").start();
		new Thread(() -> enter(process)).start();
		new Thread(() -> out(process)).start();
		
	}

}
