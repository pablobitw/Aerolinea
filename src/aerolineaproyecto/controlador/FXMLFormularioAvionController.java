package aerolineaproyecto.controlador;

import aerolineaproyecto.modelo.pojo.Avion;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLFormularioAvionController implements Initializable {

    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField tfID;
    @FXML
    private TextField tfPeso;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfCapacidad;

    private Consumer<Avion> onAvionGuardado;
    private Avion avionEditar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    public void setOnAvionGuardado(Consumer<Avion> onAvionGuardado) {
        this.onAvionGuardado = onAvionGuardado;
    }

    public void llenarFormulario(Avion avion) {
    this.avionEditar = avion;
    if (avion != null) {
        tfID.setText(String.valueOf(avion.getId()));  // Convertir int a String
        tfModelo.setText(avion.getModelo());
        tfCapacidad.setText(String.valueOf(avion.getCapacidad()));
        tfPeso.setText(String.valueOf(avion.getPeso()));
    }
}

    @FXML
    private void btnCancelar(ActionEvent event) {
        cerrarVentana(event);
    }

    @FXML
private void btnGuardar(ActionEvent event) {
    String id = tfID.getText();
    String modelo = tfModelo.getText();
    String capacidadStr = tfCapacidad.getText();
    String pesoStr = tfPeso.getText();
    
    if (id.isEmpty() || modelo.isEmpty() || capacidadStr.isEmpty() || pesoStr.isEmpty()) {
        showError("Debe completar todos los campos.");
        return;
    }
    
    int capacidad;
    double peso;
    try {
        capacidad = Integer.parseInt(capacidadStr);
        peso = Double.parseDouble(pesoStr);
    } catch (NumberFormatException e) {
        showError("Capacidad debe ser número entero y Peso un número decimal.");
        return;
    }
    
    Avion avion = (avionEditar != null) ? avionEditar : new Avion();
    avion.setId(id);  // Ahora directamente como String
    avion.setModelo(modelo);
    avion.setCapacidad(capacidad);
    avion.setPeso(peso);
    
    if (onAvionGuardado != null) {
        onAvionGuardado.accept(avion);
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
