package omniSpectrum.fridgeassistant.UI;

import java.util.List;

import omniSpectrum.fridgeassistant.R;
import omniSpectrum.fridgeassistant.Logic.DatabaseHelper;
import omniSpectrum.fridgeassistant.Logic.InventoryArrayAdapter;
import omniSpectrum.fridgeassistant.Models.ItemDefinition;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	
	DatabaseHelper db;	
	ListView inventoryListView;
	ItemDefinition[] inventoryList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		inventoryListView = (ListView) findViewById(R.id.inventoryListView);
		
		populateInventoryListView();
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
		
		db = new DatabaseHelper(this);
		
		List<ItemDefinition> itemList = db.getAllItemDefenitions();
		inventoryList = itemList.toArray(new ItemDefinition[itemList.size()]);
		
		// Create Adapter
		InventoryArrayAdapter adapter = 
				new InventoryArrayAdapter(this, inventoryList);
		
		// Assign Adapter to listView
		inventoryListView.setAdapter(adapter);
		
		// List Item Click
		inventoryListView.setOnItemClickListener(new OnItemClickListener() {			  
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
			    int position, long id) {
				
			    Toast.makeText(getApplicationContext(),
			      "Click ListItem Number " + position, Toast.LENGTH_LONG).show();
			    
				view.showContextMenu();
			}
		}); 
	}
	
	// TODO Change TO NumberPicker Dialog
	@SuppressLint("NewApi")
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
	  
		if (v.getId()==R.id.inventoryListView) {
		    
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
			
			menu.setHeaderTitle(inventoryList[info.position].getName());
			
//			NumberPicker np = (NumberPicker) findViewById(R.id.numberPickerBalance);
//			np.setMaxValue(50);
//			np.setMinValue(0);
//			np.setValue(30);
		    
		    String[] menuItems = {"balance UP", "balance DOWN", "SAVE"};
		    for (int i = 0; i<menuItems.length; i++) {
		      menu.add(Menu.NONE, i, i, menuItems[i]);
		    }
		}
	}
}
