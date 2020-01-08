package com.ailogic.ailogicpayment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ailogic.ailogicpayment.adapter.MultiSelectionAdapter;
import com.ailogic.ailogicpayment.model.Product;

import java.util.ArrayList;

public class MutiSelectListView extends AppCompatActivity implements View.OnClickListener {

    ListView mListView;


    Button btnShowCheckedItems;

    ArrayList<Product> mProducts;


    MultiSelectionAdapter<Product> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.muti_select_list_view_activity);


        bindComponents();


        init();


        addListeners();

    }




    private void bindComponents() {


        // TODO Auto-generated method stub


        mListView = (ListView) findViewById(android.R.id.list);


        btnShowCheckedItems = (Button) findViewById(R.id.btnShowCheckedItems);


    }





    private void init() {


        // TODO Auto-generated method stub


        mProducts = new ArrayList<Product>();


        mProducts.add(new Product("Pendrive"));


        mProducts.add(new Product("Laptop"));


        mProducts.add(new Product("Mouse"));


        mProducts.add(new Product("Keyboard"));


        mProducts.add(new Product("HDD"));


        mProducts.add(new Product("Ram"));


        mProducts.add(new Product("Cable"));


        mProducts.add(new Product("Monitor"));


        mProducts.add(new Product("Cabinate"));


        mProducts.add(new Product("CMOS"));


        mProducts.add(new Product("Charger"));


        mProducts.add(new Product("Processor"));


        mProducts.add(new Product("Laptop Bag"));


        mProducts.add(new Product("Laptop Stand"));


        mProducts.add(new Product("Head Phone"));


        mProducts.add(new Product("Mike"));


        mProducts.add(new Product("Bluetooth"));


        mProducts.add(new Product("Dongle"));





        mAdapter = new MultiSelectionAdapter<Product>(this, mProducts,5);


        mListView.setAdapter(mAdapter);


    }





    private void addListeners() {


        // TODO Auto-generated method stub


        btnShowCheckedItems.setOnClickListener(this);


    }




    @Override


    public void onClick(View v) {


        // TODO Auto-generated method stub





        if(mAdapter != null) {


            ArrayList<Product> mArrayProducts = mAdapter.getCheckedItems();


            Log.d(MainActivity.class.getSimpleName(), "Selected Items: " + mArrayProducts.toString());


            Toast.makeText(getApplicationContext(), "Selected Items: " + mArrayProducts.toString(), Toast.LENGTH_LONG).show();


        }


    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
