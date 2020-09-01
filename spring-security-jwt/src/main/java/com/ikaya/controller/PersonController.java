package com.ikaya.controller;

import com.ikaya.dto.PersonDto;
import com.ikaya.service.PersonService;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    //@CachePut(value = "cars", key = "#personDto.id")
    public ResponseEntity<PersonDto> add(@RequestBody PersonDto personDto) {
        return ResponseEntity.ok(personService.save(personDto));
    }
    @GetMapping
    /*@CacheEvict(value="personCache", key = "#isCacheable",
            condition = "#isCacheable == null || !#isCacheable", beforeInvocation = true)*/
    @Cacheable(value = "personCache", key = "#isCacheable", condition = "#isCacheable != null && #isCacheable")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    public ResponseEntity<List<PersonDto>> getAll(@RequestParam(name = "isCacheable") boolean  isCacheable) throws InterruptedException {
        Thread.sleep(4000);
        return ResponseEntity.ok(personService.getAll());
    }
    @DeleteMapping
    public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
        try {
            personService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.OK).body(false);
        }
    }
}
