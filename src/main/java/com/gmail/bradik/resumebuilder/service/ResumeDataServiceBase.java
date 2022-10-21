package com.gmail.bradik.resumebuilder.service;

import com.gmail.bradik.resumebuilder.dto.ResumeData;
import com.gmail.bradik.resumebuilder.dto.experiences.Experiences;
import com.gmail.bradik.resumebuilder.util.DateUtil;
import com.gmail.bradik.resumebuilder.util.Mode;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

abstract public class ResumeDataServiceBase implements ResumeDataService {

    private final Map<Pattern, String> patterns;
    private final Pattern compileLines;
    private final Pattern compileAllText;

    {
        final int flags = Pattern.MULTILINE;

        patterns = new LinkedHashMap<>();
        patterns.put(Pattern.compile("(\\*([^\\s]*)\\*)", flags), "<strong>$2</strong>");
        patterns.put(Pattern.compile("(^\\s*\\*(.*))", flags), "<li>$2</li>");

        compileLines = Pattern.compile("<li>|<div>");

        compileAllText = Pattern.compile("<li>", flags);
    }

    @Autowired
    private MessageService messageService;

    abstract protected Mode getMode();

    @Override
    public String getResource(String resourceName) {
        return messageService.getProperty(getMode(), resourceName);
    }

    @Override
    public String getWorkExperience(ResumeData resumeData) {
        Experiences experiences = resumeData.getExperiences();

        LocalDate start = DateUtil.toLocalDate(experiences.getStart(), "yyyy-MM");
        LocalDate end;
        if (Strings.isEmpty(experiences.getEnd())) {
            end = LocalDate.now();
        } else {
            end = DateUtil.toLocalDate(experiences.getEnd(), "yyyy-MM");
        }

        return messageService.getProperty(getMode(), "work.experience", end.getYear() - start.getYear());
    }

    @Override
    public String getFormattedDescription(String textContent) {

        for (Map.Entry<Pattern, String> entry : patterns.entrySet()) {
            Pattern pattern = entry.getKey();
            textContent = pattern.matcher(textContent).replaceAll(entry.getValue());
        }

        StringBuilder builder = new StringBuilder();
        String[] strings = textContent.split("\\n");
        for (String line : strings) {
            if (line.trim().isEmpty())
                continue;

            if (compileLines.matcher(line).find()) {
                builder.append(line);
            } else
                builder.append("<div>" + line + "</div>");

            builder.append("\n");
        }

        textContent = builder.toString();

        if (compileAllText.matcher(textContent).find())
            textContent = "<lu>" + textContent + "</lu>";
        else
            textContent = "<div>" + textContent + "</div>";


        return textContent;
    }

}
