package com.gmail.bradik.resumebuilder.render.freemarker;

import freemarker.core.CommonMarkupOutputFormat;
import freemarker.core.CommonTemplateMarkupOutputModel;

public class TemplateJSONEscapingOutputModel extends CommonTemplateMarkupOutputModel<TemplateJSONEscapingOutputModel> {

    /**
     * A least one of the parameters must be non-{@code null}!
     */
    protected TemplateJSONEscapingOutputModel(String plainTextContent, String markupContent) {
        super(plainTextContent, markupContent);
    }

    @Override
    public CommonMarkupOutputFormat<TemplateJSONEscapingOutputModel> getOutputFormat() {
        return JSONEscapingOutputFormat.INSTANCE;
    }
}
