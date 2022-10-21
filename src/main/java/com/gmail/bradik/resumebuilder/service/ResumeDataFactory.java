package com.gmail.bradik.resumebuilder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.gmail.bradik.resumebuilder.util.Mode;

@Component
@RequiredArgsConstructor
public class ResumeDataFactory {
    @Qualifier("ResumeDataServiceEn")
    private final ResumeDataService dataServiceEn;
    @Qualifier("ResumeDataServiceRu")
    private final ResumeDataService dataServiceRu;

    public ResumeDataService getResumeDataService(Mode mode) {
        return mode == Mode.EN ? dataServiceEn : dataServiceRu;
    }
}
