# CalculatorInputView
With CalculatorInputView you can add functionality to a calculation EDITTEXT.

![](https://github.com/Gperez88/CalculatorInputView/blob/master/screen/c1.png)
![](https://github.com/Gperez88/CalculatorInputView/blob/master/screen/c2.png)
![](https://github.com/Gperez88/CalculatorInputView/blob/master/screen/c3.png)


## Usage

### Add the dependencies to your gradle file:
```java
  dependencies {
        compile 'com.github.gperez88:calculatorInputView:0.0.1'
  }
```

#### Manifest
```xml
<!-- CalculatorActivity -->
<activity
    android:name="com.gp89developers.calculatorinputview.activities.CalculatorActivity"
    android:screenOrientation="portrait">
    <intent-filter>
        <action android:name="com.gp89developers.calculatorinputview.activities.CalculatorActivity" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
</activity>
```

#### Layout 
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="com.gp89developers.example.MainActivity">

    <EditText
        android:id="@+id/editText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:clickable="true"
        android:cursorVisible="false"
        android:focusable="false"
        android:inputType="none" />

</RelativeLayout>
```

#### Activity
```java
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
```
# Example

[Example app](https://github.com/Gperez88/CalculatorInputView/tree/master/example)

# License

Copyright 2015 Gabriel Perez.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

[Apache License, Version 2.0](https://github.com/Gperez88/CalculatorInputView/blob/master/LICENSE)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
