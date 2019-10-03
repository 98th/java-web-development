package by.training.module1;


import by.training.module1.entity.Aircraft;
import by.training.module1.entity.MedicalAircraft;
import by.training.module1.entity.MilitaryAircraft;
import by.training.module1.repository.Repository;
import by.training.module1.repository.AircraftRepository;
import by.training.module1.repository.Specification;
import by.training.module1.repository.SpecificationByFuelConsumption;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static junit.framework.TestCase.assertEquals;


@RunWith(JUnit4.class)
public class AircraftRepositoryTest {
    private Repository repository;

    @Before
    public void init () {
        repository = new AircraftRepository();
        repository.add(new MilitaryAircraft("A458", 10, 54,
                528.84, 125, 5) {
        });
        repository.add(new MilitaryAircraft("K7845", 10, 27,
                748.84, 125, 2) {
        });
        repository.add(new MilitaryAircraft("B485", 10, 54,
                752.74, 100, 8) {
        });
        repository.add(new MedicalAircraft("E9845", 10, 95,
                748, 145, true, 5));

        repository.add(new MedicalAircraft("T565", 20, 42,
                453, 248, false, 5));
    }

    @Test
    public void shouldSearchByFuelConsumptionSpecification (){
        Specification spec = new SpecificationByFuelConsumption(20, 30);
        List<Aircraft> result = repository.find(spec);
        assertEquals(1, result.size());
    }

    @Test
    public void shouldGetAll (){
        List<Aircraft> result = repository.getAll();
        assertEquals(5, result.size());
    }

    @Test
    public void shouldAdd (){
        repository.add(new MilitaryAircraft("M964", 20, 95,
                46, 56,66));
        List<Aircraft> result = repository.getAll();
        assertEquals(6, result.size());
    }
}
