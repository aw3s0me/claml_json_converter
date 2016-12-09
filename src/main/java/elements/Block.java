package elements;

import org.json.simple.JSONObject;
import org.w3c.dom.Element;

import javax.xml.xpath.XPathExpressionException;

/**
 * Created by korovin on 12/9/2016.
 */
public class Block extends ClassKind {
    public Block(Element xmlNode) throws XPathExpressionException {
        super(xmlNode, "/ClaML/Class[@kind='block' and @code='%1$s']");
    }

    @Override
    public String toString() {
        return "Block{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("code", this.getCode());
        obj.put("name", this.getName());
        obj.put("hasPart", this.getHasPartJSON());
        obj.put("isPartOf", this.getIsPartOf());
        return obj;
    }
}
