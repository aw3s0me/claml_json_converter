package elements.base;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

/**
 * Created by akorovin on 10.12.2016.
 * Base class for claml objects with name property
 */
public class LabelBase extends ClamlBase {
    protected String basePath;
    protected String name;

    /**
     * create xpath and fetch name from Rubric with attr value preferred
     * @param element
     * @return
     * @throws XPathExpressionException
     */
    protected String fetchName(Element element) throws XPathExpressionException {
        String path = this.basePath + "/Rubric[@kind='preferred']/Label";
        Node node = (Node) xpath.compile(path).evaluate(element, XPathConstants.NODE);

        return node.getTextContent();
    }

    public LabelBase(Element xmlNode, String basePath) throws XPathExpressionException {
        super(xmlNode);
        // map chapter
        this.basePath = String.format(basePath, this.code);
        this.name = this.fetchName(xmlNode);
    }

    public LabelBase(String code, String basePath, String name) {
        super(code);
        this.basePath = basePath;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
