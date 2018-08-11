package ssbahmed.com.inventoryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import ssbahmed.com.inventoryapp.data.InventoryContract;
import ssbahmed.com.inventoryapp.data.InventoryContract.ItemEntry;

public class EditorActivity extends AppCompatActivity {
    /**
     * EditText fieldes
     */
    private EditText mNameEditText;
    private EditText mDesEditText;
    private EditText mPriceEditText;
    private EditText mQuantityEditText;
    private Uri mCurrentPetUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        //examine the intent that was used to launch this activity
        //in order to figure out if we editing pet or creating new one
        Intent intent = getIntent();
        mCurrentPetUri = intent.getData();

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText)findViewById(R.id.edit_name);
        mDesEditText = (EditText)findViewById(R.id.edit_des);
        mQuantityEditText = (EditText)findViewById(R.id.edit_quntity);
       // mPriceEditText = (EditText)findViewById(R.id.edit_price);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                save();
                // Exit activity
                finish();
                return true;
            case R.id.action_delete:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save()
    {  // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String desString = mDesEditText.getText().toString().trim();
       // String priceString = mPriceEditText.getText().toString().trim();
        String quantityString = mQuantityEditText.getText().toString().trim();

        //int price =Integer.parseInt(priceString);
        int quantity =Integer.parseInt(quantityString);
        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(ItemEntry.COLUMN_ITEM_NAME, nameString);
        values.put(ItemEntry.COLUMN_ITEM_DESCRIPTION, desString);
        values.put(ItemEntry.COLUMN_ITEM_PRICE, 2);
        values.put(ItemEntry.COLUMN_ITEM_QUANTITY, quantity);


        //The case of adding new pet
        if (mCurrentPetUri == null) {
            // Insert a new pet into the provider, returning the content URI for the new pet.
            Uri newUri = getContentResolver().insert(ItemEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, "failed to insert item",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, "Successfully inserted",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }
}
