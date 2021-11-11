package com.fabiofrasson.prova09nov.services;

import com.fabiofrasson.prova09nov.exceptions.ResourceAlreadyExistsException;
import com.fabiofrasson.prova09nov.exceptions.ResourceNotFoundException;
import com.fabiofrasson.prova09nov.models.Contact;
import com.fabiofrasson.prova09nov.repositories.ContactRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

  private ContactRepository repository;

  public ContactService(ContactRepository repository) {
    this.repository = repository;
  }

  public List<Contact> listAll() {
    return repository.findAll();
  }

  public Contact findByIdOrThrowResourceNotFoundException(Long id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Resource already exists."));
  }

  public List<Contact> findByName(String name) {
    return repository.findContactByNameContainingIgnoreCase(name);
  }

  @Transactional
  public Contact save(Contact contact) {
    Optional<Contact> contactByEmail = repository.findContactByEmailIgnoreCase(contact.getEmail());
    Optional<Contact> contactByPhoneNumber =
        repository.findContactByPhoneNumber(contact.getPhoneNumber());

    if (contactByEmail.isPresent() || contactByPhoneNumber.isPresent()) {
      throw new ResourceAlreadyExistsException(
          "Contact already exists. Please try again with different email and/or phone number.");
    } else {
      return repository.save(contact);
    }
  }

  public void delete(Long id) {
    repository.delete(findByIdOrThrowResourceNotFoundException(id));
  }

  public void deleteAll() {
    repository.deleteAll();
  }

  public void replace(Long id, Contact contact) {
    try {
      Contact searchedContact = repository.getById(id);

      searchedContact.setName(contact.getName());
      searchedContact.setEmail(contact.getEmail());
      searchedContact.setPhoneNumber(contact.getPhoneNumber());

      repository.save(searchedContact);
    } catch (ResourceNotFoundException resourceNotFoundException) {
      throw new ResourceNotFoundException("Contact not found. Please try again.");
    }
  }
}
