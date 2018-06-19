package Controller;

import Main.Main;
import com.khaledsab.jfxpopup.MainApp;

public abstract class AbstractController {

	protected Main main;

	public void setMainApp(Main main) {
		this.main = main;
	}

}
