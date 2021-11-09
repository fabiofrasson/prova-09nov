package com.fabiofrasson.prova09nov.services;

import com.fabiofrasson.prova09nov.exceptions.ResourceNotFoundException;
import com.fabiofrasson.prova09nov.models.Contact;
import com.fabiofrasson.prova09nov.repositories.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
