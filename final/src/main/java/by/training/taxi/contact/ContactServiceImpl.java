package by.training.taxi.contact;

import by.training.taxi.bean.Bean;
import by.training.taxi.dao.DAOException;
import by.training.taxi.dao.TransactionSupport;
import by.training.taxi.dao.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.List;
import java.util.Optional;

@Bean
@AllArgsConstructor
@Log4j
@TransactionSupport
public class ContactServiceImpl implements ContactService {
    private ContactDao contactDao;

    @Override
    public boolean update(ContactDto contactDto) throws ContactServiceException {
        try {
            return contactDao.update(contactDto);
        } catch (DAOException e) {
            throw  new ContactServiceException(e.getMessage());
        }
    }

    @Override
    public boolean isEmailUnique(String email) {
        try {
            contactDao.getByEmail(email);
            return true;
        } catch (DAOException e) {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean saveContact(ContactDto entity) {
        try {
            Long id = contactDao.save(entity);
            return true;
        } catch (DAOException e) {
            return false;
        }
    }


    @Override
    public ContactDto getByUserId(Long id) throws ContactServiceException {
        try {
            return contactDao.getByUserId(id);
        } catch (DAOException e) {
            throw  new ContactServiceException(e.getMessage());
        }
    }


    @Override
    public ContactDto getByEmail(String email) throws ContactServiceException  {
        try {
            return contactDao.getByEmail(email);
        } catch (DAOException e) {
            throw new ContactServiceException(e.getMessage());
        }
    }

    @Override
    public List<ContactDto> getAllContacts() throws ContactServiceException  {
        try {
            return contactDao.findAll();
        } catch (DAOException e) {
            throw new ContactServiceException(e.getMessage());
        }
    }

    @Override
    public boolean deleteContact(Long id) throws ContactServiceException {
        try {
            return contactDao.delete(id);
        } catch (DAOException e) {
            throw new ContactServiceException(e.getMessage());
        }
    }
}
