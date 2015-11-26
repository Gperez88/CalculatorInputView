package com.gp89developers.calculatorinputview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Gaperez on 11/26/2015.
 */
public class CalculatorInputView extends RelativeLayout {
    private String operators[] = {"C", "DEL", "÷", "x", "-", "+", "="};
    private Context mContext;
    private View calculatorInputView;

    //input
    private EditText inputText;

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

    public CalculatorInputView(Context context) {
        this(context, null);
    }

    public CalculatorInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalculatorInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);

        mContext = context;

        initiateView();
    }

    private void initiateView() {
        LayoutInflater.from(mContext).inflate(R.layout.calculator_input_view_layout, this, true);
        calculatorInputView = findViewById(R.id.calculator_input_view);

        inputText = (EditText) calculatorInputView.findViewById(R.id.input_text);

        clearBtn = (Button) calculatorInputView.findViewById(R.id.clear_button);
        dividerBtn = (Button) calculatorInputView.findViewById(R.id.divider_button);
        multiplicationBtn = (Button) calculatorInputView.findViewById(R.id.multiplication_button);
        deleteBtn = (Button) calculatorInputView.findViewById(R.id.delete_button);
        subtractionBtn = (Button) calculatorInputView.findViewById(R.id.subtraction_button);
        sumBtn = (Button) calculatorInputView.findViewById(R.id.sum_button);
        equalBtn = (Button) calculatorInputView.findViewById(R.id.equal_button);

        pointBtn = (Button) calculatorInputView.findViewById(R.id.point_button);
        zeroBtn = (Button) calculatorInputView.findViewById(R.id.zero_button);
        threeZeroBtn = (Button) calculatorInputView.findViewById(R.id.three_zero_button);
        clearBtn = (Button) calculatorInputView.findViewById(R.id.clear_button);
        oneBtn = (Button) calculatorInputView.findViewById(R.id.one_button);
        towBtn = (Button) calculatorInputView.findViewById(R.id.tow_button);
        threeBtn = (Button) calculatorInputView.findViewById(R.id.three_button);
        fourBtn = (Button) calculatorInputView.findViewById(R.id.four_button);
        fiveBtn = (Button) calculatorInputView.findViewById(R.id.five_button);
        sixBtn = (Button) calculatorInputView.findViewById(R.id.six_button);
        sevenBtn = (Button) calculatorInputView.findViewById(R.id.seven_button);
        eightBtn = (Button) calculatorInputView.findViewById(R.id.eight_button);
        nineBtn = (Button) calculatorInputView.findViewById(R.id.nine_button);

        clearBtn.setOnClickListener(mOnClickListener);
        dividerBtn.setOnClickListener(mOnClickListener);
        multiplicationBtn.setOnClickListener(mOnClickListener);
        deleteBtn.setOnClickListener(mOnClickListener);
        subtractionBtn.setOnClickListener(mOnClickListener);
        sumBtn.setOnClickListener(mOnClickListener);
        equalBtn.setOnClickListener(mOnClickListener);

        pointBtn.setOnClickListener(mOnClickListener);
        zeroBtn.setOnClickListener(mOnClickListener);
        threeZeroBtn.setOnClickListener(mOnClickListener);
        clearBtn.setOnClickListener(mOnClickListener);
        oneBtn.setOnClickListener(mOnClickListener);
        towBtn.setOnClickListener(mOnClickListener);
        threeBtn.setOnClickListener(mOnClickListener);
        fourBtn.setOnClickListener(mOnClickListener);
        fiveBtn.setOnClickListener(mOnClickListener);
        sixBtn.setOnClickListener(mOnClickListener);
        sevenBtn.setOnClickListener(mOnClickListener);
        eightBtn.setOnClickListener(mOnClickListener);
        nineBtn.setOnClickListener(mOnClickListener);
    }

    private final OnClickListener mOnClickListener = new OnClickListener() {

        public void onClick(View view) {
            if (view instanceof Button) {
                String value = ((Button)view).getText().toString();
                if(0 > Arrays.binarySearch(operators, value, new Comparator<String>() {
                    @Override
                    public int compare(String lhs, String rhs) {
                        return lhs.compareTo(rhs);
                    }
                }))
                    concat(((Button) view).getText().toString());
            }
        }
    };

    private void concat(String value) {
        String oldValue = inputText.getText().toString();
        String newValue = oldValue + value;

        inputText.setText(newValue);
    }
}
