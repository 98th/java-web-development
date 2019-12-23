package by.training.taxi.discount;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DiscountDto  {
    private long id;
    private double amount;
}
