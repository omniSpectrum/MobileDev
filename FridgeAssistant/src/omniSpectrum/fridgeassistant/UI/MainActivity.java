package omniSpectrum.fridgeassistant.UI;

import java.util.List;

import omniSpectrum.fridgeassistant.R;
import omniSpectrum.fridgeassistant.Logic.DatabaseHelper;
import omniSpectrum.fridgeassistant.Logic.InventoryArrayAdapter;
import omniSpectrum.fridgeassistant.Models.ItemDefinition;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.NumberPicker;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends ActionBarActivity 
	implements NumberPicker.OnValueChangeListener {
	
	DatabaseHelper db;	
	ListView inventoryListView;
	ItemDefinition[] inventoryList;
	static Dialog d;
	String[] menuItems = {"Edit", "Delete", "Cancel"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		inventoryListView = (ListView) findViewById(R.id.inventoryListView);
		db = new DatabaseHelper(this);
		
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
	
	// Number picker interface method
	@Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		// Initially LEFT EMPTY
    }
	
	// NumberPicker Dialog
	public void showNumPickerDialog(int position){

         final Dialog d = new Dialog(MainActivity.this);
         final ItemDefinition myItem = inventoryList[position];
         
         d.setTitle("Balance: " + myItem.getName());
         d.setContentView(R.layout.balance_dialog);
         
         Button b1 = (Button) d.findViewById(R.id.btDone);
         final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPickerBalance);
         
         np.setMaxValue(100);
         np.setMinValue(0);
         np.setValue(myItem.getBalance());
         np.setWrapSelectorWheel(false);
         np.setOnValueChangedListener(this);
         
         b1.setOnClickListener(new OnClickListener(){
		  @Override
		  public void onClick(View v) {
			  Toast.makeText(getApplicationContext(),
					  "Balance: " + myItem.getName() 
							  + " updated to " + String.valueOf(np.getValue()), 
							  Toast.LENGTH_LONG).show();	
			  
			  //Update balance
			  myItem.setBalance(np.getValue());
			  db.updateBalance(myItem);
			  
		      d.dismiss();
		      
		      // Update item in ListView
		      populateInventoryListView(); 
		      // TODO Later, edit single listView item instead of fetching DB
		   }    
		  });
         
         d.show();
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

	  Toast.makeText(getApplicationContext(),
			  "Action:  " + menuItemName + " - " + listItem.getName(), Toast.LENGTH_LONG).show();
	  
	  if (menuItemName.equals("Edit")) {
		  createEditInventory(listItem);
	  }
	  else if (menuItemName.equals("Delete")) {
		  deleteInventoryItem(listItem);
	  }
	  
	  return true;
	}
	
	private void deleteInventoryItem(ItemDefinition item){
		
		//Confirmation dialog
		final Dialog d = new Dialog(MainActivity.this);
		d.setTitle("Delete " + item.getName() + "?");
		
		
		d.show();
		
		// TODO delte from DB, update listView
	}
	private void createEditInventory(ItemDefinition item){
		// TODO Intent to createEdit Activity
	}
}
