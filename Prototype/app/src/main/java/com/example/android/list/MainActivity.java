package com.example.android.list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button listButton;
    LinearLayout mLayout;
    EditText mEditText;
    int mListCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        listButton = (Button) findViewById(R.id.button);
        mLayout = (LinearLayout) findViewById(R.id.listView);
        mEditText = (EditText) findViewById(R.id.inputView);


        View.OnClickListener submitListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mLayout.addView(createNewTextView(mEditText.getText().toString()));
                mListCount++;


            }
        };
        listButton.setOnClickListener(submitListener);
    }
    private TextView createNewTextView(String text) {


        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final CheckBox checkBox = new CheckBox(this);
        checkBox.setLayoutParams(lparams);

        checkBox.setId(mListCount);
        checkBox.setText("   "+ checkBox.getId());
        mEditText.setText("");
        return checkBox;
    }
}
