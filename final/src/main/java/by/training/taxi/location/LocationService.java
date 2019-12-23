package by.training.taxi.location;

public interface LocationService {
    LocationDto getById(long id) throws LocationServiceException;
}
