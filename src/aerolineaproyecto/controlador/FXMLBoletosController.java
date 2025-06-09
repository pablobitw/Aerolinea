package aerolineaproyecto.controlador;

import aerolineaproyecto.modelo.dao.ClienteDAO;
import static aerolineaproyecto.modelo.dao.ClienteDAO.cargarClientes;
import aerolineaproyecto.modelo.dao.VueloDAO;
import aerolineaproyecto.modelo.pojo.Cliente;
import aerolineaproyecto.modelo.pojo.Vuelo;
import aerolineaproyecto.utilidad.ExportadorDatos;
import aerolineaproyecto.utilidad.Utilidad;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLBoletosController implements Initializable {

    @FXML private TableView<Vuelo> tvVuelos;
    @FXML private TableColumn<Vuelo, Integer> id;
    @FXML private TableColumn<Vuelo, Integer> numero_pasajeros;
    @FXML private TableColumn<Vuelo, Double> costo_boleto;
    @FXML private TableColumn<Vuelo, String> tiempo_recorrido;
    @FXML private TableColumn<Vuelo, String> ciudad_salida;
    @FXML private TableColumn<Vuelo, String> ciudad_llegada;
    @FXML private TableColumn<Vuelo, String> fecha_hora_salida;
    @FXML private TableColumn<Vuelo, String> hora_llegada;
    @FXML
    private TableColumn<Vuelo, String> asistentes;
    @FXML
    private TableColumn<Vuelo, String> pilotos;
    @FXML
    private ImageView btnLogo;

    private ObservableList<Vuelo> listaVuelos;
    @FXML
    private ComboBox<Cliente> cbCliente;
  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarVuelos();
        cargarClientes();
        
         cbCliente.setOnMouseClicked(event -> cargarClientes());
    }
    private void cargarClientes() {
    List<Cliente> clientes = ClienteDAO.cargarClientes(); 
    if (clientes != null && !clientes.isEmpty()) {
        ObservableList<Cliente> listaClientes = FXCollections.observableArrayList(clientes);
        cbCliente.setItems(listaClientes);
    }
}


    private void configurarColumnas() {
    id.setCellValueFactory(new PropertyValueFactory<>("id"));
    numero_pasajeros.setCellValueFactory(new PropertyValueFactory<>("numeroPasajeros"));
    costo_boleto.setCellValueFactory(new PropertyValueFactory<>("costoBoleto"));
    tiempo_recorrido.setCellValueFactory(new PropertyValueFactory<>("tiempoRecorrido"));
    ciudad_salida.setCellValueFactory(new PropertyValueFactory<>("ciudadSalida"));
    ciudad_llegada.setCellValueFactory(new PropertyValueFactory<>("ciudadLlegada"));
    fecha_hora_salida.setCellValueFactory(new PropertyValueFactory<>("salida")); 
    hora_llegada.setCellValueFactory(new PropertyValueFactory<>("llegada"));        
    asistentes.setCellValueFactory(new PropertyValueFactory<>("asistentesNombres"));
    pilotos.setCellValueFactory(new PropertyValueFactory<>("pilotosNombres"));
}

    private void cargarVuelos() {
        List<Vuelo> vuelos = VueloDAO.cargarVuelos();
        listaVuelos = FXCollections.observableArrayList(vuelos);
        tvVuelos.setItems(listaVuelos);
    }

    @FXML
private void btnComprarBoleto(ActionEvent event) {
    Cliente clienteSeleccionado = cbCliente.getSelectionModel().getSelectedItem();
    Vuelo vueloSeleccionado = tvVuelos.getSelectionModel().getSelectedItem();

    if (clienteSeleccionado == null) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Error", "Por favor selecciona un cliente antes de comprar el boleto.");
        return;
    }
    if (vueloSeleccionado == null) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Error", "Por favor selecciona un vuelo antes de comprar el boleto.");
        return;
    }

    // Confirmar compra con alerta
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    alertaConfirmacion.setTitle("Confirmar compra");
    alertaConfirmacion.setHeaderText(null);
    alertaConfirmacion.setContentText("¿Deseas comprar este boleto?");
    Optional<ButtonType> resultado = alertaConfirmacion.showAndWait();

    if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
        // Mostrar diálogo para elegir dónde guardar el PDF
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar boleto PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo PDF", "*.pdf"));
        fileChooser.setInitialFileName("boleto_" + clienteSeleccionado.getNombres() + "_" + vueloSeleccionado.getId() + ".pdf");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File archivo = fileChooser.showSaveDialog(stage);

        if (archivo != null) {
            try {
                ExportadorDatos.exportarBoletoPDF(clienteSeleccionado, vueloSeleccionado, archivo.getAbsolutePath());
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "¡Compra Exitosa!", "¡Boleto comprado e impreso correctamente!");
            } catch (Exception e) {
                e.printStackTrace();
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "No se pudo generar el boleto PDF: " + e.getMessage());
            }
        }
    }
}

    @FXML
    private void btnLogoPresionado(ActionEvent event) {

        try {
            Stage escenario = (Stage) tvVuelos.getScene().getWindow();
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
    

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
private void cbCliente(ActionEvent event) {
    Cliente clienteSeleccionado = cbCliente.getSelectionModel().getSelectedItem();
    if (clienteSeleccionado != null) {
        System.out.println("Cliente seleccionado: " + clienteSeleccionado.getNombres());
    }
}




}
