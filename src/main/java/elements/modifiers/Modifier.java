package elements.modifiers;

import elements.base.ClamlBase;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by akorovin on 09.12.2016.
 */
public class Modifier extends ClamlBase {
    private HashMap<String, ModifierClass> modifiers;

    public Modifier(Element xmlNode) {
        super(xmlNode);
    }

    private int getModifierClassNum(Element xmlNode) {
        return xmlNode.getElementsByTagName("SubClass").getLength();
    }

    public HashMap<String, ModifierClass> getModifiers() {
        return modifiers;
    }

    public void setModifiers(HashMap<String, ModifierClass> modifiers) {
        this.modifiers = modifiers;
    }

    @Override
    public String toString() {
        return "Modifier{" +
                "code='" + code + '\'' +
                '}';
    }
}
