package pl.wolke.doghouse.modules.auth.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String username;
}
