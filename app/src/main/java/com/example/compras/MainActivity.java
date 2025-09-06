package com.example.compras;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
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

        // Cargar items guardados
        itemList = DataStorage.loadItems(this);
        
        // Inicializar adaptador
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

        // Configurar clic largo para eliminar
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removeItem(position);
                return true;
            }
        });

        // Configurar clic simple para marcar como comprado
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                markAsPurchased(position);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Guardar items cuando la actividad se pausa
        DataStorage.saveItems(this, itemList);
    }

    private void addItem() {
        String item = etItem.getText().toString().trim();
        if (!item.isEmpty()) {
            itemList.add(item);
            adapter.notifyDataSetChanged();
            etItem.setText("");
            saveItems();
            Toast.makeText(this, "Artículo añadido", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Escribe un artículo", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeItem(int position) {
        String removedItem = itemList.remove(position);
        adapter.notifyDataSetChanged();
        saveItems();
        Toast.makeText(this, "Eliminado: " + removedItem, Toast.LENGTH_SHORT).show();
    }

    private void markAsPurchased(int position) {
        String item = itemList.get(position);
        if (!item.startsWith("✓ ")) {
            itemList.set(position, "✓ " + item);
            adapter.notifyDataSetChanged();
            saveItems();
            Toast.makeText(this, "Marcado como comprado", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveItems() {
        DataStorage.saveItems(this, itemList);
    }
}