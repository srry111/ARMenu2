package my.edu.tarc.armenu.Model;

/**
 * Created by you on 31/12/2017.
 */

public class Foodlist {
    private int foodid;
    private String name;
    private String foodimage;
    private String fooddesc;
    private double price;
    private double rating;

    public Foodlist(int foodid, String name, String foodimage, String fooddesc, double price, double rating) {
        this.foodid = foodid;
        this.name = name;
        this.foodimage = foodimage;
        this.rating = rating;
        this.price = price;
        this.fooddesc = fooddesc;
    }


    public int getFoodid() {
        return foodid;
    }

    public void setFoodid(int foodid) {
        this.foodid = foodid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoodimage() {
        return foodimage;
    }

    public void setFoodimage(String foodimage) {
        this.foodimage = foodimage;
    }

    public String getFooddesc() {
        return fooddesc;
    }

    public void setFooddesc(String fooddesc) {
        this.fooddesc = fooddesc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}