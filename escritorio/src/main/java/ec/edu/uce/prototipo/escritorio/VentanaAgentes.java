package ec.edu.uce.prototipo.escritorio;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Pantalla de agntes
public class VentanaAgentes {

    private static final int TOKENS_POR_DEFECTO = 1000;

    private final ServicioRest servicio = new ServicioRest();

    private final Stage ventana;
    private final Usuario usuario;

    private final ObservableList<Agente> listaAgentes = FXCollections.observableArrayList();

    private final TableView<Agente> tabla = new TableView<>();
    private final TextField campoNombre = new TextField();
    private final TextArea campoInstrucciones = new TextArea();
    private final TextField campoMaximoTokens = new TextField();
    private final CheckBox campoUsaDocumentos = new CheckBox();

    private Integer idSeleccionado = null;

    public VentanaAgentes(Stage ventana, Usuario usuario) {
        this.ventana = ventana;
        this.usuario = usuario;
    }

    public Pane construir() {

        this.prepararTabla();

        VBox raiz = new VBox(10);
        raiz.setPadding(new Insets(15));

        raiz.getChildren().add(new Label("Agentes. Sesion: " + this.usuario.getNombre()
                + " (" + this.usuario.getRol() + ")"));
        raiz.getChildren().add(this.tabla);

        if (this.usuario.esAdmin()) {
            raiz.getChildren().add(this.construirFormulario());
        }

        raiz.getChildren().add(this.construirBotones());

        this.cargarAgentes();

        return raiz;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private void prepararTabla() {

        TableColumn<Agente, String> columnaId = new TableColumn<>("Id");
        columnaId.setCellValueFactory(fila -> new SimpleStringProperty(this.texto(fila.getValue().getId())));
        columnaId.setPrefWidth(40);

        TableColumn<Agente, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getNombre()));
        columnaNombre.setPrefWidth(140);

        TableColumn<Agente, String> columnaInstrucciones = new TableColumn<>("Instrucciones");
        columnaInstrucciones.setCellValueFactory( fila -> new SimpleStringProperty(fila.getValue().getInstrucciones()));
        columnaInstrucciones.setPrefWidth(380);

        TableColumn<Agente, String> columnaTokens = new TableColumn<>("Max tokens");
        columnaTokens.setCellValueFactory(fila -> new SimpleStringProperty(this.texto(fila.getValue().getMaximoTokens())));
        columnaTokens.setPrefWidth(90);

        TableColumn<Agente, String> columnaDocumentos = new TableColumn<>("Usa documentos");
        columnaDocumentos.setCellValueFactory(fila -> new SimpleStringProperty(this.texto(fila.getValue().getUsaDocumentos())));
        columnaDocumentos.setPrefWidth(110);

        TableColumn<Agente, String> columnaActivo = new TableColumn<>("Activo");
        columnaActivo.setCellValueFactory(fila -> new SimpleStringProperty(this.texto(fila.getValue().getActivo())));
        columnaActivo.setPrefWidth(70);

        this.tabla.getColumns().add(columnaId);
        this.tabla.getColumns().add(columnaNombre);
        this.tabla.getColumns().add(columnaInstrucciones);
        this.tabla.getColumns().add(columnaTokens);
        this.tabla.getColumns().add(columnaDocumentos);
        this.tabla.getColumns().add(columnaActivo);

        this.tabla.setItems(this.listaAgentes);
        this.tabla.setPrefHeight(220);

