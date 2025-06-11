package aerolineaproyecto.controlador;

import aerolineaproyecto.modelo.dao.BoletoDAO;
import aerolineaproyecto.modelo.pojo.Boleto;
import aerolineaproyecto.modelo.pojo.Vuelo;
import aerolineaproyecto.utilidad.Utilidad;
import javafx.geometry.Insets;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FXMLAsientosController implements Initializable {

    @FXML
    private BorderPane rootPane;
    @FXML
    private Label lbVuelo;
    @FXML
    private Label lbDisponibles;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    @FXML
    private GridPane asientosGrid;

    private Vuelo vueloActual;
    private List<Button> asientosSeleccionados = new ArrayList<>();
    private static final int COLUMNAS = 10;
    private int filas;

    // Estilos constantes para los asientos
    private static final String ESTILO_OCUPADO = "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;";
    private static final String ESTILO_LIBRE = "-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;";
    private static final String ESTILO_SELECCIONADO = "-fx-background-color: #f1c40f; -fx-text-fill: black; -fx-font-weight: bold; -fx-background-radius: 5px;";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("initialize() - Scene is: " + rootPane.getScene());

        Scene scene = rootPane.getScene();
        if (scene != null) {
            System.out.println("Agregando CSS en initialize");
            scene.getStylesheets().add(getClass().getResource("/aerolineaproyecto/recursos/css/asientos.css").toExternalForm());
        } else {
            rootPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    System.out.println("Agregando CSS en listener de escena");
                    newScene.getStylesheets().add(getClass().getResource("/aerolineaproyecto/recursos/css/asientos.css").toExternalForm());
                }
            });
        }
    }

    public void setVueloActual(Vuelo vuelo) {
        this.vueloActual = vuelo;
        lbVuelo.setText("Vuelo: " + vuelo.getId() + " (" + vuelo.getCiudadSalida() + " → " + vuelo.getCiudadLlegada() + ")");
        System.out.println("setVueloActual() - Vuelo id: " + vuelo.getId() + ", pasajeros: " + vuelo.getNumeroPasajeros());
        construirAsientos();
        actualizarDisponibles();
    }

    private void construirAsientos() {
        asientosGrid.getChildren().clear();

        int totalAsientos = vueloActual.getNumeroPasajeros();
        filas = (int) Math.ceil((double) totalAsientos / COLUMNAS);

        List<Boleto> boletosExistentes = BoletoDAO.obtenerBoletosPorVuelo(vueloActual.getId());
        System.out.println("Boletos ocupados para vuelo " + vueloActual.getId() + ":");
        boletosExistentes.forEach(b -> System.out.println("  Asiento ocupado: " + b.getNumAsiento()));

        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < COLUMNAS; col++) {
                int asientoIndex = fila * COLUMNAS + col + 1;
                if (asientoIndex > totalAsientos) break;

                String idAsiento = String.format("%c%d", 'A' + fila, col + 1);
                Button asiento = new Button(idAsiento);
                asiento.setPrefSize(60, 40);
                asiento.setPadding(new Insets(5));

                boolean ocupado = boletosExistentes.stream()
                        .anyMatch(b -> b.getNumAsiento().trim().equalsIgnoreCase(idAsiento.trim()));

                if (ocupado) {
                    asiento.setDisable(true);
                    asiento.setStyle(ESTILO_OCUPADO);
                    System.out.println("Asiento " + idAsiento + " marcado como OCUPADO (rojo)");
                } else {
                    asiento.setDisable(false);
                    asiento.setStyle(ESTILO_LIBRE);
                    asiento.setOnAction(e -> alternarSeleccion(asiento));
                    System.out.println("Asiento " + idAsiento + " marcado como LIBRE (verde)");
                }

                asientosGrid.add(asiento, col, fila);
            }
        }
    }

    private void alternarSeleccion(Button asiento) {
        if (asientosSeleccionados.contains(asiento)) {
            asientosSeleccionados.remove(asiento);
            asiento.setStyle(ESTILO_LIBRE);
            System.out.println("Asiento " + asiento.getText() + " DES-seleccionado");
        } else {
            // Limpiar selección previa (solo 1 selección)
            for (Button btn : new ArrayList<>(asientosSeleccionados)) {
                btn.setStyle(ESTILO_LIBRE);
            }
            asientosSeleccionados.clear();

            asientosSeleccionados.add(asiento);
            asiento.setStyle(ESTILO_SELECCIONADO);
            System.out.println("Asiento " + asiento.getText() + " seleccionado (amarillo)");
        }
        actualizarDisponibles();
    }

    private void actualizarDisponibles() {
        int total = vueloActual.getNumeroPasajeros();
        long ocupados = asientosGrid.getChildren().stream()
                .filter(n -> n instanceof Button && n.isDisable())
                .count();
        int seleccionados = asientosSeleccionados.size();
        int libres = total - (int) ocupados - seleccionados;

        lbDisponibles.setText("Disponibles: " + libres + " | Seleccionados: " + seleccionados);
        System.out.println("Disponibles: " + libres + ", Seleccionados: " + seleccionados);
    }

    @FXML
    private void btnAceptar(ActionEvent event) {
        if (asientosSeleccionados.isEmpty()) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Sin selección", "Seleccione al menos un asiento.");
            return;
        }

        List<String> listaAsientos = asientosSeleccionados.stream()
                .map(Button::getText)
                .collect(Collectors.toList());

        Stage stage = (Stage) btnAceptar.getScene().getWindow();
        stage.setUserData(listaAsientos);
        stage.close();
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        Stage ventana = Utilidad.obtenerEscenarioComponente(btnCancelar);
        ventana.close();
    }
}
