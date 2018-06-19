package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class PopupController extends AbstractController implements Initializable {

		private Stage stage = null;
	@Override
	public void initialize(URL url, ResourceBundle rb) {


	}

	/**
	 * setting the stage of this view
	 * @param stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Closes the stage of this view
	 */
	private void closeStage() {
		if(stage!=null) {
			stage.close();
		}
	}
	@FXML
	void followLinkGithub(ActionEvent event) {
     Runtime rt = Runtime.getRuntime();
     String url = "https://github.com/Be55am/PetriNetConverter.git";
     try {
     	rt.exec("rundll32 url.dll,FileprotocolHandler"+url);

	 }catch (IOException e) {
		 e.printStackTrace();
	 }
	}

}
