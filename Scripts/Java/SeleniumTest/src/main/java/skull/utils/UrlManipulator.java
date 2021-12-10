package skull.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UrlManipulator {

    private UrlManipulator() {
    }

    private static final List<String> paramPreferredStores = List.of(
            "f[partner]=p_1", "f[partner]=p_9404" // noon
            , "f[partner]=p_24308" // Accessories Hub
    );
    private static final String PARAM_ITEMS_LIMIT = "limit=200";        // view 200 items per page (max)

    public static String appendParamsToBase(String baseurl, String... extraParams) {
        var params = new ArrayList<String>();
        params.addAll(paramPreferredStores);
        params.addAll(Arrays.asList(extraParams));
        params.add(PARAM_ITEMS_LIMIT);
        return baseurl + (baseurl.contains("?") ? "&" : "?") + String.join("&", params);
    }
}
