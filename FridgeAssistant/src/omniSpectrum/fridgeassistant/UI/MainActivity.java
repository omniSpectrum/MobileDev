package omniSpectrum.fridgeassistant.UI;

import java.util.List;

import omniSpectrum.fridgeassistant.R;
import omniSpectrum.fridgeassistant.Logic.DatabaseHelper;
import omniSpectrum.fridgeassistant.Logic.InventoryArrayAdapter;
import omniSpectrum.fridgeassistant.Models.ItemDefinition;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
	
	DatabaseHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		populateInventoryListView();
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Function populates Inventory List view
	 */
	private void populateInventoryListView(){
		
		db = new DatabaseHelper(this);
		db.populateTestData();
		
		List<ItemDefinition> itemList = db.getAllItemDefenitions();
		ItemDefinition[] myDummyList = itemList.toArray(new ItemDefinition[itemList.size()]);
		
		// Find list view layout
		ListView inventoryListView = 
				(ListView) findViewById(R.id.inventoryListView);
		
		// Create Adapter
		InventoryArrayAdapter adapter = 
				new InventoryArrayAdapter(this, myDummyList);
		
		// Assign Adapter to listView
		inventoryListView.setAdapter(adapter);
	}
}
