package elements.classes;

import elements.modifiers.ModifiedBy;
import elements.modifiers.ModifierClass;
import org.json.simple.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpressionException;
import java.util.List;

/**
 * Created by korovin on 12/9/2016.
 */
public class Category extends ClassKind {
    private ModifiedBy modifiedBy;

    private ModifiedBy getModifiedBy(Element xmlNode) {
        NodeList modifiedByNode = xmlNode.getElementsByTagName("ModifiedBy");
        if (modifiedByNode.getLength() == 0) {
            return null;
        }

        return new ModifiedBy((Element)modifiedByNode.item(0));
    }

    public Category(Element xmlNode) throws XPathExpressionException {
        super(xmlNode, "/ClaML/Class[@kind='category' and @code='%1$s']");
        this.modifiedBy = this.getModifiedBy(xmlNode);
    }

    public Category(String code, String basePath, String name, List<String> childrenCodes, String isPartOf) {
        super(code, basePath, name, childrenCodes, isPartOf);
    }

    public boolean isModified() {
        return this.modifiedBy != null;
    }

    public ModifiedBy getModifiedBy() {
        return modifiedBy;
    }

    @Override
    public String toString() {
        return "Category{" +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", hasPart=" + childrenCodes +
                ", isPartOf='" + isPartOf + '\'' +
                '}';
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("code", this.getCode());
        obj.put("name", this.getName());
        if (this.hasChildren()) {
            obj.put("hasPart", this.getHasPartJSON());
        }
        obj.put("isPartOf", this.getIsPartOf());
        return obj;
    }
}
