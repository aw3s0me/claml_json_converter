package elements.modifiers;

import elements.base.ClamlBase;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Created by akorovin on 10.12.2016.
 * <ModifiedBy> Abstraction
 */
public class ModifiedBy extends ClamlBase {
    private boolean isOptional;

    public ModifiedBy(Element xmlNode) {
        super(xmlNode);
        this.isOptional = this.isInitModifierOptional(xmlNode);
    }

    private boolean isInitModifierOptional(Element el) {
        NodeList metaNodes = el.getElementsByTagName("Meta");
        if (metaNodes.getLength() == 0) {
            return false;
        }

        Element metaNode = (Element)metaNodes.item(0);
        return metaNode.getAttribute("value").equals("optional");
    }

    public boolean isOptional() {
        return isOptional;
    }
}
