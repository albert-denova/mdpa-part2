package com.lasalle.second.part.week1.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lasalle.second.part.week1.R;
import com.lasalle.second.part.week1.model.Property;
import com.lasalle.second.part.week1.services.ApplicationServiceFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    private static final String TEXT1 = "text1";
    private static final String TEXT2 = "text2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListAdapter listAdapter = createListAdapter();
        ListView listView = (ListView) findViewById(R.id.previousResultsList);
        listView.setAdapter(listAdapter);

        EditText editText = (EditText) findViewById(R.id.locationSearchText);
        editText.setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;

        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            handled = true;

            EditText editText = (EditText) findViewById(R.id.locationSearchText);
            String storedText = editText.getText().toString();
            hideKeyboard(editText);

            Log.d(this.getLocalClassName(), "Search Action: " + storedText);
            List<Property> propertyList = ApplicationServiceFactory.getInstance(this).getPropertyService().searchProperties(storedText);

            if(propertyList.isEmpty())
            {
                createToastNoResultsFound();
            }
            else
            {
                Context context = getApplicationContext();
                CharSequence text = "YESS results found";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
        return handled;
    }

    private ListAdapter createListAdapter() {
        final String[] fromMapKey = new String[] {TEXT1, TEXT2};
        final int[] toLayoutId = new int[] {android.R.id.text1, android.R.id.text2};

        final List<Map<String, String>> list = new ArrayList<>();

        addListItem(list, "Alicante, en venta", "36 resultados");
        addListItem(list, "Alicante 03009, en venta", "9 resultados");
        addListItem(list, "Alicante 03010, en venta", "16 resultados");
        addListItem(list, "Alicante 03011, en venta", "1 resultados");
        addListItem(list, "Alicante 03012, en venta", "10 resultados");


        return new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, fromMapKey, toLayoutId);
    }

    private void addListItem(List<Map<String, String>> list, String itemTitle, String itemSubtitle) {
        final Map<String, String> itemMap = new HashMap<>();
        itemMap.put(TEXT1, itemTitle);
        itemMap.put(TEXT2, itemSubtitle);

        list.add(Collections.unmodifiableMap(itemMap));
    }

    private void hideKeyboard(View view) {
        view.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void createToastNoResultsFound() {
        Context context = getApplicationContext();
        CharSequence text = "No results found";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
