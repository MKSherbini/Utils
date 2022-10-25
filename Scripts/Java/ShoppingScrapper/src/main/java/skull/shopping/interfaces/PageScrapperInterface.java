package skull.shopping.interfaces;

@FunctionalInterface
public interface PageScrapperInterface<T> {
    T scrapPage();
}
