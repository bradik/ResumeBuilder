package com.gmail.bradik.resumebuilder.render;

import com.gmail.bradik.resumebuilder.util.ResumeUtil;
import freemarker.cache.ClassTemplateLoader;
import freemarker.core.HTMLOutputFormat;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FreemarkerUtils {
    private static final Version FREEMARKER_VERSION = Configuration.VERSION_2_3_28;
    /**
     * Render from separate FTL file
     *
     * @param dataModel        - values
     * @param ftlTemplateRelativePath - relative path to ftl template
     * @return - String
     */
    public static String render(Map<String, Object> dataModel, String ftlTemplateRelativePath) {
        Configuration cfg = getConfiguration();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            Template template = cfg.getTemplate(ftlTemplateRelativePath);
            template.process(dataModel, new OutputStreamWriter(baos));
        } catch (TemplateException | IOException ex) {
            log.error("Template rendering error", ex);
        }

        return baos.toString();
    }

    @SneakyThrows
    public static void addEnumContext(Map<String, Object> dataModel) {
        BeansWrapper wrapper = new BeansWrapper(FREEMARKER_VERSION);
        TemplateHashModel enumModels = wrapper.getEnumModels();
//
//        dataModel.put("Currency", enumModels.get(Currency.class.getCanonicalName()));
    }

    @SneakyThrows
    public static void addUtilContext(Map<String, Object> dataModel) {
        BeansWrapper wrapper = new BeansWrapper(FREEMARKER_VERSION);
        TemplateHashModel statics = wrapper.getStaticModels();

        dataModel.put("ResumeUtil", statics.get(ResumeUtil.class.getCanonicalName()));
//
//        dataModel.put("CurrencyUtils", statics.get(CurrencyUtils.class.getCanonicalName()));
//        dataModel.put("CardUtils", statics.get(CardUtils.class.getCanonicalName()));
//        dataModel.put("MaskingUtil", statics.get(MaskingUtil.class.getCanonicalName()));
    }

    private static Configuration getConfiguration() {
        Configuration cfg = new Configuration(FREEMARKER_VERSION);
        ClassTemplateLoader loader = new ClassTemplateLoader(FreemarkerUtils.class, "/");
        cfg.setOutputFormat(HTMLOutputFormat.INSTANCE);
        cfg.setTemplateLoader(loader);
        cfg.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        cfg.setNumberFormat("computer");
        cfg.setLocale(new Locale("ru", "RU"));
        return cfg;
    }
}
