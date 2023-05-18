import java.util.HashMap;

public class FunctionRunner<R> {
    private Function<R> in;
    private HashMap<String, Class<?>> args;

    public FunctionRunner(Class<?>[] c, String[] s, Function<R> f) {
        if (c.length != s.length) throw new IllegalArgumentException("Arrays not equal lengths");
        args = new HashMap<>();
        for (int i = 0; i < s.length; i++) {
            String key = s[i];
            Class<?> value = c[i];
            args.put(key, value);
        }
        in = f;
    }

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
