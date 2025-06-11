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
import aerolineaproyecto.excepciones.CamposVaciosException;
import aerolineaproyecto.excepciones.EdadInvalidaException;
import aerolineaproyecto.excepciones.SalarioNegativoException;

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
    cbLicencia.setItems(FXCollections.observableArrayList("PPL - Piloto Privado",
            "CPL - Piloto Comercial",
            "ATPL - Piloto de Transporte de Línea Aérea",
            "MPL - Piloto Multitripulación",
            "SPL - Piloto Deportivo",
            "LAPL - Piloto de Aeronaves Ligeras"));

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
    tfHoras.setTextFormatter(new TextFormatter<>(change -> {
    String nuevoTexto = change.getControlNewText();
    if (nuevoTexto.matches("\\d*")) {  // solo dígitos (0-9), sin decimales ni signos
        return change;
    } else {
        return null;
    }
}));

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
    try {
        actualizarFormulario(); // Asegurar campos habilitados

        // Validar campos vacíos básicos
        if (tfNombre.getText().trim().isEmpty() || tfUser.getText().trim().isEmpty() ||
            tfPass.getText().trim().isEmpty() || tfDireccion.getText().trim().isEmpty() ||
            tfSalario.getText().trim().isEmpty() || dpFechaNacimiento.getValue() == null ||
            tgGenero.getSelectedToggle() == null ||
            (!rbPiloto.isSelected() && !rbAsistente.isSelected() && !rbAdmin.isSelected())) {
            throw new CamposVaciosException("Todos los campos obligatorios deben estar llenos.");
        }

        // Validar y parsear salario
        double salario;
        try {
            salario = Double.parseDouble(tfSalario.getText().trim());
        } catch (NumberFormatException e) {
            throw new SalarioNegativoException("Salario inválido. Debe ser un número válido.");
        }
        if (salario < 0) {
            throw new SalarioNegativoException("El salario no puede ser negativo.");
        }

        // Validar edad mínima 16 años al 2025
        LocalDate fechaNacimiento = dpFechaNacimiento.getValue();
        if (fechaNacimiento.isAfter(LocalDate.of(2009, 12, 31))) {
            throw new EdadInvalidaException("El empleado debe tener al menos 16 años en 2025.");
        }

        String id;
        if (empleado != null) {
            id = empleado.getId();
        } else {
            ultimoIdNumerico++;
            id = "E" + ultimoIdNumerico;
        }

        String nombre = tfNombre.getText().trim();
        String user = tfUser.getText().trim();

        String passPlano = tfPass.getText().trim();
        String pass = Utilidad.cifrarPassword(passPlano);

        String direccion = tfDireccion.getText().trim();
        String fechaNacimientoStr = fechaNacimiento.format(DateTimeFormatter.ISO_DATE);

        String genero = tgGenero.getSelectedToggle() == rbMale ? "Masculino" : "Femenino";

        String tipoEmpleado = rbPiloto.isSelected() ? "Piloto" :
                              rbAsistente.isSelected() ? "Asistente de Vuelo" :
                              rbAdmin.isSelected() ? "Administrativo" : "";

        Empleado nuevoEmpleado;

        if ("Piloto".equals(tipoEmpleado)) {
            String licencia = cbLicencia.getValue();
            if (licencia == null || licencia.isEmpty()) {
                throw new CamposVaciosException("Debe seleccionar una licencia.");
            }
            int horasVuelo;
            try {
                horasVuelo = Integer.parseInt(tfHoras.getText().trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Horas de vuelo inválidas.");
            }
            nuevoEmpleado = new Piloto(id, nombre, user, pass, genero, tipoEmpleado, direccion,
                                       fechaNacimientoStr, salario, licencia, 0, horasVuelo);
        } else if ("Asistente de Vuelo".equals(tipoEmpleado)) {
            int horasAsistencia, numIdiomas;
            try {
                horasAsistencia = Integer.parseInt(tfHoras.getText().trim());
                numIdiomas = Integer.parseInt(tfIdiomas.getText().trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Horas de asistencia o número de idiomas inválidos.");
            }
            nuevoEmpleado = new AsistenteVuelo(id, nombre, user, pass, genero, tipoEmpleado, direccion,
                                               fechaNacimientoStr, salario, horasAsistencia, numIdiomas);
        } else if ("Administrativo".equals(tipoEmpleado)) {
            int horasAdministrativas;
            try {
                horasAdministrativas = Integer.parseInt(tfHoras.getText().trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Horas administrativas inválidas.");
            }
            nuevoEmpleado = new Administrativo(id, nombre, user, pass, genero, tipoEmpleado, direccion,
                                               fechaNacimientoStr, salario, "", horasAdministrativas);
        } else {
            throw new IllegalArgumentException("Tipo de empleado no válido.");
        }

        empleado = nuevoEmpleado;

        if (onEmpleadoGuardado != null) {
            onEmpleadoGuardado.accept(empleado);
        }

        Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", "Empleado guardado correctamente.");

        Stage stage = Utilidad.obtenerEscenarioComponente(btnGuardar);
        stage.close();

    } catch (CamposVaciosException | EdadInvalidaException | SalarioNegativoException ex) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de validación", ex.getMessage());
    } catch (IllegalArgumentException ex) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", ex.getMessage());
    } catch (Exception ex) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error inesperado", ex.getMessage());
        ex.printStackTrace();
    }
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