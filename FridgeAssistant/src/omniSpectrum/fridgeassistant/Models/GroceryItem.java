package omniSpectrum.fridgeassistant.Models;

public class GroceryItem {
	
	int id;
	ItemDefinition myItem;
	int quantityToBuy;
	
	public GroceryItem(){}

	public GroceryItem(int id, ItemDefinition myItem, int quantityToBuy) {
		this.id = id;
		this.myItem = myItem;
		this.quantityToBuy = quantityToBuy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ItemDefinition getMyItem() {
		return myItem;
	}

	public void setMyItem(ItemDefinition myItem) {
		this.myItem = myItem;
	}

	public int getQuantityToBuy() {
		return quantityToBuy;
	}

	public void setQuantityToBuy(int quantityToBuy) {
		this.quantityToBuy = quantityToBuy;
	}
	
	

}
