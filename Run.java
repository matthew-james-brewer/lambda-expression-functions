import java.util.HashMap;
public class Run {
    public static void main(String[] args) {
        Function<String> link = // replace 'String' with the return type
            (values) -> {
                    // function using '([type])values.get([arg name])' to get arguments, such as
                    Integer num = (Integer)values.get("num");
                    return "Good job in the race! You got #"+num.toString()+" place! Bye!"; //replace with returned object
            };
        FunctionRunner basic /*replace'basic' with name for function*/ = new FunctionRunner(
                new Class<?>[][]{/*list of argument types for each function in overload (such as*/{Integer.class}/*)*/},
                new String[][]{/*list of names for arguments in each function in overload(such as*/{"num"}/*)*/},
                new Function<?>[]{
                    link // use variables to show where the function is
                });
      // use [var name].exec([args]) to run it and return the result
      System.out.println(basic.exec(2));
      System.out.println((new ImplementExample()).getFr().exec(10, "banana"));
	  // Example of varargs:
	  Function<String> s = (list) -> {
            String st = "[";
            for (String string : (String[]) list.get("strings")) { // use regular techniques to get varargs parameter
                st += string + ", ";
            }
            st += "\b\b]" + (String) list.get("text")
                    + "\nexit code:" + (Integer) list.get("number");
            return st;
        };
		// FunctionRunner to run it:
        FunctionRunner fun = new FunctionRunner(
                new Class<?>[][]{{String.class, Integer.class}},
                new String[][]{{  "text"      , "number"     }},
                new Function<?>[]{s},
                new Class<?>[]{String.class}, // add this to constructor to make varargs (if you have overloaded functions, and you don't want varargs for one, use null.)
                new String[]{  "strings"   } // also for varargs (same use of null as above)
        );

        System.out.println(fun.exec("go", 0, "na na na", "na na na na", "hey hey hey", "goodbye"));
		// Overloaded function example:
        Function<String> s2 = (list) ->
        {
            return ((String)list.get("text"))+"s";
        };
        Function<Integer> t = (list) ->
        {
            return ((Integer)list.get("number"))+1;
        };
        FunctionRunner funny = new FunctionRunner(
            new Class<?>[][]{{String.class}, {Integer.class}},
            new String[][] { {"text"      }, {"number"     }},
            new Function<?>[] {s2,            t}
        );
        System.out.println(funny.exec("what?"));
        System.out.println(funny.exec(2));
    }
}
// Implement it to create functions that need to be put in smaller chunks
class ImplementExample implements Function<String> {
	// add fields if you like
	private static final int max = 15;
	private static final int min = -10;
	// make it easy to run with a FunctionRunner inside
	private FunctionRunner fr = new FunctionRunner(
		new Class<?>[][]{{Integer.class, String.class}},
		new String[][]{{"number",        "text"}},
		new Function<?>[]{this});
	public FunctionRunner getFr(){return fr;}
	// Here is the main method:
	public String exec(HashMap<String, Object> hash) {
		return implement("Why did you say \""+(String)hash.get("text")+"\"?\nexit code:",(Integer)hash.get("number"));
	}
	// add as many functions as you want
	public static String implement(String s, Integer i) throws IndexOutOfBoundsException {
		if(i.intValue() > max || i.intValue() < min) {
			throw new IndexOutOfBoundsException("out of range");
		}
		return s+i.toString();
	}
}
