package ssbahmed.com.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import ssbahmed.com.inventoryapp.data.InventoryContract.ItemEntry;

/*  CatalogCursorAdapter is an adapter for a list or grid view
that uses a  Cursor of Items data as its data source. This adapter knows
 how to create list items for each row of items data in the  Cursor*/

public class CatalogCursorAdapter extends CursorAdapter {
    /*Constructs a new CatalogCursorAdapter.
      param context The context
      param c The cursor from which to get the data. */
    public CatalogCursorAdapter(Context context, Cursor c) {

        super(context, c, 0 /* flags */);
    }

    /* Makes a new blank list item view. No data is set (or bound) to the views yet.
       param context app context
      param cursor  The cursor from which to get the data. The cursor is already
        moved to the correct position.
      param parent  The parent to which the new view is attached to
     return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    /*This method binds the pet data (in the current row pointed to by cursor) to the given
      list item layout.
    param view    Existing view, returned earlier by newView() method
    param context app context
     param cursor  The cursor from which to get the data. The cursor is already moved to the
      correct row.*/
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView nameTv = (TextView) view.findViewById(R.id.name);
        TextView desTv = (TextView) view.findViewById(R.id.des);
        TextView priceTv = (TextView) view.findViewById(R.id.price);
        // find the items attribute that we interested in
        int nameColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_NAME);
        int desColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_DESCRIPTION);
        int priceColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_PRICE);
        // read the attribute from the cursor
        String name = cursor.getString(nameColumnIndex);
        String des = cursor.getString(desColumnIndex);
        int priceString = cursor.getInt(priceColumnIndex);
        String price = Integer.toString(priceString);

        nameTv.setText(name);
        desTv.setText(des);
        priceTv.setText(price);

    }
}
