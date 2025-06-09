package aerolineaproyecto.controlador;

// imports omitidos para brevedad...

import static aerolineaproyecto.modelo.dao.AvionDAO.cargarAviones;
import aerolineaproyecto.modelo.dao.EmpleadoDAO;
import aerolineaproyecto.modelo.dao.VueloDAO;
import aerolineaproyecto.modelo.pojo.Avion;
import aerolineaproyecto.modelo.pojo.Empleado;
import aerolineaproyecto.modelo.pojo.Vuelo;
import aerolineaproyecto.utilidad.Utilidad;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;


public class FXMLFormularioVueloController {

    @FXML private TextField tfPasajeros, tfCosto, tfTiempo;
    @FXML private TextField tfCiudadSalida;
    @FXML private TextField tfCiudadLlegada;
    @FXML private DatePicker dpFechaSalida;
    @FXML private DatePicker dpFechaLlegada;
    @FXML private TextField tfHoraSalida;
    @FXML private TextField tfHoraLlegada;

    @FXML private ComboBox<Empleado> Piloto1;
    @FXML private ComboBox<Empleado> Piloto2;
    @FXML private ComboBox<Empleado> Asistente1;
    @FXML private ComboBox<Empleado> Asistente2;
    @FXML private ComboBox<Empleado> Asistente3;
    @FXML private ComboBox<Empleado> Asistente4;

    @FXML private Button btnGuardar, btnCancelar;

    private Vuelo vueloActual;
    private final DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private ObservableList<Empleado> listaEmpleados = FXCollections.observableArrayList();
    private ObservableList<Empleado> pilotos = FXCollections.observableArrayList();
    private ObservableList<Empleado> asistentes = FXCollections.observableArrayList();
    private ObservableList<Avion> listaAviones = FXCollections.observableArrayList();
    @FXML 
    private ComboBox<Avion> Avion;
    
    @FXML 
    private void initialize() {
        listaEmpleados.addAll(cargarEmpleados());
        listaAviones.setAll(cargarAviones());
        Avion.setItems(listaAviones);
        

        pilotos.clear();
        asistentes.clear();

        for (Empleado e : listaEmpleados) {
            if ("piloto".equalsIgnoreCase(e.getTipoEmpleado())) {
                pilotos.add(e);
            } else if ("AsistenteVuelo".equalsIgnoreCase(e.getTipoEmpleado())) {
                asistentes.add(e);
            }
        }

        // Inicializamos ComboBoxes con las listas
        Piloto1.setItems(FXCollections.observableArrayList(pilotos));
        Piloto2.setItems(FXCollections.observableArrayList(pilotos));
        Asistente1.setItems(FXCollections.observableArrayList(asistentes));
        Asistente2.setItems(FXCollections.observableArrayList(asistentes));
        Asistente3.setItems(FXCollections.observableArrayList(asistentes));
        Asistente4.setItems(FXCollections.observableArrayList(asistentes));

        // Agregamos listeners para evitar selección repetida
        Piloto1.getSelectionModel().selectedItemProperty().addListener((obs, old, nuevo) -> actualizarListas());
        Piloto2.getSelectionModel().selectedItemProperty().addListener((obs, old, nuevo) -> actualizarListas());
        Asistente1.getSelectionModel().selectedItemProperty().addListener((obs, old, nuevo) -> actualizarListas());
        Asistente2.getSelectionModel().selectedItemProperty().addListener((obs, old, nuevo) -> actualizarListas());
        Asistente3.getSelectionModel().selectedItemProperty().addListener((obs, old, nuevo) -> actualizarListas());
        Asistente4.getSelectionModel().selectedItemProperty().addListener((obs, old, nuevo) -> actualizarListas());
    }

    // Método para actualizar las listas y evitar empleados repetidos en cualquier combo
    private void actualizarListas() {
        // Obtener todos los empleados seleccionados
        List<Empleado> seleccionados = new ArrayList<>();
        if (Piloto1.getSelectionModel().getSelectedItem() != null) seleccionados.add(Piloto1.getSelectionModel().getSelectedItem());
        if (Piloto2.getSelectionModel().getSelectedItem() != null) seleccionados.add(Piloto2.getSelectionModel().getSelectedItem());
        if (Asistente1.getSelectionModel().getSelectedItem() != null) seleccionados.add(Asistente1.getSelectionModel().getSelectedItem());
        if (Asistente2.getSelectionModel().getSelectedItem() != null) seleccionados.add(Asistente2.getSelectionModel().getSelectedItem());
        if (Asistente3.getSelectionModel().getSelectedItem() != null) seleccionados.add(Asistente3.getSelectionModel().getSelectedItem());
        if (Asistente4.getSelectionModel().getSelectedItem() != null) seleccionados.add(Asistente4.getSelectionModel().getSelectedItem());

        // Actualizamos items para cada combo removiendo los empleados ya seleccionados en los otros combos
        actualizarComboBox(Piloto1, pilotos, seleccionados);
        actualizarComboBox(Piloto2, pilotos, seleccionados);
        actualizarComboBox(Asistente1, asistentes, seleccionados);
        actualizarComboBox(Asistente2, asistentes, seleccionados);
        actualizarComboBox(Asistente3, asistentes, seleccionados);
        actualizarComboBox(Asistente4, asistentes, seleccionados);
    }

    private void actualizarComboBox(ComboBox<Empleado> combo, ObservableList<Empleado> baseLista, List<Empleado> seleccionados) {
        Empleado seleccionadoActual = combo.getSelectionModel().getSelectedItem();
        // Creamos una lista nueva con todos los empleados menos los que están seleccionados en otros combos
        ObservableList<Empleado> disponibles = FXCollections.observableArrayList();
        for (Empleado e : baseLista) {
            if (!seleccionados.contains(e) || e.equals(seleccionadoActual)) {
                disponibles.add(e);
            }
        }

        combo.setItems(disponibles);
        if (seleccionadoActual != null && disponibles.contains(seleccionadoActual)) {
            combo.getSelectionModel().select(seleccionadoActual);
        } else {
            combo.getSelectionModel().clearSelection();
        }
    }

