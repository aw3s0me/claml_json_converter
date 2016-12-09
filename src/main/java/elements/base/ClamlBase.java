package elements.base;

import org.w3c.dom.Element;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

/**
 * Created by akorovin on 09.12.2016.
 */
public abstract class ClamlBase {
    protected XPath xpath = XPathFactory.newInstance().newXPath();
    protected String code;

    public ClamlBase(Element xmlNode) {
        this.code = xmlNode.getAttribute("code");
    }

    public String getCode() {
        return code;
    }
}
