package com.fabiofrasson.prova09nov;

import com.fabiofrasson.prova09nov.exceptions.ResourceAlreadyExistsException;
import com.fabiofrasson.prova09nov.models.Contact;
import com.fabiofrasson.prova09nov.repositories.ContactRepository;
import com.fabiofrasson.prova09nov.services.ContactService;
import com.fabiofrasson.prova09nov.util.ContactCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class Prova09novApplicationTests {

  @InjectMocks private ContactService service;

  @Mock private ContactRepository repository;

  @BeforeEach
  void setUp() {
    when(repository.save(ArgumentMatchers.isA(Contact.class)))
        .thenReturn(ContactCreator.createContact());
  }

  @Test
  @DisplayName("Test for saving method")
  void shouldSaveContactWhenSuccessful() {
    Contact savedContact = service.save(ContactCreator.createContact());

    assertThat(savedContact).isNotNull();
    assertThat("45999439105").isEqualTo(savedContact.getPhoneNumber());
    assertThat("fabio.frass@gmail.com").isEqualTo(savedContact.getEmail());
  }

  @Test
  @DisplayName("Test for not allowing double entries")
  void shouldNotSaveRepeatedContact() {

    when(repository.findContactByPhoneNumber(ArgumentMatchers.anyString()))
        .thenReturn(Optional.of(ContactCreator.createContact()));

    assertThatExceptionOfType(ResourceAlreadyExistsException.class)
        .isThrownBy(() -> service.save(ContactCreator.createContact()));
  }
}
