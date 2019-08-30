package View;

import java.io.IOException;
import java.util.Optional;

import Controller.SysData;
import Controller.Validation;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;

/**
 * Class AllWindowsController ~ Class that contains methods related to all Controllers.
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public abstract class AllWindowsController extends AbstractController {

	/**
	 * logout button listener
	 */
	@FXML
	protected void logOut() {
		Sound.playLogoutSound();
		Alert alert = new Alert(AlertType.WARNING);

		ButtonType buttonTypeYes;
		ButtonType buttonTypeNo;
		ButtonType buttonTypeCancel;
		
		buttonTypeYes = new ButtonType("Yes", ButtonData.YES);
		buttonTypeNo = new ButtonType("No", ButtonData.NO);
		buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		
		alert.setHeaderText("Would you like to Save Changes and Exit?");
		alert.setContentText("");
		alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);

		alert.setTitle("Save Changes");

		Optional<ButtonType> answer = alert.showAndWait();

		//ButtonType answer = alert.getResult();
		if (answer.get().getButtonData() == ButtonData.YES) {
			//Serialize
			try {
				if (ViewLogic.sysData.serialize()) {
				
					Validation.info("Data Serialized Successfully!", "DataBase.ser is saved in the project's folder.");
					System.out.println("Serialized");
				}
				else {
			
					Validation.alert("Data Was Not Serialized!");
					System.out.println("Not Serialized");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			closeWindow();
			ViewLogic.newLoginWindow();
		}
		
		else if (answer.get().getButtonData() == ButtonData.NO) {
		
			try {
				ViewLogic.sysData = SysData.deserialize();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Validation.alert("Data Was Not Serialized!");
			System.out.println("Not Serialized");
			closeWindow();
			ViewLogic.newLoginWindow();
		}
	}
}