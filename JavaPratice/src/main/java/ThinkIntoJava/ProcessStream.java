package ThinkIntoJava;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ProcessStream {
 	//Reader Writer ��Ӧ�ַ��� stream��Ӧ�ֽ�
	public static void enter(Process ps) {
		BufferedWriter in = new BufferedWriter(new OutputStreamWriter(ps.getOutputStream()));
		//netstat����� �ᵼ������
		String commands = "dir\n ps\n ipconfig\n 124\n netstat\n";
		for(String command : commands.split(" ")) {
			try {
				in.write(command);
				//BufferedWriter, ������д����Ҫflush
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
		
		//����д���쳣ʱ������ѭ��
		try {
			while(true) {
				//����cmd���һ��û�н�β������������ֱ��ʹ��readLine���޷������������������Բ����������̶�д 
				rst = out.readLine();
				System.out.println(rst);
				//ÿ��ӡһ�ν�����Ȩ�����������߳�
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		} 
	}
	public static void main(String[] args) throws IOException {
		//ʹ��ProcessBuilder��start����
		Process process = new ProcessBuilder("cmd.exe").start();
		new Thread(() -> enter(process)).start();
		new Thread(() -> out(process)).start();
		
	}

}
