package app.com.example.parul.primenumberslist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import app.com.example.parul.primenumberslist.adapter.RecyclerViewAdapter;
import app.com.example.parul.primenumberslist.data.PrimeCalculator;
import app.com.example.parul.primenumberslist.util.PrimeConstants;
import app.com.example.parul.primenumberslist.util.Utility;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PrimeConstants {
    private EditText mInputText;
    private Button mSearch;
    private TextView mPrimeNumber;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RadioButton mOptionOne, mOptionTwo;
    private int inputNumber;
    private ArrayList<Integer> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            mInputText = (EditText) findViewById(R.id.etInputText);
            mOptionOne = (RadioButton) findViewById(R.id.option_one);
            mOptionTwo = (RadioButton) findViewById(R.id.option_two);
            mSearch = (Button) findViewById(R.id.btnSearch);
            mSearch.setOnClickListener(this);
            mPrimeNumber = (TextView) findViewById(R.id.nthprimeNumber);
            mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

            /**
             * check if the data has been saved to the bundle
             */

            if (savedInstanceState != null) {
                mInputText.setText(String.valueOf(savedInstanceState.getInt(INPUT_STRING)));

                boolean optionOneStatus = savedInstanceState.getBoolean(OPTION_ONE_STATUS);
                boolean optionTwoStatus = savedInstanceState.getBoolean(OPTION_TWO_STATUS);

                if (optionOneStatus) {

                    mRecyclerView.setVisibility(View.VISIBLE);
                    if (data == null) {
                        data = new ArrayList<Integer>();
                    }
                    data = savedInstanceState.getIntegerArrayList(PRIME_LIST);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                    mAdapter = new RecyclerViewAdapter(this, data);
                    mRecyclerView.setAdapter(mAdapter);
                } else if (optionTwoStatus) {
                    mPrimeNumber.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                    String primeNumber = savedInstanceState.getString(NTH_PRIME_NUMBER);
                    mPrimeNumber.setText(primeNumber);
                }

            } else {
                mPrimeNumber.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.i(LOG_TAG, e.toString());
        }
    }

    /**
     * @param saveData:On screen orientation
     */

    @Override
    public void onSaveInstanceState(Bundle saveData) {

        super.onSaveInstanceState(saveData);
        try {
            saveData.putIntegerArrayList(PRIME_LIST, data);
            String ipString = mInputText.getText().toString();
            ipString = ipString.trim();
            inputNumber = !ipString.equals("") ? Integer.parseInt(ipString) : 0;
            saveData.putInt(INPUT_STRING, inputNumber);
            String nthPrimeNo = mPrimeNumber.getText().toString();
            saveData.putString(NTH_PRIME_NUMBER, nthPrimeNo);
            boolean optionOneStatus = mOptionOne.isChecked();
            boolean optionTwoStatus = mOptionTwo.isChecked();
            saveData.putBoolean(OPTION_ONE_STATUS, optionOneStatus);
            saveData.putBoolean(OPTION_TWO_STATUS, optionTwoStatus);
        } catch (Exception e) {
            Log.i(LOG_TAG, e.toString());
        }
    }

    /**
     * OnClick event of Search button:fetch the input from edit textbox and generate the primelist or nth prime number
     * @param v
     */

    @Override
    public void onClick(View v) {
        String ipString = mInputText.getText().toString();

            try {
               ipString = ipString.trim();
               inputNumber = !ipString.equals("") ? Integer.parseInt(ipString) : 0;
            if (inputNumber == 0) {
                Utility.displayOKDialog(this, getString(R.string.dialogbox_hint));
            } else {

                if (mOptionOne.isChecked()) {
                    setScreenForPrimeList();
                } else if (mOptionTwo.isChecked()) {
                    setScreenForNthPrimeNumber();
                }
            }
        }
            catch (NumberFormatException ne){
                Utility.displayOKDialog(this, getString(R.string.invalid_number));
            }
            catch (Exception e) {
            Log.i(LOG_TAG, e.toString());
        }
    }


    /**
     * bind the data to the RecyclerView
     */
    public void setScreenForPrimeList() {
        try {
            PrimeCalculator pc = new PrimeCalculator();
            mPrimeNumber.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mPrimeNumber.setText(getString(R.string.prime_list));

            if (data == null) {
                data = new ArrayList<Integer>();
            }
            data = pc.getPrimeList(inputNumber);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new RecyclerViewAdapter(this, data);
            mRecyclerView.setAdapter(mAdapter);
        } catch (Exception e) {
            Log.i(LOG_TAG, e.toString());
        }
    }

    /**
     * display the Nth prime number
     */
    public void setScreenForNthPrimeNumber() {
        try {
            PrimeCalculator pc = new PrimeCalculator();
            mPrimeNumber.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            int number = pc.getNthPrimeNumber(inputNumber);
            mPrimeNumber.setText(Integer.toString(inputNumber) + setTextOfPrimeNo(inputNumber) + Integer.toString(number));

        } catch (Exception e) {
            Log.i(LOG_TAG, e.toString());
        }
    }

    /**
     * set Text of TextView acording to last digit of input number:ex-1st Prime Number,22nd Prime Number etc.
     *
     * @param number
     * @return
     */
    public String setTextOfPrimeNo(int number) {
        String result = "";
        try {
            int lastDigit = number % 10;
            if (lastDigit == 1 && number != 11) {
                result = "st";
            } else if (lastDigit == 2 && number != 12) {
                result = "nd";
            } else if (lastDigit == 3 && number != 13) {
                result = "rd";
            } else {
                result = "th";
            }

        } catch (Exception e) {
            Log.i(LOG_TAG, e.toString());
        }

        return result + getString(R.string.prime_number);

    }
}
