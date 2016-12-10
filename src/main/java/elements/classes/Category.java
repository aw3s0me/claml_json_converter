package elements.classes;

import org.json.simple.JSONObject;
import org.w3c.dom.Element;

import javax.xml.xpath.XPathExpressionException;

/**
 * Created by korovin on 12/9/2016.
 */
public class Category extends ClassKind {
    public Category(Element xmlNode) throws XPathExpressionException {
        super(xmlNode, "/ClaML/Class[@kind='category' and @code='%1$s']");
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
