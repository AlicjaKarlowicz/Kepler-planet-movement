package MN;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TrajectoryApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/trajectory.fxml"));
        Scene scene = new Scene(root,700,400);
        primaryStage.setTitle("Welcome to trajectory app");
        scene.getStylesheets().add(getClass().getResource("/fxml/style.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
