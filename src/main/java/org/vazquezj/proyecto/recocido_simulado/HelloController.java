package org.vazquezj.proyecto.recocido_simulado;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML private TextField txtObjectiveFunction;
    @FXML private TextField txtInitialTemp;
    @FXML private TextField txtMinTemp;
    @FXML private TextField txtCoolingRate;
    @FXML private TextField txtDomain;
    @FXML private TextField txtIterTemp;
    @FXML private ComboBox comboMode;
    @FXML private TextArea txtOutput;

    @FXML
    protected void onRunSimulation(ActionEvent event) {
        try {
            // Recolectar y validar inputs
            String funcion = txtObjectiveFunction.getText().trim();
            double temperaturaInicial = Double.parseDouble(txtInitialTemp.getText().trim());
            double temperaturaMinima = Double.parseDouble(txtMinTemp.getText().trim());
            float tasaEnfriamiento = Float.parseFloat(txtCoolingRate.getText().trim());

            // Dominio: lo recibimos como "-10,10"
            String[] dominioStr = txtDomain.getText().trim().split(",");
            if (dominioStr.length != 2) throw new IllegalArgumentException("Dominio inválido, usa formato: -10,10");

            double[] dominio = new double[]{
                    Double.parseDouble(dominioStr[0].trim()),
                    Double.parseDouble(dominioStr[1].trim())
            };

            // Obtener tipo de problema
            String tipoProblema = comboMode.getValue().toString().toLowerCase();

            int iterTemp = Integer.parseInt(txtIterTemp.getText().trim());

            // Crear y ejecutar
            RecocidoSimulado recocido = new RecocidoSimulado(
                    "min", // o podrías agregar un ComboBox para "max/min"
                    tipoProblema,
                    dominio,
                    funcion,
                    iterTemp, // iteraciones por temperatura (podrías agregar otro TextField si quieres hacerlo configurable)
                    temperaturaInicial,
                    temperaturaMinima,
                    tasaEnfriamiento
            );

            double solucion = recocido.optimizar();

            txtOutput.setText("Mejor solución encontrada: x = " + solucion +
                    "\nValor: f(x) = " + evaluarFuncion(funcion, solucion));
        } catch (Exception e) {
            txtOutput.setText("Error: " + e.getMessage());
        }
    }

    private double evaluarFuncion(String funcion, double x) {
        return new net.objecthunter.exp4j.ExpressionBuilder(funcion)
                .variable("x")
                .build()
                .setVariable("x", x)
                .evaluate();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboMode.getItems().setAll("Discreto", "Continuo");
        comboMode.getSelectionModel().selectFirst(); // Selecciona el primer elemento por defecto
    }
}