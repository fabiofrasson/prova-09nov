package com.fabiofrasson.prova09nov.resources;

import com.fabiofrasson.prova09nov.exceptions.ResourceNotFoundException;
import com.fabiofrasson.prova09nov.models.Contact;
import com.fabiofrasson.prova09nov.services.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(
    origins = {"*"},
    maxAge = 3600,
    allowCredentials = "false")
@RequestMapping("/api/contacts")
public class ContactResource {

  private final ContactService service;

  public ContactResource(ContactService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<Contact>> list() {
    return ResponseEntity.ok(service.listAll());
  }

  @GetMapping(path = "/id/{id}")
  public ResponseEntity<Contact> findById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(service.findByIdOrThrowResourceNotFoundException(id));
  }

  @GetMapping(path = "/search")
  public ResponseEntity<List<Contact>> findByName(@RequestParam String name) {
    return ResponseEntity.ok(service.findByName(name));
  }

  @PostMapping
  public ResponseEntity<Contact> save(@RequestBody @Valid Contact contact) {
    return new ResponseEntity<>(service.save(contact), HttpStatus.CREATED);
  }

  @DeleteMapping(path = "/id/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

    try {
      service.delete(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (ResourceNotFoundException resourceNotFoundException) {
      throw new ResourceNotFoundException("Contact not found. Please try again.");
    }
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteAll() {
    service.deleteAll();
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping(path = "/id/{id}")
  public ResponseEntity<Void> replace(
      @PathVariable("id") Long id, @RequestBody @Valid Contact contact) {
    service.replace(id, contact);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
