/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package aerolineaproyecto.controlador;

import aerolineaproyecto.modelo.dao.EmpleadoDAO;
import aerolineaproyecto.modelo.pojo.Empleado;
import java.net.URL;
import java.time.LocalDate;
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
    private TextField tfNombre;
    @FXML
    private TextField tfUser;
    @FXML
    private TextField tfPass;
    @FXML
    private DatePicker dpFechaNacimiento;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    @FXML
    private RadioButton rbMale;
    @FXML
    private RadioButton rbFemale;

    private Consumer<Empleado> onEmpleadoGuardado;
    @FXML
    private TextField tfSalario;
    @FXML
    private TextField tfDireccion;
    @FXML
    private TextField tfIdiomas;
    @FXML
    private TextField tfHoras;
    @FXML private RadioButton rbPiloto;
@FXML private RadioButton rbAsistente;
@FXML private RadioButton rbAdmin;
@FXML private ComboBox<String> cbLicencia;

    private Empleado empleadoOriginal = null; // Para edición

    public void setOnEmpleadoGuardado(Consumer<Empleado> onEmpleadoGuardado) {
        this.onEmpleadoGuardado = onEmpleadoGuardado;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbLicencia.setItems(FXCollections.observableArrayList(
            "PPL - Piloto Privado",
            "CPL - Piloto Comercial",
            "ATPL - Piloto de Transporte de Línea Aérea",
            "MPL - Piloto Multitripulación",
            "SPL - Piloto Deportivo",
            "LAPL - Piloto de Aeronaves Ligeras"
        ));
        tfNombre.setTextFormatter(new TextFormatter<>(change -> {
    if (change.getControlNewText().matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]*")) {
        return change;
    } else {
        return null;
    }
}));
        tfSalario.setTextFormatter(new TextFormatter<>(change -> {
    if (change.getControlNewText().matches("\\d*(\\.\\d{0,2})?")) {
        return change;
    } else {
        return null;
    }
}));
tfHoras.setTextFormatter(new TextFormatter<>(change -> {
    return change.getControlNewText().matches("\\d*") ? change : null;
}));

tfIdiomas.setTextFormatter(new TextFormatter<>(change -> {
    return change.getControlNewText().matches("\\d*") ? change : null;
}));


        cbLicencia.setVisible(false);
        tfIdiomas.setVisible(false);
        tfHoras.setVisible(false);

        ToggleGroup grupoTipo = new ToggleGroup();
        rbPiloto.setToggleGroup(grupoTipo);
        rbAsistente.setToggleGroup(grupoTipo);
        rbAdmin.setToggleGroup(grupoTipo);

        grupoTipo.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == rbPiloto) {
                cbLicencia.setVisible(true);
                tfIdiomas.setVisible(false);
                tfHoras.setVisible(true);
            } else if (newVal == rbAsistente) {
                cbLicencia.setVisible(false);
                tfIdiomas.setVisible(true);
                tfHoras.setVisible(true);
            } else if (newVal == rbAdmin) {
                cbLicencia.setVisible(false);
                tfIdiomas.setVisible(false);
                tfHoras.setVisible(true);
            }
        });
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar salida");
        alert.setHeaderText(null);
        alert.setContentText("¿Está seguro que desea salir sin guardar?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Stage stage = (Stage) btnCancelar.getScene().getWindow();
                stage.close();
            }
        });
    }

    public void llenarFormulario(Empleado empleado) {
        this.empleadoOriginal = empleado; // Guardamos referencia para edición

        tfNombre.setText(empleado.getNombre());
        tfUser.setText(empleado.getUser());
        tfPass.setText(empleado.getPass());
        dpFechaNacimiento.setValue(LocalDate.parse(empleado.getFechaNacimiento()));
        tfSalario.setText(String.valueOf(empleado.getSalario()));
        tfDireccion.setText(empleado.getDireccion());

        if ("M".equals(empleado.getGenero())) rbMale.setSelected(true);
        else rbFemale.setSelected(true);

        switch (empleado.getTipoEmpleado()) {
            case "Piloto":
                rbPiloto.setSelected(true);
                cbLicencia.setValue(empleado.getLicencia());
                tfHoras.setText(String.valueOf(empleado.getHorasVuelo()));
                break;
            case "AsistenteVuelo":
                rbAsistente.setSelected(true);
                tfIdiomas.setText(String.valueOf(empleado.getNumIdiomas()));
                tfHoras.setText(String.valueOf(empleado.getHorasAsistencia()));
                break;
            case "Administrativo":
                rbAdmin.setSelected(true);
                tfHoras.setText(String.valueOf(empleado.getHorasTrabajo()));
                break;
        }
    }

    @FXML
