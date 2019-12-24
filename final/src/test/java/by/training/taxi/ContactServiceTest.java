package by.training.taxi;

import by.training.taxi.contact.ContactDto;
import by.training.taxi.contact.ContactService;
import by.training.taxi.contact.ContactServiceException;
import by.training.taxi.dao.DataSource;
import by.training.taxi.dao.DataSourceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactServiceTest {
    
    @Before
    public void createTable() throws SQLException {
        ApplicationContext.initialize();
        String sql = "CREATE TABLE user_contact" +
                "(" +
                "    contact_id bigserial NOT NULL," +
                "    first_name character varying NOT NULL," +
                "    last_name character varying," +
                "    email character varying," +
                "    phone character varying," +
                "    user_account_id bigint," +
                "    PRIMARY KEY (contact_id)" +
                ");";
        executeSql(sql);
    }

    @After
    public void dropTable() throws SQLException {
        String sql = "DROP TABLE user_contact";
        executeSql(sql);
        ApplicationContext.getInstance().destroy();
    }

    @Test
    public void shouldRegisterUser() throws ContactServiceException  {
        ContactService contactService = ApplicationContext.getInstance().getBean(ContactService.class);
        Assert.assertNotNull(contactService);
        ContactDto contactDto = new ContactDto();
        contactDto.setPhone("3784512");
        contactDto.setUserId((long) 1);
        contactDto.setLastName("lastNameTest0");
        contactDto.setFirstName("firstNameTest0");
        contactDto.setEmail("aaaaa@gmail.com");

        boolean registered = contactService.saveContact(contactDto);
        Assert.assertTrue(registered);

        boolean founded = contactService.findByUserId((long) 1).isPresent();
        Assert.assertTrue(founded);

    }


    private void executeSql(String sql) throws SQLException {
        DataSource dataSource = new DataSourceImpl();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
    }
}
