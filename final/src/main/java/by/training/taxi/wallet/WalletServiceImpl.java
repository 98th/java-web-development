package by.training.taxi.wallet;

import by.training.taxi.bean.Bean;
import by.training.taxi.dao.DAOException;
import by.training.taxi.dao.TransactionSupport;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Bean
@Log4j
@AllArgsConstructor
@TransactionSupport
public class WalletServiceImpl implements WalletService {
    private WalletDao walletDao;

    @Override
    public boolean update (WalletDto wallet) throws  WalletServiceException {
        try {
            return  walletDao.update(wallet);
        } catch (DAOException e) {
            throw new WalletServiceException(e.getMessage());
        }
    }

    @Override
    public WalletDto getById(Long id) throws WalletServiceException {
        try {
            return walletDao.getById(id);
        } catch (DAOException e) {
            log.error(e.getMessage());
            throw new WalletServiceException(e.getMessage());
        }
    }

    @Override
    public List<WalletDto> getByUserId(Long id) throws WalletServiceException {
        try {
            return walletDao.getByUserId(id);
        } catch (DAOException e) {
            throw new WalletServiceException(e.getMessage());
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            return walletDao.delete(id);
        } catch (DAOException e) {
            return false;
        }
    }
}
