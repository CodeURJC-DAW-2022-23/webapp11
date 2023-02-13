public class Product {
    private String name;
    private String description;
    private String image;
    private String price;
    private String tags;
    private String brand;
    private int stock;
    private String url;

    public Product(String name, String description, String image, String price, String tags, String brand, int stock, String url) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.tags = tags;
        this.brand = brand;
        this.stock = stock;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getPrice() {
        return price;
    }

    public String getTags() {
        return tags;
    }

    public String getBrand() {
        return brand;
    }

    public int getStock() {
        return stock;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
