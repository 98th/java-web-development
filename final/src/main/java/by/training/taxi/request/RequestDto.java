package by.training.taxi.request;


import by.training.taxi.feedback.FeedbackDto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestDto {
    private long requestId;
    private RequestStatus requestStatus;
    private String pickLocation;
    private String dropLocation;
    private byte passengersNum;
    private long driverId;
    private long customerId;
    private FeedbackDto feedbackDto;
}
