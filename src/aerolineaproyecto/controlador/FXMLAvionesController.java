/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package aerolineaproyecto.controlador;

import aerolineaproyecto.modelo.dao.AvionDAO;
import aerolineaproyecto.modelo.dao.ClienteDAO;
import aerolineaproyecto.modelo.pojo.Avion;
import aerolineaproyecto.modelo.pojo.Cliente;
import aerolineaproyecto.utilidad.ExportadorDatos;
import aerolineaproyecto.utilidad.Utilidad;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Pablo Silva
 */
public class FXMLAvionesController implements Initializable {

    private TableView<?> tvEmpleados;
    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private ImageView btnLogo;
    @FXML
    private TableColumn<?, ?> capacidad;
    @FXML
    private TableColumn<?, ?> modelo;
    @FXML
    private TableColumn<?, ?> peso;

    /**
     * Initializes the controller class.
     */
    private ObservableList<Avion> listaAviones;
    @FXML
    private TableView<Avion> tvAviones;

@Override
public void initialize(URL url, ResourceBundle rb) {
    configurarColumnas();
    cargarAviones();
}

private void configurarColumnas() {
    id.setCellValueFactory(new PropertyValueFactory<>("id"));
    capacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
    modelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
    peso.setCellValueFactory(new PropertyValueFactory<>("peso"));
}

private void cargarAviones() {
    List<Avion> aviones = AvionDAO.cargarAviones();
    listaAviones = FXCollections.observableArrayList(aviones);
    tvAviones.setItems(listaAviones);
}

    @FXML
private void btnAgregar(ActionEvent event) {
    mostrarFormularioAvion(null);
}

@FXML
private void btnModificar(ActionEvent event) {
    Avion seleccionado = tvAviones.getSelectionModel().getSelectedItem();

    if (seleccionado == null) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Debe seleccionar un avión para modificar");
        return;
    }

    mostrarFormularioAvion(seleccionado);
}

private void mostrarFormularioAvion(Avion avion) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/aerolineaproyecto/vista/FXMLFormularioAvion.fxml"));
        Parent root = loader.load();

        FXMLFormularioAvionController controller = loader.getController();

        if (avion != null) {
            controller.llenarFormulario(avion);
        }

        controller.setOnAvionGuardado(avionGuardado -> {
            if (avion == null) {
                listaAviones.add(avionGuardado);
            } else {
                int index = listaAviones.indexOf(avion);
                if (index >= 0) {
                    listaAviones.set(index, avionGuardado);
                }
            }
            AvionDAO.guardarAviones(new ArrayList<>(listaAviones));
        });

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.setTitle(avion == null ? "Agregar Avión" : "Modificar Avión");
        stage.showAndWait();

    } catch (IOException ex) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Error al abrir formulario: " + ex.getMessage());
        ex.printStackTrace();
    }
}

@FXML
private void btnEliminar(ActionEvent event) {
    Avion seleccionado = tvAviones.getSelectionModel().getSelectedItem();

    if (seleccionado == null) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Debe seleccionar un avión para eliminar");
        return;
    }

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmar eliminación");
    alert.setHeaderText(null);
    alert.setContentText("¿Está seguro que desea eliminar el avión seleccionado?");

    alert.showAndWait().ifPresent(res -> {
        if (res == ButtonType.OK) {
            listaAviones.remove(seleccionado);
            AvionDAO.guardarAviones(new ArrayList<>(listaAviones));
        }
    });
}

@FXML
private void btnExportar(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Exportar Aviones");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Archivo CSV (*.csv)", "*.csv"),
        new FileChooser.ExtensionFilter("Archivo Excel (*.xls)", "*.xls"),
        new FileChooser.ExtensionFilter("Archivo PDF (*.pdf)", "*.pdf")
    );
    fileChooser.setInitialFileName("aviones");

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    File selectedFile = fileChooser.showSaveDialog(stage);

    if (selectedFile != null) {
        String path = selectedFile.getAbsolutePath().toLowerCase();

        try {
            if (path.endsWith(".csv")) {
                ExportadorDatos.exportarCSVAviones(listaAviones, selectedFile.getAbsolutePath());
            } else if (path.endsWith(".xls")) {
                ExportadorDatos.exportarExcelAviones(listaAviones, selectedFile.getAbsolutePath());
            } else if (path.endsWith(".pdf")) {
                ExportadorDatos.exportarPDFAviones(listaAviones, selectedFile.getAbsolutePath());
            } else {
                String pathCSV = selectedFile.getAbsolutePath() + ".csv";
                ExportadorDatos.exportarCSVAviones(listaAviones, pathCSV);
            }
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", "Archivo exportado correctamente.");
        } catch (Exception e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al exportar", e.getMessage());
        }
    }
}

    @FXML
    private void btnLogoPresionado(ActionEvent event) {
        try {
            Stage escenario = (Stage) tvAviones.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aerolineaproyecto/vista/FXMLPantallaPrincipal.fxml"));
            Parent root = loader.load();

            Scene escena = new Scene(root);
            escenario.setScene(escena);
            escenario.setTitle("Pantalla Principal");
            escenario.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
