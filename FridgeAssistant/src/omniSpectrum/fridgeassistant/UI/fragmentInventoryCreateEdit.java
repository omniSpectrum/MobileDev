package omniSpectrum.fridgeassistant.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import omniSpectrum.fridgeassistant.R;

public class fragmentInventoryCreateEdit extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_inventory_create_edit, 
				container, false);
		return view;
	}
}
