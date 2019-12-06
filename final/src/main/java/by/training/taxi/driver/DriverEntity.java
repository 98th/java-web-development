package by.training.taxi.driver;

import lombok.*;

import java.sql.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class DriverEntity {
    private long id;
    private long userId;
    private String drivingLicenceNum;
    private Date expiryDate;
}
