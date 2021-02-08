package klasik.group.core.production.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetaInfo {
    private String timestamp;
    private String user;
    private List<String> roles;
}
