package by.training.taxi.user;

import by.training.taxi.contact.ContactDto;
import by.training.taxi.discount.DiscountDto;
import by.training.taxi.driver.DriverDto;
import by.training.taxi.location.LocationDto;

import by.training.taxi.util.ImageUtil;
import by.training.taxi.wallet.WalletDto;
import lombok.*;

import java.io.IOException;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDto {
    private Long id;
    private String login;
    private String password;
    private Role role;
    private boolean isLocked;
    private byte[] avatar;

    private ContactDto contact;
    private List<WalletDto> wallets;

    private DriverDto driver;
    private LocationDto location;
    private DiscountDto discount;

    public boolean getIsLocked() {
        return isLocked;
    }

    public String toBase64() throws IOException {
        return ImageUtil.toBase64(avatar);
    }
}