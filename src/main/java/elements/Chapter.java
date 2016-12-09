package elements;

import org.w3c.dom.Element;

/**
 * Created by korovin on 12/9/2016.
 */
public class Chapter {
    private String name;
    private String code;

    public Chapter(Element xmlNode) {
        // TODO: map chapter
        this.name = "test";
        this.code = "I";
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
