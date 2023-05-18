import java.util.HashMap;

interface Function<R> {
    R exec(HashMap<String, Object> hash);
}
