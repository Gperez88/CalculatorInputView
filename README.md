# CalculatorInputView
With CalculatorInputView you can add functionality to a calculation EditText.

<p align="center">
    <img src="https://raw.githubusercontent.com/Gperez88/CalculatorInputView/master/screen/c1.png" width="200">
    <img src="https://raw.githubusercontent.com/Gperez88/CalculatorInputView/master/screen/c2.png" width="200">
    <img src="https://raw.githubusercontent.com/Gperez88/CalculatorInputView/master/screen/c3.png" width="200">
</p>

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-CalculatorInputView-green.svg?style=true)](https://android-arsenal.com/details/1/3059)

## Usage

### Add the dependency to the application level build.gradle
```gradle
dependencies {
    compile 'com.github.gperez88:CalculatorInputView:0.0.3'
}
```

#### Example Layout 
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

#### Example Activity
```java
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
```
# Example

[Example app](example)

# License

Copyright 2015 Gabriel Perez.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

[Apache License, Version 2.0](https://github.com/Gperez88/CalculatorInputView/blob/master/LICENSE)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
