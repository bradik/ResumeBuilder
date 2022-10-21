package com.gmail.bradik.resumebuilder.dto.personal;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Personal {
    private String gender;
    private String birthdate;
    private String name;
    private Addresslocality addresslocality;
    private List<Contact> contacts = new ArrayList<>();
}
