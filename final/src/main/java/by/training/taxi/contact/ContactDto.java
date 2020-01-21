package by.training.taxi.contact;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Long userId;
}
