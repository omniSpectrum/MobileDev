package omniSpectrum.fridgeassistant.Logic;

public class InventoryTransaction {
	int id;
	ItemDefinition myItem;
	String transactionType;
	int quantityType;
	
	public InventoryTransaction(){
		
	}

	public InventoryTransaction(int id, ItemDefinition myItem,
			String transactionType, int quantityType) {
		this.id = id;
		this.myItem = myItem;
		this.transactionType = transactionType;
		this.quantityType = quantityType;
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

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public int getQuantityType() {
		return quantityType;
	}

	public void setQuantityType(int quantityType) {
		this.quantityType = quantityType;
	}
	
	
}
