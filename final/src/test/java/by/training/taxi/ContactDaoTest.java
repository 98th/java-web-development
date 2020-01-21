package by.training.taxi;

import by.training.taxi.contact.ContactDao;
import by.training.taxi.contact.ContactDto;
import by.training.taxi.dao.ConnectionManager;
import by.training.taxi.dao.DAOException;
import org.junit.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ContactDaoTest {

    @BeforeClass
    public static void initContext() {
        ApplicationContext.initialize();
    }

    @Before
    public void createTable() throws SQLException {
        String sql = "CREATE TABLE user_contact" +
                "(" +
                "    contact_id bigint auto_increment primary key," +
                "    first_name character varying NOT NULL," +
                "    last_name character varying," +
                "    email character varying," +
                "    phone character varying," +
                "    user_account_id bigint" +
                ");";
        executeSql(sql);
        String insertContact = "INSERT INTO user_contact (first_name, last_name, phone, email, user_account_id) " +
                "                VALUES ('Oleg', 'Ivanov', '375299638521', 'olegOleg@gmail.com', 2)";
        executeSql(insertContact);
    }

    @Test
    public void shouldSaveAndDeleteContact() throws DAOException {
        ContactDao contactDao = ApplicationContext.getInstance().getBean(ContactDao.class);
        Assert.assertNotNull(contactDao);
        ContactDto contactDto =  getContact();
        long savedContact = contactDao.save(contactDto);
        assertEquals(2, savedContact);
        assertEquals("firstNameTest0", contactDto.getFirstName());
        assertTrue(contactDao.delete(savedContact));
    }

    @Test
    public void shouldFindAllContacts() throws DAOException {
        ContactDao contactDao = ApplicationContext.getInstance().getBean(ContactDao.class);
        Assert.assertNotNull(contactDao);
        ContactDto secondContact = getContact();
        long savedSecond = contactDao.save(secondContact);
        assertEquals(2, contactDao.findAll().size());
        contactDao.delete(savedSecond);
    }

    @Test
    public void shouldFindByEmail() throws DAOException {
        ContactDao contactDao = ApplicationContext.getInstance().getBean(ContactDao.class);
        Assert.assertNotNull(contactDao);
        ContactDto foundedContact = contactDao.getByEmail("olegOleg@gmail.com");
        assertEquals(1, (long) foundedContact.getId());
    }

    @Test(expected = DAOException.class)
    public void shouldNotFindByEmail() throws DAOException {
        ContactDao contactDao = ApplicationContext.getInstance().getBean(ContactDao.class);
        Assert.assertNotNull(contactDao);
        contactDao.getByEmail("notExisting@email.com");
    }

    @Test
    public void shouldUpdateContact() throws DAOException {
        ContactDao contactDao = ApplicationContext.getInstance().getBean(ContactDao.class);
        Assert.assertNotNull(contactDao);
        ContactDto contact = contactDao.getById(1L);
        contact.setEmail("newEmail@gmail.com");
        assertTrue(contactDao.update(contact));
        assertEquals("newEmail@gmail.com", contact.getEmail());
    }

    @Test
    public void shouldGetByUserId() throws DAOException {
        ContactDao contactDao = ApplicationContext.getInstance().getBean(ContactDao.class);
        Assert.assertNotNull(contactDao);
        assertNotNull(contactDao.getByUserId(2L));
    }

    @Test
    public void shouldGetById() throws DAOException {
        ContactDao contactDao = ApplicationContext.getInstance().getBean(ContactDao.class);
        Assert.assertNotNull(contactDao);
        assertNotNull(contactDao.getById(1L));
    }

    @After
    public void dropTable() throws SQLException {
        String sql = "DROP TABLE user_contact";
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

    private ContactDto getContact() {
        return ContactDto.builder()
                .firstName("firstNameTest0")
                .lastName("lastNameTest0")
                .email("aaaaaaaa@gmail.com")
                .phone("3784512")
                .userId((long)5)
                .build();
    }
}
