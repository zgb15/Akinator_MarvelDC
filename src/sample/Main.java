package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Zoe Gonzalez
 *
 * @since 24.05.2021
 */

public class Main extends Application {

    /**
     * @see Main
     * @param primaryStage
     */

    @Override
    public void start(Stage primaryStage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/sample.fxml"));
            // Establecemos el t�tulo de la ventana
            primaryStage.setTitle("Akinator");
            // Establecemos el ancho y el alto
            primaryStage.setScene(new Scene(root));
            // Mostramos la ventana
            primaryStage.show();
            //CSS
            root.getStylesheets().add(getClass().getResource("/sample/application.css").toExternalForm());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
