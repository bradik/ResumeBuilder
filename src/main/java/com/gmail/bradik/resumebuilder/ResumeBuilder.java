package com.gmail.bradik.resumebuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.gmail.bradik.resumebuilder.dto.ResumeData;
import com.gmail.bradik.resumebuilder.render.FreemarkerUtils;
import com.gmail.bradik.resumebuilder.service.ResumeDataFactory;
import com.gmail.bradik.resumebuilder.service.ResumeDataService;
import lombok.RequiredArgsConstructor;
import com.gmail.bradik.resumebuilder.util.FileUtils;
import com.gmail.bradik.resumebuilder.util.Mode;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ResumeBuilder {
    private final ResumeDataFactory resumeDataFactory;

    @SneakyThrows
    public void  build(Path dataPath, Path photoPath) {

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ResumeData resumeData = xmlMapper.readValue(dataPath.toFile(), ResumeData.class);


        Mode lang = "en".equalsIgnoreCase(resumeData.getLang()) ? Mode.EN : Mode.RU;

        ResumeDataService dataService = resumeDataFactory.getResumeDataService(lang);

        Map<String, Object> data = new HashMap<>();
        data.put("data", resumeData);
        data.put("dataService", dataService);

        String html = FreemarkerUtils.render(data, "templates/index.ftl");

        FileUtils.copyFile(photoPath, Paths.get("out/static/pic/photo.jpeg"));
        Files.write(Paths.get("out/index.html"), html.getBytes(StandardCharsets.UTF_8));



    }
}
