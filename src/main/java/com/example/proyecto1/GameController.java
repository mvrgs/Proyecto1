package com.example.proyecto1;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import static com.example.proyecto1.InicioController.dificultad;


public class GameController {

    static Button[][] buttons;
    static int size = 8;
    static int numMinas= 5;
    static Tablero tablero;
    private static final SimpleIntegerProperty minutos = new SimpleIntegerProperty(0);
    private static final SimpleIntegerProperty segundos = new SimpleIntegerProperty(0);
    private static StringProperty minasMarcadas = new SimpleStringProperty("0");
    private static Timeline timeline;
    private static boolean turnoUsuario;
    static boolean deComputador = false;






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
        tablero.setEventWinGame(new Consumer<List<Casilla>>() {
            @Override
            public void accept(List<Casilla> t) {
                for (Casilla casillaConMina : t){
                    buttons[casillaConMina.getNumFila()][casillaConMina.getNumColumna()].setText(":)");

                }
            }
        });
        tablero.setEventCasillaAbierta(new Consumer<Casilla>() {
            @Override
            public void accept(Casilla t) {
                buttons[t.getNumFila()][t.getNumColumna()].setDisable(true);
                buttons[t.getNumFila()][t.getNumColumna()].setText(t.getNumMinasAlrededor()==0 ? "": t.getNumMinasAlrededor() +"");
            }
        });

    }

    static void colocarTablero(){
        Stage stage = new Stage();
        turnoUsuario= true;
        Label labelCronometro = new Label();
        labelCronometro.textProperty().bind(Bindings.createStringBinding(() -> String.format("%02d:%02d", minutos.get(), segundos.get()), minutos, segundos));
        labelCronometro.setStyle("-fx-font-size: 37px;");

        Label labelMinas = new Label();
        labelMinas.textProperty().bind(minasMarcadas);
        labelMinas.setStyle("-fx-font-size: 37px;");

        buttons = new Button[size][size];
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setAlignment(Pos.CENTER);
        stage.setTitle("Minesweeper");

        grid.add(labelCronometro, 0, 0, size, 1);
        grid.add(labelMinas, size-1, 0);

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Button button = new Button();
                button.setPrefSize(40, 40);
                button.setId(row + "," + col);

                button.setOnAction(actionEvent -> {
                            String[] coordenada = button.getId().split(",");
                            int posFila = Integer.parseInt(coordenada[0]);
                            int posColumna = Integer.parseInt(coordenada[1]);
                            tablero.selectCasilla(posFila, posColumna);

                            turnoUsuario = false;
                            grid.setDisable(true);
                            Random ranFila = new Random();
                            Random ranCol = new Random();
                            new Thread(() -> {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                int fila = ranFila.nextInt(8);
                                int columna = ranCol.nextInt(8);
                                Platform.runLater(() -> {
                                    deComputador = true;
                                    tablero.selectCasilla(fila, columna);
                                    System.out.println("Funciona :)");
                                });
                                try {
                                    Thread.sleep(1000);
                                }catch (InterruptedException e){
                                    e.printStackTrace();
                                }
                                deComputador = false;
                                grid.setDisable(false);
                            }).start();
                        });
                /**
                 * Metodo que cambia el color del boton con el click derecho para marcar una posible mina"
                 */
                button.setOnContextMenuRequested(contextMenuEvent -> {
                    button.setStyle("-fx-background-color: red;");
                    String[] coordenada = button.getId().split(",");
                    int posFila = Integer.parseInt(coordenada[0]);
                    int posColumna = Integer.parseInt(coordenada[1]);
                    tablero.marcarCasilla(posFila,posColumna);
                });
                buttons[row][col] = button;
                grid.add(button, col, row+1);
            }
        }
        Scene scene = new Scene(new StackPane(grid), size * 40, (size+1) * 40);
        stage.setScene(scene);
        stage.show();
    }

    static void computadora(){
        if (dificultad == "Dummy"){
            System.out.println("Dummy");
        }
        else{
            System.out.println("Advanced");
        }

    }

    static void iniciarCronometro() {
        if (timeline != null) {
            timeline.stop();
        }
        minutos.set(0);
        segundos.set(0);
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    segundos.set(segundos.get() + 1);
                    if (segundos.get() == 60) {
                        minutos.set(minutos.get() + 1);
                        segundos.set(0);
                    }
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    static void actualizarMinasMarcadas(int minas) {
        minasMarcadas.set(Integer.toString(minas));
    }

    static void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
