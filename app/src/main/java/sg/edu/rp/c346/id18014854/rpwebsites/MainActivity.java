package sg.edu.rp.c346.id18014854.rpwebsites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Spinner spnCategory;
    Spinner spnSubCategory;
    Button btnGo;
    ArrayList<String> alWebs;
    ArrayAdapter<String> aaWebs;
    String[] strWebs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnCategory = findViewById(R.id.spinnerCategory);
        spnSubCategory = findViewById(R.id.spinnerSub);
        btnGo = findViewById(R.id.buttonGo);

        // Initialise the ArrayList
        alWebs = new ArrayList<>();

        // Create an ArrayAdapter using the default Spinner layout and the ArrayList
        aaWebs = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, alWebs);

        // Bind the ArrayAdapter to the Spinner
        spnSubCategory.setAdapter(aaWebs);

        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        alWebs.clear();
                        strWebs = getResources().getStringArray(R.array.sub_category_rp);
                        alWebs.addAll(Arrays.asList(strWebs));
                        aaWebs.notifyDataSetChanged();
                        break;
                    case 1:
                        alWebs.clear();
                        strWebs = getResources().getStringArray(R.array.sub_category_soi);
                        alWebs.addAll(Arrays.asList(strWebs));
                        aaWebs.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebPageActivity.class);
                String[][] sites = {{"https://www.rp.edu.sg","https://www.rp.edu.sg/student-life",},{"https://www.rp.edu.sg/soi/full-time-diplomas/details/r47","https://www.rp.edu.sg/soi/full-time-diplomas/details/r12",}};
                String url = sites[spnCategory.getSelectedItemPosition()][spnSubCategory.getSelectedItemPosition()];
                intent.putExtra("website_url", url);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();

        // Step 1a: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Step 1b: Obtain an instance of the SharedPreferences Editor for update later
        SharedPreferences.Editor prefEdit = prefs.edit();

        // Step 1c: Add the key-value pair
        prefEdit.putInt("category-save", spnCategory.getSelectedItemPosition());
        prefEdit.putInt("sub-cate-save", spnSubCategory.getSelectedItemPosition());

        // Step 1d: Call commit() to save the changes into SharedPreferences
        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Step 2a: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Step 2b: Retrieve the saved data with key from SharedPreference object
        int cate = prefs.getInt("category-save", 0);
        int subCate = prefs.getInt("sub-cate-save",0);

        spnCategory.setSelection(cate);
        spnSubCategory.setSelection(subCate);
    }
}
