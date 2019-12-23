package by.training.taxi.request;


import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestDto {
    private long id;
    private long driverId;
    private long clientId;
    private RequestStatus requestStatus;
    private String pickLocation;
    private String dropLocation;
    private BigDecimal price;
    private Date requestDate;
}
