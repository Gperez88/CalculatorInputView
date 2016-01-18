package com.gp89developers.calculatorinputview.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gp89developers.calculatorinputview.R;
import com.gp89developers.calculatorinputview.utils.Operators;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorActivity extends AppCompatActivity {
    public static final int NUMBER_EDIT_TEXT_MAX_LENGTH = 9;
    public static final int REQUEST_RESULT_SUCCESSFUL = 2;
    public static final String VALUE = "value_calculator";
    public static final String RESULT = "result_calculator";

    //input
    private EditText inputNumberText;
    private TextView developmentOperationInputText;

    //button operation
    private Button clearBtn;
    private Button dividerBtn;
    private Button multiplicationBtn;
    private Button deleteBtn;
    private Button subtractionBtn;
    private Button sumBtn;
    private Button equalBtn;

    //button numeric
    private Button pointBtn;
    private Button zeroBtn;
    private Button threeZeroBtn;
    private Button oneBtn;
    private Button towBtn;
    private Button threeBtn;
    private Button fourBtn;
    private Button fiveBtn;
    private Button sixBtn;
    private Button sevenBtn;
    private Button eightBtn;
    private Button nineBtn;

    //operations values
    private boolean clickOperator;
    private boolean clearInput;
    private Integer firstValue;
    private Integer secondsValue;
    private String operatorExecute = Operators.NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caculator);

        initComponents();
    }

    private void initComponents() {
        String value = getIntent().getStringExtra(VALUE) == null ? "" : getIntent().getStringExtra(VALUE);

        developmentOperationInputText = (TextView) findViewById(R.id.developing_operation_inputText);

        inputNumberText = (EditText) findViewById(R.id.numberInputText);
        inputNumberText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(NUMBER_EDIT_TEXT_MAX_LENGTH)});
        inputNumberText.setText(value);

        clearBtn = (Button) findViewById(R.id.clear_button);
        deleteBtn = (Button) findViewById(R.id.delete_button);
        equalBtn = (Button) findViewById(R.id.equal_button);

        dividerBtn = (Button) findViewById(R.id.divider_button);
        multiplicationBtn = (Button) findViewById(R.id.multiplication_button);
        subtractionBtn = (Button) findViewById(R.id.subtraction_button);
        sumBtn = (Button) findViewById(R.id.sum_button);

        pointBtn = (Button) findViewById(R.id.point_button);
        zeroBtn = (Button) findViewById(R.id.zero_button);
        threeZeroBtn = (Button) findViewById(R.id.three_zero_button);
        oneBtn = (Button) findViewById(R.id.one_button);
        towBtn = (Button) findViewById(R.id.tow_button);
        threeBtn = (Button) findViewById(R.id.three_button);
        fourBtn = (Button) findViewById(R.id.four_button);
        fiveBtn = (Button) findViewById(R.id.five_button);
        sixBtn = (Button) findViewById(R.id.six_button);
        sevenBtn = (Button) findViewById(R.id.seven_button);
        eightBtn = (Button) findViewById(R.id.eight_button);
        nineBtn = (Button) findViewById(R.id.nine_button);

        List<Button> arithmeticOperators = new ArrayList<>();
        arithmeticOperators.add(dividerBtn);
        arithmeticOperators.add(multiplicationBtn);
        arithmeticOperators.add(subtractionBtn);
        arithmeticOperators.add(sumBtn);

        List<Button> secondaryOperators = new ArrayList<>();
        secondaryOperators.add(clearBtn);
        secondaryOperators.add(deleteBtn);
        secondaryOperators.add(equalBtn);

        List<Button> numericOperators = new ArrayList<>();
        numericOperators.add(pointBtn);
        numericOperators.add(zeroBtn);
        numericOperators.add(threeZeroBtn);
        numericOperators.add(oneBtn);
        numericOperators.add(towBtn);
        numericOperators.add(threeBtn);
        numericOperators.add(fourBtn);
        numericOperators.add(fiveBtn);
        numericOperators.add(sixBtn);
        numericOperators.add(sevenBtn);
        numericOperators.add(eightBtn);
        numericOperators.add(nineBtn);

        setOnClickListenerBtn(arithmeticOperators, mOnOperatorBtnClickListener);
        setOnClickListenerBtn(secondaryOperators, mOnOperatorBtnClickListener);
        setOnClickListenerBtn(numericOperators, mOnNumberBtnClickListener);
    }

    private final OnClickListener mOnOperatorBtnClickListener = new OnClickListener() {
        public void onClick(View view) {
            if (view instanceof Button) {

                String value = ((Button) view).getText().toString();

                switch (value) {
                    case Operators.SUM:
                    case Operators.SUBTRACTION:
                    case Operators.MULTIPLICATION:
                    case Operators.DIVIDER: {
                        operatorExecute = value;
                        if (!clickOperator) {
                            clickOperator = true;
                            prepareOperation();
                        } else {
                            replaceOperator(value);
                        }
                        break;
                    }
                    case Operators.CLEAR: {
                        clear();
                        break;
                    }
                    case Operators.DELETE: {
                        removeLastNumber();
                        break;
                    }
                    case Operators.EQUAL:
                    case Operators.SUBMIT: {
                        if (operatorExecute.equals(Operators.NONE)) {
                            returnResultOperation();
                        } else {
                            prepareOperation();
                        }
                        break;
                    }
                }
            }
        }
    };

    private final OnClickListener mOnNumberBtnClickListener = new OnClickListener() {
        public void onClick(View view) {
            if (view instanceof Button) {
                String value = ((Button) view).getText().toString();
                concatNumeric(value);
                clickOperator = false;
            }
        }
    };

    private void prepareOperation() {
        concatDevelopingOperation(operatorExecute, inputNumberText.getText().toString(), false);
        clearInput = true;

        if (firstValue == null) {
            firstValue = Integer.parseInt(inputNumberText.getText().toString());
        } else if (secondsValue == null) {
            secondsValue = Integer.parseInt(inputNumberText.getText().toString());
            executeOperation(operatorExecute);
        }
    }

    private void executeOperation(String operator) {
        if (firstValue == null || secondsValue == null) {
            return;
        }

        int resultOperation = 0;

        switch (operator) {
            case Operators.SUM: {
                resultOperation = firstValue + secondsValue;
                break;
            }
            case Operators.SUBTRACTION: {
                resultOperation = firstValue - secondsValue;
                break;
            }
            case Operators.MULTIPLICATION: {
                resultOperation = firstValue * secondsValue;
                break;
            }
            case Operators.DIVIDER: {
                resultOperation = firstValue / secondsValue;
                break;
            }
        }

        inputNumberText.setText(String.valueOf(resultOperation));
        firstValue = resultOperation;
        secondsValue = null;
        operatorExecute = Operators.NONE;
    }

    private void concatNumeric(String value) {

        if (value == null || inputNumberText.getText() == null) {
            return;
        }

        Pattern decimalPattern = Pattern.compile("[0-9]*+((\\.[0-9]{0,2})?)||(\\.)?");

        String oldValue = inputNumberText.getText().toString();
        String newValue = clearInput ? value : oldValue + value;

        Matcher matcher = decimalPattern.matcher(newValue);
        if (!matcher.matches()) {
            inputNumberText.setText(oldValue);
        } else {
            inputNumberText.setText(newValue);
        }
        clearInput = false;
    }

    private void concatDevelopingOperation(String operator, String value, boolean clear) {
        boolean noValidCharacter = operator.equals(Operators.CLEAR) || operator.equals(Operators.DELETE) || operator.equals(Operators.EQUAL);

        if (!noValidCharacter) {
            String oldValue = clear ? "" : developmentOperationInputText.getText().toString();
            developmentOperationInputText.setText(String.format("%s %s %s", oldValue, value, operator));
        }
    }

    private void removeLastNumber() {
        String value = inputNumberText.getText().toString();

        if (TextUtils.isEmpty(value)) {
            inputNumberText.setText("0");
            return;
        }

        inputNumberText.setText(value.substring(0, value.length() - 1));
    }

    private void clear() {
        firstValue = null;
        secondsValue = null;
        operatorExecute = Operators.NONE;

        inputNumberText.setText("");
        developmentOperationInputText.setText("");
    }

    private void setOnClickListenerBtn(List<Button> btns, OnClickListener onClickListener) {
        for (Button button : btns) {
            button.setOnClickListener(onClickListener);
        }
    }

    private void returnResultOperation() {

        String result = inputNumberText.getText().toString();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(RESULT, result);

        setResult(REQUEST_RESULT_SUCCESSFUL, resultIntent);
        finish();
    }

    private void replaceOperator(String operator) {
        String operationValue = developmentOperationInputText.getText().toString();
        String operationNewValue = operationValue.substring(0, operationValue.length() - 1);

        if (TextUtils.isEmpty(operationValue) || TextUtils.isEmpty(operationNewValue)) {
            return;
        }

        concatDevelopingOperation(operator, operationNewValue, true);
    }
}
