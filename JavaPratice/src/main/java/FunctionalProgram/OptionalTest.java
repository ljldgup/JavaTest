package FunctionalProgram;

import java.util.Arrays;
import java.util.Optional;

public class OptionalTest {
	
	public static void main(String[] args) {
	    String str = null;
	    Optional<String> opt = Optional.ofNullable(str);
	    System.out.println(opt.orElse("null orElse"));
	    System.out.println(opt.isPresent() ? opt.get() : "null ?:");
	    System.out.println("------------------------------------------------------------------------");
	    
	    //map 返回的任然是optional， flatMap 返回的未经过包装。
	    str = "An original standalone origin story of the iconic villain not seen before on the big screen, "
	    		+ "it's a gritty character study of Arthur Fleck, a man disregarded by society, "
	    		+ "and a broader cautionary tale.";
	    
	    opt = Optional.ofNullable(str);
	    Optional<Object> t = opt.map(s -> s.split("\\W")).flatMap(word -> Optional.ofNullable(word));
	    System.out.println(((String[])t.get())[1]);
	    System.out.println("------------------------------------------------------------------------");
	    
	    String[] strArray = {null, "clsdkf", null, "feig", null};
	    System.out.println( Arrays.asList(strArray).stream().findFirst().orElse("first is null") );
	    
	}
}
