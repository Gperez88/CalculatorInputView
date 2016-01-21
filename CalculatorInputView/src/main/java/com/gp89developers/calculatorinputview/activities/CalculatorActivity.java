package com.gp89developers.calculatorinputview.activities;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gp89developers.calculatorinputview.R;
import com.gp89developers.calculatorinputview.utils.Operators;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorActivity extends AppCompatActivity {
    public static final int NUMBER_EDIT_TEXT_MAX_LENGTH = 30;
    public static final int REQUEST_RESULT_SUCCESSFUL = 2;
    public static final String TITLE_ACTIVITY = "title_activity";
    public static final String PARENT_ACTIVITY = "parent_activity";
    public static final String VALUE = "value_calculator";
    public static final String RESULT = "result_calculator";

    public static final String ZERO = "0";
    public static final String ZERO_ZERO = "00";
    public static final String POINT = ".";
    public static final String CLICK_ARITHMETIC_OPERATOR = "clickArithmeticOperator";
    public static final String CLICK_EQUAL_OPERATOR = "clickEqualOperator";
    public static final String CLEAR_INPUT = "clearInput";
    public static final String FIRST_VALUE = "firstValue";
    public static final String SECONDS_VALUE = "secondsValue";
    public static final String OPERATOR_EXECUTE = "operatorExecute";
    public static final String ZERO_ZERO_ZERO = "000";

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
    private Button submitBtn;

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
    private boolean clickArithmeticOperator;
    private boolean clickEqualOperator;
    private boolean clearInput;
    private Double firstValue;
    private Double secondsValue;
    private String operatorExecute = Operators.NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caculator);

        setupActionBar();
        initComponents();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (outState != null) {
            outState.putBoolean(CLICK_ARITHMETIC_OPERATOR, clickArithmeticOperator);
            outState.putBoolean(CLICK_EQUAL_OPERATOR, clickEqualOperator);
            outState.putBoolean(CLEAR_INPUT, clearInput);
            outState.putDouble(FIRST_VALUE, firstValue);
            outState.putDouble(SECONDS_VALUE, secondsValue);
            outState.putString(OPERATOR_EXECUTE, operatorExecute);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            clickArithmeticOperator = savedInstanceState.getBoolean(CLICK_ARITHMETIC_OPERATOR);
            clickEqualOperator = savedInstanceState.getBoolean(CLICK_EQUAL_OPERATOR);
            clearInput = savedInstanceState.getBoolean(CLEAR_INPUT);
            firstValue = savedInstanceState.getDouble(FIRST_VALUE);
            secondsValue = savedInstanceState.getDouble(SECONDS_VALUE);
            operatorExecute = savedInstanceState.getString(OPERATOR_EXECUTE);
        }
    }

    @Override
    public Intent getParentActivityIntent() {
        String className = getIntent().getStringExtra(PARENT_ACTIVITY);

        if (className == null)
            return null;

        Intent newIntent = null;

        try {
            newIntent = new Intent(this, Class.forName(className));
            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    private void initComponents() {
        setTitle(getIntent().getStringExtra(TITLE_ACTIVITY));

        String value = TextUtils.isEmpty(getIntent().getStringExtra(VALUE)) ? ZERO : getIntent().getStringExtra(VALUE);

        developmentOperationInputText = (TextView) findViewById(R.id.developing_operation_inputText);

        inputNumberText = (EditText) findViewById(R.id.number_inputText);
        inputNumberText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(NUMBER_EDIT_TEXT_MAX_LENGTH)});
        inputNumberText.setText(value);

        clearBtn = (Button) findViewById(R.id.clear_button);
        deleteBtn = (Button) findViewById(R.id.delete_button);
        equalBtn = (Button) findViewById(R.id.equal_button);
        submitBtn = (Button) findViewById(R.id.submit_button);

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
        secondaryOperators.add(submitBtn);

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

                        equalBtn.setVisibility(View.VISIBLE);
                        submitBtn.setVisibility(View.GONE);
                        clickEqualOperator = false;
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
                        if (operatorExecute.equals(Operators.NONE)) {
                            returnResultOperation();
                        } else {
                            prepareOperation(true);

                            equalBtn.setVisibility(View.GONE);
                            submitBtn.setVisibility(View.VISIBLE);
                            clickEqualOperator = true;
                            clickArithmeticOperator = false;
                            firstValue = null;
                            secondsValue = null;
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

                equalBtn.setVisibility(View.VISIBLE);
                submitBtn.setVisibility(View.GONE);
                clickEqualOperator = false;
                clickArithmeticOperator = false;
            }
        }
    };

    private void prepareOperation(boolean isEqualExecute) {
        clearInput = true;

        if (isEqualExecute) {
            developmentOperationInputText.setText("");
        } else {
            concatDevelopingOperation(operatorExecute, inputNumberText.getText().toString(), false);
        }

        if (firstValue == null) {
            firstValue = Double.parseDouble(inputNumberText.getText().toString());
        } else if (secondsValue == null) {
            secondsValue = Double.parseDouble(inputNumberText.getText().toString());

            if (!clickEqualOperator) {
                executeOperation(operatorExecute);
            }
        }
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
        operatorExecute = Operators.NONE;
    }

    private void concatNumeric(String value) {

        if (value == null || inputNumberText.getText() == null) {
            return;
        }

        Pattern decimalPattern = Pattern.compile("[0-9]*+((\\.[0-9]{0,2})?)||(\\.)?");

        String oldValue = inputNumberText.getText().toString();
        String newValue = clearInput || (oldValue.equals(ZERO) && !value.equals(POINT)) ? value : oldValue + value;
        newValue = oldValue.equals(ZERO) && value.equals(ZERO_ZERO_ZERO) ? oldValue : newValue;

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

        if (TextUtils.isEmpty(value) || value.length() == 1) {
            inputNumberText.setText(ZERO);
            return;
        }

        inputNumberText.setText(value.substring(0, value.length() - 1));
    }

    private void clear() {
        firstValue = null;
        secondsValue = null;
        operatorExecute = Operators.NONE;

        developmentOperationInputText.setText("");
        inputNumberText.setText(ZERO);
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
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#0.00", decimalFormatSymbols);

        String valueStr = decimalFormat.format(value);

        String integerValue = valueStr.substring(0, valueStr.indexOf(POINT));
        String decimalValue = valueStr.substring(valueStr.indexOf(POINT) + 1, valueStr.length());

        if (decimalValue.equals(ZERO_ZERO) || decimalValue.equals(ZERO)) {
            return integerValue;
        }

        return valueStr;
    }
}
