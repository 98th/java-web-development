package by.training.taxi.driver;

import by.training.taxi.car.CarDto;
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
    private Date expiryDate;
    private CarDto carDto;
}
