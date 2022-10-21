package com.gmail.bradik.resumebuilder.dto.personal;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contact {
    private String name;
    @JsonAlias("")
    private String value;
    private String href;
    private String target;
    @JsonAlias("default")
    private boolean def;
}