    public void mostrarDatosVuelo() {
        if (vueloActual == null) return;

        tfPasajeros.setText(String.valueOf(vueloActual.getNumeroPasajeros()));
        tfCosto.setText(String.valueOf(vueloActual.getCostoBoleto()));
        tfTiempo.setText(vueloActual.getTiempoRecorrido());
        tfCiudadSalida.setText(vueloActual.getCiudadSalida());
        tfCiudadLlegada.setText(vueloActual.getCiudadLlegada());

        dpFechaSalida.setValue(vueloActual.getFechaSalida());
        tfHoraSalida.setText(vueloActual.getHoraSalida() != null ? vueloActual.getHoraSalida().format(horaFormatter) : "");

        dpFechaLlegada.setValue(vueloActual.getFechaLlegada());
        tfHoraLlegada.setText(vueloActual.getHoraLlegada() != null ? vueloActual.getHoraLlegada().format(horaFormatter) : "");

        // Seleccionamos pilotos y asistentes si existen
        Piloto1.getSelectionModel().select(vueloActual.getPiloto1());
        Piloto2.getSelectionModel().select(vueloActual.getPiloto2());

        Asistente1.getSelectionModel().select(vueloActual.getAsistente1());
        Asistente2.getSelectionModel().select(vueloActual.getAsistente2());
        Asistente3.getSelectionModel().select(vueloActual.getAsistente3());
        Asistente4.getSelectionModel().select(vueloActual.getAsistente4());
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        Utilidad.obtenerEscenarioComponente(btnCancelar).hide();
    }

    @FXML
private void btnGuardar(ActionEvent event) {
    try {
        // Validaciones básicas (ya incluidas por ti)
        if (tfPasajeros.getText().trim().isEmpty() || tfCosto.getText().trim().isEmpty() || tfTiempo.getText().trim().isEmpty() ||
            tfCiudadSalida.getText().trim().isEmpty() || tfCiudadLlegada.getText().trim().isEmpty() ||
            dpFechaSalida.getValue() == null || tfHoraSalida.getText().trim().isEmpty() ||
            dpFechaLlegada.getValue() == null || tfHoraLlegada.getText().trim().isEmpty() ||
            Piloto1.getSelectionModel().isEmpty() || Piloto2.getSelectionModel().isEmpty() ||
            Asistente1.getSelectionModel().isEmpty() || Asistente2.getSelectionModel().isEmpty() ||
            Asistente3.getSelectionModel().isEmpty() || Asistente4.getSelectionModel().isEmpty()) {

            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de validación", "Por favor, completa todos los campos.");
            return;
        }

        // Obtener y validar datos
        int pasajeros = Integer.parseInt(tfPasajeros.getText().trim());
        double costo = Double.parseDouble(tfCosto.getText().trim());
        String tiempo = tfTiempo.getText().trim();
        String ciudadSalida = tfCiudadSalida.getText().trim();
        String ciudadLlegada = tfCiudadLlegada.getText().trim();

        LocalDate fechaSalida = dpFechaSalida.getValue();
        LocalDate fechaLlegada = dpFechaLlegada.getValue();
        LocalTime horaSalida = LocalTime.parse(tfHoraSalida.getText().trim(), horaFormatter);
        LocalTime horaLlegada = LocalTime.parse(tfHoraLlegada.getText().trim(), horaFormatter);

        // Crear o actualizar vuelo
        if (vueloActual == null) {
            vueloActual = new Vuelo();
        }

        vueloActual.setNumeroPasajeros(pasajeros);
        vueloActual.setCostoBoleto(costo);
        vueloActual.setTiempoRecorrido(tiempo);
        vueloActual.setCiudadSalida(ciudadSalida);
        vueloActual.setCiudadLlegada(ciudadLlegada);
        vueloActual.setFechaSalida(fechaSalida);
        vueloActual.setFechaLlegada(fechaLlegada);
        vueloActual.setHoraSalida(horaSalida);
        vueloActual.setHoraLlegada(horaLlegada);
        vueloActual.setPiloto1(Piloto1.getValue());
        vueloActual.setPiloto2(Piloto2.getValue());
        vueloActual.setAsistente1(Asistente1.getValue());
        vueloActual.setAsistente2(Asistente2.getValue());
        vueloActual.setAsistente3(Asistente3.getValue());
        vueloActual.setAsistente4(Asistente4.getValue());

        boolean resultado;

        if (vueloActual.getId() > 0) {
            resultado = VueloDAO.modificarVuelo(vueloActual);
        } else {
            resultado = VueloDAO.agregarVuelo(vueloActual);
        }

        if (resultado) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", "El vuelo fue guardado correctamente.");
            Utilidad.obtenerEscenarioComponente(btnGuardar).close();
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "No se pudo guardar el vuelo.");
        }

    } catch (NumberFormatException e) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de formato", "Asegúrate de que los campos numéricos sean válidos.");
    } catch (DateTimeParseException e) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de hora", "Usa el formato HH:mm para las horas.");
    } catch (Exception e) {
        e.printStackTrace();
        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error inesperado", "Ocurrió un error inesperado.");
    }
}


    private List<Empleado> cargarEmpleados() {
    return EmpleadoDAO.cargarEmpleados();
}


    public void setVuelo(Vuelo vuelo) {
        this.vueloActual = vuelo;
        mostrarDatosVuelo();
    }
    
}
