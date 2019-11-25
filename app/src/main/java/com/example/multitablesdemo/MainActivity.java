package com.example.multitablesdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText product,qty,price;
    Button btd;
    Spinner spinner;
    ListView listView1,listView2,listView3;

    DatabaseHelper databaseHelper;

    ArrayList arrayList1,arrayList2,arrayList3;
    ArrayAdapter arrayAdapter1,arrayAdapter2,arrayAdapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assign variable
        product = findViewById(R.id.et_product);
        qty = findViewById(R.id.et_qty);
        price = findViewById(R.id.et_price);
        btd = findViewById(R.id.bt_add);
        spinner = findViewById(R.id.spinner);
        listView1 = findViewById(R.id.list_view1);
        listView2 = findViewById(R.id.list_view2);
        listView3 = findViewById(R.id.list_view3);

       databaseHelper = new DatabaseHelper(this);

       // Add Database Values to Arraaylist
        arrayList1 = databaseHelper.getProduct();
        arrayList2 = databaseHelper.getQty();
        arrayList3 = databaseHelper.getPrice();

        //Initialize Arrayadapter For ListView
        arrayAdapter1 = new ArrayAdapter(MainActivity.this,
                android.R.layout.simple_list_item_1,arrayList1);
        listView1.setAdapter(arrayAdapter1);

        arrayAdapter2 = new ArrayAdapter(MainActivity.this,
                android.R.layout.simple_list_item_1,arrayList2);
        listView2.setAdapter(arrayAdapter2);

        arrayAdapter3 = new ArrayAdapter(MainActivity.this,
                android.R.layout.simple_list_item_1,arrayList3);
        listView3.setAdapter(arrayAdapter3);




       //Create String Array
        String[] spinnerList = new String[] {"Product", "Qty", "Price"};

        //Initialize ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 ) {
                    listView1.setVisibility(View.VISIBLE);
                    listView2.setVisibility((View.GONE));
                    listView3.setVisibility(View.GONE);
                }

                if (position == 1 ) {
                    listView1.setVisibility(View.GONE);
                    listView2.setVisibility((View.VISIBLE));
                    listView3.setVisibility(View.GONE);
                }

                if (position == 2 ) {
                    listView1.setVisibility(View.GONE);
                    listView2.setVisibility((View.GONE));
                    listView3.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eproduct = product.getText().toString();
                String eqty = qty.getText().toString();
                String eprice = price.getText().toString();

                if (!eproduct.isEmpty() && !eqty.isEmpty() && !eprice.isEmpty()) {
                    if(databaseHelper.insert(eproduct,eqty,eprice)) {

                        Toast.makeText(getApplicationContext(), " Data inserted",
                                Toast.LENGTH_SHORT).show();

                        product.setText("");
                        price.setText("");
                        qty.setText("");

                        arrayList1.clear();
                        arrayList2.clear();
                        arrayList3.clear();

                        //add data
                        arrayList1.addAll(databaseHelper.getProduct());
                        arrayList2.addAll(databaseHelper.getQty());
                        arrayList3.addAll(databaseHelper.getPrice());

                        //Notify Adapter
                        arrayAdapter1.notifyDataSetChanged();
                        arrayAdapter2.notifyDataSetChanged();
                        arrayAdapter3.notifyDataSetChanged();

                        listView1.invalidateViews();
                        listView1.refreshDrawableState();
                    }
                }
            }
        });
    }
}
