package com.gmail.bradik.resumebuilder.dto.experiences;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Experience {
    private String start;
    private String end;
    private String duration;
    private String seniority;
    private String title;
    private String url;
    private Position position;
    private String description;
}
