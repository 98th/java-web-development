package by.training.taxi.car;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequirementDto {
    private long id;
    private RequirementType requirementType;
}
