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
import java.io.FileWriter;
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
    // private Map<String, Category> diseases;
    private Map<String, Modifier> modifiers;
    private JSONArray categoryJSONArr = new JSONArray();

    public ClamlConverter(Document dom) {
        this.dom = dom;
        XPathFactory factory = XPathFactory.newInstance();
        this.xpath = factory.newXPath();
    }

    public void convertToJson() throws XPathExpressionException, InstantiationException, IllegalAccessException {
        this.modifiers = this.getModifiers();
        this.chapters = this.getClassKindInstances(Chapter.class);
        this.blocks = this.getClassKindInstances(Block.class);
        this.initCategoriesJSON();
        this.saveResult("final.json", this.getFinalResult());
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

    /**
     * Saving categories to json array
     * Unchecked warning suppressed because JSONArray is used without generic type
     * @throws XPathExpressionException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @SuppressWarnings("unchecked")
    private void initCategoriesJSON() throws XPathExpressionException, IllegalAccessException, InstantiationException {
        String expression = "/ClaML/Class[@kind='category']";
        NodeList classKindNodes = (NodeList) this.xpath.compile(expression).evaluate(this.dom, XPathConstants.NODESET);
        for (int i = 0; i < 100; i++) {
        //for (int i = 0; i < classKindNodes.getLength(); i++) {
            if (classKindNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) classKindNodes.item(i);
                ClassKind classKind = ClassKindFactory.getClassKind(el);
                System.out.println(i);
                Category categoryToAdd = Category.class.cast(classKind);
                categoryJSONArr.add(categoryToAdd.toJSON());
                // classMap.put(classKind.getCode(), categoryToAdd);

                if (categoryToAdd.isModified()) {
                    this.createCategoriesByModifiers(categoryToAdd);
                }
            }
        }

    }

    private void createCategoriesByModifiers(Category category) {
        // check if optional or has subclasses. then skip it
        if (category.getModifiedBy().isOptional()|| category.hasChildren()) {
            return;
        }

        String code = category.getModifiedBy().getCode();
        Modifier modifier = this.modifiers.get(code);
        HashMap<String, ModifierClass> mClasses = modifier.getModifiers();

        for(ModifierClass mc: mClasses.values()) {
            String codeC = category.getCode() + mc.getCode();
            String name = category.getName() + " : " + mc.getName();
            String isPartOf = category.getCode();
            Category newCategory = new Category(codeC, "", name, null, isPartOf);
            categoryJSONArr.add(newCategory.toJSON());
            category.addChildCode(codeC);
            // this.diseases.put(newCategory.getCode(), newCategory);
        }
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
                // init here modifier class for modifier
                HashMap<String, ModifierClass> modifierClasses = this.getModifierClasses(modifier.getCode());
                modifier.setModifiers(modifierClasses);
            }
        }

        return modifiers;
    }

    private HashMap<String, ModifierClass> getModifierClasses(String modifierCode) throws XPathExpressionException {
        String expression = String.format("/ClaML/ModifierClass[@modifier='%1$s']", modifierCode);
        NodeList mcNodes = (NodeList) this.xpath.compile(expression).evaluate(this.dom, XPathConstants.NODESET);
        HashMap<String, ModifierClass> mcArr = new HashMap<>();
        for (int i = 0; i < mcNodes.getLength(); i++) {
            if (mcNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) mcNodes.item(i);
                ModifierClass mc = new ModifierClass(el);
                mcArr.put(mc.getCode(), mc);
            }
        }
        System.out.println(mcArr);

        return mcArr;
    }

    private JSONObject getFinalResult() {
        JSONObject obj = new JSONObject();
        obj.put("diseases", this.categoryJSONArr);
        return obj;
    }

    private void saveResult(String fileName, JSONObject obj) {
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(obj.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + obj);
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, IllegalAccessException, InstantiationException {
        FileLoader loader = new FileLoader();
        // Document dom = loader.getDom("icd.claml.min.xml");
        Document dom = loader.getDom("icd.claml.xml");
        System.out.println(dom.getDocumentElement().getAttribute("version"));
        ClamlConverter converter = new ClamlConverter(dom);
        converter.convertToJson();
    }
}
