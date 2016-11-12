package com.atomickitten.likeanappleface;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.atomickitten.DB.AppDBHelper;
import com.atomickitten.DB.Item;
import com.atomickitten.DB.ItemListAdapter;

import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final AppDBHelper db = new AppDBHelper(this);

    int navSelection = 0;
    ListView listView;
    ItemListAdapter itemListAdapter;
    ArrayList<Item> list_itemArrayList;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Typekit.getInstance().addNormal(Typekit.createFromAsset(this, "fonts/BMJUA_ttf.ttf"));

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setListView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_item_all);
     }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_edit) {
            Toast.makeText(MainActivity.this, "편집", Toast.LENGTH_SHORT).show();
            return true;
        }else if(id == R.id.action_sort){

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(getString(R.string.dialog_sort_title))
                    .setSingleChoiceItems(R.array.dialog_sort, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int select) {
                            if(select == 0){
                                onResume();
                                dialogInterface.dismiss();
                            }else if(select == 1){
                                setListView(navSelection);
                                dialogInterface.dismiss();
                            }
                        }
                    }).show();

            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_item_all:
                setListView();
                navSelection = 0;
                break;
            case R.id.nav_item_skin:
                setListView(getString(R.string.nav_item_skin));
                navSelection = 1;
                break;
            case R.id.nav_item_base:
                setListView(getString(R.string.nav_item_base));
                navSelection = 2;
                break;
            case R.id.nav_item_color:
                setListView(getString(R.string.nav_item_color));
                navSelection = 3;
                break;
            case R.id.nav_item_etc:
                setListView(getString(R.string.nav_item_etc));
                navSelection = 4;
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        switch (navSelection){
            case 0:
                setListView();
                break;
            case 1:
                setListView(getString(R.string.nav_item_skin));
                break;
            case 2:
                setListView(getString(R.string.nav_item_base));
                break;
            case 3:
                setListView(getString(R.string.nav_item_color));
                break;
            case 4:
                setListView(getString(R.string.nav_item_etc));
                break;
        }


    }


    private void setListView(){
        listView = (ListView) findViewById(R.id.listview1);
        registerForContextMenu(listView);

        list_itemArrayList = new ArrayList<Item>();
        List<Item> itemList = db.getAllItems();

        for (Item cn : itemList) {
            list_itemArrayList.add(cn);
        }

        itemListAdapter = new ItemListAdapter(MainActivity.this, list_itemArrayList);

        listView.setAdapter(itemListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("update", list_itemArrayList.get(position).get_id());
                startActivity(intent);

            }
        });
    }
    private void setListView(String str){
        listView = (ListView) findViewById(R.id.listview1);
        registerForContextMenu(listView);

        list_itemArrayList = new ArrayList<Item>();
        List<Item> itemList = db.getAllItems(str);

        for (Item cn : itemList) {
            list_itemArrayList.add(cn);
        }

        itemListAdapter = new ItemListAdapter(MainActivity.this, list_itemArrayList);

        listView.setAdapter(itemListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("update", list_itemArrayList.get(position).get_id());
                startActivity(intent);

            }
        });
    }
    private void setListView(int navSelection){
        listView = (ListView) findViewById(R.id.listview1);
        registerForContextMenu(listView);

        list_itemArrayList = new ArrayList<Item>();
        List<Item> itemList;

        switch (navSelection){
            case 0:
                itemList = db.getAllItemsOrderByExpireDate(null);
                break;
            case 1:
                itemList = db.getAllItemsOrderByExpireDate(getString(R.string.nav_item_skin));
                break;
            case 2:
                itemList = db.getAllItemsOrderByExpireDate(getString(R.string.nav_item_base));
                break;
            case 3:
                itemList = db.getAllItemsOrderByExpireDate(getString(R.string.nav_item_color));
                break;
            case 4:
                itemList = db.getAllItemsOrderByExpireDate(getString(R.string.nav_item_etc));
                break;
            default:
                itemList = db.getAllItemsOrderByExpireDate(null);
                break;
        }

        for (Item cn : itemList) {
            list_itemArrayList.add(cn);
        }

        itemListAdapter = new ItemListAdapter(MainActivity.this, list_itemArrayList);

        listView.setAdapter(itemListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("update", list_itemArrayList.get(position).get_id());
                startActivity(intent);

            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.listview1) {
            // AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            String[] menuItems = getResources().getStringArray(R.array.menu);
            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = getResources().getStringArray(R.array.menu);
        String menuItemName = menuItems[menuItemIndex];

        switch (menuItemIndex){
            case 0:
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("update", list_itemArrayList.get(info.position).get_id());
                startActivity(intent);
                break;

            case 1:

                new AlertDialog.Builder(MainActivity.this)
                        .setMessage(R.string.dialog_delete_text)
                        .setPositiveButton(getString(R.string.btn_positive), new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.deleteItem(list_itemArrayList.get(info.position));
                                onResume();
                                Toast.makeText(MainActivity.this, getString(R.string.toast_delete_text), Toast.LENGTH_SHORT);
                            }
                        })
                        .setNegativeButton(getString(R.string.btn_negative), null)
                        .show();
                break;
        }


        return true;
    }
}
