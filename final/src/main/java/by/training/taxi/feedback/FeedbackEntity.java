package by.training.taxi.feedback;


import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FeedbackEntity {
    private long id;
    private long requestId;
    private byte userRating;
    private byte driverRating;
    private String userMessage;
    private String driverMessage;
}
