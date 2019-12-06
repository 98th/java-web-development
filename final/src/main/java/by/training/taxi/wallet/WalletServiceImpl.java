package by.training.taxi.wallet;

import by.training.taxi.bean.Bean;
import by.training.taxi.dao.DAOException;
import by.training.taxi.dao.TransactionSupport;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.List;

@Bean
@Log4j
@AllArgsConstructor
@TransactionSupport
public class WalletServiceImpl implements WalletService {
    private WalletDao walletDao;

    @Override
    public List<WalletDto> findAllWallets() {
        try {
            return walletDao.findAll();
        } catch (DAOException e) {
            throw new WalletServiceException();
        }
    }
}
