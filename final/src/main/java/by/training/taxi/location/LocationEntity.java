package by.training.taxi.location;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationEntity {
    private long id;
    private double latitude;
    private double longitude;
}
