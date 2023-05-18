import java.util.HashMap;

public interface Function<R> {
    R exec(HashMap<String, Object> hash);
}
