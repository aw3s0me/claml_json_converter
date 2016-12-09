package elements;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Created by korovin on 12/9/2016.
 */
public class BaseClamlEl {
    protected XPath xpath = XPathFactory.newInstance().newXPath();
    protected String name;
    protected String code;

    /**
     * create xpath and fetch name from Rubric with attr value preferred
     * @param element
     * @param path
     * @return
     * @throws XPathExpressionException
     */
    protected String fetchName(Element element, String path) throws XPathExpressionException {
        Node node = (Node) xpath.compile(path).evaluate(element, XPathConstants.NODE);

        return node.getTextContent();
    }
}
