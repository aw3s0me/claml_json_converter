package elements;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
    protected List<String> childrenCodes;
    protected String basePath;

    public BaseClamlEl(Element xmlNode, String basePath)  throws XPathExpressionException {
        // map chapter
        this.code = xmlNode.getAttribute("code");
        this.basePath = String.format(basePath, this.code);
        this.name = this.fetchName(xmlNode);
        this.childrenCodes = this.fetchChildrenCodes(xmlNode);
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

    protected List<String> fetchChildrenCodes(Element element) throws XPathExpressionException {
        ArrayList<String> childrenCodes = new ArrayList<String>();
        String path = this.basePath + "/SubClass";
        NodeList subclasses = (NodeList) this.xpath.compile(path).evaluate(element, XPathConstants.NODESET);
        for (int i = 0; i < subclasses.getLength(); i++) {
            if (subclasses.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) subclasses.item(i);
                System.out.println(el.getAttribute("code"));
                childrenCodes.add(el.getAttribute("code"));
            }
        }
        return childrenCodes;
    }
}
