package Views;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;

/**
 *
 * @author kn
 */
public class ArrowView extends Group{
    private static final double defaultArrowHeadSize = 5.0;
    private double startX,startY,endX,endY;
    private static final double RADIUS=15;
    private Path path;
    private int weight;

    public ArrowView(double startX, double startY, double endX, double endY, double arrowHeadSize,int weight){
        super();

        //the added code
        this.startX=startX;
        this.startY=startY;
        this.endX=endX;
        this.endY=endY;

        path=new Path();

        path.strokeProperty().bind(path.fillProperty());
        path.setFill(Color.BLUE);
        setFocused(true);
//
//        Arc a1 = new Arc(startX, startY,
//                100, 100, 90, Math.sqrt(Math.pow(endX - startX ,2)-Math.pow(endY - startY , 2)));
//        a1.setType(ArcType.OPEN);
//        a1.setStroke(Color.BLACK);
//        a1.setFill(null);
//        a1.setStrokeWidth(3);

      this.getStyleClass().add("arrowspace");
//
//        QuadCurveTo quadTo = new QuadCurveTo();
//        quadTo.setControlX(25.0f);
//        quadTo.setControlY(0.0f);
//        quadTo.setX(endX);
//        quadTo.setY(endY);

//        CubicCurveTo cubicTo = new CubicCurveTo();
//        cubicTo.setControlX1(50.0f);
//        cubicTo.setControlY1(0.0f);
//        cubicTo.setControlX2(100.0f);
//        cubicTo.setControlY2(100.0f);
//        cubicTo.setX(endX);
//        cubicTo.setY(endY);

        //Line
        path.getElements().add(new MoveTo(startX, startY));
        path.getElements().add(new LineTo(endX , endY));
/*
Path path = new Path();

    MoveTo moveTo = new MoveTo();
    moveTo.setX(0.0f);
    moveTo.setY(50.0f);

    QuadCurveTo quadTo = new QuadCurveTo();
    quadTo.setControlX(25.0f);
    quadTo.setControlY(0.0f);
    quadTo.setX(50.0f);
    quadTo.setY(50.0f);

    path.getElements().add(moveTo);
    path.getElements().add(quadTo);

 */
        //ArrowHead
        double angle = Math.atan2((endY - startY), (endX - startX)) - Math.PI / 2.0;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        //point1
        double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
        double y1 = (- 1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;
        //point2
        double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
        double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;

        path.getElements().add(new LineTo(x1, y1));
         path.getElements().add(new LineTo(x2, y2));
         path.getElements().add(new LineTo(endX, endY));
         this.getChildren().add(path);
         double x=(startX+endX)/2;
         double y=(startY+endY)/2;
         //the weigh text

        Text text=new Text(x,y,String.valueOf(weight));
        this.getChildren().add(text);

       // initialize();
    }

    public ArrowView(double startX, double startY, double endX, double endY,int weight){
        this(startX, startY, endX, endY, defaultArrowHeadSize,weight);
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}