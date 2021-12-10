package skull.noon.output;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import skull.noon.model.NoonProduct;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
public class FilePrinter {
    private final String filePath;
    @Getter
    private final String logID;

    public FilePrinter(String filePath, String logID) {
        this.filePath = filePath;
        this.logID = logID;
    }

    public void sortAndPrint(List<NoonProduct> allDiscounts) {
        log.info("{}:writing pages...", logID);
        try {
            allDiscounts.sort(Collections.reverseOrder());
            NoonRenderer.printToFile(filePath,
                    NoonRenderer.renderHtmlOutput(allDiscounts));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
