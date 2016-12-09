package elements;

import org.w3c.dom.Element;

import javax.xml.xpath.XPathExpressionException;

/**
 * Created by korovin on 12/9/2016.
 */
public class Block extends ClassKind {
    public Block(Element xmlNode) throws XPathExpressionException {
        super(xmlNode, "/ClaML/Class[@kind='block' and @code='%1$s']");
    }

    @Override
    public String toString() {
        return "Block{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
