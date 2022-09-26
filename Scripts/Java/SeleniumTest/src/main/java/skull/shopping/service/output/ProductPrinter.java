package skull.shopping.service.output;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Slf4j
public abstract class ProductPrinter<T> {
    private final String filePath;
    @Getter
    private final String logID;


    protected ProductPrinter(String filePath, String logID) {
        this.filePath = filePath;
        this.logID = logID;
    }

    public abstract String renderHtmlOutput(List<T> products);

    public void printToFile(String location, String data) throws IOException {
        Files.write(Path.of(location), data.getBytes());
    }

    public void sortAndPrint(List<T> products) {
        log.info("{}:writing pages...", logID);
        try {
            products.sort(Collections.reverseOrder());
            printToFile(filePath, renderHtmlOutput(products));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
