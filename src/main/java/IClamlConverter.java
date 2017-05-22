import org.json.simple.JSONObject;

import javax.xml.xpath.XPathExpressionException;

/**
 * Created by korovin on 12/9/2016.
 */
public interface IClamlConverter {
    JSONObject convertToJson() throws XPathExpressionException, InstantiationException, IllegalAccessException;
}
