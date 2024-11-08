package com.oskart10.calculator_android_studio;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTV, solutionTV;
    MaterialButton botonC, botonDividir, botonMultiplicar, botonSumar, botonRestar, botonIgual;
    MaterialButton boton1, boton2, boton3, boton4, boton5, boton6, boton7, boton8, boton9, boton0;
    MaterialButton botonAC, botonPunto;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTV = findViewById(R.id.display);
        solutionTV = findViewById(R.id.display);

        botonC = findViewById(R.id.buttonClear);
        botonC.setOnClickListener(this);

        botonDividir = findViewById(R.id.buttonDivide);
        botonDividir.setOnClickListener(this);

        botonMultiplicar = findViewById(R.id.buttonMultiply);
        botonMultiplicar.setOnClickListener(this);

        botonSumar = findViewById(R.id.buttonAdd);
        botonSumar.setOnClickListener(this);

        botonRestar = findViewById(R.id.buttonSubtract);
        botonRestar.setOnClickListener(this);

        botonIgual = findViewById(R.id.buttonEquals);
        botonIgual.setOnClickListener(this);

        boton0 = findViewById(R.id.button0);
        boton0.setOnClickListener(this);

        boton1 = findViewById(R.id.button1);
        boton1.setOnClickListener(this);

        boton2 = findViewById(R.id.button2);
        boton2.setOnClickListener(this);

        boton3 = findViewById(R.id.button3);
        boton3.setOnClickListener(this);

        boton4 = findViewById(R.id.button4);
        boton4.setOnClickListener(this);

        boton5 = findViewById(R.id.button5);
        boton5.setOnClickListener(this);

        boton6 = findViewById(R.id.button6);
        boton6.setOnClickListener(this);

        boton7 = findViewById(R.id.button7);
        boton7.setOnClickListener(this);

        boton8 = findViewById(R.id.button8);
        boton8.setOnClickListener(this);

        boton9 = findViewById(R.id.button9);
        boton9.setOnClickListener(this);

        botonAC = findViewById(R.id.buttonAllClear);
        botonAC.setOnClickListener(this);

        botonPunto = findViewById(R.id.buttonDot);
        botonPunto.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String textoBoton = button.getText().toString();
        String datosCalcular = solutionTV.getText().toString();
        String operadores = "+-*/";

        if (textoBoton.equals("AC")) {
            solutionTV.setText("");
            resultTV.setText("0");
            return;
        }
        if (textoBoton.equals("=")) {
            try {
                double resultado = evaluarExpresiones(datosCalcular);
                resultTV.setText(String.valueOf(resultado));
            } catch (Exception e) {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                resultTV.setText("Error");
            }
            return;
        }
        if (textoBoton.equals("C") && !datosCalcular.isEmpty()) {
            datosCalcular = datosCalcular.substring(0, datosCalcular.length() - 1);
            solutionTV.setText(datosCalcular);
            return;
        }

        if (solutionTV.getText().toString().equals("0") && !textoBoton.equals("0")) {
            solutionTV.setText("");
        }

        if (!datosCalcular.isEmpty()) {
            char ultimoChar = datosCalcular.charAt(datosCalcular.length() - 1);
            if (operadores.contains(String.valueOf(ultimoChar)) && operadores.contains(textoBoton)) {
                Toast.makeText(this, "No se pueden ingresar 2 operadores seguidos", Toast.LENGTH_SHORT).show();
                return;
            }
            if (operadores.contains(textoBoton) && datosCalcular.isEmpty()) {
                Toast.makeText(this, "No se puede iniciar con un operador", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        datosCalcular = datosCalcular + textoBoton;
        solutionTV.setText(datosCalcular);
    }

    private double evaluarExpresiones(String expresion) {
        ArrayList<Double> numeros = new ArrayList<>();
        ArrayList<Character> operadores = new ArrayList<>();
        StringBuilder numeroActual = new StringBuilder();

        for (int i = 0; i < expresion.length(); i++) {
            char caracter = expresion.charAt(i);

            if ("+-*/".indexOf(caracter) != -1) {
                if (numeroActual.length() > 0) {
                    numeros.add(Double.parseDouble(numeroActual.toString()));
                    numeroActual = new StringBuilder();
                }
                operadores.add(caracter);
            } else {
                numeroActual.append(caracter);
            }
        }

        if (numeroActual.length() > 0) {
            numeros.add(Double.parseDouble(numeroActual.toString()));
        }

        for (int i = 0; i < operadores.size(); i++) {
            char operador = operadores.get(i);
            if (operador == '*') {
                double resultado = numeros.get(i) * numeros.get(i + 1);
                numeros.set(i, resultado);
                numeros.remove(i + 1);
                operadores.remove(i);
                i--;
            } else if (operador == '/') {
                if (numeros.get(i + 1) == 0) {
                    throw new ArithmeticException("División por cero");
                }
                double resultado = numeros.get(i) / numeros.get(i + 1);
                numeros.set(i, resultado);
                numeros.remove(i + 1);
                operadores.remove(i);
                i--;
            }
        }

        double resultadoFinal = numeros.get(0);
        for (int i = 0; i < operadores.size(); i++) {
            char operador = operadores.get(i);
            if (operador == '+') {
                resultadoFinal += numeros.get(i + 1);
            } else if (operador == '-') {
                resultadoFinal -= numeros.get(i + 1);
            }
        }
        return resultadoFinal;
    }
}