        this.tabla.getSelectionModel().selectedItemProperty().addListener((observado, anterior, seleccionado) -> this.mostrarEnFormulario(seleccionado));
    }


    private GridPane construirFormulario() {

        this.campoInstrucciones.setPrefRowCount(3);
        this.campoInstrucciones.setWrapText(true);

        GridPane formulario = new GridPane();
        formulario.setHgap(10);
        formulario.setVgap(8);

        formulario.add(new Label("Nombre"), 0, 0);
        formulario.add(this.campoNombre, 1, 0);

        formulario.add(new Label("Instrucciones"), 0, 1);
        formulario.add(this.campoInstrucciones, 1, 1);

        formulario.add(new Label("Maximo de tokens"), 0, 2);
        formulario.add(this.campoMaximoTokens, 1, 2);

        formulario.add(new Label("Usa documentos"), 0, 3);
        formulario.add(this.campoUsaDocumentos, 1, 3);

        this.campoNombre.setPrefWidth(450);
        this.campoInstrucciones.setPrefWidth(450);
        this.campoMaximoTokens.setPrefWidth(450);

        return formulario;
    }

    private HBox construirBotones() {

        HBox fila = new HBox(8);

        Button botonVerTodos = new Button("Ver todos");
        botonVerTodos.setOnAction(evento -> this.cargarAgentes());

        Button botonProbar = new Button("Probar");
        botonProbar.setOnAction(evento -> this.probar());

        fila.getChildren().add(botonVerTodos);
        fila.getChildren().add(botonProbar);

        if (this.usuario.esAdmin()) {

            Button botonNuevo = new Button("Nuevo");
            botonNuevo.setOnAction(evento -> this.limpiarFormulario());

            Button botonGuardar = new Button("Guardar");
            botonGuardar.setOnAction(evento -> this.guardar());

            Button botonActualizar = new Button("Actualizar");
            botonActualizar.setOnAction(evento -> this.actualizar());

            Button botonDesactivar = new Button("Desactivar");
            botonDesactivar.setOnAction(evento -> this.desactivar());

            Button botonActivar = new Button("Activar");
            botonActivar.setOnAction(evento -> this.activar());

            fila.getChildren().add(botonNuevo);
            fila.getChildren().add(botonGuardar);
            fila.getChildren().add(botonActualizar);
            fila.getChildren().add(botonDesactivar);
            fila.getChildren().add(botonActivar);
        }

        Button botonVolver = new Button("Volver al menu");
        botonVolver.setOnAction(evento -> this.volverAlMenu());

        fila.getChildren().add(botonVolver);

        return fila;
    }

    private void cargarAgentes() {
        try {
            List<Agente> agentes = this.servicio.buscarAgentes();
            this.listaAgentes.setAll(agentes);

        } catch (RuntimeException error) {
            this.avisar(error.getMessage());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    private void guardar() {

        Agente agente = this.leerFormulario();

        if (agente == null) {
            return;
        }

        try {

            boolean seGuardo = this.servicio.guardarAgente(agente);

            if (!seGuardo) {
                this.avisar("Ya existe un agente con el nombre " + agente.getNombre());
                return;
            }

            this.cargarAgentes();
            this.limpiarFormulario();
            this.avisar("Agente guardado");

        } catch (RuntimeException error) {
            this.avisar(error.getMessage());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private void actualizar() {

        if (this.idSeleccionado == null) {
            this.avisar("Seleccione primero un agente de la tabla");
            return;
        }

        Agente agente = this.leerFormulario();

        if (agente == null) {
            return;
        }

        try {
            this.servicio.actualizarAgente(agente, this.idSeleccionado);
            this.cargarAgentes();
            this.avisar("Agente actualizado");

        } catch (RuntimeException error) {
            this.avisar(error.getMessage());
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////

    private void desactivar() {

        if (this.idSeleccionado == null) {
            this.avisar("Seleccione primero un agente de la tabla");
            return;
        }

        try {
            this.servicio.desactivarAgente(this.idSeleccionado);
            this.cargarAgentes();
            this.avisar("Agente desactivado");

        } catch (RuntimeException error) {
            this.avisar(error.getMessage());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    private void activar() {

        if (this.idSeleccionado == null) {
            this.avisar("Seleccione primero un agente de la tabla");
            return;
        }

        try {
            this.servicio.activarAgente(this.idSeleccionado);
            this.cargarAgentes();
            this.avisar("Agente activado");

        } catch (RuntimeException error) {
            this.avisar(error.getMessage());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    private void probar() {

        Agente seleccionado = this.tabla.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            this.avisar("Seleccione primero un agente de la tabla");
            return;
        }

        Pane contenido = new VentanaProbarAgente(this.ventana, this.usuario, seleccionado).construir();

        this.ventana.setScene(new Scene(contenido, 900, 650));
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    private void volverAlMenu() {

        Pane contenido = new VentanaMenu(this.ventana, this.usuario).construir();

        this.ventana.setScene(new Scene(contenido, 400, 300));
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private Agente leerFormulario() {

        if (this.campoNombre.getText().isBlank()) {
            this.avisar("Escriba el nombre del agente");
            return null;
        }

        if (this.campoInstrucciones.getText().isBlank()) {
            this.avisar("Escriba las instrucciones del agente");
            return null;
        }

        Agente agente = new Agente();
        agente.setNombre(this.campoNombre.getText().trim());
        agente.setInstrucciones(this.campoInstrucciones.getText().trim());
        agente.setUsaDocumentos(this.campoUsaDocumentos.isSelected());

        String tokens = this.campoMaximoTokens.getText();

        if (tokens.isBlank()) {
            agente.setMaximoTokens(TOKENS_POR_DEFECTO);
        } else {
            try {
                agente.setMaximoTokens(Integer.parseInt(tokens.trim()));
            } catch (NumberFormatException error) {
                this.avisar("El maximo de tokens debe ser un numero entero");
                return null;
            }
        }

        return agente;
    }

    private void mostrarEnFormulario(Agente agente) {

        if (agente == null) {
            return;
        }

        this.idSeleccionado = agente.getId();
        this.campoNombre.setText(agente.getNombre());
        this.campoInstrucciones.setText(agente.getInstrucciones());
        this.campoMaximoTokens.setText(this.texto(agente.getMaximoTokens()));
        this.campoUsaDocumentos.setSelected(Boolean.TRUE.equals(agente.getUsaDocumentos()));
    }

    private void limpiarFormulario() {

        this.idSeleccionado = null;
        this.campoNombre.clear();
        this.campoInstrucciones.clear();
        this.campoMaximoTokens.clear();
        this.campoUsaDocumentos.setSelected(false);
        this.tabla.getSelectionModel().clearSelection();
    }

    private void avisar(String mensaje) {

        Alert aviso = new Alert(Alert.AlertType.INFORMATION, mensaje);

        aviso.setHeaderText(null);
        aviso.showAndWait();
    }

    private String texto(Object valor) {
        if (valor == null) {
            return "";
        }
        return String.valueOf(valor);
    }
}
