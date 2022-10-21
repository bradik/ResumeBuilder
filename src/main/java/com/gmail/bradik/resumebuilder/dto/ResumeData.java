package com.gmail.bradik.resumebuilder.dto;

import com.gmail.bradik.resumebuilder.dto.additional.Additional;
import com.gmail.bradik.resumebuilder.dto.courses.Course;
import com.gmail.bradik.resumebuilder.dto.educations.Education;
import com.gmail.bradik.resumebuilder.dto.experiences.Experiences;
import com.gmail.bradik.resumebuilder.dto.personal.Personal;
import com.gmail.bradik.resumebuilder.dto.position.Position;
import com.gmail.bradik.resumebuilder.dto.skills.Row;
import com.gmail.bradik.resumebuilder.util.Mode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResumeData {
    private String lang;
    private Personal personal;
    private Position position;
    private Experiences experiences;
    private List<Row> skills;
    private String qualitys;
    private String interests;
    private List<Education> educations;
    private List<String> languages;
    private List<Course> courses;
    private Additional additional;
}
