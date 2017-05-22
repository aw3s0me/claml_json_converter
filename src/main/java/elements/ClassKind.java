package elements;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by korovin on 12/9/2016.
 */
public abstract class ClassKind {
    // protected XPath xpath = XPathFactory.newInstance().newXPath();
    protected String name;
    protected String code;
    protected Map<String, ClassKind> children = new HashMap<>();
    protected List<String> childrenCodes;
    protected String basePath;
    // parent code
    protected String isPartOf;

    public ClassKind(Element xmlNode, String basePath)  throws XPathExpressionException {
        // map chapter
        this.code = xmlNode.getAttribute("code");
        this.basePath = String.format(basePath, this.code);
        this.name = this.fetchName(xmlNode);
        this.childrenCodes = this.fetchChildrenCodes(xmlNode);
        this.isPartOf = this.fetchParentCode(xmlNode);
    }

    /**
     * create xpath and fetch name from Rubric with attr value preferred
     * @param element
     * @return
     * @throws XPathExpressionException
     */
    protected String fetchName(Element element) {
        Element rubric = this.getLabelRubric(element);
        if (rubric == null) {
            throw new IllegalArgumentException("Element does not contain label");
        }
        Node label = rubric.getElementsByTagName("Label").item(0);

        return label.getTextContent();
    }

    protected Element getLabelRubric(Element element) {
        NodeList nodes = element.getElementsByTagName("Rubric");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element el = (Element)nodes.item(i);
            if (el.getAttribute("kind").equals("preferred")) {
                return el;
            }
        }

        return null;
    }

    protected List<String> fetchChildrenCodes(Element element) {
        ArrayList<String> childrenCodes = new ArrayList<>();
        NodeList subclasses = element.getElementsByTagName("SubClass");
        for (int i = 0; i < subclasses.getLength(); i++) {
            if (subclasses.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) subclasses.item(i);
                childrenCodes.add(el.getAttribute("code"));
            }
        }
        return childrenCodes;
    }

    protected String fetchParentCode(Element element) {
        Node node = element.getElementsByTagName("SuperClass").item(0);
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
