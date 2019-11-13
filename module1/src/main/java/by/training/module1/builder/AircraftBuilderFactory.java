package by.training.module1.builder;

import by.training.module1.entity.AircraftType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class AircraftBuilderFactory  {
    private static final Logger log = LogManager.getLogger(AircraftBuilderFactory.class);

    public AircraftBuilder getAircraft (AircraftType type) {
        switch (type) {
            case MILITARY:
                return new MilitaryAircraftBuilder();
            case MEDICAL:
                return new MedicalAircraftBuilder();
            default:
                log.fatal("Incorrect type");
                throw new IllegalArgumentException("Incorrect type");
        }
    }
}

