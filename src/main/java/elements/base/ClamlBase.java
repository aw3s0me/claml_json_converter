package elements.base;

import org.w3c.dom.Element;

/**
 * Created by akorovin on 09.12.2016.
 */
public abstract class ClamlBase {
    protected String code;

    public ClamlBase(Element xmlNode) {
        this.code = xmlNode.getAttribute("code");
    }
}
