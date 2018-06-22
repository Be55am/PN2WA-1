package Views.AutomateDrawer;


import model.AutomateDrawer.Link;
import model.AutomateDrawer.Node;
import javafx.scene.Group;
import javafx.scene.shape.*;
import javafx.scene.text.Text;

public class LinkView extends Group {
    private Link link;
    private Node start,end;
    private double startX,startY,endX,endY;

    LinkV linkV;
    private Text text;

    public LinkView(Link link){
        this.link=link;
        start=link.getStartNode();
        end=link.getEndNode();


        if(start!=end) {
            startX = start.getX() + 60;
            startY = start.getY() - 28;
            endX = end.getX() + 60;
            endY = end.getY() - 28;
            if (startY > endY) {
                endY += 30;
            } else if (endY > startY) {
                startY += 30;
            }

            if (endX < startX & startY == endY) {
                startY += 30;
                endY += 30;
                startX -= 50;
                endX += 20;
            }
            if (endX > startX & endY < startY) {
                startX += 20;
                endX -= 20;
            }
            if (startX > endX & startY > endY) {
                startX -= 40;
            }
            if (endX > startX & startY == endY) {
                startX += 30;
                endX -= 50;
            }
            if (startY < endY & startX == endX) {

            }


            this.text = new Text((startX + endX) / 2, (endY + startY / 2), link.getName());
            linkV = new LinkV(startX, startY, endX, endY, link.getName());


            this.getChildren().addAll(linkV);
        }
//        else{
//            Path line = createSelfPath(1);
//
//            text=new Text(startX-12,startY+37,link.getName());
//
//            this.getChildren().addAll(line,text);
//
////            for(int i=0;i<GraphView.graph.getLinksList().size();i++){
////                Link l=GraphView.graph.getLinksList().get(i);
////                if(l.getStartNode().equals(l.getEndNode())){
////                    if(!l.getStartNode().equals(this.start)){
////
////                    }
////                }
////            }
//
//
//        }








    }

    public Path createSelfPath(int co) {
        this.startX=start.getX();
        this.startY=start.getY()-5;
        this.endX=end.getX();
        this.endY=end.getY();
        //link body
        if(co==1) {
            Path line = new Path();
            MoveTo start = new MoveTo(this.startX, this.startY);// change here
            line.getElements().add(start);
            line.getElements().add(new HLineTo(this.startX - 15));
            line.getElements().add(new VLineTo(this.startY + 25));
            line.getElements().add(new HLineTo(this.startX + 10));
            line.getElements().add(new VLineTo(this.startY + 12));


            //Arrow head
            line.getElements().add(new LineTo(this.startX + 7, this.startY + 15));
            line.getElements().add(new LineTo(this.startX + 13, this.startY + 15));
            line.getElements().add(new LineTo(this.startX + 10, this.startY + 12));

            line.setStrokeWidth(2);
            return line;
        }else{
            this.startX=startX-5;
            Path line = new Path();
            MoveTo start = new MoveTo(this.startX+(20*co), this.startY+10);
            line.getElements().add(start);
            line.getElements().add(new VLineTo(this.startY + 25));
            line.getElements().add(new HLineTo(this.startX+20+(co*20)));
            line.getElements().add(new VLineTo(this.startY + 10));

            //Arrow head
            line.getElements().add(new LineTo(this.startX + 17+(co*20), this.startY + 15));
            line.getElements().add(new HLineTo(this.startX + 23+(co*20)));
            line.getElements().add(new LineTo(this.startX +20+(co*20), this.startY + 10));

            line.setStrokeWidth(2);
            return line;
        }
    }
}
