/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package aerolineaproyecto.controlador;

import aerolineaproyecto.modelo.dao.EmpleadoDAO;
import aerolineaproyecto.modelo.pojo.Empleado;
import aerolineaproyecto.modelo.pojo.Piloto;
import aerolineaproyecto.modelo.pojo.AsistenteVuelo;
import aerolineaproyecto.modelo.pojo.Administrativo;
import aerolineaproyecto.utilidad.Utilidad;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.function.Consumer;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PABLO
 */
public class FXMLFormularioEmpleadoController implements Initializable {

    @FXML
    private TextField tfNombre, tfHoras, tfRango, tfUser, tfPass, tfSalario, tfDireccion, tfIdiomas;
    @FXML
    private ComboBox<String> cbLicencia;
    @FXML
    private DatePicker dpFechaNacimiento;
    @FXML
    private RadioButton rbMale, rbFemale, rbPiloto, rbAsistente, rbAdmin;
    @FXML
    private Button btnGuardar, btnCancelar;

    @FXML
    private ToggleGroup tgRol;  // Grupo para radio buttons de rol
    @FXML
private ToggleGroup tgGenero; // declara este atributo

    private Empleado empleado;
     private int ultimoIdNumerico = 0;


    private Consumer<Empleado> onEmpleadoGuardado;
    

   @Override
public void initialize(URL url, ResourceBundle rb) {
    
    tgGenero = new ToggleGroup();
    rbMale.setToggleGroup(tgGenero);
    rbFemale.setToggleGroup(tgGenero);
    cbLicencia.setItems(FXCollections.observableArrayList("PPL", "CPL", "ATPL"));

    tfNombre.setTextFormatter(new TextFormatter<>(c ->
            c.getControlNewText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]*") ? c : null));

    tgRol = new ToggleGroup();
    rbPiloto.setToggleGroup(tgRol);
    rbAsistente.setToggleGroup(tgRol);
    rbAdmin.setToggleGroup(tgRol);

    tgRol.selectedToggleProperty().addListener((obs, oldVal, newVal) -> actualizarFormulario());

    //  Restringir fechas para mayores de 16 en 2025
    dpFechaNacimiento.setDayCellFactory(picker -> new DateCell() {
        @Override
        public void updateItem(LocalDate date, boolean empty) {
            super.updateItem(date, empty);
            LocalDate fechaLimite = LocalDate.of(2009, 12, 31);
            setDisable(empty || date.isAfter(fechaLimite));
        }
    });

    actualizarFormulario();
}

    private void actualizarFormulario() {
    // Deshabilitar todos los campos específicos inicialmente
    tfHoras.setDisable(true);
    cbLicencia.setDisable(true);
    tfIdiomas.setDisable(true);

    if (rbPiloto.isSelected()) {
        tfHoras.setDisable(false);
        cbLicencia.setDisable(false);
    } else if (rbAsistente.isSelected()) {
        tfHoras.setDisable(false);
        tfIdiomas.setDisable(false);
    } else if (rbAdmin.isSelected()) {
        tfHoras.setDisable(false);
    }
}

    public void cargarEmpleado(Empleado empleado) {
        if (empleado == null) {
            return;
        }

        this.empleado = empleado;

        tfNombre.setText(empleado.getNombre());
        tfUser.setText(empleado.getUser());
        tfPass.setText(empleado.getPass());
        tfDireccion.setText(empleado.getDireccion());
        tfSalario.setText(String.valueOf(empleado.getSalario()));

        if (empleado.getFechaNacimiento() != null && !empleado.getFechaNacimiento().isEmpty()) {
            dpFechaNacimiento.setValue(LocalDate.parse(empleado.getFechaNacimiento(), DateTimeFormatter.ISO_DATE));
        }

        // Género
        if ("Masculino".equalsIgnoreCase(empleado.getGenero())) {
            rbMale.setSelected(true);
        } else if ("Femenino".equalsIgnoreCase(empleado.getGenero())) {
            rbFemale.setSelected(true);
        }

        // Rol y campos específicos
        if (empleado instanceof Piloto) {
            rbPiloto.setSelected(true);
            Piloto piloto = (Piloto) empleado;
            cbLicencia.setValue(piloto.getLicencia());
            tfHoras.setText(String.valueOf(piloto.getHorasVuelo()));
        } else if (empleado instanceof AsistenteVuelo) {
            rbAsistente.setSelected(true);
            AsistenteVuelo asistente = (AsistenteVuelo) empleado;
            tfRango.setText(String.valueOf(asistente.getHorasAsistencia()));
            tfIdiomas.setText(String.valueOf(asistente.getNumIdiomas()));
        } else if (empleado instanceof Administrativo) {
            rbAdmin.setSelected(true);
            // Completa si Administrativo tiene campos extra
        }

        actualizarFormulario();
    }

    public void setOnEmpleadoGuardado(Consumer<Empleado> onEmpleadoGuardado) {
        this.onEmpleadoGuardado = onEmpleadoGuardado;
    }

  @FXML
private void btnGuardar(ActionEvent event) {
    actualizarFormulario(); // Asegurar campos habilitados

    String id;
    if (empleado != null) {
        id = empleado.getId();
    } else {
        // Incrementar el último ID numérico y construir nuevo ID
        ultimoIdNumerico++;
        id = "E" + ultimoIdNumerico;
    }

    if (!validarCampos()) {
        return;
    }

    String nombre = tfNombre.getText().trim();
    String user = tfUser.getText().trim();

    String passPlano = tfPass.getText().trim();
    String pass = Utilidad.cifrarPassword(passPlano); // Cifrado de contraseña

    String direccion = tfDireccion.getText().trim();
    String fechaNacimiento = (dpFechaNacimiento.getValue() != null)
            ? dpFechaNacimiento.getValue().format(DateTimeFormatter.ISO_DATE)
            : "";
    double salario;
    try {
        salario = Double.parseDouble(tfSalario.getText().trim());
    } catch (NumberFormatException e) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Error", "Salario inválido.");
        return;
    }

    if (tgGenero.getSelectedToggle() == null) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Error", "Debe seleccionar un género.");
        return;
    }

