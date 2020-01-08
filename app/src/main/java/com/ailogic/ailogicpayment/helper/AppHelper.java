package com.ailogic.ailogicpayment.helper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AppHelper {


    private static Calendar myCalendar;

    public static void getPerfectDate(final EditText editText, Activity activity)  {
        try {
            myCalendar = Calendar.getInstance();
//            SimpleDateFormat df1 = new SimpleDateFormat("MM-dd-yyyy");
            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {


                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
//                view.updateDate(2016, 5, 22);
                    Log.i("AAAA-->",""+year+"   "+monthOfYear+" "+dayOfMonth);
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "MM/dd/yyyy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    editText.setText(sdf.format(myCalendar.getTime()));
                }
            };


            if(editText.getText().toString().isEmpty()){

                new DatePickerDialog(activity, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }else{
                Date etDate = new SimpleDateFormat("MM/dd/yyyy").parse(editText.getText().toString());
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTime(etDate);
                Log.i("Getting Date:-->",""+ calendar.get(Calendar.YEAR)+"  "+calendar.get(Calendar.MONTH) +"  "+calendar.get(Calendar.DAY_OF_MONTH));
                new DatePickerDialog(activity,date, calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH) ,calendar.get(Calendar.DAY_OF_MONTH)).show();

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
