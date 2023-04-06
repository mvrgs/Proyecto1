package com.example.proyecto1;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.function.Consumer;

import static com.example.proyecto1.Cronometro.tiempo;

public class GameController {

    static Button[][] buttons;
    static int size = 8;
    static int numMinas= 5;
    static Tablero tablero;

    @FXML
    static Label timer;
    @FXML
    ImageView bandera;
    static boolean gameOver;
    static boolean gameWon;
    static boolean[][] mines;

    public void iniciarTimer() {
        timer.setText(tiempo);
    }

    static void crearTablero(){
        tablero = new Tablero(8, 8, numMinas);
        tablero.printTablero();
        tablero.setEventLoseGame(new Consumer<List<Casilla>>() {
            @Override
            public void accept(List<Casilla> t) {
                for (Casilla casillaConMina : t){
                    buttons[casillaConMina.getNumFila()][casillaConMina.getNumColumna()].setText("*");

                }
            }
        });
        tablero.setEventCasillaAbierta(new Consumer<Casilla>() {
            @Override
            public void accept(Casilla t) {
                buttons[t.getNumFila()][t.getNumColumna()].setDisable(true);
                buttons[t.getNumFila()][t.getNumColumna()].setText(t.getNumMinasAlrededor()+"");
            }
        });

    }

    static void colocarTablero(){
        Stage stage = new Stage();

        buttons = new Button[size][size];
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setAlignment(Pos.CENTER);
        stage.setTitle("Minesweeper");
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Button button = new Button();
                button.setPrefSize(40, 40);
                button.setId(row + "," + col);

                button.setOnAction(actionEvent -> {
                    String[] coordenada = button.getId().split(",");
                    int posFila = Integer.parseInt(coordenada[0]);
                    int posColumna = Integer.parseInt(coordenada[1]);
                    tablero.selectCasilla(posFila,posColumna);

                });


                buttons[row][col] = button;
                grid.add(button, col, row);
            }
        }

        Scene scene = new Scene(new StackPane(grid), size * 40, size * 40);
        stage.setScene(scene);
        stage.show();
    }
    public void ponerBandera(){
        bandera.setVisible(true);
        System.out.println("Si es el derecho :)");
    }

    static void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
