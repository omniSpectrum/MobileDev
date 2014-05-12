package omniSpectrum.fridgeassistant.UI;

import java.util.ArrayList;
import java.util.List;

import omniSpectrum.fridgeassistant.R;
import omniSpectrum.fridgeassistant.R.id;
import omniSpectrum.fridgeassistant.R.layout;
import omniSpectrum.fridgeassistant.R.menu;
import omniSpectrum.fridgeassistant.Logic.DatabaseHelper;
import omniSpectrum.fridgeassistant.Models.Category;
import omniSpectrum.fridgeassistant.Models.ItemDefinition;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Build;

public class InventoryCreateEdit extends ActionBarActivity {
	
	DatabaseHelper db;
	Spinner spinner;
	Button button;
	EditText etName;
	int id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory_create_edit);
		
		//Retrieving Id from main activity
        Bundle extras = getIntent().getExtras();

		//Calling database
		db = new DatabaseHelper(this);

        //Finding elements in the fragment
		spinner = (Spinner)findViewById(R.id.spCategory);
        etName = (EditText)findViewById(R.id.etName);

        //Populate spinner with data from DB
        loadspinner();

        if(extras != null){
            id = extras.getInt("itemId");
            ItemDefinition myItem = db.getSingleItem(id);
            Category myCategory = myItem.getItemCategory();
            etName.setText(myItem.getName());
            Toast.makeText(this, myItem.getItemCategory().toString(), Toast.LENGTH_SHORT).show();
            spinner.setSelection(getIndex(spinner, myCategory));

        }
	
//		
//		if (savedInstanceState == null) {
//			getSupportFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inventory_create_edit, menu);
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
	
	//Method to populate Spinner
	private void loadspinner() {
		List<Category> categories = db.getAllCategories();
		
		ArrayAdapter<Category> dataAdapter = new ArrayAdapter<Category>(this, 
				android.R.layout.simple_spinner_item, categories);
		
		spinner.setAdapter(dataAdapter);
		
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Toast.makeText(InventoryCreateEdit.this, "Hello", Toast.LENGTH_SHORT).show();
        String name = etName.getText().toString();
        Category category = (Category) spinner.getSelectedItem();

        ItemDefinition newItem = new ItemDefinition(category,name);

        if (newItem != null)
        {
            Toast.makeText(this, "New item is not null", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, category.toString(), Toast.LENGTH_SHORT).show();
            long dbItemDefinition = db.createItemDefinition(newItem);
            finish();

            //Toast.makeText(this, (int) dbItemDefinition, Toast.LENGTH_SHORT);
        }
	}
	
    private int getIndex(Spinner spinner, Category myCategory){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myCategory)){
                index = i;
            }
        }
        return index;
    }

//	/**
//	 * A placeholder fragment containing a simple view.
//	 */
//	public static class PlaceholderFragment extends Fragment {
//
//		public PlaceholderFragment() {
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			View rootView = inflater.inflate(
//					R.layout.fragment_inventory_create_edit, container, false);
//			return rootView;
//		}
//	}

}
