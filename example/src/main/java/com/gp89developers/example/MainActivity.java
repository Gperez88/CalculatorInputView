package com.gp89developers.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.gp89developers.calculatorinputview.CalculatorBuilder;
import com.gp89developers.calculatorinputview.activities.CalculatorActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TITLE = "CalculatorInputView";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CalculatorBuilder()
                        .withTitle(TITLE)
                        .withValue(editText.getText().toString())
                        .start(MainActivity.this);
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
