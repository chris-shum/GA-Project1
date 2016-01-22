package com.example.android.list;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    private boolean mIsWaitingForDeleteInput = false;
    private String mDuplicate = "";
    ArrayList<String> mArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListNames = (ListView) findViewById(R.id.listView);
        mEditText = (EditText) findViewById(R.id.inputView);
        mListButton = (Button) findViewById(R.id.button);
        mListArray = new ArrayList<>();


        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListArray);
        mListNames.setAdapter(mAdapter);

        View.OnClickListener submitListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsWaitingForDeleteInput) {
                    if (mEditText.getText().toString().toLowerCase().contains("y")) {
                        for (int i = 0; i < mListArray.size(); i++) {
                            String tempName = mListArray.get(i);
                            if (tempName.equals(mDuplicate)) {
                                mListArray.remove(i);
                                mIsWaitingForDeleteInput = false;
                                mAdapter.notifyDataSetChanged();
                                break;
                            }
                            mEditText.setText("");
                        }
                    } else mIsWaitingForDeleteInput = false;
                    mEditText.setText("");

                } else {
                    if (mEditText.getText().toString().length() == 0) {
                        mEditText.setError("Fill in before adding.");
                    } else if (mListArray.contains(mEditText.getText().toString())) {
                        mEditText.setError("List name already exists. Would you like to delete it? (y/n)");
                        mIsWaitingForDeleteInput = true;
                        mDuplicate = mEditText.getText().toString();
                        mEditText.setText("");

                    } else {
                        mIsWaitingForDeleteInput = false;
                        mListArray.add(mEditText.getText().toString());
                        mAdapter.notifyDataSetChanged();
                        mEditText.setText("");
                    }
                }
            }
        };
        mListButton.setOnClickListener(submitListener);


        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {












                Intent intent = new Intent(MainActivity.this, SubActivity.class);

                intent.putExtra("TITLE", mListArray.get(position));


                intent.putStringArrayListExtra("LIST", mArrayList);
                startActivityForResult(intent, 0);









            }
        };
        mListNames.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        ArrayList<String> returnedStringArray = data.getStringArrayListExtra("RETURNED_ARRAY");
        int returnedNumber = data.getIntExtra("RETURNED_NUMBER", -1);










        mArrayList.clear();
        mArrayList.addAll(returnedStringArray);


    }
}

