package by.training.taxi.user;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountEntity {
    private Long id;
    private String login;
    private String password;
    private Role role;
    private boolean isLocked;

}
