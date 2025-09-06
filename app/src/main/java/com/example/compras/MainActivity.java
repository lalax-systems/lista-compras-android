package com.example.compras;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etItem;
    private Button btnAdd;
    private ListView lvItems;
    private ArrayList<String> itemList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        etItem = findViewById(R.id.etItem);
        btnAdd = findViewById(R.id.btnAdd);
        lvItems = findViewById(R.id.lvItems);

        // Inicializar lista y adaptador
        itemList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_list_item_1, itemList);
        lvItems.setAdapter(adapter);

        // Configurar botón de añadir
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
    }

    private void addItem() {
        String item = etItem.getText().toString().trim();
        if (!item.isEmpty()) {
            itemList.add(item);
            adapter.notifyDataSetChanged();
            etItem.setText("");
        }
    }
}