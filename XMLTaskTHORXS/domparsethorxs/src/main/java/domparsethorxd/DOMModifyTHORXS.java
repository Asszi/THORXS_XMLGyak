package domparsethorxd;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class DOMModifyTHORXS {
    public static void main(String[] args) {
        try {
            // Read the XML Document
            URL url = DOMReadTHORXS.class.getClassLoader().getResource("XMLTHORXS.xml");
            File xmlFile = new File(url.toURI());
            FileInputStream fileInputStream = new FileInputStream(xmlFile);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document xmlDocument = documentBuilder.parse(fileInputStream);

            Node first = xmlDocument.createElement("language");
            first.appendChild(xmlDocument.createTextNode("english"));
            addElement(xmlDocument, "/database/books/book", first);

            removeElement(xmlDocument, "/database/books/book", "isbn");

            Node third = xmlDocument.createElement("name");
            Node fName = xmlDocument.createElement("first_name");
            fName.appendChild(xmlDocument.createTextNode("Elek"));
            Node mName = xmlDocument.createElement("middle_name");
            mName.appendChild(xmlDocument.createTextNode("MODIFIED"));
            Node lName = xmlDocument.createElement("last_name");
            lName.appendChild(xmlDocument.createTextNode("Teszt"));
            third.appendChild(fName);
            third.appendChild(mName);
            third.appendChild(lName);
            modifyElement(xmlDocument, "/database/authors/author[1]", third);

            writeXML(xmlDocument);

        } catch (URISyntaxException | IOException | ParserConfigurationException | SAXException | TransformerException | XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a Document into file.
     * @param xmlDocument The XML document
     * @throws TransformerException TransformerException
     */
    private static void writeXML(Document xmlDocument) throws TransformerException {
        xmlDocument.normalizeDocument();

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(xmlDocument);
        StreamResult result = new StreamResult(new File("XMLTHORXS_modified.xml"));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);

        System.out.println("The XML file was modified successfully.");
    }

    /**
     * Appends a Node to every item of a NodeList
     * @param xmlDocument The XML document
     * @param expression The XPATH expression
     * @param element The Node element
     * @throws XPathExpressionException XPathExpressionException
     */
    private static void addElement(Document xmlDocument, String expression, Node element) throws XPathExpressionException {
        NodeList nodeList = getNodeList(xmlDocument, expression);

        if (nodeList.getLength() == 0) {
            System.out.println("The NodeList was empty, no elements were added!");
        } else {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                node.appendChild(element.cloneNode(true));
            }

            System.out.println("Elements successfully added!");
        }
    }

    /**
     * Removes a ChildNode from a Node
     * @param xmlDocument The XML document
     * @param expression The XPATH expression
     * @param elementName The name of the Node we want to remove
     * @throws XPathExpressionException XPathExpressionException
     */
    private static void removeElement(Document xmlDocument, String expression, String elementName) throws XPathExpressionException {
        NodeList nodeList = getNodeList(xmlDocument, expression);

        if (nodeList.getLength() == 0) {
            System.out.println("The NodeList was empty, no elements were removed!");
        } else {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                for (int j = 0; j < node.getChildNodes().getLength(); j++) {
                    Node childNode = node.getChildNodes().item(j);

                    if (childNode.getNodeName().equals(elementName)) {
                        node.removeChild(childNode);
                    }
                }
            }

            System.out.println("Elements were removed!");
        }
    }

    /**
     * Modifies the value of the desired element
     * @param xmlDocument The XML document
     * @param expression The XPATH expression
     * @param element The new Node
     * @throws XPathExpressionException XPathExpressionException
     */
    private static void modifyElement(Document xmlDocument, String expression, Node element) throws XPathExpressionException {
        NodeList nodeList = getNodeList(xmlDocument, expression);

        if (nodeList.getLength() == 0) {
            System.out.println("The NodeList was empty, no elements were modified!");
        } else {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                System.out.println(element.getChildNodes().item(2).getNodeName());

                for (int j = 0; j < node.getChildNodes().getLength(); j++) {
                    Node childNode = node.getChildNodes().item(j);

                    if (childNode.getNodeName().equals(element.getNodeName())) {
                        node.removeChild(childNode);
                        node.appendChild(element);
                    }
                }
            }

            System.out.println("Elements were modified!");
        }
    }

    /**
     * Helper function to return a list of node items
     * @param xmlDocument The XML document
     * @param expression The XPATH expression
     * @return  NodeList that contains the node items returned by the XPATH query
     * @throws XPathExpressionException XPathExpressionException
     */
    private static NodeList getNodeList(Document xmlDocument, String expression) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodeList = null;

        nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);

        return nodeList;
    }
}
