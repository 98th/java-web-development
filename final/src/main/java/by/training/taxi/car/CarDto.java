package by.training.taxi.car;


import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    private long id;
    private String color;
    private String model;
    private String licencePlateNum;
    private Set<RequirementType> requirement;

    private String waitingTime;
    private double price;
}
