package elements;

import org.w3c.dom.Element;

/**
 * Created by korovin on 12/9/2016.
 */
public class Chapter extends BaseClamlEl {
    private String name;
    private String code;

    public Chapter(Element xmlNode) {
        // TODO: map chapter
        this.name = this.fetchName(xmlNode);
        this.code = xmlNode.getAttribute("code");

    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
