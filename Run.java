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
    }
}
