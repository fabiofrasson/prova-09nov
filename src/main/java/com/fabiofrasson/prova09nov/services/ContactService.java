package com.fabiofrasson.prova09nov.services;

import com.fabiofrasson.prova09nov.exceptions.ResourceAlreadyExistsException;
import com.fabiofrasson.prova09nov.exceptions.ResourceNotFoundException;
import com.fabiofrasson.prova09nov.models.Contact;
import com.fabiofrasson.prova09nov.repositories.ContactRepository;
import org.springframework.stereotype.Service;

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

  public Optional<Contact> findByEmail(String email) {
    return repository.findContactByEmailIgnoreCase(email);
  }

  public Optional<Contact> findByPhoneNumber(String phoneNumber) {
    return repository.findContactByPhoneNumber(phoneNumber);
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

  public void replace(Contact contact) {
    Optional<Contact> searchedContact = repository.findContactByEmailIgnoreCase(contact.getEmail());

    if (searchedContact.isEmpty()) {
      throw new ResourceNotFoundException("Contact not found. Please try again.");
    } else {
      repository.save(contact);
    }
  }
}
