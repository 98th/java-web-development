package by.training.taxi.contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    ContactDto getByUserId(Long id) throws ContactServiceException;

    ContactDto getByEmail(String email) throws ContactServiceException ;

    List<ContactDto> getAllContacts() throws ContactServiceException;

    boolean deleteContact(Long id)  throws ContactServiceException;

    boolean saveContact(ContactDto entity);

    boolean isEmailUnique(String email) throws ContactServiceException;

    boolean update(ContactDto contactDto) throws  ContactServiceException;
}
