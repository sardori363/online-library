package com.sardor.unsplash.service;

import com.sardor.unsplash.entity.Contacts;
import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.payload.ContactsDto;
import com.sardor.unsplash.repository.ContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactsService {
    @Autowired
    ContactsRepository contactsRepository;

    public ApiResponse add(ContactsDto contactsDto) {
        Contacts contacts = new Contacts();
        contacts.setName(contactsDto.getName());
        contacts.setUrl(contactsDto.getUrl());

        contactsRepository.save(contacts);
        return new ApiResponse("saved", true);
    }

    public ApiResponse edit(Integer id, ContactsDto contactsDto) {
        if (!contactsRepository.existsById(id)) return new ApiResponse("contact not found", false);

        Contacts contacts = contactsRepository.getById(id);
        contacts.setName(contactsDto.getName());
        contacts.setUrl(contactsDto.getUrl());

        contactsRepository.save(contacts);
        return new ApiResponse("edit", true);
    }

    public ApiResponse getOne(Integer id) {
        if (!contactsRepository.existsById(id)) return new ApiResponse("contact not found", false);
        return new ApiResponse("found", true, contactsRepository.findById(id));
    }

    public ApiResponse getAll() {
        return new ApiResponse("found", true, contactsRepository.findAll());
    }

    public ApiResponse delete(Integer id) {
        if (!contactsRepository.existsById(id)) return new ApiResponse("contact not found", false);
        contactsRepository.deleteById(id);
        return new ApiResponse("deleted", true);
    }
}
