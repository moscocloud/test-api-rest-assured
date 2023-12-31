package data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserData {

    private Integer id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;
}
