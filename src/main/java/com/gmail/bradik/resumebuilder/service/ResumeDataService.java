package com.gmail.bradik.resumebuilder.service;

import com.gmail.bradik.resumebuilder.dto.ResumeData;
import com.gmail.bradik.resumebuilder.dto.experiences.Experience;

public interface ResumeDataService {
    String getResource(String resourceName);
    String getPersonInfo(ResumeData resumeData);
    String getWorkExperience(ResumeData resumeData);
    String getSeniority(Experience experience);
    String getDuration(Experience experience);
    String getFormattedDescription(String description);
}
