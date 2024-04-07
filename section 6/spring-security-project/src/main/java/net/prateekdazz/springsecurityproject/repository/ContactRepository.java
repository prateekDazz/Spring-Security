package net.prateekdazz.springsecurityproject.repository;

import net.prateekdazz.springsecurityproject.entity.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
}
