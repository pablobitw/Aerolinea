package aerolineaproyecto.controlador;

import aerolineaproyecto.modelo.dao.VueloDAO;
import aerolineaproyecto.modelo.pojo.Vuelo;
import aerolineaproyecto.utilidad.ExportadorDatos;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLVuelosController implements Initializable {

    @FXML private TableView<Vuelo> tvVuelos;
    @FXML private TableColumn<Vuelo, Integer> id;
    @FXML private TableColumn<Vuelo, Integer> numero_pasajeros;
    @FXML private TableColumn<Vuelo, Double> costo_boleto;
    @FXML private TableColumn<Vuelo, String> tiempo_recorrido;
    @FXML private TableColumn<Vuelo, String> ciudad_salida;
    @FXML private TableColumn<Vuelo, String> ciudad_llegada;
    @FXML private TableColumn<Vuelo, String> fecha_hora_salida;
    @FXML private TableColumn<Vuelo, String> hora_llegada;
    
    @FXML
    private TableColumn<Vuelo, String> pilotos; 
    @FXML
    private TableColumn<Vuelo, String> asistentes;

    @FXML private ImageView btnLogo;

    private ObservableList<Vuelo> listaVuelos;
    @FXML
    private TableColumn<Vuelo, String> avion;
  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarVuelos();
       
    }

    private void configurarColumnas() {
    id.setCellValueFactory(new PropertyValueFactory<>("id"));
    numero_pasajeros.setCellValueFactory(new PropertyValueFactory<>("numeroPasajeros"));
    costo_boleto.setCellValueFactory(new PropertyValueFactory<>("costoBoleto"));
    tiempo_recorrido.setCellValueFactory(new PropertyValueFactory<>("tiempoRecorrido"));
    ciudad_salida.setCellValueFactory(new PropertyValueFactory<>("ciudadSalida"));
    ciudad_llegada.setCellValueFactory(new PropertyValueFactory<>("ciudadLlegada"));
    fecha_hora_salida.setCellValueFactory(new PropertyValueFactory<>("salida")); 
    hora_llegada.setCellValueFactory(new PropertyValueFactory<>("llegada"));
    pilotos.setCellValueFactory(new PropertyValueFactory<>("pilotosNombres"));
    asistentes.setCellValueFactory(new PropertyValueFactory<>("asistentesNombres"));
    
    // Para mostrar el modelo del avión en la columna avión
    avion.setCellValueFactory(cellData -> 
        new ReadOnlyStringWrapper(cellData.getValue().getModeloAvion())
    );
}

    private void cargarVuelos() {
        List<Vuelo> vuelos = VueloDAO.cargarVuelos();
        listaVuelos = FXCollections.observableArrayList(vuelos);
        tvVuelos.setItems(listaVuelos);
    }

    @FXML
    private void btnAgregar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aerolineaproyecto/vista/FXMLFormularioVuelo.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Agregar Vuelo");
            stage.showAndWait();

            cargarVuelos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnModificar(ActionEvent event) {
        Vuelo vueloSeleccionado = tvVuelos.getSelectionModel().getSelectedItem();
        if (vueloSeleccionado == null) {
            mostrarAlerta("Modificación", "Selecciona un vuelo para modificar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aerolineaproyecto/vista/FXMLFormularioVuelo.fxml"));
            Parent root = loader.load();

            FXMLFormularioVueloController controlador = loader.getController();
            controlador.setVuelo(vueloSeleccionado);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modificar Vuelo");
            stage.showAndWait();

            cargarVuelos();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
        Vuelo vueloSeleccionado = tvVuelos.getSelectionModel().getSelectedItem();
        if (vueloSeleccionado == null) {
            mostrarAlerta("Eliminación", "Selecciona un vuelo para eliminar.");
            return;
        }

        // Confirmación con Alert
        Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmacion.setTitle("Confirmar eliminación");
        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setContentText("¿Seguro que deseas eliminar el vuelo seleccionado?");
        
        Optional<ButtonType> resultado = alertaConfirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            if (VueloDAO.eliminarVuelo(vueloSeleccionado)) {
                mostrarAlerta("Eliminación", "Vuelo eliminado correctamente.");
                cargarVuelos();
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el vuelo.");
            }
        }
    }

    @FXML
private void btnExportar(ActionEvent event) {
    if (listaVuelos == null || listaVuelos.isEmpty()) {
        mostrarAlerta("Exportación", "No hay vuelos para exportar.");
        return;
    }

    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Exportar vuelos");

    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Archivo CSV (*.csv)", "*.csv"),
        new FileChooser.ExtensionFilter("Archivo Excel (*.xls)", "*.xls"),
        new FileChooser.ExtensionFilter("Archivo PDF (*.pdf)", "*.pdf")
    );

    fileChooser.setInitialFileName("vuelos_exportados");

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    File selectedFile = fileChooser.showSaveDialog(stage);

    if (selectedFile != null) {
        String path = selectedFile.getAbsolutePath().toLowerCase();

        try {
            if (path.endsWith(".csv")) {
                ExportadorDatos.exportarCSVVuelos(listaVuelos, selectedFile.getAbsolutePath());
            } else if (path.endsWith(".xls")) {
                ExportadorDatos.exportarExcelVuelos(listaVuelos, selectedFile.getAbsolutePath());
            } else if (path.endsWith(".pdf")) {
                ExportadorDatos.exportarPDFVuelos(listaVuelos, selectedFile.getAbsolutePath());
            } else {
                // Si no especificó extensión, por defecto CSV
                String pathCSV = selectedFile.getAbsolutePath() + ".csv";
                ExportadorDatos.exportarCSVVuelos(listaVuelos, pathCSV);
            }

            mostrarAlerta("Exportación", "Archivo exportado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Ocurrió un error al exportar los archivos: " + e.getMessage());
        }
    }
}



    @FXML
    private void btnLogoPresionado(ActionEvent event) {

        try {
            Stage escenario = (Stage) tvVuelos.getScene().getWindow();
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

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
