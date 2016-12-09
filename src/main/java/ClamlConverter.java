import com.sun.org.apache.xpath.internal.operations.Mod;
import elements.classes.ClassKind;
import elements.classes.Block;
import elements.classes.Chapter;
import elements.classes.Category;
import elements.modifiers.Modifier;
import elements.modifiers.ModifierClass;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by korovin on 12/9/2016.
 */
public class ClamlConverter implements IClamlConverter {
    private Document dom;
    private XPath xpath;
    private Map<String, Chapter> chapters;
    private Map<String, Block> blocks;
    private Map<String, Category> diseases;
    private Map<String, Modifier> modifiers;

    public ClamlConverter(Document dom) {
        this.dom = dom;
        XPathFactory factory = XPathFactory.newInstance();
        this.xpath = factory.newXPath();
    }

    public void convertToJson() throws XPathExpressionException, InstantiationException, IllegalAccessException {
        this.modifiers = this.getModifiers();
        this.chapters = this.getClassKindInstances(Chapter.class);
        this.blocks = this.getClassKindInstances(Block.class);
        this.diseases = this.getClassKindInstances(Category.class);
    }

    private <T extends ClassKind> Map<String, T> getClassKindInstances(Class<T> classType) throws XPathExpressionException, IllegalAccessException, InstantiationException {
        String expression = String.format("/ClaML/Class[@kind='%1$s']", classType.getSimpleName().toLowerCase());
        NodeList classKindNodes = (NodeList) this.xpath.compile(expression).evaluate(this.dom, XPathConstants.NODESET);
        Map<String, T> classMap = new HashMap<>();
        for (int i = 0; i < classKindNodes.getLength(); i++) {
            if (classKindNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) classKindNodes.item(i);
                ClassKind classKind = ClassKindFactory.getClassKind(el);
                System.out.println(classKind);
                classMap.put(classKind.getCode(), classType.cast(classKind));
            }
        }
        return classMap;
    }

    private Map<String, Modifier> getModifiers() throws XPathExpressionException {
        Map<String, Modifier> modifiers = new HashMap<>();

        NodeList modifierNodeList = this.dom.getElementsByTagName("Modifier");
        for (int i = 0; i < modifierNodeList.getLength(); i++) {
            if (modifierNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) modifierNodeList.item(i);
                Modifier modifier = new Modifier(el);
                System.out.println(modifier);
                modifiers.put(modifier.getCode(), modifier);

                // TODO: init here modifier class for modifier
            }
        }

        return modifiers;
    }

    private ArrayList<ModifierClass> getModifierClasses(Modifier modifier) {
        String expression = String.format("/ClaML/ModifierClass[@modifier='%1$s']", modifier.getCode());
        
        return null;
    }

    private String getFinalResult() {
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();
        for (Category category: this.diseases.values()) {
            JSONObject categoryJSON = category.toJSON();
            System.out.println(categoryJSON);
            arr.add(categoryJSON);
        }
        obj.put("diseases", arr);
        return obj.toJSONString();
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, IllegalAccessException, InstantiationException {
        FileLoader loader = new FileLoader();
        Document dom = loader.getDom("icd.claml.min.xml");
        System.out.println(dom.getDocumentElement().getAttribute("version"));
        ClamlConverter converter = new ClamlConverter(dom);
        converter.convertToJson();
    }
}
