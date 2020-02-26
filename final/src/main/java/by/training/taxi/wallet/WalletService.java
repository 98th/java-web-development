package by.training.taxi.wallet;


import by.training.taxi.dao.DAOException;

import java.util.List;

public interface WalletService {
    WalletDto getById(Long id) throws WalletServiceException;
    boolean update(WalletDto wallet) throws WalletServiceException;
    boolean delete(Long id);
    List<WalletDto> getByUserId(Long id) throws WalletServiceException;
    long save(WalletDto walletDto) throws WalletServiceException;
}
