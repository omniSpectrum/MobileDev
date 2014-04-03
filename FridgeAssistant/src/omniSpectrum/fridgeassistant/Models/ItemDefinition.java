package omniSpectrum.fridgeassistant.Models;

public class ItemDefinition {
	
	int id;
	Category itemCategory;
	String name;
	int balance;
	
	public ItemDefinition(){}
	
	public ItemDefinition(Category itemCategory, String name) {
		this.itemCategory = itemCategory;
		this.name = name;
	}
	
	public ItemDefinition(int itemId, Category itemCategory, String name) {
		super();
		this.id = itemId;
		this.itemCategory = itemCategory;
		this.name = name;
	}
	
	public ItemDefinition(int itemId, Category itemCategory, String name, int balance){
		this(itemId, itemCategory, name);
		this.balance = balance;
	}

	public int getItemId() {
		return id;
	}


	public void setItemId(int itemId) {
		this.id = itemId;
	}

	public Category getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(Category itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	
}
