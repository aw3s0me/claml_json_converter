import elements.Block;
import elements.Chapter;
import elements.Disease;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by korovin on 12/9/2016.
 */
public class ClamlConverter implements IClamlConverter {
    private Document dom;
    private XPath xpath;
    private Map<String, Chapter> chapters;
    private Map<String, Block> blocks = new HashMap<String, Block>();
    private Map<String, Disease> diseases = new HashMap<String, Disease>();


    public ClamlConverter(Document dom) {
        this.dom = dom;
        XPathFactory factory = XPathFactory.newInstance();
        this.xpath = factory.newXPath();
    }

    public void convertToJson() throws XPathExpressionException {
        this.chapters = getChapters();
        this.blocks = initBlocks();
        this.diseases = getDiseases();
    }

    private Map<String, Chapter> getChapters() throws XPathExpressionException {
        String chapterExpression = "/ClaML/Class[@kind='chapter']";
        NodeList chaptersNodes = (NodeList) this.xpath.compile(chapterExpression).evaluate(this.dom, XPathConstants.NODESET);
        Map<String, Chapter> chapters = new HashMap<String, Chapter>();
        for (int i = 0; i < chaptersNodes.getLength(); i++) {
            if (chaptersNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) chaptersNodes.item(i);
                Chapter chapter = new Chapter(el);
                System.out.println(chapter);
                chapters.put(chapter.getCode(), chapter);
            }
        }
        return chapters;
    }

    private Map<String, Block> initBlocks() { return null; }

    private Map<String, Disease> getDiseases() {
        return null;
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        FileLoader loader = new FileLoader();
        Document dom = loader.getDom("icd.claml.xml");
        ClamlConverter converter = new ClamlConverter(dom);
        converter.convertToJson();
    }
}
