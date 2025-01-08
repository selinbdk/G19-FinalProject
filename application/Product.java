package application;

public class Product {
	private String name;
	private double price;
	private int stockAmount;
	private String imageUrl;
	

	public Product(String name, double price, int stockAmount, String imageUrl) {
		this.name = name;
        this.price = price;
        this.stockAmount = stockAmount;
        this.imageUrl = imageUrl;
		

	}
	
	
	public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public int getStockAmount() {
        return this.stockAmount;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public boolean checkStock(int enteredStock) {
        return this.stockAmount >= enteredStock;
    }
    
    public void setStock(int stock) {
        this.stockAmount=stock;
    }


    public void reduceStock(int enteredStock) {
        this.stockAmount -= enteredStock;
    }

    
    
    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
