import java.util.HashMap;
/**
 * Represents a function with arguments and a return type.
 * Implement this interface to define custom functions.
 *
 * @param <R> the return type of the function
 */
public interface Function<R> {
    /**
     * Executes the function with the given arguments.
     *
     * @param hash a HashMap containing the arguments as key-value pairs
     * @return the result of the function execution
     */
    R exec(HashMap<String, Object> hash);
}
