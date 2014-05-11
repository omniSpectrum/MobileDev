package omniSpectrum.fridgeassistant.UI;

import java.util.List;

import omniSpectrum.fridgeassistant.R;
import omniSpectrum.fridgeassistant.Logic.DatabaseHelper;
import omniSpectrum.fridgeassistant.Logic.InventoryArrayAdapter;
import omniSpectrum.fridgeassistant.Models.ItemDefinition;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.NumberPicker;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends ActionBarActivity {
	
	DatabaseHelper db;	
	ListView inventoryListView;
	ItemDefinition[] inventoryList;
	String[] menuItems = {"Edit", "Delete", "Cancel"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		inventoryListView = (ListView) findViewById(R.id.inventoryListView);
		db = new DatabaseHelper(this);
		db.onUpgrade(db.getWritableDatabase(), 1, 1); //TODO delete THIS Line before release
		populateInventoryListView();
		//Register Context Menu
		registerForContextMenu(inventoryListView);
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
		if (id == R.id.action_add) {
			createEditInventory(new ItemDefinition());
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Function populates Inventory List view
	 */
	private void populateInventoryListView(){		
		List<ItemDefinition> itemList = db.getAllItemDefenitions();
		inventoryList = itemList.toArray(new ItemDefinition[itemList.size()]);
		
		// Create Adapter
		InventoryArrayAdapter adapter = 
				new InventoryArrayAdapter(this, inventoryList);
		
		// Assign Adapter to listView
		inventoryListView.setAdapter(adapter);
		
		// List Item Short Click Event
		inventoryListView.setOnItemClickListener(new OnItemClickListener() {			  
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
			    int position, long id) {
				
			    // Num Picker Balance dialog call
			    showNumPickerDialog(position);
			}
		}); 
	}
	
	// NumberPicker Dialog
	public void showNumPickerDialog(int position){
		
		//Create dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final ItemDefinition myItem = inventoryList[position];
        
        //Set Up Dialog
		builder.setTitle("Balance: " + myItem.getName());
		builder.setCancelable(true);
		View myView = (LinearLayout) getLayoutInflater().inflate(R.layout.balance_dialog, null);			
		builder.setView(myView);
	
		//Set Up Number Picker
		final NumberPicker np = (NumberPicker) myView.findViewById(R.id.numberPickerBalance);
  
		np.setMaxValue(100);
		np.setMinValue(0);
		np.setValue(myItem.getBalance());
		np.setWrapSelectorWheel(false);
		
		//Button Click dialog event
		builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int id) {
		    	
				  Toast.makeText(getApplicationContext(),
				  "Balance: " + myItem.getName() 
						  + " updated to " + String.valueOf(np.getValue()), 
						  Toast.LENGTH_LONG).show();	
		  
				  //Update balance
				  myItem.setBalance(np.getValue());
				  db.updateBalance(myItem);
		    	
				  dialog.dismiss(); // hide	
		        
				  // Update item in ListView
				  populateInventoryListView(); 
		    }
		});
		AlertDialog dialog = builder.create();
		
		dialog.show();
    }
	
	// Context Menu builder
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
	  
		if (v.getId()==R.id.inventoryListView) {
		    
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;			
			menu.setHeaderTitle(inventoryList[info.position].getName());
		    
		    for (int i = 0; i<menuItems.length; i++) {
		      menu.add(Menu.NONE, i, i, menuItems[i]);
		    }
		}
	}
	
	//Context Menu Edit/Delete Actions
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	  int menuItemIndex = item.getItemId();
	  String menuItemName = menuItems[menuItemIndex];
	  ItemDefinition listItem = inventoryList[info.position];
	  
	  if (menuItemName.equals("Edit")) {
		  createEditInventory(listItem);
	  }
	  else if (menuItemName.equals("Delete")) {
		  deleteInventoryItem(listItem);
	  }
	  
	  return true;
	}
	
	private void deleteInventoryItem(final ItemDefinition item){

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Delete " + item.getName() + "?");
		builder.setCancelable(true);
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int id) {
		    	
		    	//delete from DB, update listView
		    	db.deleteItemDefinition(item.getItemId());
		    	
		    	Toast.makeText(getApplicationContext(),
		  			  "Deleted:  " + item.getName(), Toast.LENGTH_LONG).show();
		    	
		        dialog.dismiss(); // hide	
		        
		        // Update item in ListView
			    populateInventoryListView(); 
		    }
		});
		builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id) {
                // Hide Dialog, Do nothing
				dialog.dismiss();
            }
		});
		AlertDialog dialog = builder.create();
		
		dialog.show();
	}
	private void createEditInventory(ItemDefinition item){
		
		// Intent to createEdit Activity
		Intent tx = new Intent(MainActivity.this, InventoryCreateEdit.class);
		tx.putExtra("itemId", item.getItemId());
	    startActivity(tx);
	}
}
