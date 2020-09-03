package IO.NIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class  FileTest{
	String dir;
	public static void main(String[] args) throws IOException{
		

		FileTest test = new FileTest("H:\\BaiduNetDisk\\WinKawaks\\skin.bmp");

		//stream�ǰ����ֽ�ȥ����ģ�������������ˮ��һ����һ����һ������channel�ǰ������ݿ�������ġ�
		//channel�������첽�����������ʹ�ã��ڶ�ȡ���ݵĹ����л������Ӧ��ʱ�¼�����streamֻ�������������ʹ�á�
		//stream�ǰ����ֽ�ȥ����ģ�������������ˮ��һ����һ����һ������channel�ǰ������ݿ��������,������д���buffer��
		test.byStream();//InputStream/OutputStream����byte[]
		test.byChannel(); //Channel����buffer
	}
	
	public FileTest(String directory ) {
		// TODO Auto-generated constructor stub
		dir = directory;
	}
	
	void byChannel()  throws IOException {
		File file1= new File(dir); 
		FileInputStream fins = new FileInputStream(file1);
 
		File file2 = new File(System.getProperty("user.dir") + File.separator + file1.getName());
		FileOutputStream fos = new FileOutputStream(file2);
		
		FileChannel fc = fins.getChannel();
		FileChannel foc = fos.getChannel();
 
		ByteBuffer buffer = ByteBuffer.allocate(1024*1024);
		int temp = 0;
		
		long start = System.currentTimeMillis();
		
		while ((temp = fc.read(buffer) )!=-1) {
			buffer.flip();
			foc.write(buffer);
			buffer.clear();
		}
 
		System.out.println("byChannel:"+(System.currentTimeMillis() - start));
		fc.close();
		fins.close();
		foc.close();
		fos.close();
	}
	
	void byStream() throws IOException{
		
		File file1= new File(dir); 
		FileInputStream fins = new FileInputStream(file1);
 
		File file2 = new File(System.getProperty("user.dir") + File.separator + file1.getName());
		FileOutputStream fos = new FileOutputStream(file2);
 
		int temp = 0;
		
		long start = System.currentTimeMillis();
		
		byte[] buffer = new byte[1024*1024];
		while ((temp = fins.read(buffer) )!=-1) {
			fos.write(buffer,0,temp);
		}
 
		System.out.println("byStream:"+(System.currentTimeMillis() - start));
		fins.close();
		fos.close();
	}
}
