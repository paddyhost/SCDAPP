package com.hatchers.solar_conduction_dryer.activity.user_registration;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hatchers.solar_conduction_dryer.R;
import com.hatchers.solar_conduction_dryer.activity.Preference_Manager.PrefManager;
import com.hatchers.solar_conduction_dryer.activity.activity.Menu_Activity;
import com.hatchers.solar_conduction_dryer.activity.activity.home.Menus_Fragment;
import com.hatchers.solar_conduction_dryer.activity.user_registration.apihelper.Registration_ApiHelper;
import com.hatchers.solar_conduction_dryer.activity.utils.validations.Validation;

import java.util.Calendar;

public class UserRegistration extends AppCompatActivity  {

    private EditText edtName,edtMobile, edtBithDate, edtCity,edtstate,edtcountry,edtpin;
    private Button subminBtn,skipBtn;
    private RadioGroup genderGrup;
    private RadioButton femaleBtn,maleBtn;
    private PrefManager prefManager;
    private DatePickerDialog dpd;
    private int mYear, mMonth, mDay;
    private String selected_gender="";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_registration);

        initialization();

        onClickListners();
    }

    private void initialization()
    {
        prefManager = new PrefManager(this);
        edtName = (EditText)findViewById(R.id.username);
        edtMobile = (EditText)findViewById(R.id.mobile_number);
        edtBithDate = (EditText)findViewById(R.id.birthdate);
        edtCity = (EditText)findViewById(R.id.city);
        genderGrup = (RadioGroup)findViewById(R.id.gender);
        femaleBtn = (RadioButton)findViewById(R.id.female);
        maleBtn = (RadioButton)findViewById(R.id.male);
        edtcountry= (EditText) findViewById(R.id.country);
        edtstate= (EditText) findViewById(R.id.state);
        edtpin= (EditText) findViewById(R.id.pin);
        subminBtn = (Button) findViewById(R.id.save_btn);
        skipBtn=(Button)findViewById(R.id.skip_btn);

        edtBithDate.setFocusable(false);
    }

    private void onClickListners()
    {
        edtBithDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDatepicker();
            }
        });

        genderGrup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.male)
                {
                    selected_gender = "M";
                }
                else if (checkedId == R.id.female)
                {
                    selected_gender = "F";

                }
            }
        });

        subminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidations()) {
                    setUserInfo();
                    Registration_ApiHelper.newUserRegistration(UserRegistration.this);
                }
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefManager.setSkipped_Registration(true);
                Intent intent=new Intent(UserRegistration.this, Menu_Activity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void setUserInfo()
    {
        prefManager.setUserName(edtName.getText().toString());
        prefManager.setMobileNumber(edtMobile.getText().toString());
        prefManager.setCity(edtCity.getText().toString());
        prefManager.setBirthDate(edtBithDate.getText().toString());
        prefManager.setCountry(edtcountry.getText().toString());
        prefManager.setState(edtstate.getText().toString());
        prefManager.setPincode(edtpin.getText().toString());

        prefManager.setGender(selected_gender);
    }

    public void displayDatepicker()
    {
        final Calendar calender = Calendar.getInstance();
        calender.set(1980, Calendar.JANUARY, 1);
        mYear = calender.get(Calendar.YEAR);
        mMonth = calender.get(Calendar.MONTH);
        mDay = calender.get(Calendar.DAY_OF_MONTH);

        dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear = monthOfYear + 1;
                edtBithDate.setText(dayOfMonth + "/" + monthOfYear + "/" + year);

            }
        }, mYear, mMonth, mDay);
        dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
        dpd.show();
    }

    private boolean checkValidations()
    {
        boolean result=true;

        if(edtName.getText().toString().length()==0)
        {
            edtName.setError("Enter Name");
            result = false;
        }
        else
        {
            edtName.setError(null);
        }

        if(!Validation.isValidPhoneNumber(edtMobile.getText().toString()))
        {
            edtMobile.setError("Enter Mobile Number");
            result = false;
        }
        else
        {
            edtMobile.setError(null);
        }
       /* if(edtBithDate.getText().toString().length()==0)
        {
            edtBithDate.setError("Enter Birth Date");
            result = false;
        }
        else
        {
            edtBithDate.setError(null);
        }
       */
        if(edtCity.getText().toString().length()==0)
        {
            edtCity.setError("Enter City");
            result = false;
        }
        else
        {
            edtCity.setError(null);
        }
        if(edtcountry.getText().toString().length()==0)
        {
            edtcountry.setError("Enter Country");
            result = false;
        }
        else
        {
            edtcountry.setError(null);
        }

        if(edtstate.getText().toString().length()==0)
        {
            edtstate.setError("Enter State");
            result = false;
        }
        else
        {
            edtstate.setError(null);
        }
        if (genderGrup.getCheckedRadioButtonId() == -1)
        {
            Toast.makeText(UserRegistration.this,"Please select gender",Toast.LENGTH_SHORT).show();
            result = false;
            // no radio buttons are checked
        }
        else
        {
            // one of the radio buttons is checked
        }


        if(!Validation.isValidPincode(edtpin.getText().toString()))
        {
            edtpin.setError("Enter Pin");
            result = false;
        }
        else
        {
            edtpin.setError(null);
        }
        return result;
    }
}
