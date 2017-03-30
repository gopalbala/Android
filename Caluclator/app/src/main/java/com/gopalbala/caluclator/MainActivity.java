package com.gopalbala.caluclator;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    EditText inputText;
    EditText resultText;

    Button buttonOne;
    Button buttonTwo;
    Button buttonThree;
    Button buttonFour;
    Button buttonFive;
    Button buttonSix;
    Button buttonSeven;
    Button buttonEight;
    Button buttonNine;
    Button buttonZero;
    String operation = "";
    Button buttonAdd;
    Button buttonSubtract;
    Button buttonMultiply;
    Button buttonDivide;
    Button buttonEqual;
    Button buttonDel;
    Button buttonDecimal;

    Stack<Double> operandStack = new Stack<>();
    List<String> inputList1 = new ArrayList<>();
    List<String> inputList2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = (EditText) findViewById(R.id.inputNumberText);
        resultText = (EditText) findViewById(R.id.resultValueText);

        buttonOne = (Button) findViewById(R.id.buttonOne);
        buttonTwo = (Button) findViewById(R.id.buttonTwo);
        buttonThree = (Button) findViewById(R.id.buttonThree);
        buttonFour = (Button) findViewById(R.id.buttonFour);
        buttonFive = (Button) findViewById(R.id.buttonFive);
        buttonSix = (Button) findViewById(R.id.buttonSix);
        buttonSeven = (Button) findViewById(R.id.buttonSeven);
        buttonEight = (Button) findViewById(R.id.buttonEight);
        buttonNine = (Button) findViewById(R.id.buttonNine);
        buttonZero = (Button) findViewById(R.id.buttonZero);
        buttonDecimal = (Button) findViewById(R.id.buttonDecimal);

        View.OnClickListener operandListener = (view) -> {
            Button button = (Button) view;
            String buttonText = button.getText().toString();
            inputText.append(buttonText);
            if (!operation.equals("")) {
                inputList2.add(button.getText().toString());
            } else {
                try {
                    inputList1.add(button.getText().toString());
                } catch (Exception e) {

                }
            }
            calculate(inputList1, inputList2);
        };

        buttonZero.setOnClickListener(operandListener);
        buttonOne.setOnClickListener(operandListener);
        buttonTwo.setOnClickListener(operandListener);
        buttonThree.setOnClickListener(operandListener);
        buttonFour.setOnClickListener(operandListener);
        buttonFive.setOnClickListener(operandListener);
        buttonSix.setOnClickListener(operandListener);
        buttonSeven.setOnClickListener(operandListener);
        buttonEight.setOnClickListener(operandListener);
        buttonNine.setOnClickListener(operandListener);
        buttonDecimal.setOnClickListener(operandListener);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonSubtract = (Button) findViewById(R.id.buttonSubtract);
        buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        buttonDivide = (Button) findViewById(R.id.buttonDivide);
        buttonEqual = (Button) findViewById(R.id.buttonEqual);


        buttonDel = (Button) findViewById(R.id.buttonDel);
        buttonDel.setOnClickListener(view ->{
            inputText.setText("");
            resultText.setText("");
            inputList1.clear();
            inputList2.clear();
            operation = "";
        });

        buttonEqual.setOnClickListener(v -> {
            if (resultText.equals("")) {
                calculate(inputList1, inputList2);
            } else {
                inputText.setText(resultText.getText());
            }
        });


        View.OnClickListener operatorListener = (view) -> {
            Button button = (Button) view;
            operation = button.getText().toString();
            String inputString = inputText.getText().toString();
            String previousOperation = inputString.substring(inputString.length() - 1);
            if (previousOperation.equals("+") ||
                    previousOperation.equals("-") ||
                    previousOperation.equals("x") ||
                    previousOperation.equals("÷")) {

                inputText.setText(inputString.replace(previousOperation, operation));
            } else {
                inputText.append(operation);
                if (!resultText.getText().toString().isEmpty()) {
                    inputList1.clear();
                    String outputStr = resultText.getText().toString();
                    for (char c : outputStr.toCharArray()){
                        inputList1.add(String.valueOf(c));
                    }
                    inputList2.clear();
                }
            }

        };

        buttonAdd.setOnClickListener(operatorListener);
        buttonSubtract.setOnClickListener(operatorListener);
        buttonMultiply.setOnClickListener(operatorListener);
        buttonDivide.setOnClickListener(operatorListener);


    }

    private void calculate(List<String> op1, List<String> op2) {
        String operand1 = "";
        String operand2 = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            operand1 = inputList1.stream().map(x -> x).collect(Collectors.joining(""));
            operand2 = op2.stream().map(x -> x).collect(Collectors.joining(""));
        } else {
            for (String num : op1) {
                operand1 += num;
            }
            for (String num : op2) {
                operand2 += num;
            }
        }
        if (!operand1.isEmpty() && !operand2.isEmpty()) {
            performOperation(operand1, operand2);
        }

    }

    private void performOperation(String operand1, String operand2) {

        double op1 = Double.valueOf(operand1);
        double op2 = Double.valueOf(operand2);
        switch (operation) {
            case "+":
                resultText.setText(String.valueOf(op1 + op2));
                break;
            case "-":
                resultText.setText(String.valueOf(op1 - op2));
                break;
            case "x":
                resultText.setText(String.valueOf(op1 * op2));
                break;
            case "÷":
                resultText.setText(String.valueOf(op1 / op2));
                break;
        }
    }
}
