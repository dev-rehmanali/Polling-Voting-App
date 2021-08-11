package controller;

import application.PollTrackerApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Factory;

public class PollTrackerViewController {

    @FXML
    private TextField numberOfSeatsTextField;

    @FXML
    private TextField numberOfPartiesTextField;

    @FXML
    private TextField numberOfPollsTextField;

    @FXML
    private Button reset;

    @FXML
    private Button submit;

    private PollTrackerApp pollTrackerApp;

    public void initialize(){

        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                clear();
            }
        });

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Factory factory = updateInFactory();
                pollTrackerApp.loadAddPartyNames(Integer.valueOf(numberOfPartiesTextField.getText()), factory);
            }
        });




    }//initialize

    public void linkWithApplication(PollTrackerApp pollTrackerApp) {
        this.pollTrackerApp = pollTrackerApp;
    }

    private void clear(){
        numberOfSeatsTextField.clear();
        numberOfPartiesTextField.clear();
        numberOfPollsTextField.clear();
    }//clear

    private Factory updateInFactory(){
        Factory factory = null;
        if(validateSetupPoll()){
            factory = Factory.getInstance();
            factory.setNumOfSeats(Integer.valueOf(numberOfSeatsTextField.getText()));
            factory.setNumOfPolls(Integer.valueOf(numberOfPollsTextField.getText()));

        }else{
            alert("Could not Add to Factory");
        }

        return factory;
    }//updateInFactory

    private boolean validateSetupPoll(){
        boolean toReturn = false;
        if (numberOfSeatsTextField.getText().isEmpty()){
            alert("Please Enter Number of Seats");
        }else if (numberOfPartiesTextField.getText().isEmpty()){
            alert("Please Enter Number of Parties");
        }else if (numberOfPollsTextField.getText().isEmpty()){
            alert("Please Enter Number of Polls");
        }else {
            toReturn = true;
        }

        return toReturn;
    }//validateSetupPoll

    private void alert(String message){
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setContentText(message);
        alert.setTitle("Message");
        alert.show();
    }//alert

}
