package com.ikaya.service.impl;

import com.ikaya.dto.AddressDto;
import com.ikaya.dto.PersonDto;
import com.ikaya.entity.Address;
import com.ikaya.entity.Person;
import com.ikaya.repository.AddressRepository;
import com.ikaya.repository.PersonRepository;
import com.ikaya.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PersonServiceImpl implements PersonService {


    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;

    public PersonServiceImpl(PersonRepository personRepository, AddressRepository addressRepository) {
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public PersonDto save(PersonDto personDto) {
        Person person = new Person();
        person.setName(personDto.getName());
        person.setSurname(personDto.getSurname());
        Person _person = personRepository.save(person);

        List<Address> addressList = new ArrayList<Address>();
        personDto.getAddresses().forEach(item -> {
            Address address = new Address();
            address.setAddress(item.getAddress());
            address.setActive(item.getActive());
            address.setType(item.getType());
            address.setPerson(_person);
            addressList.add(address);
        });
        addressRepository.saveAll(addressList);
        personDto.setId(_person.getId());
        return personDto;
    }

    @Override
    public void delete(Long id) {
        personRepository.deleteById(id);
        List<Address> addressList = personRepository.getOne(id).getAddresses();
        addressRepository.deleteAll(addressList);
    }

    @Override
    public List<PersonDto> getAll() {
        List<Person> personList = personRepository.findAll();
        List<PersonDto> personDtos = new ArrayList<>();
        personList.forEach(item -> {
            PersonDto personDto = new PersonDto();
            personDto.setId(item.getId());
            personDto.setName(item.getName());
            personDto.setSurname(item.getSurname());
            personDto.setAddresses(item.getAddresses().stream().map(PersonServiceImpl::convertToDto)
                    .collect(Collectors.toList()));
            personDtos.add(personDto);
        });
        return personDtos;
    }

    private static AddressDto convertToDto(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setType(address.getType());
        addressDto.setActive(address.getActive());
        addressDto.setAddress(address.getAddress());
        addressDto.setId(address.getId());
        return addressDto;
    }

    @Override
    public Page<PersonDto> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public PersonDto update(PersonDto personDto) {
        return null;
    }
}
