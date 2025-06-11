/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package aerolineaproyecto.controlador;

import aerolineaproyecto.modelo.dao.BoletoDAO;
import aerolineaproyecto.modelo.dao.ClienteDAO;
import aerolineaproyecto.modelo.pojo.Boleto;
import aerolineaproyecto.modelo.pojo.Cliente;
import aerolineaproyecto.modelo.pojo.Vuelo;
import aerolineaproyecto.utilidad.GenerarBoleto;
import aerolineaproyecto.utilidad.Utilidad;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PABLO
 */
public class FXMLComprarBoletoController implements Initializable {

      @FXML
    private ComboBox<Cliente> cbCliente;
    @FXML
    private DatePicker dpFechaCompra;
    @FXML
    private TextField tfNumAsiento;
    @FXML
    private javafx.scene.control.Button btnEscogerAsiento;
    @FXML
    private javafx.scene.control.Button btnCancelar;
    @FXML
    private javafx.scene.control.Button btnGuardar;
    
    private Vuelo vuelo;
    private ClienteDAO clienteDAO;
    private BoletoDAO boletoDAO;
    private FXMLBoletosController boletosController;  // controlador padre para refrescar lista

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDAO = new ClienteDAO();
        boletoDAO = new BoletoDAO();

        btnEscogerAsiento.setOnAction(this::btnEscogerAsiento);
        btnCancelar.setOnAction(this::btnCancelar);
        btnGuardar.setOnAction(this::btnGuardar);

        // Limitar fechas futuras para la fecha de compra
        dpFechaCompra.setDayCellFactory(picker -> new javafx.scene.control.DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isAfter(LocalDate.now()));
            }
        });
        dpFechaCompra.setValue(LocalDate.now());

        cargarClientes();
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public void setBoletosController(FXMLBoletosController controller) {
        this.boletosController = controller;
    }

    private void cargarClientes() {
    List<Cliente> clientes = clienteDAO.cargarClientes();  
    ObservableList<Cliente> listaClientes = FXCollections.observableArrayList(clientes);
    cbCliente.setItems(listaClientes);
}
    
    @FXML
private void btnEscogerAsiento(ActionEvent event) {
    if (vuelo == null) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Error", "No se ha seleccionado un vuelo.");
        return;
    }

    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/aerolineaproyecto/vista/FXMLAsientos.fxml"));
        Parent root = loader.load();

        FXMLAsientosController controladorSelector = loader.getController();
        controladorSelector.setVueloActual(vuelo);  // Asegúrate de tener este método

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Seleccionar Asiento");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        // Recibir la lista de asientos seleccionados
        List<String> asientosSeleccionados = (List<String>) stage.getUserData();
        if (asientosSeleccionados != null && !asientosSeleccionados.isEmpty()) {
            tfNumAsiento.setText(String.join(", ", asientosSeleccionados));
        }

    } catch (IOException e) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "No se pudo abrir el selector de asientos: " + e.getMessage());
    }
}

    @FXML
private void btnGuardar(ActionEvent event) {
    if (!validarCampos()) {
        return;
    }

    Cliente clienteSeleccionado = cbCliente.getValue();
    LocalDate fechaCompra = dpFechaCompra.getValue();
    String numAsiento = tfNumAsiento.getText().trim();

    try {
        List<Boleto> boletosExistentes = boletoDAO.findByVuelo(vuelo.getId());

        boolean asientoOcupado = boletosExistentes.stream()
                .anyMatch(b -> b.getNumAsiento().equals(numAsiento));

        if (asientoOcupado) {
            Utilidad.mostrarAlertaSimple(
                Alert.AlertType.WARNING,
                "Asiento ocupado",
                "El asiento " + numAsiento + " ya está ocupado. Por favor seleccione otro."
            );
            return;
        }

        Boleto nuevoBoleto = new Boleto();
        nuevoBoleto.setId(UUID.randomUUID().toString());
        nuevoBoleto.setVuelo(vuelo);
        nuevoBoleto.setIdVuelo(vuelo.getId());
        nuevoBoleto.setCliente(clienteSeleccionado);
        nuevoBoleto.setFechaCompra(Date.from(fechaCompra.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        nuevoBoleto.setNumAsiento(numAsiento);

        boolean guardado = boletoDAO.save(nuevoBoleto);
        if (guardado) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Compra Exitosa", "El boleto ha sido comprado.");

            vuelo.setNumPasajeros(vuelo.getNumeroPasajeros() + 1);

            // Abrir diálogo para elegir dónde guardar el PDF
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar boleto PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
            fileChooser.setInitialFileName("boleto_" + nuevoBoleto.getId() + ".pdf");

            File archivo = fileChooser.showSaveDialog(btnGuardar.getScene().getWindow());

            if (archivo != null) {
                try {
                    GenerarBoleto.generarPDF(clienteSeleccionado, vuelo, nuevoBoleto, archivo.getAbsolutePath());
                    Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "PDF Generado", "El boleto PDF se ha guardado correctamente.");
                } catch (Exception e) {
                    Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error PDF", "No se pudo generar el PDF: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            // Refrescar la lista en el controlador padre
            if (boletosController != null) {
                boletosController.cargarBoletos();
            }

            // Cerrar ventana
            cerrarVentana();

        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "No se pudo comprar el boleto.");
        }

    } catch (Exception e) {
        e.printStackTrace();
        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "No se pudo completar la compra: " + (e.getMessage() != null ? e.getMessage() : e.toString()));
    }
}


    private boolean validarCampos() {
        if (cbCliente.getValue() == null) {
            Utilidad.mostrarAlertaSimple(
                javafx.scene.control.Alert.AlertType.WARNING,
                "Validación",
                "Debe seleccionar un cliente."
            );
            return false;
        }
        if (dpFechaCompra.getValue() == null) {
            Utilidad.mostrarAlertaSimple(
                javafx.scene.control.Alert.AlertType.WARNING,
                "Validación",
                "Debe seleccionar una fecha de compra."
            );
            return false;
        }
        if (tfNumAsiento.getText().trim().isEmpty()) {
            Utilidad.mostrarAlertaSimple(
                javafx.scene.control.Alert.AlertType.WARNING,
                "Validación",
                "Debe seleccionar un asiento."
            );
            return false;
        }
        return true;
    }

    private int generarIdBoleto() {
        // TODO: Implementar lógica para generar ID único
        return (int) (Math.random() * 100000);
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
}