package by.training.taxi.wallet;

import by.training.taxi.dao.CRUDDao;
import by.training.taxi.dao.DAOException;

import java.util.List;

public interface WalletDao extends CRUDDao<Long, WalletDto> {
    List<WalletDto> getByUserId(Long id) throws DAOException;
}
