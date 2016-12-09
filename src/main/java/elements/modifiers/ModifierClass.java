package elements.modifiers;

import elements.base.ClamlBase;
import org.w3c.dom.Element;

/**
 * Created by akorovin on 09.12.2016.
 */
public class ModifierClass extends ClamlBase {
    // initial modifier code
    protected String isPartOf;

    public ModifierClass(Element xmlNode) {
        super(xmlNode);
        this.isPartOf = xmlNode.getAttribute("modifier");
    }

    public String getIsPartOf() {
        return isPartOf;
    }
}
