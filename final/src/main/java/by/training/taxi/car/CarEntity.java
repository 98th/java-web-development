package by.training.taxi.car;

import lombok.*;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarEntity {
    private long id;
    private String color;
    private String model;
    private String licencePlateNum;
    private CarStatus carStatus;
    private long driverId;
}
