package com.gmail.bradik.resumebuilder.render;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;


@Slf4j
@Component
public class ScreenTemplateRender {

    private final ObjectMapper mapper = new ObjectMapper();

    public Serializable render(Map<String, Object> data, String template) {
        try {
            FreemarkerUtils.addUtilContext(data);

            FreemarkerUtils.addEnumContext(data);

            String strOutput = FreemarkerUtils.render(data, template);

            if (log.isDebugEnabled()) {
                log.debug("\nFreemarker out:\n{}", strOutput);
            }

            return (Serializable) mapper.readValue(strOutput, Object.class);
        } catch (IOException e) {
            log.error("Freemarker out error", e);
        }

        return null;
    }
}