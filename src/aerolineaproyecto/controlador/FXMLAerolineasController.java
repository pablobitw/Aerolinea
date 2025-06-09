package aerolineaproyecto.controlador;

import aerolineaproyecto.modelo.dao.AerolineaDAO;
import aerolineaproyecto.modelo.pojo.Aerolinea;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLAerolineasController implements Initializable {

    @FXML private TableView<Aerolinea> tvAerolineas;
    @FXML private TableColumn<Aerolinea, String> colId;
    @FXML private TableColumn<Aerolinea, String> colNombre;
    @FXML private TableColumn<Aerolinea, String> colDireccion;
    @FXML private TableColumn<Aerolinea, String> colContacto;
    @FXML private TableColumn<Aerolinea, String> colTelefono;

    private ObservableList<Aerolinea> listaAerolineas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarAerolineas();
    }

    private void configurarColumnas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colContacto.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
    }

    private void cargarAerolineas() {
        List<Aerolinea> aerolineas = AerolineaDAO.cargarAerolineas();
        listaAerolineas = FXCollections.observableArrayList(aerolineas);
        tvAerolineas.setItems(listaAerolineas);
    }

    @FXML
    private void btnAgregar(ActionEvent event) {
        mostrarFormularioAerolinea(null);
    }

    @FXML
    private void btnModificar(ActionEvent event) {
        Aerolinea seleccionada = tvAerolineas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Debe seleccionar una aerolínea para modificar");
            return;
        }
        mostrarFormularioAerolinea(seleccionada);
    }

    private void mostrarFormularioAerolinea(Aerolinea aerolinea) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aerolineaproyecto/vista/FXMLFormularioAerolinea.fxml"));
            Parent root = loader.load();

            FXMLFormularioAerolineaController controller = loader.getController();

            if (aerolinea != null) {
                controller.llenarFormulario(aerolinea);
            }

            controller.setOnAerolineaGuardada(aerGuardada -> {
                if (aerolinea == null) {
                    listaAerolineas.add(aerGuardada);
                } else {
                    int index = listaAerolineas.indexOf(aerolinea);
                    if (index >= 0) {
                        listaAerolineas.set(index, aerGuardada);
                    }
                }
                AerolineaDAO.guardarAerolineas(new ArrayList<>(listaAerolineas));
            });

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle(aerolinea == null ? "Agregar Aerolínea" : "Modificar Aerolínea");
            stage.showAndWait();

        } catch (IOException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Error al abrir formulario: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
        Aerolinea seleccionada = tvAerolineas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Debe seleccionar una aerolínea para eliminar");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText(null);
        alert.setContentText("¿Está seguro que desea eliminar la aerolínea seleccionada?");

        alert.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                listaAerolineas.remove(seleccionada);
                AerolineaDAO.guardarAerolineas(new ArrayList<>(listaAerolineas));
            }
        });
    }

    @FXML
    private void btnExportar(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar Aerolíneas");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Archivo CSV (*.csv)", "*.csv"),
            new FileChooser.ExtensionFilter("Archivo Excel (*.xls)", "*.xls"),
            new FileChooser.ExtensionFilter("Archivo PDF (*.pdf)", "*.pdf")
        );
        fileChooser.setInitialFileName("aerolineas");

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showSaveDialog(stage);

        if (selectedFile != null) {
            String path = selectedFile.getAbsolutePath().toLowerCase();
            try {
                if (path.endsWith(".csv")) {
                    ExportadorDatos.exportarCSVAerolineas(listaAerolineas, selectedFile.getAbsolutePath());
                } else if (path.endsWith(".xls")) {
                    ExportadorDatos.exportarExcelAerolineas(listaAerolineas, selectedFile.getAbsolutePath());
                } else if (path.endsWith(".pdf")) {
                    ExportadorDatos.exportarPDFAerolineas(listaAerolineas, selectedFile.getAbsolutePath());
                } else {
                    String pathCSV = selectedFile.getAbsolutePath() + ".csv";
                    ExportadorDatos.exportarCSVAerolineas(listaAerolineas, pathCSV);
                }
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", "Datos exportados correctamente");
            } catch (Exception e) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Error al exportar: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

  
    @FXML
    private void btnLogoPresionado(ActionEvent event) {
        try {
            Stage escenario = (Stage) tvAerolineas.getScene().getWindow();
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
