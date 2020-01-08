package com.ailogic.ailogicpayment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ailogic.ailogicpayment.helper.AppHelper;
import com.ailogic.ailogicpayment.util.StepView;
import com.ailogic.ailogicpayment.validations.AsyncTaskHelper;
import com.ailogic.ailogicpayment.validations.ValidationDTO;
import com.ailogic.ailogicpayment.validations.ValidationHelper;
import com.ailogic.ailogicpayment.validations.ValidationUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int currentStep = 0;
    private Spinner SpExpMonth,SpExpYear,SpAccType,SpState,SpSubscDuration;
    private EditText ETCVV,ETBankRouting,ETBankAcc,ETNameOnCad,ETCardNumber,EtZipCode,EtAddress,EtCity,EtDate,EtDiscountAmt,EtPerUserFee,EtNoOfUsers;
    private LinearLayout LLBillingDetails,LLBillingAddress,LLSubscrDetails;
    private Button BtnSubmit,BtnNext,BtnBack;
    private String StateId;
    private  StepView stepView;
    private View ppView;
    private Dialog ppDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inIt();
// Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        stepView = findViewById(R.id.step_view);
        BtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

         if(LLBillingAddress.getVisibility()==View.VISIBLE){
             BillingAddresValidations();
         }if(LLSubscrDetails.getVisibility()==View.VISIBLE){
                    SubscrDetailsValidations();
            }
        }});
        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStep > 0) {
                    currentStep--;
                }
                stepView.done(false);
                stepView.go(currentStep, true);

            if(LLBillingAddress.getVisibility()==View.VISIBLE ){
                LLBillingAddress.setVisibility(View.GONE);
                LLBillingDetails.setVisibility(View.GONE);
                LLSubscrDetails.setVisibility(View.VISIBLE);
                BtnNext.setVisibility(View.VISIBLE);
                BtnSubmit.setVisibility(View.GONE);
            } if(LLBillingDetails.getVisibility()==View.VISIBLE ){
                LLBillingAddress.setVisibility(View.VISIBLE);
                LLBillingDetails.setVisibility(View.GONE);
                LLSubscrDetails.setVisibility(View.GONE);
                BtnNext.setVisibility(View.VISIBLE);
                BtnSubmit.setVisibility(View.GONE);
            }
            }
        });

        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitValidations();

            }
        });


    }//onCreate

    private void BillingAddresValidations() {
        try{
            ValidationHelper helper=new ValidationHelper();
            String[] strIds = getResources().getStringArray(R.array.billingAddress_ids_array);
            String[] strErrMsgs = getResources().getStringArray(R.array.billingAddress_errors_array);
            String[] strCompTypeArr = getResources().getStringArray(R.array.billingAddress_comptypes_array);
            ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

            int iPos = 0;
            for(String strCompType:strCompTypeArr){
                ValidationDTO valDTO=new ValidationDTO();
                valDTO.setComponentType(strCompType);
                valDTO.setComponentID(ValidationUtils.getIdResourceByName(MainActivity.this,strIds[iPos]));
                valDTO.setErrorMessage(strErrMsgs[iPos]);
                aList.add(valDTO);
                iPos++;
            }
            boolean isValidData = helper.validateData(MainActivity.this.getBaseContext(), aList,getWindow().getDecorView() );
            if (!isValidData) {
                return;
            }else{
                if (currentStep < stepView.getStepCount() - 1) {
                    currentStep++;
                    stepView.go(currentStep, true);
                } else {
                    stepView.done(true);
                }

                LLSubscrDetails.setVisibility(View.GONE);
                LLBillingAddress.setVisibility(View.GONE);
                LLBillingDetails.setVisibility(View.VISIBLE);
                BtnNext.setVisibility(View.GONE);
                BtnSubmit.setVisibility(View.VISIBLE);
//                SubmitAsyncTask runner =new SubmitAsyncTask();  runner.execute();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void SubscrDetailsValidations() {
        try{
            ValidationHelper helper=new ValidationHelper();
            String[] strIds = getResources().getStringArray(R.array.subscribe_ids_array);
            String[] strErrMsgs = getResources().getStringArray(R.array.subscribe_errors_array);
            String[] strCompTypeArr = getResources().getStringArray(R.array.subscribe_comptypes_array);
            ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

            int iPos = 0;
            for(String strCompType:strCompTypeArr){
                ValidationDTO valDTO=new ValidationDTO();
                valDTO.setComponentType(strCompType);
                valDTO.setComponentID(ValidationUtils.getIdResourceByName(MainActivity.this,strIds[iPos]));
                valDTO.setErrorMessage(strErrMsgs[iPos]);
                aList.add(valDTO);
                iPos++;
            }
            boolean isValidData = helper.validateData(MainActivity.this.getBaseContext(), aList,getWindow().getDecorView() );
            if (!isValidData) {
                return;
            }else{


                if (currentStep < stepView.getStepCount() - 1) {
                    currentStep++;
                    stepView.go(currentStep, true);
                } else {
                    stepView.done(true);
                }

                LLSubscrDetails.setVisibility(View.GONE);
                LLBillingAddress.setVisibility(View.VISIBLE);
                LLBillingDetails.setVisibility(View.GONE);
//                SubmitAsyncTask runner =new SubmitAsyncTask();  runner.execute();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void submitValidations() {
        try{
            ValidationHelper helper=new ValidationHelper();
            String[] strIds = getResources().getStringArray(R.array.billingDetails_ids_array);
            String[] strErrMsgs = getResources().getStringArray(R.array.billingDetails_errors_array);
            String[] strCompTypeArr = getResources().getStringArray(R.array.billingDetails_comptypes_array);
            ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

            int iPos = 0;
            for(String strCompType:strCompTypeArr){
                ValidationDTO valDTO=new ValidationDTO();
                valDTO.setComponentType(strCompType);
                valDTO.setComponentID(ValidationUtils.getIdResourceByName(MainActivity.this,strIds[iPos]));
                valDTO.setErrorMessage(strErrMsgs[iPos]);
                aList.add(valDTO);
                iPos++;
            }
            boolean isValidData = helper.validateData(MainActivity.this.getBaseContext(), aList,getWindow().getDecorView() );
            if (!isValidData) {
                return;
            }else{
                SubmitAsyncTask runner =new SubmitAsyncTask();  runner.execute();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void inIt() {

      BtnSubmit = (Button)findViewById(R.id.submit);
      BtnNext = (Button)findViewById(R.id.next);
      BtnBack = (Button)findViewById(R.id.back);
      SpSubscDuration = (Spinner)findViewById(R.id.spSubscrDuration);
      EtNoOfUsers = (EditText)findViewById(R.id.etNoOfUsers);
      EtPerUserFee = (EditText)findViewById(R.id.etPerUserFee);
      EtDiscountAmt = (EditText)findViewById(R.id.etDiscountAmount);
      EtDate = (EditText)findViewById(R.id.etDate);
      EtAddress = (EditText)findViewById(R.id.etAddress);
      EtCity = (EditText)findViewById(R.id.etCity);
      EtZipCode = (EditText)findViewById(R.id.etZip);
      SpState = (Spinner)findViewById(R.id.spState);
      SpAccType = (Spinner)findViewById(R.id.spAccType);
      SpExpYear = (Spinner)findViewById(R.id.spExpYear);
      SpExpMonth = (Spinner)findViewById(R.id.spExpMonth);
      ETCardNumber = (EditText)findViewById(R.id.etCeditCard);
      ETCVV = (EditText)findViewById(R.id.etCCV);
      ETNameOnCad = (EditText)findViewById(R.id.etNameOnCard);
      ETBankRouting = (EditText)findViewById(R.id.etBankRouting);
      ETBankAcc = (EditText)findViewById(R.id.etBankAccount);

      LLSubscrDetails=(LinearLayout)findViewById(R.id.llSubscrDetails);
      LLBillingAddress=(LinearLayout)findViewById(R.id.llBillingAddress);
      LLBillingDetails=(LinearLayout)findViewById(R.id.llBillingDetails);


        EtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppHelper.getPerfectDate(EtDate,MainActivity.this);
            }
        });

    }//inIt

    private class SubmitAsyncTask extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;
        private int userFees,discountFee,noOfUser,stateId;
        private String expDate;
        private int amount;


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this,     "Please wait",   "loading...");

            String expYear = SpExpYear.getSelectedItem().toString();
            String expMonth = SpExpMonth.getSelectedItem().toString();

            expDate = expMonth+"/"+expYear;

            String stateName = SpState.getSelectedItem().toString();
            if(stateName.equalsIgnoreCase("MA")){
                stateId =1;
            }else if(stateName.equalsIgnoreCase("NH")){
                stateId =4;
            }else if(stateName.equalsIgnoreCase("NY")){
                stateId =5;
            }
             noOfUser =Integer.parseInt(EtNoOfUsers.getText().toString());
             userFees =Integer.parseInt(EtPerUserFee.getText().toString());
             discountFee =Integer.parseInt(EtDiscountAmt.getText().toString());

            amount =(noOfUser*userFees)-discountFee;

        }
        @Override
        protected String doInBackground(String... params) {
            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("Ispayment",true);
                obj.accumulate("Address",EtAddress.getText().toString());
                obj.accumulate("City",EtCity.getText().toString());
                obj.accumulate("State",stateId);
                obj.accumulate("ZIP",EtZipCode.getText().toString());
                obj.accumulate("Cardnum",ETCardNumber.getText().toString());
                obj.accumulate("CardCode",ETCVV.getText().toString());
                obj.accumulate("CardDate",expDate);
                obj.accumulate("Amount",amount);
                obj.accumulate("Recurring","");
                obj.accumulate("Isallowed",true);
                obj.accumulate("USerHostAddress",1);
                obj.accumulate("ItemsDescrip","Total Language");
                obj.accumulate("Country","USA");
                obj.accumulate("CompanyId",7);
                obj.accumulate("SubscriptionDuration",SpSubscDuration.getSelectedItem().toString());
                obj.accumulate("NumberofUsers",noOfUser);
                obj.accumulate("PerUserFee",userFees);
                obj.accumulate("DiscountAmount",discountFee);
                obj.accumulate("SubscriptionStartDate",EtDate.getText().toString());
                obj.accumulate("Active",true);

                Log.i("Payment Obj-->",obj.toString());
            }catch (Exception e){
            }
            String content= AsyncTaskHelper.makeServiceCall("http://totallsp.com/Controls/CompaniesManagement/SubscribeUsers","POST",obj);
            return content;
        }
        @Override
        protected void onPostExecute(String result) {
            Log.i("Payment OnPost:-->",result);
            progressDialog.dismiss();
            try {
                    ViewPopUp(result);

            }catch (Exception e){
                e.printStackTrace();
            }
        }//onPostExecute
    }//LoginAsyncTask class

    private void ViewPopUp(String result) {
    try {

        ppView = View.inflate(MainActivity.this,R.layout.preview, null);
        ppView.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.zoom_in_enter));
        this.ppDialog = new Dialog(MainActivity.this, R.style.NewDialog);
        this.ppDialog.setContentView(ppView);
        this.ppDialog.setCancelable(true);
        this.ppDialog.show();

        Window window = this.ppDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER | Gravity.CENTER;
        window.setGravity(Gravity.CENTER);
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.dimAmount = 0.0f;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wlp.windowAnimations = R.anim.slide_move;

        window.setAttributes(wlp);
        window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        ImageView v = (ImageView) this.ppDialog.findViewById(R.id.closeDialog);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ppDialog.dismiss();;
            }
        });
        TextView TvHeading = (TextView) this.ppDialog.findViewById(R.id.tvPpUpHeading);  TvHeading.setText("Response");
        TextView TvResponse = (TextView) this.ppDialog.findViewById(R.id.tvResp);
        TvResponse.setText(result);

    }catch (Exception e){ e.printStackTrace();  }
    }//popUp


}
