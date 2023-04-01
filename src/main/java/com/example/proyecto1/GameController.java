package com.example.proyecto1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


import static com.example.proyecto1.Cronometro.tiempo;

public class GameController {
    @FXML
    static Label timer;

    public void iniciarTimer() {
        timer.setText(tiempo);
    }











}
