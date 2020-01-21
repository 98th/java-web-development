package by.training.taxi.wallet;



public interface WalletService {
    WalletDto getById(Long id) throws WalletServiceException;
    boolean update(WalletDto wallet) throws WalletServiceException;
}
