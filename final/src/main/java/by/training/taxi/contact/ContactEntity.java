package by.training.taxi.contact;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContactEntity {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Long userId;
}
