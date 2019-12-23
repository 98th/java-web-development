package by.training.taxi.discount;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Setter
@Getter
public class DiscountEntity {
    private long id;
    private double amount;
}