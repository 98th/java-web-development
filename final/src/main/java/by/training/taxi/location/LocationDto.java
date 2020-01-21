package by.training.taxi.location;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    private long id;
    private double latitude;
    private double longitude;
}
