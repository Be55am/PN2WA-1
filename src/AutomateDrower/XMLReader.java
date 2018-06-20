package AutomateDrower;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

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

        rootNode = new MyNode("Weighted Automata");
        while (!nodeList.filtered(n -> !n.isUsed()).isEmpty()) {
            MyNode node = nodeList.filtered(n -> !n.isUsed()).get(0);
            node.setUsed(true);
            node.addTransitions(transitonList, nodeList);
            System.out.println("Root :-"+node.getMark()+"-");
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
    // fill up the two lists Links and Nodes
    public void PrintTreeMGC()   {

        try {
            InputStream inputStream=
                    new FileInputStream("automate.xml");

            Reader reader = new InputStreamReader(inputStream,"UTF-8");
            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");

            //	File xmlFile = new File("C:\\Users\\sniper\\IdeaProjects\\PN2WA-1\\src\\k\\automat.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;

            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            doc.getDocumentElement().normalize();
            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList rootLinks =doc.getElementsByTagName("Link");
            NodeList rootNodes =doc.getElementsByTagName("Node");

            setLinksList(rootLinks);
            setNodeList(rootNodes);

        } catch (FileNotFoundException | UnsupportedEncodingException e)
        {
            e.printStackTrace();
        } catch (SAXException | ParserConfigurationException | IOException e1)
        {
            e1.printStackTrace();
        }
    }
    // fill up the  list of  Nodes
    private  void setNodeList(NodeList rootNodes) {
        for (int i = 0; i < rootNodes.getLength(); i++) {
            Node node = rootNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element a = (Element) node;
                MyNode nodesWa =new MyNode(a.getAttribute("id"));

                // Add Node To ListNodes
                nodeList.add(nodesWa);

            }
        }
    }
    // fill up the lists of Links
    private  void setLinksList(NodeList root) {
         for (int i = 0; i < root.getLength(); i++) {
            Node node = root.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element a = (Element) node;

                NodeList str =a.getElementsByTagName("startNode");
                Element strId =
                        ((Element) str.item(0));

                //Id
                NodeList end =a.getElementsByTagName("endNode");
                Element endId =
                        ((Element) end.item(0));

                transitonList.add(new Transition(
                   a.getElementsByTagName("event").item(0).getTextContent()+":"+
                         a.getElementsByTagName("Energy").item(0).getTextContent()
                        , strId.getAttribute("id"),
                        endId.getAttribute("id")));
            }
        }
    }
    private static void getNode(Node node) throws TransformerException {
        Transformer tform
                = TransformerFactory.newInstance().newTransformer();
        tform.setOutputProperty(OutputKeys.INDENT, "yes");
        tform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        tform.transform(new DOMSource(node), new StreamResult(System.out));
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
}
