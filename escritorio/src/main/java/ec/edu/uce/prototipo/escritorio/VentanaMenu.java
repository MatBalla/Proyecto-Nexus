package ec.edu.uce.prototipo.escritorio;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Menu
public class VentanaMenu {

    private final Stage ventana;
    private final Usuario usuario;

    public VentanaMenu(Stage ventana, Usuario usuario) {
        this.ventana = ventana;
        this.usuario = usuario;
    }

    public Pane construir() {

        VBox raiz = new VBox(8);
        raiz.setPadding(new Insets(20));

        raiz.getChildren().add(new Label("Sesion: " + this.usuario.getNombre()
                + " (" + this.usuario.getRol() + ")"));

        Button botonAgentes = new Button("Agentes");
        botonAgentes.setOnAction(evento -> this.abrirAgentes());
        raiz.getChildren().add(botonAgentes);

        raiz.getChildren().add(this.botonPendiente("Flujos"));
        raiz.getChildren().add(this.botonPendiente("Documentos"));
        raiz.getChildren().add(this.botonPendiente("Ejecuciones"));
        raiz.getChildren().add(this.botonPendiente("Auditoria y consumo"));

        Button botonSalir = new Button("Salir");
        botonSalir.setOnAction(evento -> this.salir());
        raiz.getChildren().add(botonSalir);

        return raiz;
    }

    private Button botonPendiente(String nombre) {

        Button boton = new Button(nombre);

        boton.setOnAction(evento -> this.avisar( "El modulo de " + nombre.toLowerCase() + " se implementa luego."));

        return boton;
    }

    private void abrirAgentes() {

        Pane contenido = new VentanaAgentes(this.ventana, this.usuario).construir();

        this.ventana.setScene(new Scene(contenido, 900, 620));
    }

    private void salir() {

        Pane contenido = new VentanaLogin(this.ventana).construir();

        this.ventana.setScene(new Scene(contenido, 400, 220));
    }

    private void avisar(String mensaje) {

        Alert aviso = new Alert(Alert.AlertType.INFORMATION, mensaje);

        aviso.setHeaderText(null);
        aviso.showAndWait();
    }
}
