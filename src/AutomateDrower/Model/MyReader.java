package AutomateDrower.Model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class MyReader {

    public ObservableList<Node> nodeList;
    public ObservableList<Link> linksList;

    public MyReader() {

        nodeList = FXCollections.observableArrayList();
        linksList = FXCollections.observableArrayList();
    }



    // fill up the two lists Links and Nodes
    public Graph PrintTreeMGC()   {

        try {
            InputStream inputStream=
                    new FileInputStream("/media/bessam/Mes documents/Lees works/PN2WA-1/automate.xml");

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
            setNodeList(rootNodes);
            setLinksList(rootLinks);




        } catch (FileNotFoundException | UnsupportedEncodingException e)
        {
            e.printStackTrace();
        } catch (SAXException | ParserConfigurationException | IOException e1)
        {
            e1.printStackTrace();
        }

        return new Graph(nodeList,linksList);
    }
    // fill up the  list of  Nodes
    private  void setNodeList(NodeList rootNodes) {
        for (int i = 0; i < rootNodes.getLength(); i++) {
            org.w3c.dom.Node node = rootNodes.item(i);
            if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element a = (Element) node;
                Node nodesWa =new Node(a.getAttribute("id"),100,100);

                // Add Node To ListNodes
                nodeList.add(nodesWa);

            }
        }
    }
    // fill up the lists of Links
    private  void setLinksList(NodeList root) {
        for (int i = 0; i < root.getLength(); i++) {
            org.w3c.dom.Node node = root.item(i);

            if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element a = (Element) node;

                NodeList str =a.getElementsByTagName("startNode");
                Element strId =
                        ((Element) str.item(0));

                //Id
                NodeList end =a.getElementsByTagName("endNode");
                Element endId =
                        ((Element) end.item(0));

                String name=a.getElementsByTagName("event").item(0).getTextContent()+":"+
                        a.getElementsByTagName("Energy").item(0).getTextContent();
                String start=strId.getAttribute("id");
                String endNode=endId.getAttribute("id");
                Node startNode=null;
                Node endingNode=null;
                boolean b1=false;
                boolean b2=false;
                for (Node n:nodeList) {
                    if(n.getMarking().equals(start)){
                        System.out.println(n.getMarking());
                        System.out.println(start);
                        startNode=n;
                        b1=true;

                    }
                    if(n.getMarking().equals(endNode)){
                        endingNode=n;
                        b2=true;

                    }
                }
                if(!b1)
                    System.out.println("no starting node founded");
                if(!b2)
                    System.out.println("no ending node founded");

                linksList.add(new Link(name,startNode,endingNode));
            }
        }
    }

}
