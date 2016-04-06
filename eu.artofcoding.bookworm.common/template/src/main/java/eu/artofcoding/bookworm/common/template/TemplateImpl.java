package eu.artofcoding.bookworm.common.template;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.StringTemplateSource;
import com.github.jknack.handlebars.io.TemplateSource;
import com.github.jknack.handlebars.io.URLTemplateSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

@Component
public class TemplateImpl implements Template {

    private String renderTemplate(final Map<String, String> values, final TemplateSource templateSource) {
        final Handlebars handlebars = new Handlebars();
        try {
            final com.github.jknack.handlebars.Template _template = handlebars.compile(templateSource);
            final Context context = Context.newContext(values);
            return _template.apply(context);
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    public String render(final String template, final Map<String, String> values) {
        final TemplateSource templateSource = new StringTemplateSource("afile", template);
        return renderTemplate(values, templateSource);
    }

    @Override
    public String render(final URL template, final Map<String, String> values) {
        final TemplateSource templateSource = new URLTemplateSource("afile", template);
        return renderTemplate(values, templateSource);
    }

}
