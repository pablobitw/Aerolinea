/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package aerolineaproyecto.controlador;

import aerolineaproyecto.modelo.dao.BoletoDAO;
import aerolineaproyecto.modelo.pojo.Boleto;
import aerolineaproyecto.modelo.pojo.Vuelo;
import aerolineaproyecto.utilidad.Utilidad;
import java.awt.Insets;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PABLO
 */
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
    private static final int FILAS = 6;
    private static final int COLUMNAS = 5;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // El vuelo se asignará desde otra vista
    }

    public void setVueloActual(Vuelo vuelo) {
        this.vueloActual = vuelo;
        construirAsientos();
        actualizarDisponibles();
    }

    private void construirAsientos() {
        asientosGrid.getChildren().clear();
        List<Boleto> boletosExistentes = BoletoDAO.obtenerBoletosPorVuelo(vueloActual.getId());

        for (int fila = 0; fila < FILAS; fila++) {
            for (int col = 0; col < COLUMNAS; col++) {
                String idAsiento = String.format("%c%d", 'A' + fila, col + 1);
                Button asiento = new Button(idAsiento);
                asiento.setPrefSize(60, 40);
                asiento.setPadding(new Insets(5));
                asiento.getStyleClass().add("asiento-libre");

                boolean ocupado = boletosExistentes.stream()
                        .anyMatch(b -> b.getAsiento().equals(idAsiento));

                if (ocupado) {
                    asiento.setDisable(true);
                    asiento.getStyleClass().add("asiento-ocupado");
                } else {
                    asiento.setOnAction(e -> alternarSeleccion(asiento));
                }

                asientosGrid.add(asiento, col, fila);
            }
        }
    }

    private void alternarSeleccion(Button asiento) {
        if (asientosSeleccionados.contains(asiento)) {
            asientosSeleccionados.remove(asiento);
            asiento.getStyleClass().remove("asiento-seleccionado");
        } else {
            asientosSeleccionados.add(asiento);
            asiento.getStyleClass().add("asiento-seleccionado");
        }
        actualizarDisponibles();
    }

    private void actualizarDisponibles() {
        int total = FILAS * COLUMNAS;
        long ocupados = asientosGrid.getChildren().stream()
                .filter(n -> n instanceof Button && n.isDisable())
                .count();
        int seleccionados = asientosSeleccionados.size();
        int libres = total - (int) ocupados - seleccionados;

        lbDisponibles.setText("Disponibles: " + libres + " | Seleccionados: " + seleccionados);
    }

    @FXML
    private void btnAceptarAction(ActionEvent event) {
        if (asientosSeleccionados.isEmpty()) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Sin selección", "Seleccione al menos un asiento.");
            return;
        }

        List<Boleto> nuevosBoletos = new ArrayList<>();
        for (Button asiento : asientosSeleccionados) {
            Boleto nuevo = new Boleto();
            nuevo.setIdVuelo(vueloActual.getId());
            nuevo.setAsiento(asiento.getText());
            // puedes setear más propiedades si es necesario
            nuevosBoletos.add(nuevo);
        }

        boolean exito = BoletoDAO.guardarBoletos(nuevosBoletos);
        if (exito) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", "Boletos reservados correctamente.");
            btnCancelarAction(null);
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "No se pudieron guardar los boletos.");
        }
    }

    @FXML
    private void btnCancelarAction(ActionEvent event) {
        Stage ventana = Utilidad.obtenerEscenarioComponente(btnCancelar);
        ventana.close();
    }
}