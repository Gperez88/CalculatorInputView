package com.gp89developers.example;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.gp89developers.calculatorinputview.activities.CalculatorActivity;

public class MainActivity extends AppCompatActivity {
    public static final String PARENT_CLASS_SOURCE = "com.gp89developers.example.MainActivity";
    public static final String TITLE = "CalculatorInputView";

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calculatorIntent = new Intent(MainActivity.this, CalculatorActivity.class);
                calculatorIntent.putExtra(CalculatorActivity.TITLE_ACTIVITY, TITLE);
                calculatorIntent.putExtra(CalculatorActivity.PARENT_ACTIVITY, PARENT_CLASS_SOURCE);
                calculatorIntent.putExtra(CalculatorActivity.VALUE, editText.getText().toString());

                startActivityForResult(calculatorIntent, CalculatorActivity.REQUEST_RESULT_SUCCESSFUL);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == CalculatorActivity.REQUEST_RESULT_SUCCESSFUL) {
            String result = data.getStringExtra(CalculatorActivity.RESULT);
            editText.setText(result);
        }
    }
}
