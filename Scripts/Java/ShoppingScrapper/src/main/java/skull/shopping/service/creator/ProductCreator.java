package skull.shopping.service.creator;

import org.openqa.selenium.WebElement;

public interface ProductCreator<T> {
    default T createProductFromSearchPage(WebElement element) {
        return null;
    }

    default T createProduct(WebElement el) {
        return null;
    }

    default T createProductIfWorth(WebElement element) {
        return null;
    }
}
