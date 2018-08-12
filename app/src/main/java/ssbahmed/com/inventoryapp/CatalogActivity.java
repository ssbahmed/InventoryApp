package ssbahmed.com.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.FloatingActionButton;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import ssbahmed.com.inventoryapp.data.InventoryHelper;
import ssbahmed.com.inventoryapp.data.InventoryContract.ItemEntry;

public class CatalogActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor> {
    /** Database helper that will provide us access to the database */
    private InventoryHelper mDbHelper;
    private CatalogCursorAdapter mCursorAdapter;
    private static final int ITEM_LOADER = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // Find the ListView which will be populated with the pet data
        ListView itemListView = (ListView) findViewById(R.id.list);
        mCursorAdapter = new CatalogCursorAdapter(this,null);
        itemListView.setAdapter(mCursorAdapter);
        getLoaderManager().initLoader(ITEM_LOADER,null,this);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        itemListView.setEmptyView(emptyView);

        //setup an item click listener
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int postion, long id) {
                Intent intent = new Intent (CatalogActivity.this,EditorActivity.class);
                /*form the content uri that represent the specified item that was clicked on
                 * by appending the id passed as input to the method  */
                Uri currentItemUri = ContentUris.withAppendedId(ItemEntry.CONTENT_URI,id);
                //set the uri in the data field of the intent
                intent.setData(currentItemUri);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_insert_dummy_data:
                insertItem();
                return true;
            case R.id.action_delete_all_entries:
                deleteItem();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void insertItem(){

        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(ItemEntry.COLUMN_ITEM_NAME, "Toto");
        values.put(ItemEntry.COLUMN_ITEM_DESCRIPTION, "The last trend of ");
        values.put(ItemEntry.COLUMN_ITEM_PRICE, 55);
        values.put(ItemEntry.COLUMN_ITEM_QUANTITY, 2);
        Uri newUri =getContentResolver().insert(ItemEntry.CONTENT_URI,values);
    }


    private void deleteItem()
    {
     int rowsDeleted = getContentResolver().delete(ItemEntry.CONTENT_URI,null,null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from pet database");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Define a projection that specifies which columns from the database
        String[] projection = {
                ItemEntry._ID,
                ItemEntry.COLUMN_ITEM_NAME,
                ItemEntry.COLUMN_ITEM_DESCRIPTION,
                ItemEntry.COLUMN_ITEM_PRICE,
               // ItemEntry.COLUMN_ITEM_QUANTITY
        };
        //this loader will execute the content provider qury method in the background thread
        return new CursorLoader(this,   //parent activity context
                ItemEntry.CONTENT_URI,           //provider content uri to query
                projection,                     //column to include in the resulting cursor
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        //update ItemCursorAdapter with this new cursor containing update item data
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //callback called when the data need to be deleted
        mCursorAdapter.swapCursor(null);
    }
}
