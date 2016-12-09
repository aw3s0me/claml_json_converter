package elements;

import org.w3c.dom.Element;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

/**
 * Created by korovin on 12/9/2016.
 */
public class BaseClamlEl {
    protected XPath xpath = XPathFactory.newInstance().newXPath();

    protected String fetchName(Element element) {
        // TODO: create xpath and fetch name from Rubric with attr value preferred
        return "";
    }
}
