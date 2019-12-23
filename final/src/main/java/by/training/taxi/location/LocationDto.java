package by.training.taxi.location;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    private long id;
    private double latitude;
    private double longitude;
}
