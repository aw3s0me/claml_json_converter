package elements;

import org.w3c.dom.Element;

import javax.xml.xpath.XPathExpressionException;

/**
 * Created by korovin on 12/9/2016.
 */
public class Chapter extends BaseClamlEl {
    public Chapter(Element xmlNode) throws XPathExpressionException {
        super(xmlNode, "/ClaML/Class[@kind='chapter' and @code='%1$s']");
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
