package omniSpectrum.fridgeassistant.UI;

import java.util.List;

import omniSpectrum.fridgeassistant.Models.ItemDefinition;
import omniSpectrum.fridgeassistant.R;
import omniSpectrum.fridgeassistant.Logic.DatabaseHelper;
import omniSpectrum.fridgeassistant.Models.Category;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class InventoryCreateEdit extends ActionBarActivity {
	
	DatabaseHelper db;
	Spinner spinner;
    EditText etName;
	Button button;
    ArrayAdapter<Category> dataAdapter;
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

        //Checking if Create or Update
        if(extras != null){
            id = extras.getInt("itemId");
            if(id > 0){
                ItemDefinition myItem = db.getSingleItem(id);
                Category myCategory = myItem.getItemCategory();
                etName.setText(myItem.getName(), TextView.BufferType.EDITABLE);
                spinner.setSelection(getIndex(spinner, myCategory));
            }
        }
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
		

        dataAdapter = new ArrayAdapter<Category>(this,
                android.R.layout.simple_spinner_item, categories);

        spinner.setAdapter(dataAdapter);
		
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

    //Handling event onClick
	public void onClick(View v) {
		String name = etName.getText().toString();
        Category category = (Category) spinner.getSelectedItem();

        ItemDefinition newItem;

        //Check if create new or update
        if (id > 0){
            newItem = db.getSingleItem(id);
            newItem.setItemCategory(category);
            newItem.setName(name);
            db.updateItemDefinition(newItem);

        }
        else {
            newItem = new ItemDefinition(category, name);
            db.createItemDefinition(newItem);
        }

        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();

        finish();

	}
}
