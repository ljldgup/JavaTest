package language;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TryTest {
    public static void main(String[] args) throws Exception {
        //try 不带catch不会捕获异常，异常仍然会抛给上层，可以作为关闭资源使用
        try(FileInputStream is = new FileInputStream(new File("123"))){
            throw new Exception("测试能否抓住异常");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
