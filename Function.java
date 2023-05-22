import java.util.HashMap;
/**
 * Represents a function (a lambda expression) with arguments and a return type.
 * Put a lambda expression in place of this interface to define custom functions.
 *
 * @author Matttew Brewer: mjbrewer17@gmail.com
 * @param <R> the return type of the function
 * @see FunctionRunner
 */
@FunctionalInterface
public interface Function<R> {
    /**
     * Executes the function with the given arguments.
     *
     * @param hash a HashMap containing the arguments as key-value pairs
     * @return the result of the function execution
     * @see FunctionRunner#exec(Object...)
     */
    R exec(HashMap<String, Object> hash);
}
