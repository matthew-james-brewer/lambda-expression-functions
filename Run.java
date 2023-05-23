import java.util.HashMap;
public class Run {
    public static void main(String[] args) {
        FunctionRunner<String> basic /*replace 'String' with return type and 'basic' with name for function*/ = new FunctionRunner<>(
                new Class<?>[]{/*list of argument types(such as*/Integer.class/*)*/},
                new String[]{/*list of names for arguments(such as*/"num"/*)*/},
                (values) -> {
                    //function using '([type])values.get([arg name])' to get arguments, such as
                    Integer num = (Integer)values.get("num");
                    return "Good job in the race! You got #"+num.toString()+" place! Bye!"; //replace with returned object
                }
        );
      //use [var name].exec([args]) to run it and return the result
      System.out.println(basic.exec(2));
	  System.out.println((new ImplementExample()).getFr().exec(10, "banana"));
    }
}
//implement it to create functions that need to be put in smaller chunks
class ImplementExample implements Function<String> {
	//add fields if you like
	private static final int max = 15;
	private static final int min = -10;
	//make it easy to run with a FunctionRunner inside
	private FunctionRunner<String> fr = new FunctionRunner<>(
		new Class<?>[]{Integer.class,String.class},
		new String[]{"number","text"},
		this);
	public FunctionRunner<String> getFr(){return fr;}
	//here is the main method:
	public String exec(HashMap<String, Object> hash) {
		return implement("Why did you say \""+(String)hash.get("text")+"\"?",(Integer)hash.get("number"));
	}
	//add as many functions as you want
	public static String implement(String s, Integer i) throws IndexOutOfBoundsException {
		if(i.intValue() > max || i.intValue() < min) {
			throw new IndexOutOfBoundsException("out of range");
		}
		return s+i.toString();
	}
}
