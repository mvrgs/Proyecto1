package com.example.proyecto1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.io.IOException;

import static com.example.proyecto1.GameController.*;

public class InicioController {

    private serialComm serialComm = new serialComm();
    @FXML
    private Label errorLabel;
    @FXML
    public ChoiceBox dificultadChoice;
    static String dificultad;
    ObservableList<String> dificultades = FXCollections.observableArrayList("Dummy", "Advanced");



    @FXML
    private void iniciarJuego () throws IOException {
        /*** Verifica que se haya seleccionado una dificultad*/
        if(dificultadChoice.getValue() == null){
            /*** Verifica que se haya seleccionado una dificultad*/
            errorLabel.setVisible(true);
        }

        else {
            /**Inicia la ventana de juego sobre la de inicio*/
            dificultad = (dificultadChoice.getValue().toString());
            errorLabel.setVisible(false);
            crearTablero();
            colocarTablero();
            iniciarCronometro();
        }
    }


    @FXML
    public void initialize() {dificultadChoice.setItems(dificultades);}
}