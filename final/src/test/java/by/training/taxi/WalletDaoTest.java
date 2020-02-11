package by.training.taxi;

import by.training.taxi.dao.ConnectionManager;
import by.training.taxi.dao.DAOException;
import by.training.taxi.wallet.WalletDao;
import by.training.taxi.wallet.WalletDto;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RunWith(JUnit4.class)
public class WalletDaoTest {

    @BeforeClass
    public static void initContext() {
        ApplicationContext.initialize();
    }

    @Before
    public void createTable() throws SQLException {
        String sql = "CREATE TABLE user_wallet" +
                "(" +
                "    wallet_id bigint auto_increment primary key," +
                "    wallet_amount numeric NOT NULL," +
                "    user_account_id bigint NOT NULL," +
                ");";
        executeSql(sql);
        String insertWallet = "INSERT INTO user_wallet (wallet_id, wallet_amount, user_account_id) " +
                " VALUES (1, 100, 1)";
        executeSql(insertWallet);
        String insertWallet2 = "INSERT INTO user_wallet (wallet_id, wallet_amount, user_account_id) " +
                " VALUES (2, 200, 1)";
        executeSql(insertWallet2);
        String insertWallet3 = "INSERT INTO user_wallet (wallet_id, wallet_amount, user_account_id) " +
                " VALUES (3, 75, 3)";
        executeSql(insertWallet3);
    }

    @Test
    public void shouldSaveAndDeleteWallet() throws DAOException {
        WalletDao walletDao = ApplicationContext.getInstance().getBean(WalletDao.class);
        Assert.assertNotNull(walletDao);
        WalletDto wallet = WalletDto.builder()
                .amount(new BigDecimal("0"))
                .userId(2L)
                .build();
        long savedWallet = walletDao.save(wallet);
        Assert.assertEquals(4, savedWallet);
        Assert.assertTrue(walletDao.delete(savedWallet));
    }

    @Test
    public void shouldFindWalletById() throws DAOException {
        WalletDao walletDao = ApplicationContext.getInstance().getBean(WalletDao.class);
        Assert.assertNotNull(walletDao);
        WalletDto wallet = walletDao.getById(1L);
        Assert.assertEquals(1, wallet.getId());
    }

    @Test(expected = DAOException.class)
    public void shouldNotFindWalletById() throws DAOException {
        WalletDao walletDao = ApplicationContext.getInstance().getBean(WalletDao.class);
        Assert.assertNotNull(walletDao);
        walletDao.getById(78L);
    }

    @Test
    public void shouldFindAllWallets() throws DAOException {
        WalletDao walletDao = ApplicationContext.getInstance().getBean(WalletDao.class);
        Assert.assertNotNull(walletDao);
        Assert.assertEquals(3, walletDao.findAll().size());
    }

    @Test
    public void shouldUpdateWallet() throws DAOException {
        WalletDao walletDao = ApplicationContext.getInstance().getBean(WalletDao.class);
        Assert.assertNotNull(walletDao);
        WalletDto wallet = walletDao.getById(1L);
        wallet.setAmount(new BigDecimal("35"));
        Assert.assertTrue(walletDao.update(wallet));
        Assert.assertEquals(0, wallet.getAmount().compareTo(new BigDecimal("35")));
    }

    @Test
    public void shouldGetByUserId() throws DAOException {
        WalletDao walletDao = ApplicationContext.getInstance().getBean(WalletDao.class);
        Assert.assertNotNull(walletDao);
        Assert.assertEquals(2, walletDao.getByUserId(1L).size());
    }

    @Test
    public void shouldNotGetByUserId() throws DAOException {
        WalletDao walletDao = ApplicationContext.getInstance().getBean(WalletDao.class);
        Assert.assertNotNull(walletDao);
        Assert.assertTrue(walletDao.getByUserId(859L).isEmpty());
    }

    @After
    public void dropTable() throws SQLException {
        String sql = "DROP TABLE user_wallet";
        executeSql(sql);
    }

    @AfterClass
    public static void destroyContext(){
        ApplicationContext.getInstance().destroy();
    }

    private void executeSql(String sql) throws SQLException {
        ConnectionManager connectionManager = ApplicationContext.getInstance().getBean(ConnectionManager.class);
        Assert.assertNotNull(connectionManager);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(true);
            preparedStatement.executeUpdate();
        }
    }
}
