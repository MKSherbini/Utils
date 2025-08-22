package app;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Item {
    private String exerciseName;
    private String exerciseUrl;
}
