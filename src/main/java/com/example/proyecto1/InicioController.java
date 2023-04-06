package com.example.proyecto1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.proyecto1.GameController.*;

public class InicioController {
    @FXML
    public ChoiceBox dificultadChoice;
    ObservableList<String> dificultades = FXCollections.observableArrayList("Dummy", "Advanced");



    @FXML
    private void iniciarJuego () {
        /*** Verifica que se haya seleccionado una dificultad*/
        if(dificultadChoice.getValue() == null){
            /*** Verifica que se haya seleccionado una dificultad*/
            System.out.println("Error");
        }

        else {
            /**Inicia la ventana de juego sobre la de inicio*/
            /*Cronometro c = new Cronometro();
            c.start();
             */
            crearTablero();
            colocarTablero();
        }

    }


    @FXML
    public void initialize() {dificultadChoice.setItems(dificultades);}
}