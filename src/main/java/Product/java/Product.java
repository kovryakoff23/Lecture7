package Product.java;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Product {
    private int id;
    private String name;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", organization='" + organization + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    private String organization;
    private  int quantity;
    private static final Type listProducts = new TypeToken<ArrayList<Product>>(){}.getType();

    public static ArrayList<Product> read(String fileName) {
        try {
            return new Gson().fromJson(new BufferedReader(new FileReader(fileName)), listProducts);
        } catch (
                FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product(int id, String name, String organization, int quantity) {
        this.id = id;
        this.name = name;
        this.organization = organization;
        this.quantity = quantity;
    }
}
