import elements.Chapter;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by korovin on 12/9/2016.
 */
public class ClamlConverter implements IClamlConverter {
    private Document xmlData;
    private XPath xpath;
    private Map<String, Chapter> chapters = new HashMap<String, Chapter>();

    public ClamlConverter(Document xmlData) {
        this.xmlData = xmlData;
        XPathFactory factory = XPathFactory.newInstance();
        this.xpath = factory.newXPath();
    }

    public void convertToJson() {
        //
    }

    private Map<String, Chapter> getChapters() {
        return null;
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        FileLoader loader = new FileLoader();
        Document dom = loader.getDom("icd.claml.xml");
        System.out.println(dom.getDocumentElement());
    }
}
