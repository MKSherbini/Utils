package skull.shopping.service.output;

import skull.shopping.model.NoonProduct;

import java.util.List;
import java.util.stream.Collectors;

public class NoonProductPrinter extends ProductPrinter<NoonProduct> {
    public NoonProductPrinter(String filePath, String logID) {
        super(filePath, logID);
    }

    @Override
    public String renderHtmlOutput(List<NoonProduct> noonProducts) {
        return String.format("  <!doctype html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <!-- Required meta tags -->\n" +
                "  <meta charset=\"utf-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" +
                "\n" +
                "  <!-- Bootstrap CSS -->\n" +
                "  <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\" integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\" crossorigin=\"anonymous\">\n" +
                "\n" +
                "  <title>Discounts</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "%s" +
                "\n" +
                "  <!-- Optional JavaScript -->\n" +
                "  <!-- jQuery first, then Popper.js, then Bootstrap JS -->\n" +
                "  <script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>\n" +
                "  <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js\" integrity=\"sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49\" crossorigin=\"anonymous\"></script>\n" +
                "  <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\" integrity=\"sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy\" crossorigin=\"anonymous\"></script>\n" +
                "</body>\n" +
                "</html>", renderTable(noonProducts));
    }

    private static String renderTable(List<NoonProduct> noonProducts) {
        return String.format("<table class=\"table table-striped table-dark table-hover\">\n" +
                "  <thead>\n" +
                "    <tr>\n" +
                "      <th scope=\"col\">Discount</th>\n" +
                "      <th scope=\"col\">Item</th>\n" +
                "      <th scope=\"col\">Price</th>\n" +
                "      <th scope=\"col\">OldPrice</th>\n" +
                "      <th scope=\"col\">Images</th>\n" +
                "    </tr>\n" +
                "  </thead>\n" +
                "  <tbody>\n" +
                "%s" +
                "  </tbody>\n" +
                "</table>", noonProducts.stream().map(NoonProductPrinter::renderRow).collect(Collectors.joining()));
    }

    private static String renderRow(NoonProduct discount) {
        return "<tr>\n" +
                "      <th scope=\"row\">" + discount.getDiscount() + "%</th>\n" +
                "      <td> <a href=\"" + discount.getUrl() + "\">" + discount.getItemName() + "</a></td>\n" +
                "      <td>" + discount.getPrice() + "</td>\n" +
                "      <td>" + discount.getOldPrice() + "</td>\n" +
                "      <td><img src=\"" + discount.getImageUrlMain() + "\" width=100px></td>\n" +
                "  </tr>";
    }
}