    String genero = tgGenero.getSelectedToggle() == rbMale ? "Masculino" : "Femenino";

    String tipoEmpleado = rbPiloto.isSelected() ? "Piloto" :
                          rbAsistente.isSelected() ? "Asistente de Vuelo" :
                          rbAdmin.isSelected() ? "Administrativo" : "";

    if (tipoEmpleado.isEmpty()) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Error", "Debe seleccionar un rol.");
        return;
    }

    if ("Piloto".equals(tipoEmpleado)) {
        String licencia = cbLicencia.getValue();
        if (licencia == null || licencia.isEmpty()) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Error", "Debe seleccionar una licencia.");
            return;
        }
        int horasVuelo;
        try {
            horasVuelo = Integer.parseInt(tfHoras.getText().trim());
        } catch (NumberFormatException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Error", "Horas de vuelo inválidas.");
            return;
        }

        empleado = new Piloto(id, nombre, user, pass, genero, tipoEmpleado, direccion,
                fechaNacimiento, salario, licencia, 0, horasVuelo);

    } else if ("Asistente de Vuelo".equals(tipoEmpleado)) {
        int horasAsistencia;
        int numIdiomas;
        try {
            horasAsistencia = Integer.parseInt(tfHoras.getText().trim());
        } catch (NumberFormatException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Error", "Horas de asistencia inválidas.");
            return;
        }
        try {
            numIdiomas = Integer.parseInt(tfIdiomas.getText().trim());
        } catch (NumberFormatException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Error", "Número de idiomas inválido.");
            return;
        }

        empleado = new AsistenteVuelo(id, nombre, user, pass, genero, tipoEmpleado, direccion,
                fechaNacimiento, salario, horasAsistencia, numIdiomas);

    } else if ("Administrativo".equals(tipoEmpleado)) {
        int horasAdministrativas;
        try {
            horasAdministrativas = Integer.parseInt(tfHoras.getText().trim());
        } catch (NumberFormatException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Error", "Horas administrativas inválidas.");
            return;
        }

        empleado = new Administrativo(id, nombre, user, pass, genero, tipoEmpleado, direccion,
                fechaNacimiento, salario, "", horasAdministrativas);
    } else {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Error", "Tipo de empleado no válido.");
        return;
    }

    // Verificación de edad mínima (mayor de 16 en 2025)
    LocalDate fecha = dpFechaNacimiento.getValue();
    if (fecha == null || fecha.isAfter(LocalDate.of(2009, 12, 31))) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Error", "El empleado debe tener al menos 16 años en 2025.");
        return;
    }

    if (onEmpleadoGuardado != null) {
        onEmpleadoGuardado.accept(empleado);
    }

    Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", "Empleado guardado correctamente.");

    Stage stage = Utilidad.obtenerEscenarioComponente(btnGuardar);
    stage.close();
}


    @FXML
    private void btnCancelar(ActionEvent event) {
        btnCancelar.getScene().getWindow().hide();
    }

    private boolean validarCampos() {
        if (tgGenero.getSelectedToggle() == null) {
         mostrarAlerta("Debe seleccionar un género.");
        return false;
}
        if (tfNombre.getText().trim().isEmpty()) {
            mostrarAlerta("El nombre no puede estar vacío.");
            return false;
        }
        if (tfUser.getText().trim().isEmpty()) {
            mostrarAlerta("El usuario no puede estar vacío.");
            return false;
        }
        if (tfPass.getText().trim().isEmpty()) {
            mostrarAlerta("La contraseña no puede estar vacía.");
            return false;
        }
        if (dpFechaNacimiento.getValue() == null) {
            mostrarAlerta("Debe seleccionar la fecha de nacimiento.");
            return false;
        }
        if (!rbMale.isSelected() && !rbFemale.isSelected()) {
            mostrarAlerta("Debe seleccionar un género.");
            return false;
        }
        if (!rbPiloto.isSelected() && !rbAsistente.isSelected() && !rbAdmin.isSelected()) {
            mostrarAlerta("Debe seleccionar un rol.");
            return false;
        }
        if (tfSalario.getText().trim().isEmpty()) {
            mostrarAlerta("El salario no puede estar vacío.");
            return false;
        }
        return true;
    }
    
public void cargarEmpleadosDesdeJson() {
    List<Empleado> empleados = EmpleadoDAO.cargarEmpleados();
    ultimoIdNumerico = empleados.stream()
        .mapToInt(e -> Integer.parseInt(e.getId().substring(1)))
        .max()
        .orElse(0);
}


    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public Empleado getEmpleado() {
        return empleado;
    }
}