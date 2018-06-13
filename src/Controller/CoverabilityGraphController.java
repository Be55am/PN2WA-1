package Controller;

import MCGGeneration.MCG;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CoverabilityGraphController {
    private Scene view;
    MCG graph;

    public CoverabilityGraphController(Scene pane,MCG graph){
        this.view=pane;
        this.graph=graph;


        view.setOnMouseClicked(event -> {
            if(event.getButton()== MouseButton.SECONDARY){
                //creating a ContextMenu
                ContextMenu contextMenu=new ContextMenu();
                MenuItem save=new MenuItem("Save...");
                MenuItem saveAs=new MenuItem("Save As...");

                contextMenu.getItems().addAll(save,saveAs,new SeparatorMenuItem());
                contextMenu.show(view.getWindow(),event.getScreenX(),event.getScreenY());
                save.setOnAction(event1 -> save());
                saveAs.setOnAction(event1 -> saveAs());

            }
        });
    }

    public void save(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Coverability graph (*.xml)", "*.xml");
        fileChooser.setSelectedExtensionFilter(extFilter);
        File selected=fileChooser.showSaveDialog(view.getWindow());
        String pathname;
        File file;
        if(selected != null) {
            pathname = selected.getAbsolutePath();
            System.out.println("saving file to " + pathname);
            file = new File(pathname);
            System.out.println(file.getAbsolutePath());
            System.out.print("saving Coverability Graph ...");
            String text=graph.toString();

            BufferedWriter writer = null;
            try {

                writer = new BufferedWriter(new FileWriter(file));
                writer.write(text);
                writer.flush();
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (writer != null)
                        writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private void saveAs(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Coverability graph (*.png)", "*.png");
        fileChooser.setSelectedExtensionFilter(extFilter);
        File selected=fileChooser.showSaveDialog(view.getWindow());
        String pathname;
        File file;
        if(selected!=null) {
            WritableImage wim = new WritableImage((int) view.getWidth(), (int) view.getHeight());
            pathname=selected.getAbsolutePath();
           file=new File(pathname);

            view.snapshot(wim);


            try {
                ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
            } catch (Exception s) {

            }
        }
    }
}
