package by.training.taxi.feedback;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FeedbackDto {
        private long id;
        private long requestId;
        private byte userRating;
        private byte driverRating;
        private String userMessage;
        private String driverMessage;
}
