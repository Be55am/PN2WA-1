package Views;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 *
 * @author kn
 */
public class ArrowView extends Path{
    private static final double defaultArrowHeadSize = 5.0;
    private double startX,startY,endX,endY;
    private static final double RADIUS=15;

    public ArrowView(double startX, double startY, double endX, double endY, double arrowHeadSize){
        super();

        //the added code
        this.startX=startX;
        this.startY=startY;
        this.endX=endX;
        this.endY=endY;

        strokeProperty().bind(fillProperty());
        setFill(Color.BLUE);
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
        getElements().add(new MoveTo(startX, startY));
        getElements().add(new LineTo(endX , endY));
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

        getElements().add(new LineTo(x1, y1));
         getElements().add(new LineTo(x2, y2));
         getElements().add(new LineTo(endX, endY));

       // initialize();
    }

    public ArrowView(double startX, double startY, double endX, double endY){
        this(startX, startY, endX, endY, defaultArrowHeadSize);
    }
//todo this methode suppose to fix the problem of the arc but it fucks things up
    private void initialize() {
        double angle = Math.atan2(endY - startY, endX - startX) * 180 / 3.14;
        double height = endY - startY;
        double width = endX - startX;
        double length = Math.sqrt(Math.pow(height, 2) + Math.pow(width, 2));

        double subtractWidth = RADIUS * width / length;
        double subtractHeight = RADIUS * height / length;
        setRotate(angle );
        setTranslateX(startX);
        setTranslateY(startY);
        setTranslateX(endX - subtractWidth);
        setTranslateY(endY - subtractHeight);
    }
}