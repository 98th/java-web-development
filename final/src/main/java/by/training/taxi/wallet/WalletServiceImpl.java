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
            Optional<WalletDto> walletOptional = Optional.ofNullable(walletDao.getById(id));
            return walletOptional.orElseGet(() -> new WalletDto(new BigDecimal("0"), id));
        } catch (DAOException e) {
            throw new WalletServiceException(e.getMessage());
        }
    }


}
