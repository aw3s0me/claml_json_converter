package elements;

import org.w3c.dom.Element;

import javax.xml.xpath.XPathExpressionException;

/**
 * Created by korovin on 12/9/2016.
 */
public class Disease extends ClassKind {
    public Disease(Element xmlNode) throws XPathExpressionException {
        super(xmlNode, "");
    }
}
