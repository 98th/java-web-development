package by.training.taxi.contact;

import by.training.taxi.bean.Bean;
import by.training.taxi.dao.DAOException;
import by.training.taxi.dao.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@Bean
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {
    private ContactDao contactDao;

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
    public Optional<ContactDto> findByUserId(Long id) throws ContactServiceException {
        try {
            return contactDao.getByUserId(id);
        } catch (DAOException e) {
            throw  new ContactServiceException(e.getMessage());
        }
    }


    @Override
    public Optional<ContactDto> findByEmail(String email) throws ContactServiceException  {
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
    public boolean deleteContact(ContactDto contactDto) throws ContactServiceException {
        try {
            return contactDao.delete(contactDto);
        } catch (DAOException e) {
            throw new ContactServiceException(e.getMessage());
        }
    }
}
