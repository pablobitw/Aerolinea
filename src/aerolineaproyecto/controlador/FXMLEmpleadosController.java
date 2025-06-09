
package aerolineaproyecto.controlador;

import aerolineaproyecto.modelo.dao.EmpleadoDAO;
import aerolineaproyecto.modelo.pojo.Empleado;
import aerolineaproyecto.utilidad.ExportadorDatos;
import aerolineaproyecto.utilidad.Utilidad;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.lang.reflect.Type;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;


/**
 * FXML Controller class
 *
 * @author Pablo Silva
 */
public class FXMLEmpleadosController implements Initializable {

    @FXML
    private TableView<Empleado> tvEmpleados;

    @FXML
    private TableColumn<Empleado, String> id;

    @FXML
    private TableColumn<Empleado, String> nombre;

    @FXML
    private TableColumn<Empleado, String> direccion;

    @FXML
    private TableColumn<Empleado, String> fechaNacimiento;

    @FXML
    private TableColumn<Empleado, Double> salario;

    @FXML
    private ImageView btnLogo;
    @FXML
    private TableColumn<Empleado, String> tipoEmpleado; 

    private ObservableList<Empleado> listaEmpleados;
    @FXML
    private TextField tfBuscarEmpleado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarEmpleados();
    }

    private void configurarColumnas() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tipoEmpleado.setCellValueFactory(new PropertyValueFactory<>("tipoEmpleado"));
        direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        fechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        salario.setCellValueFactory(new PropertyValueFactory<>("salario"));
    }

    private void cargarEmpleados() {
        List<Empleado> empleados = EmpleadoDAO.cargarEmpleados();
        listaEmpleados = FXCollections.observableArrayList(empleados);
        tvEmpleados.setItems(listaEmpleados);
    }
    @FXML
private void buscarEmpleado() {
    String filtro = tfBuscarEmpleado.getText().toLowerCase();

    if (filtro.isEmpty()) {
        tvEmpleados.setItems(listaEmpleados); // Mostrar todos si está vacío
    } else {
        ObservableList<Empleado> filtrados = FXCollections.observableArrayList();

        for (Empleado emp : listaEmpleados) {
            if (emp.getNombre().toLowerCase().contains(filtro) || 
                emp.getId().toLowerCase().contains(filtro)) {
                filtrados.add(emp);
            }
        }

        tvEmpleados.setItems(filtrados);
    }
}


    @FXML
    private void btnAgregar(ActionEvent event) {
        mostrarFormularioEmpleado(null);
    }

    @FXML
private void btnModificar(ActionEvent event) {
    Empleado seleccionado = tvEmpleados.getSelectionModel().getSelectedItem();

    if (seleccionado == null) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Debe seleccionar un empleado para modificar");
        return;
    }

    mostrarFormularioEmpleado(seleccionado);
}

private void mostrarFormularioEmpleado(Empleado empleado) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/aerolineaproyecto/vista/FXMLFormularioEmpleado.fxml"));
        Parent root = loader.load();

        FXMLFormularioEmpleadoController controller = loader.getController();

        if (empleado != null) {
            controller.llenarFormulario(empleado);
        }

        controller.setOnEmpleadoGuardado(empGuardado -> {
            if (empleado == null) {
                // Nuevo empleado
                listaEmpleados.add(empGuardado);
            } else {
                // Modificación
                int index = listaEmpleados.indexOf(empleado);
                if (index >= 0) {
                    listaEmpleados.set(index, empGuardado);
                }
            }
            // Guardar en archivo
            EmpleadoDAO.guardarEmpleados(new ArrayList<>(listaEmpleados));
        });

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.setTitle(empleado == null ? "Agregar Empleado" : "Modificar Empleado");
        stage.showAndWait();

    } catch (IOException ex) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Error al abrir formulario: " + ex.getMessage());
        ex.printStackTrace();
    }
}

@FXML
private void btnEliminar(ActionEvent event) {
    Empleado seleccionado = tvEmpleados.getSelectionModel().getSelectedItem();

    if (seleccionado == null) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Debe seleccionar un empleado para eliminar");
        return;
    }

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmar eliminación");
    alert.setHeaderText(null);
    alert.setContentText("¿Está seguro que desea eliminar el empleado seleccionado?");

    alert.showAndWait().ifPresent(res -> {
        if (res == ButtonType.OK) {
            listaEmpleados.remove(seleccionado);
            EmpleadoDAO.guardarEmpleados(new ArrayList<>(listaEmpleados));
        }
    });
}

@FXML
private void btnExportar(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Exportar empleados");

    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Archivo CSV (*.csv)", "*.csv"),
        new FileChooser.ExtensionFilter("Archivo Excel (*.xls)", "*.xls"),
        new FileChooser.ExtensionFilter("Archivo PDF (*.pdf)", "*.pdf")
    );

    fileChooser.setInitialFileName("empleados");

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    File selectedFile = fileChooser.showSaveDialog(stage);

    if (selectedFile != null) {
        String path = selectedFile.getAbsolutePath().toLowerCase();

        try {
            if (path.endsWith(".csv")) {
                ExportadorDatos.exportarCSV(listaEmpleados, selectedFile.getAbsolutePath());
            } else if (path.endsWith(".xls")) {
                ExportadorDatos.exportarExcel(listaEmpleados, selectedFile.getAbsolutePath());
            } else if (path.endsWith(".pdf")) {
                ExportadorDatos.exportarPDF(listaEmpleados, selectedFile.getAbsolutePath());
            } else {
                // Si el usuario no puso extensión, se asume .csv
                String pathCSV = selectedFile.getAbsolutePath() + ".csv";
                ExportadorDatos.exportarCSV(listaEmpleados, pathCSV);
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
            Stage escenario = (Stage) tvEmpleados.getScene().getWindow();
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

