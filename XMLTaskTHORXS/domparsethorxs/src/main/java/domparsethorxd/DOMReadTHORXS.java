package domparsethorxd;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class DOMReadTHORXS {
    public static void main(String[] args) {
        try {
            // Read the XML Document
            URL url = DOMReadTHORXS.class.getClassLoader().getResource("XMLTHORXS.xml");
            File xmlFile = new File(url.toURI());
            FileInputStream fileInputStream = new FileInputStream(xmlFile);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document xmlDocument = documentBuilder.parse(fileInputStream);

            // Normalize the document
            xmlDocument.normalize();

            // XPATH queries
            System.out.println("Select the first library:");
            String first = "/database/libraries/library[1]";
            printNodeList(xmlDocument, first);

            System.out.println("Select the last author in the database:");
            String second = "/database/authors/author[last()]";
            printNodeList(xmlDocument, second);

            System.out.println("Select all books:");
            String third = "/database/books/book";
            printNodeList(xmlDocument, third);

        } catch (URISyntaxException | ParserConfigurationException | IOException | SAXException | XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints out the text values inside a NodeList
     * @param xmlDocument The xml document we want to read from
     * @param expression The XPATH query expression
     * @throws XPathExpressionException XPathExpressionException
     */
    private static void printNodeList(Document xmlDocument, String expression) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();

        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
        if (nodeList.getLength() == 0) {
            System.out.println("No nodes were found! Check your xpath expression!");
        } else if (nodeList.getLength() == 1) {
            System.out.println(nodeList.item(0).getTextContent());
        } else {
            for (int i = 0; i < nodeList.getLength(); i++) {
                System.out.println(i + ".");
                System.out.println(nodeList.item(i).getTextContent());
            }
        }
    }
}
