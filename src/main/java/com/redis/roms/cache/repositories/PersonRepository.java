package com.redis.roms.cache.repositories;



import com.redis.om.spring.repository.RedisDocumentRepository;
import com.redis.roms.cache.domain.Person;

import java.util.List;

public interface PersonRepository extends RedisDocumentRepository<Person, String> {
  List<Person> findByLastNameAndFirstName(String lastName, String firstName);
}
