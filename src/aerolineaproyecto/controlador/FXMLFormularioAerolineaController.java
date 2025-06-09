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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLFormularioAerolineaController {

    @FXML
    private TextField tfID;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfDireccion;
    @FXML
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

    /**
     * Llena el formulario con los datos de una aerolínea para editar.
     * @param aerolinea Aerolínea a editar
     */
    public void llenarFormulario(Aerolinea aerolinea) {
        if (aerolinea != null) {
            esEdicion = true;
            idOriginal = aerolinea.getId();

            tfID.setText(aerolinea.getId());
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

        Aerolinea aerolinea = new Aerolinea(
                tfID.getText().trim(),
                tfNombre.getText().trim(),
                tfDireccion.getText().trim(),
                tfNombreContacto.getText().trim(),
                tfNumero.getText().trim()
        );

        try {
            if (esEdicion) {
                // Actualizar aerolínea existente
                AerolineaDAO.actualizarAerolinea(aerolinea);
            } else {
                // Validar que el ID no exista ya
                boolean existe = AerolineaDAO.cargarAerolineas().stream()
                        .anyMatch(a -> a.getId().equals(aerolinea.getId()));

                if (existe) {
                    Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Ya existe una aerolínea con ese ID.");
                    return;
                }
                // Agregar nueva aerolínea
                AerolineaDAO.agregarAerolinea(aerolinea);
            }
        } catch (Exception e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Error al guardar la aerolínea: " + e.getMessage());
            return;
        }

        if (onAerolineaGuardada != null) {
            onAerolineaGuardada.accept(aerolinea);
        }

        // Cerrar ventana
        Stage stage = (Stage) btnGuardar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
}
