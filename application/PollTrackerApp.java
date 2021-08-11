package application;

import controller.MainWindowController;
import controller.PollTrackerViewController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Factory;
import model.PollList;

import java.io.FileNotFoundException;
import java.io.IOException;


public class PollTrackerApp extends Application {
	Stage primaryStage;
	PollList polls;
	Button submitPartyNames;
	
	
	private void setupView() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../views/PollTrackerView.fxml"));
		Scene scene;
		try {
			Parent root = (Parent) loader.load();
			PollTrackerViewController pollTrackerViewController = loader.getController();
			pollTrackerViewController.linkWithApplication(this);
			scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Setup Poll Tracker");
			primaryStage.show();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void pollView() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../views/MainWindow.fxml"));
		Scene scene;
		try {
			TabPane root = (TabPane) loader.load();
			MainWindowController mainWindowController = loader.getController();
			mainWindowController.linkWithApplication(this);
			scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Main Window");
			primaryStage.show();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void loadAddPartyNames(final int numberOfPartyNames, final Factory factory){

		this.submitPartyNames = new Button("Submit Party Names");
		final VBox vBox = new VBox(submitPartyNames);
		vBox.setSpacing(3);
		final Label[] arrayLabel = new Label[numberOfPartyNames];
		final TextField[] arrayTextFeild = new TextField[numberOfPartyNames];
		final HBox[] arrayHBox = new HBox[numberOfPartyNames];

		final String[] partyNameArray = new String[numberOfPartyNames];

		for (int i = 0; i < numberOfPartyNames; i++ ){

			arrayLabel[i] = new Label("Party Name " + i + " ");
			arrayTextFeild[i] = new TextField();
			arrayHBox[i] = new HBox();

			arrayLabel[i].setStyle("-fx-font: 14 Arial;");
			arrayTextFeild[i].setPromptText("Party Name " + i);
			arrayTextFeild[i].setStyle("-fx-font: 14 Arial;");

			arrayHBox[i].getChildren().addAll(arrayLabel[i],arrayTextFeild[i]);
			arrayHBox[i].setSpacing(2);

			vBox.getChildren().add(arrayHBox[i]);


		}

		Scene scene = new Scene(vBox,300,300);
		primaryStage.setTitle("Add Party Names");
		primaryStage.setScene(scene);
		primaryStage.setResizable(true);
		primaryStage.show();

		submitPartyNames.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {

				for (int i = 0; i < numberOfPartyNames; i++ ){

					partyNameArray[i] = arrayTextFeild[i].getText();

				}
				factory.setPartyNames(partyNameArray);
				pollView();
			}
		});


	}//loadAddPartyNames
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		setupView();


	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
