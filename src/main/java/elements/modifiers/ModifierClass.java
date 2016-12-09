package elements.modifiers;

import elements.base.ClamlBase;
import elements.base.LabelBase;
import org.w3c.dom.Element;

import javax.xml.xpath.XPathExpressionException;

/**
 * Created by akorovin on 09.12.2016.
 */
public class ModifierClass extends LabelBase {
    // initial modifier code
    protected String isPartOf;

    public ModifierClass(Element xmlNode) throws XPathExpressionException {
        super(xmlNode, String.format("/ClaML/ModifierClass[@modifier='%1$s'", xmlNode.getAttribute("modifier")) + " and @code='%1$s']");
        this.isPartOf = xmlNode.getAttribute("modifier");
    }

    public String getIsPartOf() {
        return isPartOf;
    }
}