private void btnGuardar(ActionEvent event) {
    if (tfNombre.getText().isEmpty() || tfUser.getText().isEmpty() || tfPass.getText().isEmpty() ||
        dpFechaNacimiento.getValue() == null || tfSalario.getText().isEmpty() || tfDireccion.getText().isEmpty() ||
        (!rbMale.isSelected() && !rbFemale.isSelected()) ||
        (!rbPiloto.isSelected() && !rbAsistente.isSelected() && !rbAdmin.isSelected())) {
        showError("Debe completar todos los campos obligatorios.");
        return;
    }

    // Validaciones específicas según el tipo de empleado
    if (rbPiloto.isSelected()) {
        if (cbLicencia.getValue() == null || tfHoras.getText().isEmpty()) {
            showError("Debe seleccionar licencia y horas de vuelo para Piloto.");
            return;
        }
    } else if (rbAsistente.isSelected()) {
        if (tfIdiomas.getText().isEmpty() || tfHoras.getText().isEmpty()) {
            showError("Debe llenar idiomas y horas para Asistente.");
            return;
        }
    } else if (rbAdmin.isSelected()) {
        if (tfHoras.getText().isEmpty()) {
            showError("Debe llenar horas para Administrativo.");
            return;
        }
    }

    // Validaciones de contenido (formato)
    if (!tfNombre.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+")) {
        showError("Nombre inválido. Solo se permiten letras.");
        return;
    }

    if (!tfSalario.getText().matches("\\d+(\\.\\d{1,2})?")) {
        showError("Salario inválido. Solo se permiten números (puede incluir decimales).");
        return;
    }

    if (!tfHoras.getText().matches("\\d+")) {
        showError("Horas inválidas. Solo se permiten números.");
        return;
    }

    if (rbAsistente.isSelected() && !tfIdiomas.getText().matches("\\d+")) {
        showError("Idiomas inválidos. Solo se permiten números.");
        return;
    }

    try {
        double salario = Double.parseDouble(tfSalario.getText());
        int horas = Integer.parseInt(tfHoras.getText());

        String fechaNacimiento = dpFechaNacimiento.getValue().toString();
        String genero = rbMale.isSelected() ? "M" : "F";

        String tipoEmpleado;
        if (rbPiloto.isSelected()) tipoEmpleado = "Piloto";
        else if (rbAsistente.isSelected()) tipoEmpleado = "AsistenteVuelo";
        else tipoEmpleado = "Administrativo";

        Empleado empleadoGuardado;

        if (empleadoOriginal == null) {
            // Nuevo empleado
            List<Empleado> empleados = EmpleadoDAO.cargarEmpleados();
            String nuevoId = EmpleadoDAO.generarNuevoId(empleados);

            empleadoGuardado = new Empleado();
            empleadoGuardado.setId(nuevoId);
        } else {
            // Edición
            empleadoGuardado = new Empleado();
            empleadoGuardado.setId(empleadoOriginal.getId());
        }

        empleadoGuardado.setNombre(tfNombre.getText());
        empleadoGuardado.setUser(tfUser.getText());
        empleadoGuardado.setPass(tfPass.getText());
        empleadoGuardado.setGenero(genero);
        empleadoGuardado.setTipoEmpleado(tipoEmpleado);
        empleadoGuardado.setDireccion(tfDireccion.getText());
        empleadoGuardado.setFechaNacimiento(fechaNacimiento);
        empleadoGuardado.setSalario(salario);

        switch (tipoEmpleado) {
            case "Piloto":
                empleadoGuardado.setLicencia(cbLicencia.getValue());
                empleadoGuardado.setHorasVuelo(horas);
                break;
            case "AsistenteVuelo":
                empleadoGuardado.setNumIdiomas(Integer.parseInt(tfIdiomas.getText()));
                empleadoGuardado.setHorasAsistencia(horas);
                break;
            case "Administrativo":
                empleadoGuardado.setHorasTrabajo(horas);
                break;
        }

        if (onEmpleadoGuardado != null) {
            onEmpleadoGuardado.accept(empleadoGuardado);
        }

        Stage stage = (Stage) btnGuardar.getScene().getWindow();
        stage.close();

    } catch (NumberFormatException e) {
        showError("Ingrese números válidos en salario, horas y/o idiomas.");
    } catch (Exception e) {
        showError("Error al guardar empleado: " + e.getMessage());
        e.printStackTrace();
    }
}

    private void showError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.show();
    }
}