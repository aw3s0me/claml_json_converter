package elements;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by korovin on 12/9/2016.
 */
public class BaseClamlEl {
    protected XPath xpath = XPathFactory.newInstance().newXPath();
    protected String name;
    protected String code;
    protected Map<String, BaseClamlEl> children = new HashMap<String, BaseClamlEl>();
    protected List<String> childrenCodes = new ArrayList<String>();
    protected String basePath;

    public BaseClamlEl(Element xmlNode, String basePath)  throws XPathExpressionException {
        // map chapter
        this.code = xmlNode.getAttribute("code");
        this.basePath = String.format(basePath, this.code);
        this.name = this.fetchName(xmlNode);
    }

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

    protected List<String> fetchChildrenCodes(Element element, String path) throws XPathExpressionException {
        return null;
    }
}
