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

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import static com.example.proyecto1.InicioController.dificultad;
import static com.example.proyecto1.Tablero.*;


public class GameController {

    static Button[][] buttons;
    static int size = 8;
    static int numMinas= 5;
    static Tablero tablero;
    private static final SimpleIntegerProperty minutos = new SimpleIntegerProperty(0);
    private static final SimpleIntegerProperty segundos = new SimpleIntegerProperty(0);
    private static StringProperty minasMarcadas = new SimpleStringProperty("0");
    private static Timeline timeline;
    private static linkedList listaGeneral;
    private static linkedList listaSegura;
    private static linkedList listaIncertidumbre;
    private static stack pilaSugerencias = new stack();
    static boolean turnoJugador =true;
    static boolean perdioComputadora = false;
    private static int jugadas = 0;
    private static int largoPila;
    static int largo;
    static Button sugerenciasButton;







    static void crearTablero(){
        tablero = new Tablero(8, 8, numMinas);
        tablero.printTablero();
        tablero.setEventLoseGame(new Consumer<List<Casilla>>() {
            @Override
            public void accept(List<Casilla> t) {
                for (Casilla casillaConMina : t){
                    buttons[casillaConMina.getNumFila()][casillaConMina.getNumColumna()].setText("*");
                }
                showMessage("Ha explotado una mina :(");
                System.exit(0);
            }
        });
        tablero.setEventWinGame(new Consumer<List<Casilla>>() {
            @Override
            public void accept(List<Casilla> t) {
                for (Casilla casillaConMina : t){
                    buttons[casillaConMina.getNumFila()][casillaConMina.getNumColumna()].setText(":)");
                }
                showMessage("Felicidades, ha ganado :)");
                System.exit(0);
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

    static void colocarTablero() throws IOException {
        Stage stage = new Stage();
        Label labelCronometro = new Label();

        labelCronometro.textProperty().bind(Bindings.createStringBinding(() -> String.format("%02d:%02d", minutos.get(), segundos.get()), minutos, segundos));
        labelCronometro.setStyle("-fx-font-size: 37px;");

        Label labelMinas = new Label();
        labelMinas.textProperty().bind(minasMarcadas);
        labelMinas.setStyle("-fx-font-size: 37px;");

        sugerenciasButton = new Button("0");
        sugerenciasButton.setPrefSize(40, 40);
        sugerenciasButton.setStyle("-fx-background-color: gray;");
        sugerenciasButton.setOnAction(actionEvent -> {
            crearSugerencias();
        });

        buttons = new Button[size][size];
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setAlignment(Pos.CENTER);
        stage.setTitle("Minesweeper");


        grid.add(labelCronometro, 0, 0, size, 1);
        grid.add(labelMinas, size-1, 0);
        grid.add(sugerenciasButton,5,0);

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Button button = new Button();
                button.setPrefSize(40, 40);
                button.setId(row + "," + col);

                button.setOnAction(actionEvent -> {
                    jugadas++;
                    turnoJugador= false;
                    String[] coordenada = button.getId().split(",");
                    int posFila = Integer.parseInt(coordenada[0]);
                    int posColumna = Integer.parseInt(coordenada[1]);
                    tablero.selectCasilla(posFila, posColumna);
                    System.out.println("Jugadas:"+jugadas);
                    obtenerListaGeneral();
                    if (dificultad == "Dummy"){
                        dummy();
                    }
                    else{
                        advanced();
                    }
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

    private static void dummy(){
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
                buttons[fila][columna].setStyle("-fx-background-color: blue;");
                tablero.selectCasilla(fila, columna);
                System.out.println("El computador ya hizo su turno");
            });
        }).start();
    }

    static private void obtenerListaGeneral() {
        listaGeneral = new linkedList();
        largo = 0;
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (!casillas[i][j].isAbierta()) {
                    Casilla casilla = new Casilla(i, j);
                    listaGeneral.agregar(casilla);
                    largo++;
                }
            }
        }obtenerListas();
    }
    /*static private void obtenerListas(){
        listaSegura = new linkedList();
        listaIncertidumbre = new linkedList();
        while (listaGeneral.getLargo()>0){
            if (casillasConMinas.contains(listaGeneral.getPrimero().getCasilla())){
                listaIncertidumbre.agregar(listaGeneral.getPrimero().getCasilla());
            }
            else {
                listaSegura.agregar(listaGeneral.getPrimero().getCasilla());
            }
            listaGeneral.eliminarPrimero();
        }
        System.out.println("general:"+listaGeneral.getLargo());
        System.out.println("segura:"+listaSegura.getLargo());
        System.out.println("incertidumbre:"+listaIncertidumbre.getLargo());
    }
     */
    static private void obtenerListas(){
        listaSegura= new linkedList();
        listaIncertidumbre = new linkedList();
        while (listaGeneral.getLargo()>0){
            for (int i = 0; i < listaGeneral.getLargo(); i++) {
                for (int j = 0; j < casillasConMinas.size(); j++) {
                    if (casillasConMinas.get(j).equals(listaGeneral.getPrimero().getCasilla())) {
                        Casilla casilla = listaGeneral.getPrimero().getCasilla();
                        listaIncertidumbre.agregar(casilla);
                        listaGeneral.eliminarPrimero();
                        System.out.println("general:"+listaGeneral.getLargo());
                        System.out.println("segura:"+listaSegura.getLargo());
                        System.out.println("incertidumbre:"+listaIncertidumbre.getLargo());
                    }
                }
                Casilla casilla= listaGeneral.getPrimero().getCasilla();
                listaSegura.agregar(casilla);
                listaGeneral.eliminarPrimero();
                System.out.println("general:"+listaGeneral.getLargo());
                System.out.println("segura:"+listaSegura.getLargo());
                System.out.println("incertidumbre:"+listaIncertidumbre.getLargo());
            }
        }
        sugerencias();
    }


    static void advanced() {
        Random ranIndex = new Random();
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int index = ranIndex.nextInt(largo);
            int posFila;
            int posCol;
            if (listaSegura.getLargo()> 0){
                posFila= listaSegura.getNodoEnIndice(index).getCasilla().getNumFila();
                posCol = listaSegura.getNodoEnIndice(index).getCasilla().getNumColumna();
                Platform.runLater(() -> {
                    buttons[posFila][posCol].setStyle("-fx-background-color: blue;");
                    tablero.selectCasilla(posFila,posCol);
                });
            }else {
                posFila= listaIncertidumbre.getNodoEnIndice(index).getCasilla().getNumFila();
                posCol = listaIncertidumbre.getNodoEnIndice(index).getCasilla().getNumColumna();
                Platform.runLater(() -> {
                    buttons[posFila][posCol].setStyle("-fx-background-color: blue;");
                    tablero.selectCasilla(posFila,posCol);
                });
            }
        }).start();

        if (tablero.juegoTerminado){
            perdioComputadora= true;
        }
    }

    private static void sugerencias(){
        Random ranIndex = new Random();
        if (jugadas==2){
            int index = ranIndex.nextInt(listaSegura.getLargo());
            pilaSugerencias.push(listaSegura.getNodoEnIndice(index-1).getCasilla());
            largoPila++;
            jugadas=0;
            sugerenciasButton.setText(String.valueOf(largoPila));
        }
    }

    private static void crearSugerencias(){
        int numFila;
        int numCol;
        if (largoPila > 0){
            Casilla casilla = (Casilla) pilaSugerencias.peek();
            numFila = casilla.getNumFila();
            numCol = casilla.getNumColumna();
            buttons[numFila][numCol].setStyle("-fx-background-color: green");
            pilaSugerencias.pop();
            largoPila--;
            sugerenciasButton.setText(String.valueOf(largoPila));
        }
        else {
            System.out.println("No hay sugerencias disponibles");
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
