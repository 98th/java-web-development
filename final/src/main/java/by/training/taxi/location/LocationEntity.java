package by.training.taxi.location;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationEntity {
    private long id;
    private double latitude;
    private double longitude;
}
