package elements.classes;

import org.json.simple.JSONObject;
import org.w3c.dom.Element;

import javax.xml.xpath.XPathExpressionException;

/**
 * Created by korovin on 12/9/2016.
 */
public class Chapter extends ClassKind {
    public Chapter(Element xmlNode) throws XPathExpressionException {
        super(xmlNode, "/ClaML/Class[@kind='chapter' and @code='%1$s']");
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("code", this.getCode());
        obj.put("name", this.getName());
        obj.put("hasPart", this.getHasPartJSON());
        return obj;
    }
}
