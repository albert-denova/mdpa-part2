package com.lasalle.second.part.propertycross;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TEXT1 = "text1";
    private static final String TEXT2 = "text2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListAdapter listAdapter = CreateListAdapter();
        ListView listView = (ListView) findViewById(R.id.previousResultsList);
        listView.setAdapter(listAdapter);
    }

    private ListAdapter CreateListAdapter() {
        final String[] fromMapKey = new String[] {TEXT1, TEXT2};
        final int[] toLayoutId = new int[] {android.R.id.text1, android.R.id.text2};

        final List<Map<String, String>> list = new ArrayList<>();

        AddListItem(list, "Alicante, en venta", "36 resultados");
        AddListItem(list, "Alicante 03009, en venta", "9 resultados");
        AddListItem(list, "Alicante 03010, en venta", "16 resultados");
        AddListItem(list, "Alicante 03011, en venta", "1 resultados");
        AddListItem(list, "Alicante 03012, en venta", "10 resultados");


        return new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, fromMapKey, toLayoutId);
    }

    private void AddListItem(List<Map<String, String>> list, String itemTitle, String itemSubtitle) {
        final Map<String, String> itemMap = new HashMap<>();
        itemMap.put(TEXT1, itemTitle);
        itemMap.put(TEXT2, itemSubtitle);

        list.add(Collections.unmodifiableMap(itemMap));
    }
}
