package elements.modifiers;

import elements.base.ClamlBase;
import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Created by akorovin on 09.12.2016.
 */
public class Modifier extends ClamlBase {
    private ArrayList<ModifierClass> modifiers;

    public Modifier(Element xmlNode) {
        super(xmlNode);
        // TODO: init modifier class
        // TODO: fetch all children
    }
}
