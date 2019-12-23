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
    private boolean isWorking;
    private String licencePlateNum;
    private long driverId;
}
