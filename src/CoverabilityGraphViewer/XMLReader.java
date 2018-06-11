package CoverabilityGraphViewer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

//import random.Point;

public class XMLReader {

    ObservableList<MyNode> nodeList;
    ObservableList<Transition> transitonList;

    public XMLReader() {
        nodeList = FXCollections.observableArrayList();
        transitonList = FXCollections.observableArrayList();
    }

    MyNode rootNode;

    public MyNode getGraoh() {
        if (nodeList.isEmpty()) {
            return null;
        }

        rootNode = new MyNode(" Coverability Graph");
        while (!nodeList.filtered(n -> !n.isUsed()).isEmpty()) {
            MyNode node = nodeList.filtered(n -> !n.isUsed()).get(0);
            node.setUsed(true);
            node.addTransitions(transitonList, nodeList);
            rootNode.addNode(node);
        }
        // search for the transition that have a child that does not exist in nodelist
        transitonList.forEach(t->{
            if(t.getNode()==null){
                t.addNewNode(); 
            }
        });
        return rootNode;
    }

    public AnchorPane getLinesHolder(MyNode pane){
        AnchorPane canvas= new AnchorPane();
        for(MyNode node :nodeList){
            canvas=node.drawLis(canvas); 
        }
        
        for(Transition node :transitonList){
            canvas=node.drawLine(canvas); 
        }
        return canvas;
        
    }
    
    // fill up the two lists
    public void PrintTreeMGC() throws TransformerException {
//        String filePath = getClass().getResource("/tree.xml")
//                .toString();
        File xmlFile = new File("coverabilityGraph.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("Nodes");
            //now XML is loaded as Document in memory, lets convert it to Object List

//            List<Point> points = new ArrayList<Point>();
            Node rootElement = doc.getFirstChild();
            System.out.println("Root Node"
                    + rootElement.getLastChild().getNodeName());

            getListNode(rootElement);

            getListLinks(rootElement);

        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }
//        catch (TransformerException e) {
//            e.printStackTrace();
//        }
    }

    private void getListLinks(Node rootElement) throws TransformerException {
        NodeList nlist = rootElement.getChildNodes();
        for (int i = 0; i < nlist.getLength(); i++) {
            Node node = nlist.item(i);
            if (node.getNodeName().equals("Links")) {
                NodeList children = node.getChildNodes();
                for (int j = 0; j < children.getLength(); j++) {
                    Node child01 = children.item(j);
//      NodeList lastChildren = child01.getChildNodes();
//                    for (int k = 0; k < lastChildren.getLength(); k++) {
//                        Node child02 = lastChildren.item(k);
                    System.out.println("\nCurrent Element :" + child01.getNodeName());
                    //    getPoint(child01);
                    HashMap<String, String> hmap = new HashMap<String, String>();

                    if (child01.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) child01;
                        String firstNode
                                = element.getElementsByTagName("firstNode").item(0).getTextContent();
                        String secondNode
                                = element.getElementsByTagName("secondNode").item(0).getTextContent();
                        String transition
                                = element.getElementsByTagName("transitions").item(0).getTextContent();
                        System.out.println("First " + firstNode + "\n second "
                                + secondNode + "\n  transition " + transition);
                        transitonList.add(new Transition(transition, firstNode,
                                secondNode));

                    }
                }

            }

        }

    }

    private void getListNode(Node rootElement) {

        NodeList nlist = rootElement.getChildNodes();

        for (int i = 0; i < nlist.getLength(); i++) {
            Node node = nlist.item(i);

            if (node.getNodeName().equals("Nodes")) {
                NodeList children = node.getChildNodes();
                for (int j = 0; j < children.getLength(); j++) {
                    Node child = children.item(j);

                    if (child.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) child;
                        System.out.println("++++++++++" + getTagValue("marking", element));
                        //  Point emp = new Point(getTagValue("marking", element));
                        nodeList.add(new MyNode(getTagValue("marking", element)));
                    }
                }
            }
        }
    }

//    private static Point getPoint(Node node) throws TransformerException {
//        Transformer tform
//                = TransformerFactory.newInstance().newTransformer();
//        tform.setOutputProperty(OutputKeys.INDENT, "yes");
//        tform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
//        tform.transform(new DOMSource(node), new StreamResult(System.out));
//        return null;
//    }
    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

}
