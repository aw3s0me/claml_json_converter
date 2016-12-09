package elements.classes;

import elements.base.ClamlBase;
import elements.base.LabelBase;
import org.json.simple.JSONArray;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.json.simple.JSONObject;

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
public abstract class ClassKind extends LabelBase {
    protected Map<String, ClassKind> children = new HashMap<>();
    protected List<String> childrenCodes;
    // parent code
    protected String isPartOf;

    public ClassKind(Element xmlNode, String basePath)  throws XPathExpressionException {
        super(xmlNode, basePath);

        this.childrenCodes = this.fetchChildrenCodes(xmlNode);
        this.isPartOf = this.fetchParentCode(xmlNode);
    }

    protected List<String> fetchChildrenCodes(Element element) throws XPathExpressionException {
        ArrayList<String> childrenCodes = new ArrayList<String>();
        String path = this.basePath + "/SubClass";
        NodeList subclasses = (NodeList) this.xpath.compile(path).evaluate(element, XPathConstants.NODESET);
        for (int i = 0; i < subclasses.getLength(); i++) {
            if (subclasses.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) subclasses.item(i);
                childrenCodes.add(el.getAttribute("code"));
            }
        }
        return childrenCodes;
    }

    protected String fetchParentCode(Element element) throws XPathExpressionException {
        String path = this.basePath + "/SuperClass";
        Node node = (Node) xpath.compile(path).evaluate(element, XPathConstants.NODE);
        return node != null? node.getAttributes().getNamedItem("code").getNodeValue() : null;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "ClassKind{" +
                "xpath=" + xpath +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", children=" + children +
                ", childrenCodes=" + childrenCodes +
                ", basePath='" + basePath + '\'' +
                ", isPartOf='" + isPartOf + '\'' +
                '}';
    }

    public abstract JSONObject toJSON();

    public JSONArray getHasPartJSON() {
        JSONArray hasPart = new JSONArray();
        hasPart.addAll(this.childrenCodes);
        return hasPart;
    }

    public boolean hasChildren() {
        return !this.childrenCodes.isEmpty();
    }

    public String getIsPartOf() {
        return isPartOf;
    }
}
