package utils;

public class Parse {

    public static long safeLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (Exception ignored) {
            return 0;
        }
    }
}
