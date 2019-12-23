package by.training.taxi.user;

import by.training.taxi.role.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserAccountEntity {
    private Long id;
    private String login;
    private String password;
    private Role role;
    private boolean isLocked;

}
