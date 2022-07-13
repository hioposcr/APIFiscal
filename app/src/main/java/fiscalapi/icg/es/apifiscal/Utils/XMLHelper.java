package fiscalapi.icg.es.apifiscal.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLHelper {

    public static String getXMLFromBooleanMap(String rootName, String groupName, String paramName, HashMap<String, Boolean> values) {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document document = docBuilder.newDocument();
            Element rootNode = (Element) document.appendChild(document.createElement(rootName));
            Element attributesNode = (Element) rootNode.appendChild(document.createElement(groupName));

            for (Map.Entry<String, Boolean> entry : values.entrySet()) {
                String key = entry.getKey();
                Boolean value = entry.getValue();
                Element param = (Element) attributesNode.appendChild(document.createElement(paramName));
                param.setAttribute("Key", key);
                param.setTextContent(value ? "True" : "False");
            }

            // Write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StringWriter xmlWriter = new StringWriter();
            StreamResult result = new StreamResult(xmlWriter);

            transformer.transform(source, result);
            xmlWriter.flush();
            return xmlWriter.toString();
        } catch (Exception ex) {
            return "";
        }
    }
}
