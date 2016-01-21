package com.example.android.list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ListView mListNames;
    EditText mEditText;
    Button mListButton;
    static ArrayList<String> mListArray;
    static ArrayAdapter<String> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListNames = (ListView) findViewById(R.id.listView);
        mEditText = (EditText) findViewById(R.id.inputView);
        mListButton = (Button) findViewById(R.id.button);
        mListArray = new ArrayList<>();

        mAdapter = new ArrayAdapter<String>(this, R.layout.checkbox, mListArray);
        mListNames.setAdapter(mAdapter);

        View.OnClickListener submitListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditText.getText().toString().length() == 0) {
                    mEditText.setError("Fill in before adding.");
                } else if (mListArray.contains(mEditText.getText().toString())) {
                    mEditText.setError("List name has already been entered");
                } else {
                    mListArray.add(mEditText.getText().toString());
                    mAdapter.notifyDataSetChanged();
                    mEditText.setText("");
                }
            }
        };
        mListButton.setOnClickListener(submitListener);


    }
}
