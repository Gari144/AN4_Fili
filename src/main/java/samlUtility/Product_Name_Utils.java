package samlUtility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import constants.FrameworkConstants;
import excelReader.ExcelReader;
import testController.TestController;

public class Product_Name_Utils
{

	public static ExcelReader reader = new ExcelReader(System.getProperty("user.dir")+"\\src\\test\\java\\testData\\TestSuite1Data.xlsx");

	public static String convertXMLDocumentToString(Document xmlData, Boolean omitXMLDeclaration) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            if (omitXMLDeclaration) transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(xmlData), new StreamResult(writer));
            return writer.getBuffer().toString();
        } catch (TransformerException e) {
            return "";
        }

    }

    public static String getCData(Document xmlDoc) {
        try {
            XPath xPath1 = XPathFactory.newInstance().newXPath();
            String expression1 = "/Response/Assertion/AttributeStatement/Attribute[@Name='data']";
            String cdata = "";
            NodeList customerNodes1 = (NodeList) xPath1.compile(expression1).evaluate(xmlDoc, XPathConstants.NODESET);
            for (int i = 0; i < customerNodes1.getLength(); i++) {
                NodeList children1 = customerNodes1.item(i).getChildNodes();
                for (int j = 0; j < children1.getLength(); j++) {
                    String childAttributeNode1 = children1.item(j).getNodeName();
                    cdata = children1.item(j).getTextContent();
                }
            }
            return cdata;

        } catch (XPathExpressionException e) {
            return "";
        }
    }

    public static Document getDataDocWithCDataPlaceHolder(Document xmlDoc) {
        try {
            XPath xPath1 = XPathFactory.newInstance().newXPath();
            String expression1 = "/Response/Assertion/AttributeStatement/Attribute[@Name='data']";
            NodeList customerNodes1 = (NodeList) xPath1.compile(expression1).evaluate(xmlDoc, XPathConstants.NODESET);
            for (int i = 0; i < customerNodes1.getLength(); i++) {
                NodeList children1 = customerNodes1.item(i).getChildNodes();
                for (int j = 0; j < children1.getLength(); j++) {
                    String childAttributeNode = children1.item(j).getNodeName();
                    //System.out.println("**************" + childAttributeNode);
                    System.out.println("\n");
                    children1.item(j).setTextContent(String.valueOf("C_DATA"));
                }
            }
            return xmlDoc;
        } catch (XPathExpressionException e) {
            return xmlDoc;
        }
    }

    public static Document getModifiedCDataProductName(Document xmlDoc) {
    	String SystemTime = TimeStamp("yyhhmmss");
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            String cDataExpression = "/Data/Extensions/Extension/Product/Names/Name";
            NodeList customerNodes = (NodeList) xPath.compile(cDataExpression).evaluate(xmlDoc, XPathConstants.NODESET);

            for (int i = 0; i < customerNodes.getLength(); i++) {
                NodeList children = customerNodes.item(i).getChildNodes();
                for (int j = 0; j < children.getLength(); j++) {
                    String childAttributeNode = children.item(j).getNodeName();
                    String childValueNode = children.item(j).getTextContent();

                    if (childValueNode.equalsIgnoreCase("DISPLAY")) {
                        children.item(j + 1).setTextContent(String.valueOf(reader.getCellData(TestController.TestCaseID, "ProductName", 2)));
                    }
                }
            }
            return xmlDoc;
        } catch (XPathExpressionException e) {
            return xmlDoc;
        }
    }

    public static String getXMLString() throws ParserConfigurationException, SAXException, IOException{

        // XML to StringXML convert
        File xmlFile = new File(FrameworkConstants.getSAMLFilePath());

		Reader fileReader = new FileReader(xmlFile);
		BufferedReader bufReader = new BufferedReader(fileReader);

		StringBuilder sb = new StringBuilder();
		String line = bufReader.readLine();
		while( line != null){
			    sb.append(line.trim());
	            line = bufReader.readLine();
	        }
		 String xml2String = sb.toString();
		 //System.out.println(xml2String);
		 bufReader.close();
		 return xml2String;

    }

    // write doc to output stream
    public static void writeXml(Document doc,
                                 OutputStream output)
            throws TransformerException, UnsupportedEncodingException {

    	 TransformerFactory transformerFactory = TransformerFactory.newInstance();
         Transformer transformer = transformerFactory.newTransformer();

         // pretty print
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         transformer.setOutputProperty(OutputKeys.STANDALONE, "no");

         DOMSource source = new DOMSource(doc);
         StreamResult result = new StreamResult(output);
         transformer.transform(source, result);

    }

    // convert String To Document
    public static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse( new InputSource( new StringReader(xmlStr)));
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
     }

     public static String TimeStamp(String format) {
		Calendar cal = Calendar.getInstance();
		String pattern = "ddMMyyhhmmss";
		SimpleDateFormat fm = new SimpleDateFormat(pattern);
		return fm.format(cal.getTime());
	 }

}
