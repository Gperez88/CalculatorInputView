package com.gp89developers.calculatorinputview.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gp89developers.calculatorinputview.R;
import com.gp89developers.calculatorinputview.utils.Operators;
import com.gp89developers.calculatorinputview.widget.NumericEditText;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class CalculatorActivity extends AppCompatActivity {
    public static final int NUMBER_EDIT_TEXT_MAX_LENGTH = 30;
    public static final int REQUEST_RESULT_SUCCESSFUL = 2;
    public static final String TITLE_ACTIVITY = "title_activity";
    public static final String VALUE = "value_calculator";
    public static final String RESULT = "result_calculator";

    public static final String ZERO = "0";
    public static final String ZERO_ZERO = "00";
    public static final String POINT = ".";
    public static final String CLICK_ARITHMETIC_OPERATOR = "clickArithmeticOperator";
    public static final String CLEAR_INPUT = "clearInput";
    public static final String FIRST_VALUE = "firstValue";
    public static final String SECONDS_VALUE = "secondsValue";
    public static final String OPERATOR_EXECUTE = "operatorExecute";
    public static final String PREV_OPERATOR_EXECUTE = "prevOperatorExecute";

    private DecimalFormat decimalFormat;

    //input
    private NumericEditText inputNumberText;
    private TextView developmentOperationInputText;

    //button operation
    private Button equalBtn;
    private Button submitBtn;

    //operations values
    private boolean clickArithmeticOperator;
    private boolean clearInput;
    private final OnClickListener mOnNumberBtnClickListener = new OnClickListener() {
        public void onClick(View view) {
            if (view instanceof Button) {
                String value = ((Button) view).getText().toString();
                concatNumeric(value);
                clickArithmeticOperator = false;
            }
        }
    };
    private Double firstValue;
    private Double secondsValue;
    private String operatorExecute = Operators.NONE;
    private String prevOperatorExecute = Operators.NONE;
    private final OnClickListener mOnOperatorBtnClickListener = new OnClickListener() {
        public void onClick(View view) {
            if (view instanceof Button) {
                String value = ((Button) view).getText().toString();

                switch (value) {
                    case Operators.SUM:
                    case Operators.SUBTRACTION:
                    case Operators.MULTIPLICATION:
                    case Operators.DIVIDER: {
                        if (TextUtils.isEmpty(inputNumberText.getText())
                                || inputNumberText.getText().toString().equals("."))
                            return;

                        equalBtn.setVisibility(View.VISIBLE);
                        submitBtn.setVisibility(View.GONE);
                        operatorExecute = value;

                        if (!clickArithmeticOperator) {
                            clickArithmeticOperator = true;
                            prepareOperation(false);
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
                        if (inputNumberText.getText().toString().equals(".")) {
                            String temp = developmentOperationInputText.getText().toString();
                            clear();
                            inputNumberText.setText(temp);
                            return;
                        }

                        if (operatorExecute.equals(Operators.SUBMIT) || firstValue == null) {
                            returnResultOperation();
                        } else {
                            prepareOperation(true);
                            clearInput = false;
                            clickArithmeticOperator = false;
                            operatorExecute = Operators.SUBMIT;
                            prevOperatorExecute = Operators.NONE;
                            firstValue = null;
                            secondsValue = null;
                        }
                        break;
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        setupActionBar();
        initComponents();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (outState != null) {
            outState.putBoolean(CLICK_ARITHMETIC_OPERATOR, clickArithmeticOperator);
            outState.putBoolean(CLEAR_INPUT, clearInput);

            if (firstValue != null)
                outState.putDouble(FIRST_VALUE, firstValue);

            if (secondsValue != null)
                outState.putDouble(SECONDS_VALUE, secondsValue);
            outState.putString(OPERATOR_EXECUTE, operatorExecute);
            outState.putString(PREV_OPERATOR_EXECUTE, prevOperatorExecute);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            clickArithmeticOperator = savedInstanceState.getBoolean(CLICK_ARITHMETIC_OPERATOR);
            clearInput = savedInstanceState.getBoolean(CLEAR_INPUT);

            if (savedInstanceState.containsKey(FIRST_VALUE))
                firstValue = savedInstanceState.getDouble(FIRST_VALUE);

            if (savedInstanceState.containsKey(SECONDS_VALUE))
                secondsValue = savedInstanceState.getDouble(SECONDS_VALUE);

            operatorExecute = savedInstanceState.getString(OPERATOR_EXECUTE);
            prevOperatorExecute = savedInstanceState.getString(PREV_OPERATOR_EXECUTE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initComponents() {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setGroupingSeparator(',');
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormat = new DecimalFormat("#,###,##0.00", decimalFormatSymbols);

        String title = getIntent().getStringExtra(TITLE_ACTIVITY);
        if (!TextUtils.isEmpty(title)) {
            setTitle(getIntent().getStringExtra(TITLE_ACTIVITY));
        } else {
            setTitle(getString(R.string.app_name));
        }

        String value = TextUtils.isEmpty(getIntent().getStringExtra(VALUE)) ? "" : getIntent().getStringExtra(VALUE);

        developmentOperationInputText = (TextView) findViewById(R.id.developing_operation_inputText);

        inputNumberText = (NumericEditText) findViewById(R.id.number_inputText);
        inputNumberText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(NUMBER_EDIT_TEXT_MAX_LENGTH)});
        inputNumberText.setText(value);

        Button clearBtn = (Button) findViewById(R.id.clear_button);
        Button deleteBtn = (Button) findViewById(R.id.delete_button);
        equalBtn = (Button) findViewById(R.id.equal_button);
        submitBtn = (Button) findViewById(R.id.submit_button);

        Button dividerBtn = (Button) findViewById(R.id.divider_button);
        Button multiplicationBtn = (Button) findViewById(R.id.multiplication_button);
        Button subtractionBtn = (Button) findViewById(R.id.subtraction_button);
        Button sumBtn = (Button) findViewById(R.id.sum_button);

        Button pointBtn = (Button) findViewById(R.id.point_button);
        Button zeroBtn = (Button) findViewById(R.id.zero_button);
        Button twoZeroButton = (Button) findViewById(R.id.two_zero_button);
        Button oneBtn = (Button) findViewById(R.id.one_button);
        Button twoBtn = (Button) findViewById(R.id.two_button);
        Button threeBtn = (Button) findViewById(R.id.three_button);
        Button fourBtn = (Button) findViewById(R.id.four_button);
        Button fiveBtn = (Button) findViewById(R.id.five_button);
        Button sixBtn = (Button) findViewById(R.id.six_button);
        Button sevenBtn = (Button) findViewById(R.id.seven_button);
        Button eightBtn = (Button) findViewById(R.id.eight_button);
        Button nineBtn = (Button) findViewById(R.id.nine_button);

        List<Button> arithmeticOperators = new ArrayList<>();
        arithmeticOperators.add(dividerBtn);
        arithmeticOperators.add(multiplicationBtn);
        arithmeticOperators.add(subtractionBtn);
        arithmeticOperators.add(sumBtn);

        List<Button> secondaryOperators = new ArrayList<>();
        secondaryOperators.add(clearBtn);
        secondaryOperators.add(deleteBtn);
        secondaryOperators.add(equalBtn);
        secondaryOperators.add(submitBtn);

        List<Button> numericOperators = new ArrayList<>();
        numericOperators.add(pointBtn);
        numericOperators.add(zeroBtn);
        numericOperators.add(twoZeroButton);
        numericOperators.add(oneBtn);
        numericOperators.add(twoBtn);
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

    private void prepareOperation(boolean isEqualExecute) {
        clearInput = true;

        if (isEqualExecute) {
            equalBtn.setVisibility(View.GONE);
            submitBtn.setVisibility(View.VISIBLE);
            developmentOperationInputText.setText("");
        } else {
            concatDevelopingOperation(operatorExecute, inputNumberText.getText().toString(), false);
        }

        if (firstValue == null) {
            firstValue = Double.parseDouble(inputNumberText.getText().toString().replaceAll(",", ""));
        } else if (secondsValue == null) {
            secondsValue = Double.parseDouble(inputNumberText.getText().toString().replaceAll(",", ""));
            executeOperation(prevOperatorExecute);
        }

        prevOperatorExecute = operatorExecute;
    }

    private void executeOperation(String operator) {
        if (firstValue == null || secondsValue == null) {
            return;
        }

        double resultOperation = 0.0;

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
                if (secondsValue > 0) {
                    resultOperation = firstValue / secondsValue;
                }
                break;
            }
        }

        inputNumberText.setText(formatValue(resultOperation));
        firstValue = resultOperation;
        secondsValue = null;
    }

    private void concatNumeric(String value) {
        if (value == null || inputNumberText.getText() == null) {
            return;
        }

        String oldValue = inputNumberText.getText().toString();
        String newValue = clearInput || (oldValue.equals(ZERO) && !value.equals(POINT)) ? value : oldValue + value;
        newValue = oldValue.equals(ZERO) && value.equals(ZERO_ZERO) ? oldValue : newValue;

        inputNumberText.setText(newValue);
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
        if (value.length() != 0)
            inputNumberText.setText(value.substring(0, value.length() - 1));
    }

    private void clear() {
        equalBtn.setVisibility(View.GONE);
        submitBtn.setVisibility(View.VISIBLE);

        firstValue = null;
        secondsValue = null;
        operatorExecute = Operators.NONE;
        prevOperatorExecute = Operators.NONE;

        developmentOperationInputText.setText("");
        inputNumberText.setText("");
    }

    private void setOnClickListenerBtn(List<Button> buttons, OnClickListener onClickListener) {
        for (Button button : buttons) {
            button.setOnClickListener(onClickListener);
        }
    }

    private void returnResultOperation() {
        String result = inputNumberText.getText().toString();
        Intent resultIntent = new Intent();

        if (result.equals(".")) {
            result = "";
        }

        resultIntent.putExtra(RESULT, result);
        setResult(REQUEST_RESULT_SUCCESSFUL, resultIntent);
        finish();
    }

    private void replaceOperator(String operator) {
        String operationValue = developmentOperationInputText.getText().toString();

        if (TextUtils.isEmpty(operationValue)) {
            return;
        }

        String oldOperator = operationValue.substring(operationValue.length() - 1, operationValue.length());

        if (oldOperator.equals(operator)) {
            return;
        }

        String operationNewValue = operationValue.substring(0, operationValue.length() - 2);
        concatDevelopingOperation(operator, operationNewValue, true);
    }

    private String formatValue(double value) {
        String valueStr = decimalFormat.format(value);

        String integerValue = valueStr.substring(0, valueStr.indexOf(POINT));
        String decimalValue = valueStr.substring(valueStr.indexOf(POINT) + 1, valueStr.length());

        if (decimalValue.equals(ZERO_ZERO) || decimalValue.equals(ZERO)) {
            return integerValue;
        }

        return valueStr;
    }
}
