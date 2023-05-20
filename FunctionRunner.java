/**
 * Executes a function with the specified arguments and return type.
 *
 * @param <R> the return type of the function
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
        if (c.length != s.length) throw new IllegalArgumentException("Arrays not equal lengths");
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
     * @throws IllegalArgumentException if the argument types do not match the expected types
     */
    public R exec(Object... o) {
        HashMap<String, Object> h = new HashMap<>();
        int i = 0;
        for (String key : args.keySet()) {
            Class<?> expectedType = args.get(key);
            Object value = o[i];
            if (value.getClass() != expectedType) {
                throw new IllegalArgumentException("Incorrect type of argument " + value.getClass() + " for " + key + "; expected " + expectedType);
            }
            h.put(key, value);
            i++;
        }
        return in.exec(h);
    }
}
