package com.fabiofrasson.prova09nov.repositories;

import com.fabiofrasson.prova09nov.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

  Optional<Contact> findContactByEmailIgnoreCase(String email);

  Optional<Contact> findContactByPhoneNumber(String email);

  List<Contact> findContactByNameContainingIgnoreCase(String name);

}
