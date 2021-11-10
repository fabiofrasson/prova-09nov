package com.fabiofrasson.prova09nov.util;

import com.fabiofrasson.prova09nov.models.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactCreator {

  public static Contact createContact() {
    return new Contact("Fabio Frasson", "fabio.frass@gmail.com", "45999439105");
  }
}
