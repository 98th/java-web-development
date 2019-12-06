package by.training.taxi.contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {

    Optional<ContactDto> findByUserId(Long id) throws ContactServiceException;

    Optional<ContactDto> findByEmail(String email) throws ContactServiceException ;

    List<ContactDto> getAllContacts() throws ContactServiceException;

    boolean deleteContact(ContactDto contactDto)  throws ContactServiceException;

    boolean saveContact(ContactDto entity);
}