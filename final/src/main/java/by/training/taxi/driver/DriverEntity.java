package by.training.taxi.driver;

import lombok.*;

import java.sql.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DriverEntity {
    private long id;
    private String drivingLicenceNum;
    private long userId;
    private long carId;
    private boolean isWorking;
}
