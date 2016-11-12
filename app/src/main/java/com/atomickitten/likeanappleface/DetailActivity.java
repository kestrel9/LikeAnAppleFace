package com.atomickitten.likeanappleface;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.atomickitten.DB.AppDBHelper;
import com.atomickitten.DB.Item;
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;


public class DetailActivity extends AppCompatActivity {

    private String[] values;
    EditText edit_name;
    Spinner spinnerA;
    int spinnerPositionA;
    int spinnerPositionB;
    Spinner spinnerB;
    Button btn_ok;
    Button btn_cancel;
    TextView tv_expire_date;
    int id;

    String expireDate;
    Item selectedItem;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/BMJUA_ttf.ttf"));

        Intent intent = getIntent();
        id = intent.getIntExtra("update", -1);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);
        spinnerA = (Spinner)findViewById(R.id.sp_categoryA);
        setmInitSpinnerA();
        spinnerB = (Spinner) findViewById(R.id.sp_categoryB);
        setmInitSpinnerB(R.array.category_skin);
        edit_name = (EditText)findViewById(R.id.edit_name);
        if(id == -1){
            final long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            expireDate  = CurDateFormat.format(date);
            spinnerPositionA = 0;
            spinnerPositionB = 0;

            btn_ok.setText(R.string.detail_btn_confrim);
        }else {
            AppDBHelper db = new AppDBHelper(DetailActivity.this);
            selectedItem = db.getItem(id);


            edit_name.setText(selectedItem.getProduct_name());
            ArrayAdapter adaspin = (ArrayAdapter)spinnerA.getAdapter();
            spinnerPositionA = adaspin.getPosition(selectedItem.getProduct_category_first());
            spinnerA.setSelection(spinnerPositionA);
            adaspin = (ArrayAdapter)spinnerB.getAdapter();
            spinnerPositionB = adaspin.getPosition(selectedItem.getProduct_category_second());
            spinnerB.setSelection(spinnerPositionB);


            expireDate = selectedItem.getExpire_date();

            btn_ok.setText(R.string.detail_btn_edit);


        }


        tv_expire_date = (TextView)findViewById(R.id.tv_expire_date);


        tv_expire_date.setText(expireDate);

        findViewById(R.id.tv_expire_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year, month, day;
                GregorianCalendar calendar = new GregorianCalendar();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(DetailActivity.this, dateSetListener, year, month, day);
                dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                dialog.show();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edit_name.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_detail_noti_text), Toast.LENGTH_SHORT).show();
                    edit_name.requestFocus();
                }else {
                    AppDBHelper db = new AppDBHelper(DetailActivity.this);
                    if(id == -1){
                        db.addItem(new Item(edit_name.getText().toString(), null, spinnerA.getSelectedItem().toString(), spinnerB.getSelectedItem().toString(), tv_expire_date.getText().toString()));
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_detail_insert_text), Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        db.updateItem(new Item(id, edit_name.getText().toString(), null, spinnerA.getSelectedItem().toString(), spinnerB.getSelectedItem().toString(), tv_expire_date.getText().toString()));
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_detail_edit_text), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        spinnerA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position){
                    case 0:
                        setmInitSpinnerB(R.array.category_skin);
                        values = getResources().getStringArray(R.array.category_skin);
                        break;
                    case 1:
                        setmInitSpinnerB(R.array.category_base);
                        values = getResources().getStringArray(R.array.category_base);
                        break;
                    case 2:
                        setmInitSpinnerB(R.array.category_color);
                        values = getResources().getStringArray(R.array.category_color);
                        break;
                    case 3:
                        setmInitSpinnerB(R.array.category_etc);
                        values = getResources().getStringArray(R.array.category_etc);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedItem = values[position];
                final long now = System.currentTimeMillis();
                Date date = new Date(now);
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                cal.setTime(date);
                int month;

                HashMap map = setHash();

                switch ((int)map.get(selectedItem)){
                    case 3:              month = 3;      break;
                    case 6:              month = 6;      break;
                    case 8:              month = 8;      break;
                    case 12:             month = 12;     break;
                    case 18:             month = 18;     break;
                    case 24:             month = 24;     break;
                    default:             month = 0;      break;
                }
                cal.add(Calendar.MONTH, month);

                String strCurDate = CurDateFormat.format(cal.getTime());

                tv_expire_date.setText(strCurDate);



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edit_name.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( edit_name.getWindowToken(), 0);    //hide keyboard
                    return true;
                }
                return false;
            }
        });

    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

            String date = String.format("%d-%02d-%02d", year, monthOfYear+1, dayOfMonth);
            tv_expire_date.setText(date);
        }
    };

    private void setmInitSpinnerA(){
        ArrayAdapter<CharSequence> adspinA;
        adspinA = ArrayAdapter.createFromResource(this, R.array.category1, R.layout.support_simple_spinner_dropdown_item);
        adspinA.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerA.setAdapter(adspinA);

    }

    private void setmInitSpinnerB(int selected){
        ArrayAdapter<CharSequence> adspinB;
        adspinB = ArrayAdapter.createFromResource(this, selected, R.layout.support_simple_spinner_dropdown_item);
        adspinB.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerB.setAdapter(adspinB);

    }

    private HashMap setHash(){
        String month3[] = getResources().getStringArray(R.array.expire_3_month);
        String month6[] = getResources().getStringArray(R.array.expire_6_month);
        String month8[] = getResources().getStringArray(R.array.expire_8_month);
        String month12[] = getResources().getStringArray(R.array.expire_12_month);
        String month18[] = getResources().getStringArray(R.array.expire_18_month);
        String month24[] = getResources().getStringArray(R.array.expire_24_month);
        HashMap<String, Integer> map = new HashMap<>();

        for(String str : month3){
            map.put(str, 3);
        }
        for(String str : month6){
            map.put(str, 6);
        }
        for(String str : month8){
            map.put(str, 8);
        }
        for(String str : month12){
            map.put(str, 12);
        }
        for (String str : month18){
            map.put(str, 18);
        }
        for(String str : month24){
            map.put(str, 24);
        }
        return map;
    }
}
