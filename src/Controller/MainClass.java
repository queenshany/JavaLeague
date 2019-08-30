package Controller;

import java.io.IOException;
import java.text.ParseException;

import View.ViewLogic;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This MainClass object - represents the program runner
 * 
 * @author Java Course Team 2018 - Shai Gutman
 * @author University Of Haifa - Israel
 * @author ID: 205791056
 * @author ID: 312581605
 */
public class MainClass extends Application {

	/**
	 * The main object for the program
	 */
	private static SysData sysData;

	/**
	 * The main method of this system, gets input from input.txt and Writes output
	 * to output.txt file
	 * 
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws IOException, ParseException, ClassNotFoundException {
		launch(args);

	}// END OF ~ main

	@Override
	public void start(Stage primaryStage) throws Exception {

		// running the Data base
		try {
			sysData = SysData.deserialize();
			// case DataBase.ser doesn't exist
			if (sysData == null) {
				System.out.println("sysData is created");
				sysData = SysData.getInstance();

			}
		} catch (IOException e) {
			System.out.println("DESERIALIZE");
			e.printStackTrace();
		} finally {
			ViewLogic.initUI();
		}

	}

}// ~ END OF Class MainClass
