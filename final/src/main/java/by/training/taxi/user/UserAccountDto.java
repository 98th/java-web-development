package by.training.taxi.user;

import by.training.taxi.contact.ContactDto;
import by.training.taxi.driver.DriverDto;
import by.training.taxi.request.RequestDto;
import by.training.taxi.role.Role;
import by.training.taxi.role.RoleDto;

import java.util.List;

import by.training.taxi.wallet.WalletDto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserAccountDto {
    private Long id;
    private String login;
    private String password;
    private Role role;

    private ContactDto contact;
    private WalletDto wallet;

    private DriverDto driver;

}