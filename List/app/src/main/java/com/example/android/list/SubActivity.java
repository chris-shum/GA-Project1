package com.example.android.list;

import android.content.Intent;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SubActivity extends AppCompatActivity {

    static ListView mListNamesSub;
    EditText mEditTextSub;
    Button mListButtonSub;
    static ArrayList<String> mListArray;
    static ArrayAdapter<String> mAdapterSub;
    private boolean mIsWaitingForDeleteInput = false;
    private boolean mIsWaitingForSaveInput = false;
    private String mDuplicate = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        mListNamesSub = (ListView) findViewById(R.id.listViewSub);
        mEditTextSub = (EditText) findViewById(R.id.inputViewSub);
        mListButtonSub = (Button) findViewById(R.id.buttonSub);




        Intent receivedIntent = getIntent();

        final String receivedString = receivedIntent.getStringExtra("TITLE");
        TextView textView = (TextView) findViewById(R.id.listTitle);
        textView.setText("List: " + receivedString);

        ArrayList<String> arrayList123 = receivedIntent.getStringArrayListExtra("LIST");
        if (arrayList123 == null) {
            mListArray = new ArrayList<>();
        } else {
            mListArray = arrayList123;
        }


        mAdapterSub = new ArrayAdapter<String>(SubActivity.this, android.R.layout.simple_list_item_1, mListArray);
        mListNamesSub.setAdapter(mAdapterSub);

        mAdapterSub.notifyDataSetChanged();

        View.OnClickListener submitListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsWaitingForDeleteInput) {
                    if (mEditTextSub.getText().toString().toLowerCase().contains("y")) {
                        for (int i = 0; i < mListArray.size(); i++) {
                            String tempName = mListArray.get(i);
                            if (tempName.equals(mDuplicate)) {
                                mListArray.remove(i);
                                mIsWaitingForDeleteInput = false;
                                mAdapterSub.notifyDataSetChanged();
                                mEditTextSub.setText("");
                                break;
                            }
                        }
                    } else {
                        mIsWaitingForDeleteInput = false;
                        mEditTextSub.setText("");
                    }
                } else {
                    if (mIsWaitingForSaveInput) {
                        if (mEditTextSub.getText().toString().toLowerCase().contains("y")) {
                            mIsWaitingForSaveInput = false;


                            Intent returningIntent = new Intent();
                            returningIntent.putStringArrayListExtra("RETURNED_ARRAY", mListArray);

                            returningIntent.putExtra("TITLE", receivedString);


                            setResult(RESULT_OK, returningIntent);
                            finish();


                        } else {
                            mIsWaitingForSaveInput = false;
                            mEditTextSub.setText("");
                        }
                    } else if (mEditTextSub.getText().toString().length() == 0) {
                        mEditTextSub.setError("No input: Would you like to save this list and return to the main screen?  (y/n)");
                        mIsWaitingForSaveInput = true;
                        mEditTextSub.setText("");
                    } else if (mListArray.contains(mEditTextSub.getText().toString())) {
                        mEditTextSub.setError("Item already exists. Would you like to delete it? (y/n)");
                        mIsWaitingForDeleteInput = true;
                        mDuplicate = mEditTextSub.getText().toString();
                        mEditTextSub.setText("");
                    } else {
                        mIsWaitingForDeleteInput = false;
                        mListArray.add(mEditTextSub.getText().toString());
                        mAdapterSub.notifyDataSetChanged();
                        mEditTextSub.setText("");
                    }
                }
            }
        };
        mListButtonSub.setOnClickListener(submitListener);


        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                TextView editTextView = ((TextView) view);
                if (editTextView.getCurrentTextColor() == Color.RED) {
                    editTextView.setTextColor(Color.BLACK);
                    editTextView.setText(mListArray.get(position));
                } else {
                    editTextView.setText(mListArray.get(position) + " checked off!\n- Enter " + mListArray.get(position) + " below and Boop to delete.");
                    editTextView.setTextColor(Color.RED);
                }
            }
        };
        mListNamesSub.setOnItemClickListener(onItemClickListener);
    }
}
