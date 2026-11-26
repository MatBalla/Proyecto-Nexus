package ec.edu.uce.prototipo.escritorio;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Probar agente
public class VentanaProbarAgente {

    private final ServicioRest servicio = new ServicioRest();

    private final Stage ventana;
    private final Usuario usuario;
    private final Agente agente;

    private final TextArea campoEntrada = new TextArea();
    private final TextArea campoSalida = new TextArea();
    private final Label etiquetaTokens = new Label("Tokens usados:");

    private final ObservableList<Ejecucion> listaPruebas = FXCollections.observableArrayList();
    private final TableView<Ejecucion> tabla = new TableView<>();

    public VentanaProbarAgente(Stage ventana, Usuario usuario, Agente agente) {
        this.ventana = ventana;
        this.usuario = usuario;
        this.agente = agente;
    }

    public Pane construir() {

        this.prepararTabla();

        this.campoEntrada.setPrefRowCount(3);
        this.campoEntrada.setWrapText(true);

        this.campoSalida.setPrefRowCount(4);
        this.campoSalida.setWrapText(true);
        this.campoSalida.setEditable(false);

        Label etiquetaInstrucciones = new Label("Instrucciones: " + this.agente.getInstrucciones());
        etiquetaInstrucciones.setWrapText(true);

        VBox raiz = new VBox(8);
        raiz.setPadding(new Insets(15));

        raiz.getChildren().add(new Label("Probar el agente: " + this.agente.getNombre()));
        raiz.getChildren().add(etiquetaInstrucciones);
        raiz.getChildren().add(new Label("Texto de entrada"));
        raiz.getChildren().add(this.campoEntrada);
        raiz.getChildren().add(this.construirBotones());
        raiz.getChildren().add(new Label("Respuesta"));
        raiz.getChildren().add(this.campoSalida);
        raiz.getChildren().add(this.etiquetaTokens);
        raiz.getChildren().add(new Label("Pruebas anteriores de este agente"));
        raiz.getChildren().add(this.tabla);

        this.cargarPruebas();

        return raiz;
    }

    private void prepararTabla() {

        TableColumn<Ejecucion, String> columnaFecha = new TableColumn<>("Fecha");
        columnaFecha.setCellValueFactory(fila -> new SimpleStringProperty(this.fechaCorta(fila.getValue().getFecha())));
        columnaFecha.setPrefWidth(130);

        TableColumn<Ejecucion, String> columnaTokens = new TableColumn<>("Tokens");
        columnaTokens.setCellValueFactory(fila -> new SimpleStringProperty(this.texto(fila.getValue().getTokens())));
        columnaTokens.setPrefWidth(70);

        TableColumn<Ejecucion, String> columnaEntrada = new TableColumn<>("Entrada");
        columnaEntrada.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getEntrada()));
        columnaEntrada.setPrefWidth(300);

        TableColumn<Ejecucion, String> columnaSalida = new TableColumn<>("Respuesta");
        columnaSalida.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getSalida()));
        columnaSalida.setPrefWidth(340);

        this.tabla.getColumns().add(columnaFecha);
        this.tabla.getColumns().add(columnaTokens);
        this.tabla.getColumns().add(columnaEntrada);
        this.tabla.getColumns().add(columnaSalida);

        this.tabla.setItems(this.listaPruebas);
        this.tabla.setPrefHeight(180);
    }

    private HBox construirBotones() {

        HBox fila = new HBox(8);

        Button botonEnviar = new Button("Enviar");
        botonEnviar.setOnAction(evento -> this.enviar());

        Button botonLimpiar = new Button("Limpiar");
        botonLimpiar.setOnAction(evento -> this.limpiar());

        Button botonVolver = new Button("Volver a agentes");
        botonVolver.setOnAction(evento -> this.volverAAgentes());

        fila.getChildren().add(botonEnviar);
        fila.getChildren().add(botonLimpiar);
        fila.getChildren().add(botonVolver);

        return fila;
    }

    private void enviar() {

        if (this.campoEntrada.getText().isBlank()) {
            this.avisar("Escriba el texto que se le va a enviar al agente");
            return;
        }

        try {

            Ejecucion ejecucion = this.servicio.probarAgente(this.agente.getId(), this.campoEntrada.getText().trim());

            if (ejecucion == null) {
                this.avisar("El agente no esta disponible -> revisar que esté activo");
                return;
            }

            this.campoSalida.setText(ejecucion.getSalida());
            this.etiquetaTokens.setText("Tokens usados: " + ejecucion.getTokens());
            this.cargarPruebas();

        } catch (RuntimeException error) {
            this.avisar(error.getMessage());
        }
    }

    private void cargarPruebas() {
        try {

            List<Ejecucion> pruebas = this.servicio.buscarPruebas(this.agente.getId());

            this.listaPruebas.setAll(pruebas);

        } catch (RuntimeException error) {
            this.avisar(error.getMessage());
        }
    }

    private void limpiar() {
        this.campoEntrada.clear();
        this.campoSalida.clear();
        this.etiquetaTokens.setText("Tokens usados:");
    }

    private void volverAAgentes() {

        Pane contenido = new VentanaAgentes(this.ventana, this.usuario).construir();

        this.ventana.setScene(new Scene(contenido, 900, 620));
    }

    private String fechaCorta(String fecha) {

        if (fecha == null || fecha.length() < 16) {
            return "";
        }

        return fecha.substring(0, 16).replace('T', ' ');
    }

    private String texto(Object valor) {
        if (valor == null) {
            return "";
        }
        return String.valueOf(valor);
    }

    private void avisar(String mensaje) {

        Alert aviso = new Alert(Alert.AlertType.INFORMATION, mensaje);

        aviso.setHeaderText(null);
        aviso.showAndWait();
    }
}
