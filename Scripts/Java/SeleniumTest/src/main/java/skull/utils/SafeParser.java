package skull.utils;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SafeParser {

    public static int parseInt(String value, int defaultValue) {
        value = value.replace(",", "");
        int res = defaultValue;
        try {
            res = Integer.parseInt(value);
        } catch (NumberFormatException ignored) {
        }
        return res;
    }

    public static float parseFloat(String value, float defaultValue) {
        value = value.replace(",", "");
        float res = defaultValue;
        try {
            res = Float.parseFloat(value);
        } catch (NumberFormatException ignored) {
        }
        return res;
    }
}
