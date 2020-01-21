package by.training.taxi.user;

import by.training.taxi.contact.ContactDto;
import by.training.taxi.discount.DiscountDto;
import by.training.taxi.driver.DriverDto;
import by.training.taxi.location.LocationDto;

import by.training.taxi.wallet.WalletDto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDto {
    private Long id;
    private String login;
    private String password;
    private byte[] avatar;
    private Role role;
    private boolean isLocked;

    private ContactDto contact;
    private WalletDto wallet;

    private DriverDto driver;
    private LocationDto location;
    private DiscountDto discount;

    public boolean getIsLocked() {
        return isLocked;
    }
}