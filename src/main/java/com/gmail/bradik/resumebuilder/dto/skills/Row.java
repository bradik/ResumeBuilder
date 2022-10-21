package com.gmail.bradik.resumebuilder.dto.skills;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Row {
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<String> skill;
}
