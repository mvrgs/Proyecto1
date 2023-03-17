package com.example.proyecto1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioController {
    @FXML
    public ChoiceBox dificultadChoice;
    ObservableList<String> dificultades = FXCollections.observableArrayList("Dummy", "Advanced");



    @FXML
    private void iniciarJuego () throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameView.fxml"));
        Parent root = loader.load();

        GameController controlador = loader.getController();
        Cronometro c= new Cronometro();
        c.start();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();

    }
    @FXML
    public void initialize() {dificultadChoice.setItems(dificultades);}
}