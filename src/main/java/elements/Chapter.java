package elements;

import org.w3c.dom.Element;

import javax.xml.xpath.XPathExpressionException;

/**
 * Created by korovin on 12/9/2016.
 */
public class Chapter extends BaseClamlEl {
    private String name;
    private String code;

    public Chapter(Element xmlNode) throws XPathExpressionException {
        // map chapter
        this.code = xmlNode.getAttribute("code");
        String pathToName = String.format("/ClaML/Class[@kind='chapter' and @code='%1$s']/Rubric[@kind='preferred']/Label", this.code);
        this.name = this.fetchName(xmlNode, pathToName);
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
