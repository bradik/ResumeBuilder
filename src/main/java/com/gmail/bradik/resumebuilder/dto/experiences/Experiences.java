package com.gmail.bradik.resumebuilder.dto.experiences;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Experiences {
    private String start;
    private String end;
    private String duration;
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Experience> experience = new ArrayList<>();
}
