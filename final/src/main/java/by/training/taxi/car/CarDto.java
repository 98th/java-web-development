package by.training.taxi.car;


import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarDto {
    private long id;
    private String color;
    private String model;
    private String licencePlateNum;
    private CarStatus carStatus;
    private long driverId;
    private Set<RequirementDto> requirement;
}
