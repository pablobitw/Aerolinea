package aerolineaproyecto.controlador;

import aerolineaproyecto.modelo.dao.AerolineaDAO;
import aerolineaproyecto.modelo.pojo.Aerolinea;
import aerolineaproyecto.utilidad.Utilidad;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLFormularioAerolineaController {

    @FXML
    private TextField tfID;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfDireccion;
    private TextField tfNombreContacto;  // corregido nombre variable
    @FXML
    private TextField tfNumero;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    private Consumer<Aerolinea> onAerolineaGuardada;

    private boolean esEdicion = false;
    private String idOriginal; // Para controlar si se modifica el id
    @FXML
    private TextField TfNombreContacto;
    @FXML
    private Label labelFlightID;
    @FXML
    private Label labelOrigin;
    @FXML
    private Label labelDestination;
    @FXML
    private Label labelDepartureDate;
    @FXML
    private Label labelPrice;
    @FXML
    private ComboBox<?> comboBoxCustomer;
    @FXML
    private DatePicker datePickerPurchaseDate;
    @FXML
    private TextField textFieldSeatNumber;
    @FXML
    private Button btnSelectSeat;

    /**
     * Llena el formulario con los datos de una aerolínea para editar.
     * @param aerolinea Aerolínea a editar
     */
    public void llenarFormulario(Aerolinea aerolinea) {
    if (aerolinea != null) {
        esEdicion = true;
        idOriginal = String.valueOf(aerolinea.getId());

        tfID.setText(idOriginal);
        tfID.setDisable(true); // El id no se puede modificar al editar

        tfNombre.setText(aerolinea.getNombre());
        tfDireccion.setText(aerolinea.getDireccion());
        tfNombreContacto.setText(aerolinea.getContacto());
        tfNumero.setText(aerolinea.getTelefono());
    }
}

    public void setOnAerolineaGuardada(Consumer<Aerolinea> onAerolineaGuardada) {
        this.onAerolineaGuardada = onAerolineaGuardada;
    }

    @FXML
private void btnGuardar(ActionEvent event) {
    // Validar que no haya campos vacíos
    if (tfID.getText().trim().isEmpty() ||
        tfNombre.getText().trim().isEmpty() ||
        tfDireccion.getText().trim().isEmpty() ||
        tfNombreContacto.getText().trim().isEmpty() ||
        tfNumero.getText().trim().isEmpty()) {

        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Debe completar todos los campos.");
        return;
    }

    int id;
    try {
        id = Integer.parseInt(tfID.getText().trim());
    } catch (NumberFormatException e) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "El ID debe ser un número entero válido.");
        return;
    }

    Aerolinea aerolinea = new Aerolinea(
            id,
            tfNombre.getText().trim(),
            tfDireccion.getText().trim(),
            tfNombreContacto.getText().trim(),
            tfNumero.getText().trim()
    );

    try {
        if (esEdicion) {
            AerolineaDAO.actualizarAerolinea(aerolinea);
        } else {
            boolean existe = AerolineaDAO.cargarAerolineas().stream()
                    .anyMatch(a -> a.getId() == aerolinea.getId());

            if (existe) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Ya existe una aerolínea con ese ID.");
                return;
            }

            AerolineaDAO.agregarAerolinea(aerolinea);
        }
    } catch (Exception e) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Error al guardar la aerolínea: " + e.getMessage());
        return;
    }

    if (onAerolineaGuardada != null) {
        onAerolineaGuardada.accept(aerolinea);
    }

    Stage stage = (Stage) btnGuardar.getScene().getWindow();
    stage.close();
}

    @FXML
    private void btnCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleSelectSeat(ActionEvent event) {
    }
}
