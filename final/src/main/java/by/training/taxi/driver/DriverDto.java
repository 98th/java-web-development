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
@Data
public class DriverDto {
    private long id;
    private String drivingLicenceNum;
    private long userId;
    private long carId;
    private boolean isWorking;

    private CarDto car;
    private UserAccountDto userAccount;
    private ContactDto contact;
    private LocationDto location;
}
