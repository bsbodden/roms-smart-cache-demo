package com.redis.roms.cache.controllers;


import com.redis.roms.cache.domain.Person;
import com.redis.roms.cache.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/persons")
public class PersonController {
  @Autowired
  PersonRepository repository;

  @GetMapping("all")
  Page<Person> all(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @GetMapping("all-ids")
  Page<String> allIds(Pageable pageable) {
    return repository.getIds(pageable);
  }

  @GetMapping("name/{last}/{first}")
  List<Person> byLastAndFirst(//
    @PathVariable("last") String last, @PathVariable("first") String first) {
    return repository.findByLastNameAndFirstName(last, first);
  }

  @GetMapping("{id}")
  Optional<Person> byId(@PathVariable("id") String id) {
    return repository.findById(id);
  }
}
