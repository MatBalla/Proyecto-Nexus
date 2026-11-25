package ec.edu.uce.prototipo.escritorio;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//login
public class VentanaLogin {

    private final ServicioRest servicio = new ServicioRest();

    private final Stage ventana;

    private final TextField campoCorreo = new TextField();

    private final PasswordField campoClave = new PasswordField();

    private final Label etiquetaMensaje = new Label();

    public VentanaLogin(Stage ventana) {
        this.ventana = ventana;
    }

    public Pane construir() {

        GridPane formulario = new GridPane();
        formulario.setHgap(10);
        formulario.setVgap(8);

        // add(control, columna, fila)
        formulario.add(new Label("Correo"), 0, 0);
        formulario.add(this.campoCorreo, 1, 0);

        formulario.add(new Label("Clave"), 0, 1);
        formulario.add(this.campoClave, 1, 1);

        Button botonEntrar = new Button("Entrar");
        botonEntrar.setOnAction(evento -> this.entrar());

        VBox raiz = new VBox(10);
        raiz.setPadding(new Insets(20));
        raiz.getChildren().add(formulario);
        raiz.getChildren().add(botonEntrar);
        raiz.getChildren().add(this.etiquetaMensaje);

        return raiz;
    }

    private void entrar() {

        String correo = this.campoCorreo.getText();
        String clave = this.campoClave.getText();

        try {

            Usuario usuario = this.servicio.iniciarSesion(correo, clave);

            if (usuario == null) {
                this.etiquetaMensaje.setText("Correo o clave incorrectos");
                return;
            }

            Pane contenido = new VentanaMenu(this.ventana, usuario).construir();

            this.ventana.setScene(new Scene(contenido, 400, 300));

        } catch (RuntimeException error) {
            this.etiquetaMensaje.setText(error.getMessage());
        }
    }
}
