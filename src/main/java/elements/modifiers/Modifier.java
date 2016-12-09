package elements.modifiers;

import elements.base.ClamlBase;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created by akorovin on 09.12.2016.
 */
public class Modifier extends ClamlBase {
    private ArrayList<ModifierClass> modifiers;

    public Modifier(Element xmlNode) {
        super(xmlNode);
        // initialize array list of modifiers
        this.modifiers = new ArrayList<>(this.getModifierClassNum(xmlNode));
    }

    private int getModifierClassNum(Element xmlNode) {
        return xmlNode.getElementsByTagName("SubClass").getLength();
    }

    public ArrayList<ModifierClass> getModifiers() {
        return modifiers;
    }
}
