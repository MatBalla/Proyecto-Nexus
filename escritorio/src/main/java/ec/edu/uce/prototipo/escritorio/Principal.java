package ec.edu.uce.prototipo.escritorio;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

// Abre login
public class Principal extends Application {

    @Override
    public void start(Stage ventana) {

        Pane contenido = new VentanaLogin(ventana).construir();

        Scene escena = new Scene(contenido, 300, 200);

        ventana.setScene(escena);
        ventana.show();
    }

    public static void main(String[] argumentos) {
        launch(argumentos);
    }
}
