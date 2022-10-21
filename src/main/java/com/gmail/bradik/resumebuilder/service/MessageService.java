package com.gmail.bradik.resumebuilder.service;

import com.gmail.bradik.resumebuilder.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.gmail.bradik.resumebuilder.util.Mode;

import java.nio.charset.StandardCharsets;

@Service
public class MessageService {

    @Autowired
    private Environment env;

    public String getProperty(Mode mode, String name, Object... objs) {
        String propName = mode.toString().toLowerCase() + "." + name;
        String msg = env.getProperty(propName);
        if (msg != null) {
            msg = new String(msg.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        } else {
            throw new IllegalArgumentException(StringUtil.format("Property {} not found", propName));
        }
        return StringUtil.format(msg, objs);
    }
}
