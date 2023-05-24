import java.util.HashMap;

/**
 * Executes a {@link Function} with the specified arguments and return type.
 */
public class FunctionRunner {
    private Function<?>[] functions;
    private HashMap<String, Class<?>> args;

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

        args = new HashMap<>();
        for (int i = 0; i < c.length; i++) {
            Class<?>[] argTypes = c[i];
            String[] argNames = s[i];
            Function<?> function = f[i];

            if (argTypes.length != argNames.length) {
                throw new IllegalArgumentException("Types and identifier pairs incomplete for function: " + i);
            }

            for (int j = 0; j < argTypes.length; j++) {
                String argName = argNames[j];
                Class<?> argType = argTypes[j];
                args.put(argName, argType);
            }
        }

        functions = f;
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
        if (o.length != args.size()) {
            throw new IllegalArgumentException("Formal and actual argument lists differ in length. Expected: " + args.size() + ", Actual: " + o.length);
        }

        HashMap<String, Object> arguments = new HashMap<>();
        int i = 0;
        for (String argName : args.keySet()) {
            Class<?> expectedType = args.get(argName);
            Object value = o[i];

            if (!isArgumentTypeCompatible(expectedType, value)) {
                throw new IllegalArgumentException("Incorrect type of argument value for '" + argName + "'. Expected: " + expectedType.getSimpleName() + ", Actual: " + value.getClass().getSimpleName());
            }

            arguments.put(argName, value);
            i++;
        }

        return executeFunction(arguments);
    }

    private boolean isArgumentTypeCompatible(Class<?> expectedType, Object value) {
        return expectedType.isInstance(value);
    }

    private Object executeFunction(HashMap<String, Object> arguments) {
        for (Function<?> function : functions) {
            try {
                return function.exec(arguments);
            } catch (IllegalArgumentException e) {
                // Ignore the exception and continue to the next function
            }
        }

        throw new IllegalArgumentException("No matching function found");
    }
}
