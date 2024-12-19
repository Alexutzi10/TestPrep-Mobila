package com.example.mobila;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mobila.data.Mobila;
import com.example.mobila.database.MobilaService;
import com.example.mobila.network.AsyncTaskRunner;
import com.example.mobila.network.Callback;
import com.example.mobila.network.HttpManager;
import com.example.mobila.network.JsonParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREF = "shared_pref";
    public static final String SPINNER_KEY = "spinner_key";
    public static final String ET_KEY = "et_key";
    private final String url = "https://api.npoint.io/efcea1d46c36fa555d04";
    private FloatingActionButton fab;
    private TextView tv;
    private Spinner spinner;
    private EditText et;
    private Button bttn;
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
    private List<Mobila> mobilaList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private MobilaService mobilaService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initComponents();

        fab.setOnClickListener(click -> {
            asyncTaskRunner.executeAsync(new HttpManager(url), onMainThreadOperation());
        });

        bttn.setOnClickListener(click -> {
            if (et.getText() == null || et.getText().toString().isBlank()) {
                Toast.makeText(getApplicationContext(), R.string.invalid_weight_value, Toast.LENGTH_SHORT).show();
            }

            String comp = spinner.getSelectedItem().toString();
            int value = Integer.parseInt(et.getText().toString());

            List<Mobila> deleted;
            if (comp.equals("are valoarea mai mare decat")) {
                deleted = mobilaList.stream().filter(m -> m.getWeight() > value).collect(Collectors.toList());
            } else if (comp.equals("are valoarea mai mica decat")) {
                deleted = mobilaList.stream().filter(m -> m.getWeight() < value).collect(Collectors.toList());
            } else {
                deleted = mobilaList.stream().filter(m -> m.getWeight() == value).collect(Collectors.toList());
            }

            mobilaService.delete(deleted, callbackDelete(comp, value));
        });
    }

    private Callback<List<Mobila>> callbackDelete(String comp, int value) {
        return result -> {
            if (result != null) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SPINNER_KEY, comp);
                editor.putInt(ET_KEY, value);
                editor.apply();
                mobilaList.removeAll(result);
                Toast.makeText(getApplicationContext(), R.string.deleted, Toast.LENGTH_SHORT).show();
            }
        };
    }

    private Callback<String> onMainThreadOperation() {
        return result -> {
            List<Mobila> parsed = JsonParser.getFromJson(result);
            List<Mobila> insert = parsed.stream().filter(m -> !mobilaList.contains(m)).collect(Collectors.toList());

            if (!insert.isEmpty()) {
                mobilaService.insertAll(insert, callbackInsert());
            }
        };
    }

    private Callback<List<Mobila>> callbackInsert() {
        return result -> {
          if (result != null) {
              mobilaList.addAll(result);
              Toast.makeText(getApplicationContext(), R.string.inserted, Toast.LENGTH_SHORT).show();
          }
        };
    }

    private void initComponents() {
        fab = findViewById(R.id.surugiu_george_alexandru_fab);
        tv = findViewById(R.id.surugiu_george_alexandru_tv);
        spinner = findViewById(R.id.surugiu_george_alexandru_spinner);
        et = findViewById(R.id.surugiu_george_alexandru_et);
        bttn = findViewById(R.id.surugiu_george_alexandru_bttn);

        sharedPreferences = getApplication().getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        mobilaService = new MobilaService(getApplicationContext());

        String comp = sharedPreferences.getString(SPINNER_KEY, "");
        int value = sharedPreferences.getInt(ET_KEY, 0);

        if (comp.equals("are valoarea mai mare decat")) {
            spinner.setSelection(0);
        } else if (comp.equals("are valoarea mai mica decat")) {
            spinner.setSelection(1);
        } else {
            spinner.setSelection(2);
        }

        if (value != 0) {
            et.setText(String.valueOf(value));
        }
        mobilaService.getAll(callbackGetAll());
    }

    private Callback<List<Mobila>> callbackGetAll() {
        return result -> {
          if (result != null) {
              mobilaList.clear();
              mobilaList.addAll(result);
              Toast.makeText(getApplicationContext(), R.string.loaded, Toast.LENGTH_SHORT).show();
          }
        };
    }
}