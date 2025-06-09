package aerolineaproyecto.controlador;

import aerolineaproyecto.modelo.dao.ClienteDAO;
import aerolineaproyecto.modelo.pojo.Cliente;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    private Cliente clienteEditar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    private Consumer<Cliente> onClienteGuardado;

public void setOnClienteGuardado(Consumer<Cliente> onClienteGuardado) {
    this.onClienteGuardado = onClienteGuardado;
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

        if (nombres.isEmpty() || apellidos.isEmpty() || nacionalidad.isEmpty() || fechaNacimiento == null) {
            showError("Debe completar todos los campos obligatorios.");
            return;
        }

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

        List<Cliente> clientes = ClienteDAO.cargarClientes();

        if (clienteEditar == null) {
            clientes.add(cliente); // nuevo cliente
        } else {
            // reemplazar cliente existente
            for (int i = 0; i < clientes.size(); i++) {
                if (Objects.equals(clientes.get(i), clienteEditar)) {
                    clientes.set(i, cliente);
                    break;
                }
            }
        }

        ClienteDAO.guardarClientes(clientes);
        cerrarVentana(event);
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
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
