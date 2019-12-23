package by.training.taxi.wallet;


import java.math.BigDecimal;

public interface WalletService {
    WalletDto getById(Long id) throws WalletServiceException;
    boolean update(WalletDto wallet) throws WalletServiceException;

}
