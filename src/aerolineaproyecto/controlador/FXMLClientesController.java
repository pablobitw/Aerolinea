/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package aerolineaproyecto.controlador;

import aerolineaproyecto.modelo.dao.ClienteDAO;
import static aerolineaproyecto.modelo.dao.ClienteDAO.cargarClientes;
import aerolineaproyecto.modelo.dao.EmpleadoDAO;
import aerolineaproyecto.modelo.pojo.Cliente;
import aerolineaproyecto.modelo.pojo.Empleado;
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
public class FXMLClientesController implements Initializable {

@FXML
private TableView<Cliente> tvClientes;

@FXML
private TableColumn<Cliente, String> nombres;

@FXML
private TableColumn<Cliente, String> apellidos;

@FXML
private TableColumn<Cliente, String> nacionalidad;

@FXML
private TableColumn<Cliente, String> fechaNacimiento;

    /**
     * Initializes the controller class.
     */
    private ObservableList<Cliente> listaClientes;

@Override
public void initialize(URL url, ResourceBundle rb) {
    configurarColumnas();
    cargarClientes();
}

private void configurarColumnas() {
    nombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
    apellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
    nacionalidad.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));
    fechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
}

private void cargarClientes() {
    List<Cliente> clientes = ClienteDAO.cargarClientes();
    listaClientes = FXCollections.observableArrayList(clientes);
    tvClientes.setItems(listaClientes);
}


    @FXML
    private void btnAgregar(ActionEvent event) {
        mostrarFormularioCliente(null);
    }

    @FXML
private void btnModificar(ActionEvent event) {
    Cliente seleccionado = tvClientes.getSelectionModel().getSelectedItem();

    if (seleccionado == null) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Debe seleccionar un cliente para modificar");
        return;
    }

    mostrarFormularioCliente(seleccionado);
}

private void mostrarFormularioCliente(Cliente cliente) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/aerolineaproyecto/vista/FXMLFormularioCliente.fxml"));
        Parent root = loader.load();

        FXMLFormularioClienteController controller = loader.getController();

        if (cliente != null) {
            controller.llenarFormulario(cliente); //
        }

        controller.setOnClienteGuardado(clnGuardado -> {
            if (cliente == null) {
                listaClientes.add(clnGuardado);
            } else {
                int index = listaClientes.indexOf(cliente);
                if (index >= 0) {
                    listaClientes.set(index, clnGuardado);
                }
            }
            ClienteDAO.guardarClientes(new ArrayList<>(listaClientes)); // 
        });

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.setTitle(cliente == null ? "Agregar Cliente" : "Modificar Cliente"); // 
        stage.showAndWait();

    } catch (IOException ex) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Error al abrir formulario: " + ex.getMessage());
        ex.printStackTrace();
    }
}

@FXML
private void btnEliminar(ActionEvent event) {
    Cliente seleccionado = tvClientes.getSelectionModel().getSelectedItem();

    if (seleccionado == null) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Debe seleccionar un cliente para eliminar"); // ✅ cambiar a cliente
        return;
    }

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmar eliminación");
    alert.setHeaderText(null);
    alert.setContentText("¿Está seguro que desea eliminar el cliente seleccionado?"); // ✅ cambiar a cliente

    alert.showAndWait().ifPresent(res -> {
        if (res == ButtonType.OK) {
            listaClientes.remove(seleccionado);
            ClienteDAO.guardarClientes(new ArrayList<>(listaClientes));
        }
    });
}

    @FXML
private void btnExportar(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Exportar Clientes");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Archivo CSV (*.csv)", "*.csv"),
        new FileChooser.ExtensionFilter("Archivo Excel (*.xls)", "*.xls"),
        new FileChooser.ExtensionFilter("Archivo PDF (*.pdf)", "*.pdf")
    );
    fileChooser.setInitialFileName("clientes");

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    File selectedFile = fileChooser.showSaveDialog(stage);

    if (selectedFile != null) {
        String path = selectedFile.getAbsolutePath().toLowerCase();

        try {
            if (path.endsWith(".csv")) {
                ExportadorDatos.exportarCSVClientes(listaClientes, selectedFile.getAbsolutePath());
            } else if (path.endsWith(".xls")) {
                ExportadorDatos.exportarExcelClientes(listaClientes, selectedFile.getAbsolutePath());
            } else if (path.endsWith(".pdf")) {
                ExportadorDatos.exportarPDFClientes(listaClientes, selectedFile.getAbsolutePath());
            } else {
                // Si no puso extensión, se asume CSV y se agrega la extensión
                String pathCSV = selectedFile.getAbsolutePath() + ".csv";
                ExportadorDatos.exportarCSVClientes(listaClientes, pathCSV);
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
            Stage escenario = (Stage) tvClientes.getScene().getWindow();
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
