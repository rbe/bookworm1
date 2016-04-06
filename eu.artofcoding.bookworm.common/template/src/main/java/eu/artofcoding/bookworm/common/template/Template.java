package eu.artofcoding.bookworm.common.template;

import java.net.URL;
import java.util.Map;

public interface Template {

    String render(String template, Map<String, String> values);

    String render(URL template, Map<String, String> values);

}
