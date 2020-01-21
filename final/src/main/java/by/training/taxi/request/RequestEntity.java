package by.training.taxi.request;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestEntity {
    private long id;
    private long clientId;
    private long driverId;
    private RequestStatus requestStatus;
    private String pickLocation;
    private String dropLocation;
    private Date requestDate;
    private BigDecimal price;
}
