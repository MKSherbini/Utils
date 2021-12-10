package skull.utils;


public class SafeParser {

    public static int parseInt(String value, int defaultValue) {
        int res = defaultValue;
        try {
            res = Integer.parseInt(value);
        } catch (NumberFormatException ignored) {
        }
        return res;
    }

    public static float parseFloat(String value, float defaultValue) {
        float res = defaultValue;
        try {
            res = Float.parseFloat(value);
        } catch (NumberFormatException ignored) {
        }
        return res;
    }
}
