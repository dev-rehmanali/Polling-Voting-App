package controller;

import application.PollTrackerApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import model.Factory;
import model.Party;
import model.Poll;
import model.PollList;

public class MainWindowController {

    @FXML
    private ComboBox<String> pollEditComboBox;

    @FXML
    private TextField updatePollNameTextField;

    @FXML
    private Button updatePollNameButton;

    @FXML
    private ComboBox<String> partyToUpdateComboBox;

    @FXML
    private TextField numberOfSeatsTextField;

    @FXML
    private TextField percentageOfVotesTextField;

    @FXML
    private Button clearButton;

    @FXML
    private Button updatePartyButton;

    @FXML
    private ComboBox<String> aggregatePollComboBox;

    @FXML
    private PieChart pieChart1;

    @FXML
    private PieChart pieChart2;

    @FXML
    private Tab editTab;

    @FXML
    private Tab visualizeTab;

    @FXML
    private TabPane tabPane;

    private PollTrackerApp pollTrackerApp;
    private Factory factory;
    private PollList pollList;
    private Poll[] poll;
    private Party[] party;
    private ObservableList<Party> partyUpdatedList;


    public void initialize(){

        setInitials();
        partyUpdatedList = FXCollections.observableArrayList();

        pollEditComboBox.getItems().addAll(getPollNameList(this.poll));

        loadDataInPieChart();

        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observableValue, Tab tab, Tab t1) {
                loadDataInPieChart();
            }
        });

        pollEditComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                party = getPartiesInPoll(pollEditComboBox.getSelectionModel().getSelectedItem());
                partyToUpdateComboBox.getItems().clear();
                partyToUpdateComboBox.getItems().addAll(getUpdatedPartyNameList(party));
            }
        });


        partyToUpdateComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                for (Party party:partyUpdatedList) {
                    if(t1 != null)
                    {
                        if(t1.compareTo(party.getName()) == 0)
                        {
                            numberOfSeatsTextField.setText(String.valueOf(party.getProjectedNumberOfSeats()));
                            percentageOfVotesTextField.setText(String.valueOf(party.getProjectedPercentageOfVotes()));
                        }
                    }
                }
            }
        });

        updatePollNameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (Poll value : poll) {

                    if (pollEditComboBox.getValue() != null) {

                        if (value.getPollName().compareTo(String.valueOf(pollEditComboBox.getSelectionModel().getSelectedItem())) == 0) {
                            value.setPollName(updatePollNameTextField.getText());
                            updatePollNameTextField.clear();
                        }
                    }
                }
                pollEditComboBox.getItems().clear();
                pollEditComboBox.getItems().addAll(getPollNameList(poll));
                pollEditComboBox.getSelectionModel().select(1);

            }
        });

        updatePartyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(isFloating(numberOfSeatsTextField.getText()) && isFloating(percentageOfVotesTextField.getText()))
                {
                    int i = 0;
                    for (Party party1:partyUpdatedList) {
                        if (party1.getName().compareTo(partyToUpdateComboBox.getSelectionModel().getSelectedItem()) == 0)
                        {
                               party[i].setProjectedNumberOfSeats(Float.parseFloat(numberOfSeatsTextField.getText()));
                               party[i].setProjectedPercentageOfVotes(Float.parseFloat(percentageOfVotesTextField.getText()));
                        }
                    }
                }
                else
                    {
                        alert("Enter Valid Number of Seats to Update");
                    }

                partyToUpdateComboBox.getItems().clear();
                partyToUpdateComboBox.getItems().addAll(getUpdatedPartyNameList(party));

            }
        });

        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                clear();
            }
        });


    }//initialize

    public void linkWithApplication(PollTrackerApp pollTrackerApp){
        this.pollTrackerApp = pollTrackerApp;
    }//linkWithApplication

    void loadDataInPieChart(){

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("CPC", 25),
                        new PieChart.Data("Green", 25),
                        new PieChart.Data("LPC", 12),
                        new PieChart.Data("BQ", 8),
                        new PieChart.Data("PPC", 5),
                        new PieChart.Data("BJP", 16),
                        new PieChart.Data("NDP", 9));

        ObservableList<PieChart.Data> pieChartData2 =
                FXCollections.observableArrayList(
                        new PieChart.Data("CPC", 25),
                        new PieChart.Data("Green", 25),
                        new PieChart.Data("LPC", 12),
                        new PieChart.Data("BQ", 8),
                        new PieChart.Data("PPC", 5),
                        new PieChart.Data("BJP", 16),
                        new PieChart.Data("NDP", 9));



        pieChart1.setData(pieChartData);
        pieChart2.setData(pieChartData2);

    }//loadDataInPieChart

    private void setInitials(){
        factory = Factory.getInstance();
        this.pollList = factory.createEmptyPolls();
        this.poll = pollList.getPolls();

    }

    private Party[] getPartiesInPoll(String pollName)
    {
        Party[] parties = null;
        for (Poll poll:this.poll)
        {

            if (pollName == null)
            {


            }else if(poll.getPollName().compareTo(pollName) == 0)
            {
               return parties =  poll.getPartiesList();
            }
        }
        return null;
    }

    private void alert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.setTitle("Message");
        alert.show();
    }//alert

    private ObservableList<String> getPollNameList(Poll[] poll1)
    {
        ObservableList<String> list  = FXCollections.observableArrayList();
        for (Poll poll:poll1) {
            list.add(poll.getPollName());
        }

        return list;
    }//getPollNameList

    private ObservableList<String> getUpdatedPartyNameList(Party[] party)
    {
        ObservableList<Party> list  = FXCollections.observableArrayList();
        ObservableList<String> nameList  = FXCollections.observableArrayList();
        for (Party party1:party) {
            party1.setName(party1.getName() + " (" + party1.getProjectedPercentageOfVotes() + " of Votes, " + party1.getProjectedNumberOfSeats() + " Seats");
            list.add(party1);
            nameList.add(party1.getName());
        }

        this.partyUpdatedList = list;
        return nameList;
    }//getPollNameList

    private  void clear()
    {
        pollEditComboBox.getSelectionModel().select(-1);
        updatePollNameTextField.setText("");
        partyToUpdateComboBox.getSelectionModel().select(-1);
        numberOfSeatsTextField.setText("");
        percentageOfVotesTextField.setText("");
    }

    private boolean isFloating(String text)
    {
        if (text == null || text.isEmpty())
        {
            return false;
        }
        try
        {
            float value = Float.parseFloat(text);
            return true;

        }catch (NumberFormatException exception)
        {
            return false;
        }

    }

}
