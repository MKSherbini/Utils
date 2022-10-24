package skull.shopping.model;

import lombok.Builder;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@SuperBuilder
public class AmazonProduct extends Product  {
    @Override
    public boolean isValuable() {
        return (discount >= 20 || goodShipping());
    }
}
