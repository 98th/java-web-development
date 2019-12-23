package by.training.taxi.wallet;

public class WalletServiceException extends Exception {
    public WalletServiceException() {
    }

    public WalletServiceException(String message) {
        super(message);
    }

    public WalletServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public WalletServiceException(Throwable cause) {
        super(cause);
    }
}
