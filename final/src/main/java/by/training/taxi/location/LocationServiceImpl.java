package by.training.taxi.location;

import by.training.taxi.bean.Bean;
import by.training.taxi.dao.DAOException;
import lombok.AllArgsConstructor;

@Bean
@AllArgsConstructor
public class LocationServiceImpl implements LocationService{
    private LocationDao locationDao;

    @Override
    public LocationDto getById(long id) throws LocationServiceException {
        try {
            return locationDao.getById(id);
        } catch (DAOException e) {
            throw new LocationServiceException();
        }
    }
}
