package com.example.javafxexample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class HelloController {
    private String text = "";
    private String sign = "";
    private String expression = "";
    private double number1;
    private double number2;
    private boolean resultDisplayed;

    @FXML
    private TextField TextField;

    @FXML
    private TextField formulaField;

    @FXML
    private void insertNumber(ActionEvent event) {
        if (resultDisplayed) {
            text = "";
            expression = "";
            resultDisplayed = false;
        }

        Button button = (Button) event.getSource();
        if (text.equals("0")) {
            text = "";
        }
        text += button.getText();
        TextField.setText(text);
        updateFormula();
    }

    @FXML
    private void insertDecimal() {
        if (resultDisplayed) {
            text = "";
            expression = "";
            resultDisplayed = false;
        }

        if (!text.contains(".")) {
            if (text.isEmpty()) {
                text = "0";
            }
            text += ".";
            TextField.setText(text);
            updateFormula();
        }
    }

    @FXML
    private void insertOperator(ActionEvent event) {
        Button button = (Button) event.getSource();
        String operator = button.getText();

        if (operator.equals("=")) {
            if (text.isEmpty() || sign.isEmpty()) {
                return;
            }

            number2 = Double.parseDouble(text);
            expression += " " + sign + " " + text;
            if (calculate()) {
                sign = "";
                resultDisplayed = true;
            }
            updateFormula();
            return;
        }

        if (text.isEmpty()) {
            if (!sign.isEmpty()) {
                sign = operator;
                updateFormula();
            }
            return;
        }

        if (sign.isEmpty()) {
            number1 = Double.parseDouble(text);
            if (resultDisplayed) {
                expression = "(" + expression + ")";
            } else {
                expression = text;
            }
        } else {
            number2 = Double.parseDouble(text);
            expression += " " + sign + " " + text;
            if (!calculate()) {
                updateFormula();
                return;
            }
            expression = "(" + expression + ")";
        }

        sign = operator;
        text = "";
        resultDisplayed = false;
        updateFormula();
    }

    private void updateFormula() {
        if (resultDisplayed) {
            formulaField.setText(expression + " =");
        } else if (sign.isEmpty()) {
            formulaField.setText(text);
        } else {
            formulaField.setText(expression + " " + sign + " " + text);
        }
        formulaField.positionCaret(formulaField.getLength());
    }

    private boolean calculate() {
        if (sign.equals("÷") && number2 == 0) {
            text = "";
            sign = "";
            number1 = 0;
            number2 = 0;
            resultDisplayed = true;
            TextField.setText("Error");
            return false;
        }

        if (sign.equals("+")) {
            number1 = number1 + number2;
        } else if (sign.equals("−")) {
            number1 = number1 - number2;
        } else if (sign.equals("×")) {
            number1 = number1 * number2;
        } else if (sign.equals("÷")) {
            number1 = number1 / number2;
        }

        text = String.valueOf(number1);
        if (text.endsWith(".0")) {
            text = text.substring(0, text.length() - 2);
        }
        TextField.setText(text);
        return true;
    }
}
