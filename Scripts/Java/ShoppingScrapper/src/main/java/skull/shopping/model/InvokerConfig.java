package skull.shopping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties("invoker")
public class InvokerConfig {
    private List<AppRequest> appRequests = new ArrayList<>();


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AppRequest {
        private APPNAME app;
        private String url;
        private String file;
    }

}
