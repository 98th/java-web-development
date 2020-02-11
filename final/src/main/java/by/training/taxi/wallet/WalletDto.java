package by.training.taxi.wallet;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WalletDto {
    private long id;
    private BigDecimal amount;
    private long userId;
}
