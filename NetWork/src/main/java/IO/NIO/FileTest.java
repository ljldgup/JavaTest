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

		//stream是按照字节去处理的，看起来就像是水流一样，一个接一个。而channel是按照数据块来处理的。
		//channel可以在异步非阻塞变成中使用，在读取数据的过程中会产生相应的时事件，而stream只能在阻塞编程中使用。
		//stream是按照字节去处理的，看起来就像是水流一样，一个接一个。而channel是按照数据块来处理的,将数据写入的buffer。
		test.byStream();//InputStream/OutputStream操作byte[]
		test.byChannel(); //Channel操作buffer
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
