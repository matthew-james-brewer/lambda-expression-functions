import java.util.HashMap;
/** executes a {@link Function} with the specified arguments and return type.
 *
 * @param <R> the return type of the function
 * @see {@link Function}
 */
public class FunctionRunner<R> {
    private Function<R> in;
    private HashMap<String, Class<?>> args;

    /**
     * Constructs a FunctionRunner instance with the specified arguments.
     *
     * @param c an array of argument types
     * @param s an array of argument names
     * @param f the function to execute
     * @throws IllegalArgumentException if the lengths of the argument arrays do not match
     */
    public FunctionRunner(Class<?>[] c, String[] s, Function<R> f) throws IllegalArgumentException {
        if (c.length != s.length) throw new IllegalArgumentException("Types and identifier pairs incomplete: "+c.length+" types and "+s.length+" identifiers");
        args = new HashMap<>();
        for (int i = 0; i < s.length; i++) {
            String key = s[i];
            Class<?> value = c[i];
            args.put(key, value);
        }
        in = f;
    }

    /**
     * Executes the function with the provided arguments.
     *
     * @param o the arguments to pass to the function
     * @return the result of the function execution
     * @throws IllegalArgumentException if the arguments' length do not match the expected arguments' length or argument types do not match the expected types
     * @see Function#exec(HashMap)
     */
    public R exec(Object... o) throws IllegalArgumentException {
        if (o.length != args.size()) {
            throw new IllegalArgumentException("Formal and actual argument lists differ in length. Expected: " + args.size() + ", Actual: " + o.length);
        }
        HashMap<String, Object> h = new HashMap<>();
        int i = 0;
        for (String key : args.keySet()) {
            Class<?> expectedType = args.get(key);
            Object value = o[i];
            if (value.getClass() != expectedType) {
                throw new IllegalArgumentException("Incorrect type of argument value for '" + key + "'. Expected: " + expectedType.getSimpleName() + ", Actual: " + value.getClass().getSimpleName());
            }
            h.put(key, value);
            i++;
        }
        return in.exec(h);
    }
}
