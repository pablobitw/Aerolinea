/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package aerolineaproyecto.controlador;

import aerolineaproyecto.modelo.pojo.Cliente;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PABLO
 */
public class FXMLFormularioClienteController implements Initializable {

    @FXML
    private DatePicker dpFechaNacimiento;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField tfNombres;
    @FXML
    private TextField tfNacionalidad;
    @FXML
    private TextField tfApellidos;
    private Consumer<Cliente> onClienteGuardado;
    private Cliente clienteEditar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setOnClienteGuardado(Consumer<Cliente> onClienteGuardado) {
        this.onClienteGuardado = onClienteGuardado;
    }

    public void llenarFormulario(Cliente cliente) {
        this.clienteEditar = cliente;

        if (cliente != null) {
            tfNombres.setText(cliente.getNombres());
            tfApellidos.setText(cliente.getApellidos());
            tfNacionalidad.setText(cliente.getNacionalidad());
            dpFechaNacimiento.setValue(cliente.getFechaNacimiento());
        }
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        cerrarVentana(event);
    }

    @FXML
private void btnGuardar(ActionEvent event) {
    String nombres = tfNombres.getText();
    String apellidos = tfApellidos.getText();
    String nacionalidad = tfNacionalidad.getText();
    LocalDate fechaNacimiento = dpFechaNacimiento.getValue();

    // Validaciones de campos vacíos
    if (nombres.isEmpty() || apellidos.isEmpty() || nacionalidad.isEmpty() || fechaNacimiento == null) {
        showError("Debe completar todos los campos obligatorios.");
        return;
    }

    // Validaciones de formato
    if (!nombres.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+")) {
        showError("El nombre solo debe contener letras y espacios.");
        return;
    }

    if (!apellidos.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+")) {
        showError("El apellido solo debe contener letras y espacios.");
        return;
    }

    if (!nacionalidad.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+")) {
        showError("La nacionalidad solo debe contener letras y espacios.");
        return;
    }

    Cliente cliente = (clienteEditar != null) ? clienteEditar : new Cliente();

    cliente.setNombres(nombres);
    cliente.setApellidos(apellidos);
    cliente.setNacionalidad(nacionalidad);
    cliente.setFechaNacimiento(fechaNacimiento);

    if (onClienteGuardado != null) {
        onClienteGuardado.accept(cliente);
    }

    cerrarVentana(event);
}


    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    private void showError(String mensaje) {
    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
}
}
