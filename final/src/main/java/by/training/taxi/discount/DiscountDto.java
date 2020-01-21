package by.training.taxi.discount;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDto  {
    private long id;
    private double amount;
}
