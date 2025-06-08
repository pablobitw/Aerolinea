/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package aerolineaproyecto.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author PABLO
 */
public class FXMLFormularioAvionController implements Initializable {

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfUser;
    @FXML
    private TextField tfPass;
    @FXML
    private TextField tfSalario;
    @FXML
    private DatePicker dpFechaNacimiento;
    @FXML
    private ComboBox<?> cbRol;
    @FXML
    private RadioButton rbMale;
    @FXML
    private RadioButton rbFemale;
    @FXML
    private TextField tfDireccion;
    @FXML
    private RadioButton rbPiloto;
    @FXML
    private RadioButton rbAsistente;
    @FXML
    private RadioButton rbAdmin;
    @FXML
    private TextField tfIdiomas;
    @FXML
    private TextField tfHoras;
    @FXML
    private ComboBox<?> cbLicencia;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnCancelar(ActionEvent event) {
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
    }
    
}
