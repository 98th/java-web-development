package by.training.module1;

import by.training.module1.entity.Aircraft;
import by.training.module1.entity.MedicalAircraft;
import by.training.module1.entity.MilitaryAircraft;
import by.training.module1.repository.AircraftRepository;
import by.training.module1.service.*;
import by.training.module1.service.comparator.AircraftSpeedAndFuelConsumComparatorAsc;
import by.training.module1.service.comparator.AircraftSpeedAndFuelConsumComparatorDesc;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(JUnit4.class)
public class AircraftServiceTest {
    private AircraftService service;

    @Before
    public void init () {
        service  = new AircraftService(new AircraftRepository());
        service.add(new MilitaryAircraft("A458", 10, 54,
                528.84, 125, 5) {
        });
        service.add(new MilitaryAircraft("K7845", 10, 27,
                748.84, 125, 2) {
        });
        service.add(new MilitaryAircraft("B485", 10, 54,
                752.74, 100, 8) {
        });
        service.add(new MedicalAircraft("E9845", 10, 95,
                748, 248, true, 5));

        service.add(new MedicalAircraft("T565", 20, 42,
                453, 248, false, 5));
    }

    @Test
    public void  shouldCalculateCargoCapacity() {
        Assert.assertEquals(3_231.42, service.calculateCargoCapacity(), 0.01);
    }

    @Test
    public void  shouldCalculatePeopleCapacity() {
        assertEquals(60, service.calculatePassengers());
    }

    @Test
    public void shouldSortBySpeedAndFuelConsumptionDesc() {
        List<Aircraft> result = service.sort(new AircraftSpeedAndFuelConsumComparatorDesc());
        assertEquals(248, result.get(0).getSpeed());
        assertEquals(248, result.get(1).getSpeed());
        assertEquals(95, result.get(0).getFuelConsumption());
        assertEquals(42, result.get(1).getFuelConsumption());
    }

    @Test
    public void shouldSortBySpeedAndFuelConsumptionAsc() {
        List<Aircraft> result = service.sort(new AircraftSpeedAndFuelConsumComparatorAsc());
        assertEquals(100, result.get(0).getSpeed());
        assertEquals(125, result.get(1).getSpeed());
        assertEquals(54, result.get(0).getFuelConsumption());
        assertEquals(27, result.get(1).getFuelConsumption());
    }

    @Test
    public void shouldSortBySpeedAsc(){
        List<Aircraft> result = service.sort(new AircraftSpeedAndFuelConsumComparatorAsc());
        assertEquals(100, result.get(0).getSpeed());
        assertEquals(125, result.get(1).getSpeed());
        assertEquals(125, result.get(2).getSpeed());
    }

    @Test
    public void shouldSortBySpeedDesc(){
        List<Aircraft> result = service.sort(new AircraftSpeedAndFuelConsumComparatorDesc());
        assertEquals(248, result.get(0).getSpeed());
        assertEquals(248, result.get(1).getSpeed());
        assertEquals(125, result.get(2).getSpeed());
    }
}
