package com.lasalle.second.part.week1.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lasalle.second.part.week1.R;
import com.lasalle.second.part.week1.fragments.NavigationDrawerFragment;
import com.lasalle.second.part.week1.listeners.PropertyServiceListener;
import com.lasalle.second.part.week1.model.AccessToken;
import com.lasalle.second.part.week1.model.Property;
import com.lasalle.second.part.week1.model.PropertySearch;
import com.lasalle.second.part.week1.services.ApplicationServiceFactory;
import com.lasalle.second.part.week1.services.AuthService;
import com.lasalle.second.part.week1.services.PropertyService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Result;

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

        setupToolbar();
        setupNavigationDrawer();
    }

    @Override
    public boolean onEditorAction(TextView editText, int actionId, KeyEvent event) {
        boolean handled = false;

        final boolean isEnterKey = (event != null) && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) &&
                (event.getAction() == KeyEvent.ACTION_DOWN);

        if (actionId == EditorInfo.IME_ACTION_SEARCH || isEnterKey) {
            handled = true;

            final String storedText = editText.getText().toString();
            final boolean isRent = ((CheckBox) findViewById(R.id.optionRent)).isChecked();
            final boolean isSell = ((CheckBox) findViewById(R.id.optionBuy)).isChecked();
            hideKeyboard(editText);

            Log.d(this.getLocalClassName(), "Search Action: " + storedText);

            ApplicationServiceFactory applicationServiceFactory = ApplicationServiceFactory.getInstance();
            AuthService authService = applicationServiceFactory.getAuthService();

            if(!authService.isAccesTokenValid())
            {
                createToastError();
                return handled;
            }

            PropertyService propertyService = applicationServiceFactory.getPropertyService();
            AccessToken accessToken = authService.getAccessToken();

            PropertySearch propertySearch = new PropertySearch(storedText, isRent, isSell);

            propertyService.searchPropertiesCachingResult(propertySearch, accessToken, new PropertyServiceListener<List<Property>>() {
                @Override
                public void onDataLoaded(List<Property> propertyList) {
                    processSearchResults(propertyList);
                }
            });

            displayWaitingButton();
        }
        return handled;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main_activity_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    protected ListAdapter createListAdapter() {
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

    protected void addListItem(List<Map<String, String>> list, String itemTitle, String itemSubtitle) {
        final Map<String, String> itemMap = new HashMap<>();
        itemMap.put(TEXT1, itemTitle);
        itemMap.put(TEXT2, itemSubtitle);

        list.add(Collections.unmodifiableMap(itemMap));
    }

    protected void hideKeyboard(View view) {
        view.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected void createToastNoResultsFound() {
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.search_not_found);
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    protected void createToastError() {
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.search_error);
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    protected void processSearchResults(List<Property> propertyList) {
        if(propertyList.isEmpty())
        {
            createToastNoResultsFound();
        }
        else
        {
            Intent intent = new Intent(this, ResultsContainerActivity.class);
            startActivity(intent);
        }
    }

    protected void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.mainActivityToolbar);
        toolbar.setTitle(getString(R.string.main_activity_title));
        setSupportActionBar(toolbar);

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,  mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();
    }

    protected void setupNavigationDrawer() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment navigationDrawerFragment = new NavigationDrawerFragment();
        fragmentTransaction.add(R.id.drawer_layout, navigationDrawerFragment);
        fragmentTransaction.commit();
    }

    protected void displayWaitingButton() {
        final RelativeLayout waitingLayout = (RelativeLayout) findViewById(R.id.floatingCancelLayout);
        waitingLayout.setVisibility(View.VISIBLE);

        FloatingActionButton cancelButton = (FloatingActionButton) findViewById(R.id.cancelSearchActionButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationServiceFactory applicationServiceFactory = ApplicationServiceFactory.getInstance();
                PropertyService propertyService = applicationServiceFactory.getPropertyService();
                propertyService.cancelSearch();
                waitingLayout.setVisibility(View.GONE);
            }
        });
    }


}
