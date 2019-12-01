package MN;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem saveToFile;

    @FXML
    private MenuItem openFile;

    @FXML
    private MenuItem close;

    @FXML
    private MenuItem howItWorks;

    @FXML
    private Button earthBtn;

    @FXML
    private Button marsBtn;

    @FXML
    private Button jupiterBtn;

    @FXML
    private Button venusBtn;

    @FXML
    private Button mercuryBtn;

    @FXML
    private Button saturnBtn;

    @FXML
    private ChoiceBox<String> choiceBoxMethod;

    @FXML
    private TextField e;

    @FXML
    private TextField a;

    @FXML
    private TextField oT;

    @FXML
    private TextField ea;

    @FXML
    private Button drawTrajectory;

    @FXML
    private Button clearBtn;

    @FXML
    private ScatterChart<Number, Number> graph;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    void closeAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void saveToFileAction(ActionEvent event) throws IOException {


        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        //set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //show save file dialog
        File file = fileChooser.showSaveDialog(saveToFile.getParentPopup().getScene().getWindow());

        if (file != null) {
            saveFileMethod(graph.getData(), file);
        }

    }

    void saveFileMethod(ObservableList<XYChart.Series<Number, Number>> content,File file) {

        try {
            FileWriter fileWriter = null;

            fileWriter = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fileWriter);

            for(XYChart.Series<Number, Number> d: content) { //get through all the data in series
                //bw.write(d.getName());
                //bw.newLine();    //write series name
                for(XYChart.Data<Number,Number> d2: d.getData()){ //get through the data in specified series
                    bw.write(String.valueOf(d2.getXValue())); //get x value
                    bw.write(" ");
                    bw.write(String.valueOf(d2.getYValue()));// get y value
                    bw.newLine();
                }  }
            bw.close();
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        }




    @FXML
    void openFileAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        File file = fileChooser.showOpenDialog(openFile.getParentPopup().getScene().getWindow());

        if (file != null) {
            openFileMethod(file);
        }
    }

    void openFileMethod(File file) {

        ArrayList<double[]> trajectory = new ArrayList<>();
        try {

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();


            while (line!=null) {

                double[] xy = new double[2];
                String[] split = line.split(" ");
                xy[0] = Double.parseDouble(split[0]);
                xy[1] = Double.parseDouble(split[1]);
                trajectory.add(xy);
                line=br.readLine();
            }

            br.close();
            fr.close();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
    }
        XYChart.Series series= new XYChart.Series();
        for (int i=0;i<trajectory.size();i++)
            series.getData().add(new XYChart.Data(trajectory.get(i)[0], trajectory.get(i)[1]));

        graph.getData().add(series);
        series.setName(file.getName());

        setSmallerNode();

    }

    @FXML
    void howItWorksOnAction(ActionEvent event) { //if how it works section in Help is pressed, open a modal window
        howItWorksMethod(); //method to create a modal window with the info
    }

    void howItWorksMethod() { //create a modal window using help.fxml

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/help.fxml"));
            Parent root = loader.load();

            HelpController helpController = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("How it works");
            stage.initModality(Modality.WINDOW_MODAL); //modal window for explanation how the program works
            stage.showAndWait();

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    void alert() { //alert for not choosing any calculating method
        Alert alert = new Alert(Alert.AlertType.WARNING); //used in method drawTrajectoryOnAction() and planet()
        alert.setTitle("Validate fields");
        alert.setContentText("Please choose a calculating method");
        alert.showAndWait();

    }

    @FXML
    void drawTrajcetoryOnAction(ActionEvent event) {

        if (choiceBoxMethod.getSelectionModel().isEmpty()) //if there is no method selected display alert
            alert();

        if(ea.getText().isEmpty() || a.getText().isEmpty() || e.getText().isEmpty() || oT.getText().isEmpty()) { //if any of the parameters is not entered, display alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate fields");
            alert.setContentText("Please enter all parameters");
            alert.showAndWait();
        }


        XYChart.Series series= new XYChart.Series();
        ArrayList<double[]> xy =chooseMethod(Double.parseDouble(ea.getText()),Double.parseDouble(e.getText()),Double.parseDouble(a.getText()),Double.parseDouble(oT.getText()));
        for (int i=0;i<xy.size();i++)
            series.getData().add(new XYChart.Data(xy.get(i)[0], xy.get(i)[1]));

        graph.getData().add(series);
        series.setName(choiceBoxMethod.getValue());
        setSmallerNode();



    }

    @FXML
    void clearBtnOnAction(ActionEvent event) {

        graph.getData().clear(); //clear all the data, labels nad grap

        chooseMethod(Double.parseDouble(ea.getText()),Double.parseDouble(e.getText()),Double.parseDouble(a.getText()),Double.parseDouble(oT.getText())).clear();
        a.clear(); e.clear(); ea.clear(); oT.clear();

    }


    void setSmallerNode() { //used in planet() drawTrajectory() and openFileMethod()
        for (XYChart.Series<Number, Number> series1 : graph.getData()) {
            //for all series, take date, each data has Node (symbol) for representing point
            for (XYChart.Data<Number, Number> data : series1.getData()) {
                // this node is StackPane
                StackPane stackPane = (StackPane) data.getNode();
                stackPane.setPrefWidth(4);
                stackPane.setPrefHeight(4);
            }}
    }

    private XYChart.Series planet(double e,double a,double oT){

        if (choiceBoxMethod.getSelectionModel().isEmpty())
            alert();

        XYChart.Series series= new XYChart.Series();
        ArrayList<double[]> planet =chooseMethod(0.00001,e,a,oT); //ea is constant, draw the graph automatically by checking radio btn

        for (int i=0;i<planet.size();i++)
            series.getData().add(new XYChart.Data(planet.get(i)[0], planet.get(i)[1]));

        graph.getData().add(series);

        setSmallerNode();

        return series;
    }

    @FXML
    void earthBtnOnAction(ActionEvent event) {
        planet(0.0167,1,365.356).setName("Earth " + choiceBoxMethod.getValue());
    }


    @FXML
    void jupiterBtnOnAction(ActionEvent event) {
        planet(0.0484,5.203,11.862*365.256).setName("Jupiter "+ choiceBoxMethod.getValue());
    }

    @FXML
    void saturnBtnOnAction(ActionEvent event) {
        planet(0.0542,9.537,29.457*365.256).setName("Saturn " + choiceBoxMethod.getValue());
    }

    @FXML
    void marsBtnOnAction(ActionEvent event) {
        planet(0.0934,1.524,686.98).setName("Mars " + choiceBoxMethod.getValue());
    }

    @FXML
    void mercuryBtnOnAction(ActionEvent event) {
       planet(0.2056,0.387,87.969).setName("Mercury " + choiceBoxMethod.getValue());
    }

    @FXML
    void venusBtnOnAction(ActionEvent event) {
        planet(0.0068,0.723,224.701).setName("Venus " + choiceBoxMethod.getValue());
    }







     void setChoiceBoxMethod(ChoiceBox<String> choiceBoxMethod) {///setting the items in choiceBix
        this.choiceBoxMethod = choiceBoxMethod;
        ObservableList<String> availableChoices = FXCollections.observableArrayList("Bisection","Regula Falsi","Fixed point iteration","Newton Raphson", "Secant");
        choiceBoxMethod.setItems(availableChoices);
    }

    private ArrayList<double[]> chooseMethod(double ea,double e,double a,double oT) { //create an object oof given class, selected through choiceBox
        //and return a list of coordinates todraw trajectory

        if (choiceBoxMethod.getValue().equals("Bisection"))
            return (new Bisection(new MyFunction(),0,ea,e,a,oT,2*Math.PI)).trajectory();

        else if (choiceBoxMethod.getValue().equals("Regula Falsi"))
            return (new RegulaFalsi(new MyFunction(),0,ea,e,a,oT,2*Math.PI)).trajectory();

        else if (choiceBoxMethod.getValue().equals("Fixed point iteration"))
            return (new FixedPointIteration(new MyFunction(),0.5,ea,e,a,oT)).trajectory();

        else if (choiceBoxMethod.getValue().equals("Newton Raphson"))
              return (new NewtonRaphson(new MyFunction(),0,ea,e,a,oT)).trajectory();

        else
             return (new Secant(new MyFunction(),Math.PI/2,ea,e,a,oT,0)).trajectory();


    }





    @FXML
    void initialize() {
        assert saveToFile != null : "fx:id=\"saveToFile\" was not injected: check your FXML file 'trajectory.fxml'.";
        assert close != null : "fx:id=\"close\" was not injected: check your FXML file 'trajectory.fxml'.";
        assert choiceBoxMethod != null : "fx:id=\"choiceBoxMethod\" was not injected: check your FXML file 'trajectory.fxml'.";
        assert graph != null : "fx:id=\"graph\" was not injected: check your FXML file 'trajectory.fxml'.";
        assert xAxis != null : "fx:id=\"xAxis\" was not injected: check your FXML file 'trajectory.fxml'.";
        assert yAxis != null : "fx:id=\"yAxis\" was not injected: check your FXML file 'trajectory.fxml'.";
        assert earthBtn != null : "fx:id=\"earthRadioBtn\" was not injected: check your FXML file 'trajectory.fxml'.";
        assert marsBtn != null : "fx:id=\"marsRadioBtn\" was not injected: check your FXML file 'trajectory.fxml'.";
        assert jupiterBtn != null : "fx:id=\"jupiterRadioBtn\" was not injected: check your FXML file 'trajectory.fxml'.";
        assert venusBtn != null : "fx:id=\"venusRadioBtn\" was not injected: check your FXML file 'trajectory.fxml'.";
        assert mercuryBtn != null : "fx:id=\"mercuryRadioBtn\" was not injected: check your FXML file 'trajectory.fxml'.";
        assert saturnBtn != null : "fx:id=\"mercuryRadioBtn\" was not injected: check your FXML file 'trajectory.fxml'.";
        assert e != null : "fx:id=\"e\" was not injected: check your FXML file 'trajectory.fxml'.";
        assert a != null : "fx:id=\"a\" was not injected: check your FXML file 'trajectory.fxml'.";
        assert oT != null : "fx:id=\"oT\" was not injected: check your FXML file 'trajectory.fxml'.";
        assert ea != null : "fx:id=\"ea\" was not injected: check your FXML file 'trajectory.fxml'.";
        assert drawTrajectory != null : "fx:id=\"drawTrajectory\" was not injected: check your FXML file 'trajectory.fxml'.";
        assert clearBtn != null : "fx:id=\"clearBtn\" was not injected: check your FXML file 'trajectory.fxml'.";
        setChoiceBoxMethod(choiceBoxMethod);
      
    }
}
