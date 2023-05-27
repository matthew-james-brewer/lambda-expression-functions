import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Executes a {@link Function} with the specified arguments and return type.
 */
public class FunctionRunner {
    /**
     * Inner Functions to execute.
     */
    private Function<?>[] functions;
    /**
     * Argument types for the Functions to match with.
     */
    private LinkedHashMap<String, Class<?>>[] args;
    /**
     * List of varargs types (default to `null`)
     */
    private Class<?>[] varargsType = null;
    /**
     * List of varargs names (default to `null`)
     */
    private String[] varargsName = null;

    /**
     * Constructs a FunctionRunner instance with the specified arguments.
     *
     * @param c an array of argument types
     * @param s an array of argument names
     * @param f the function to execute
     * @throws IllegalArgumentException if the lengths of the argument arrays do not match
     */
    public FunctionRunner(Class<?>[][] c, String[][] s, Function<?>[] f) throws IllegalArgumentException {
        if (c.length != s.length || c.length != f.length) {
            throw new IllegalArgumentException("Number of argument types, argument names, and functions do not match");
        }

        args = new LinkedHashMap[c.length];
        for (int i = 0; i < c.length; i++) {
            Class<?>[] argTypes = c[i];
            String[] argNames = s[i];
            Function<?> function = f[i];

            if (argTypes.length != argNames.length) {
                throw new IllegalArgumentException("Types and identifier pairs incomplete for function: " + i);
            }

            LinkedHashMap<String, Class<?>> argMap = new LinkedHashMap<>();
            for (int j = 0; j < argTypes.length; j++) {
                String argName = argNames[j];
                Class<?> argType = argTypes[j];
                argMap.put(argName, argType);
            }
            args[i] = argMap;
        }

        functions = f;
    }

    /**
     * Constructs a FunctionRunner instance with the specified arguments.
     *
     * @param c an array of argument types
     * @param s an array of argument names
     * @param f the function to execute
     * @param v the varargs type
     * @param n the varargs name
     * @throws IllegalArgumentException if the lengths of the argument arrays do not match
     */
    public FunctionRunner(Class<?>[][] c, String[][] s, Function<?>[] f, Class<?>[] v, String[] n) throws IllegalArgumentException {
        this(c, s, f);
        varargsType = v;
        varargsName = n;
    }

    /**
     * Executes the function with the provided arguments.
     *
     * @param o the arguments to pass to the function
     * @return the result of the function execution
     * @throws IllegalArgumentException if the arguments' length does not match the expected arguments' length
     *                                  or if the argument types do not match the expected types
     */
    public Object exec(Object... o) throws IllegalArgumentException {
        int functionIndex = findMatchingFunctionIndex(o);
        if (functionIndex != -1) {
            Function<Object> function = (Function<Object>) functions[functionIndex];
            HashMap<String, Object> arguments = createArgumentsMap(args[functionIndex], o);

            // Check if varargs are present
            if (varargsType != null && varargsName != null) {
                int varargsIndex = args[functionIndex].size();
                int varargsLength = o.length - varargsIndex;
                Object varargsArray = createVarargsArray(varargsType[0], varargsLength, o, varargsIndex);
                arguments.put(varargsName[0], varargsArray);
            }

            return function.exec(arguments);
        }
        throw new IllegalArgumentException("Type or length of arguments did not match any of the functions");
    }
    /**
     * Find the index of the Function needed
     * @param o the (object) arguments to match
     */
    private int findMatchingFunctionIndex(Object[] o) {
        for (int i = 0; i < functions.length; i++) {
            LinkedHashMap<String, Class<?>> argMap = args[i];
            if (match(o, argMap)) {
                return i;
            }
        }
        return -1;
    }
    /**
     * Gets if the arguments match the expected arguments.
     * @param o the list of (object) arguments
     * @param l the list of classes and strings to match
     */
    private boolean match(Object[] o, LinkedHashMap<String, Class<?>> l) {
        if (l.size() > o.length && (varargsType == null || !l.containsKey(varargsName[0]))) {
            return false;
        }

        int i = 0;
        for (String argName : l.keySet()) {
            Class<?> argType = l.get(argName);
            if (i >= o.length) {
                if (varargsType != null && varargsName != null && varargsType.length == 1 && varargsName.length == 1) {
                    if (!argType.isArray() || !argType.getComponentType().isInstance(o[i])) {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                // Regular argument
                Object argValue = o[i];
                if (!argType.isInstance(argValue)) {
                    return false;
                }
                i++;
            }
        }

        return true;
    }
    /**
     * Turn a list of values into a list of Function-runnable values.
     * @param argMap the list of matched Strings and Classes
     * @param values the list of Objects to put in
     */
    private HashMap<String, Object> createArgumentsMap(LinkedHashMap<String, Class<?>> argMap, Object[] values) {
        HashMap<String, Object> arguments = new HashMap<>();
        int i = 0;
        for (String argName : argMap.keySet()) {
            arguments.put(argName, values[i]);
            i++;
        }
        return arguments;
    }
    /**
     * Create the Array for varargs.
     * @param varargsComponentType the class of the varargs array
     * @param varargsLength the length of the array
     * @param o the Objects in the array
     * @param varargsIndex the index where varargs starts
     */
    private Object createVarargsArray(Class<?> varargsComponentType, int varargsLength, Object[] o, int varargsIndex) {
        Object varargsArray = Array.newInstance(varargsComponentType, varargsLength);
        for (int i = 0; i < varargsLength; i++) {
            Array.set(varargsArray, i, o[i + varargsIndex]);
        }
        return varargsArray;
    }
}
