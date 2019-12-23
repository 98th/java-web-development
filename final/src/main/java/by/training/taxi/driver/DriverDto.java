package by.training.taxi.driver;

import by.training.taxi.car.CarDto;
import by.training.taxi.contact.ContactDto;
import by.training.taxi.location.LocationDto;
import by.training.taxi.user.UserAccountDto;
import lombok.*;

import java.sql.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class DriverDto {
    private long id;
    private long userId;
    private String drivingLicenceNum;
    private CarDto car;
    private ContactDto contact;
    private LocationDto location;
    private UserAccountDto userAccount;
}
