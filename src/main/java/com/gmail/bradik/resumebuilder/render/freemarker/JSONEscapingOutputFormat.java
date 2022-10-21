package com.gmail.bradik.resumebuilder.render.freemarker;

import freemarker.core.CommonMarkupOutputFormat;
import freemarker.core.OutputFormat;
import freemarker.template.utility.StringUtil;

import java.io.IOException;
import java.io.Writer;

/**
 * Формат вывода для JSON (MIME type "application/json", name "UBJSON") с поддержкой экранирования. <br>
 * Использует встроенный {@code ?json_string} для экранирования.
 */
public class JSONEscapingOutputFormat extends CommonMarkupOutputFormat<TemplateJSONEscapingOutputModel> {

    /**
     * The only instance (singleton) of this {@link OutputFormat}.
     */
    public static final JSONEscapingOutputFormat INSTANCE = new JSONEscapingOutputFormat();

    protected JSONEscapingOutputFormat() {
        super();
    }

    /**
     * Имя формата
     */
    @Override
    public String getName() {
        return "JSONEscaping";
    }

    /**
     * MIME-тип формата
     */
    @Override
    public String getMimeType() {
        return "application/json";
    }

    /**
     * Экранирует вывод переменных с использованием {@code ?json_string}
     *
     * @param textToEsc текст для экранирования
     * @param out       поток вывода, куда будет записан результат
     */
    @Override
    public void output(String textToEsc, Writer out) throws IOException {
        String escaped = StringUtil.jsonStringEnc(textToEsc);
        out.write(escaped);
    }

    /**
     * Вызывается при вызове через {@code ?} <br>
     * Т.к. метод {@code isLegacyBuiltInBypassed()} всегда возвращает false, не должен вызываться
     *
     * @param plainTextContent Текст для экранирования
     * @return экранированный текст
     */
    @Override
    public String escapePlainText(String plainTextContent) {
        return StringUtil.jsonStringEnc(plainTextContent);
    }

    /**
     * Определяет может ли этот класс вызван при вызове в шаблоке через {@code ?builtInName}
     *
     * @return всегда false, т.к. есть встроенный {@code ?json_string}
     */
    @Override
    public boolean isLegacyBuiltInBypassed(String builtInName) {
        return false;
    }

    @Override
    protected TemplateJSONEscapingOutputModel newTemplateMarkupOutputModel(String plainTextContent, String markupContent) {
        return new TemplateJSONEscapingOutputModel(plainTextContent, markupContent);
    }
}
