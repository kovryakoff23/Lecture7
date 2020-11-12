package Product.java;

import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductMain {
    public static void main(String[] arg) {
        final Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://127.0.0.1:5432/Test7", "postgres", "2357")
                .locations("db")
                .load();
        flyway.clean();
        flyway.migrate();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Test7", "postgres", "2357")) {
            System.out.println("Connection Ok.");
            final ProductDAO productDAO = new ProductDAO(connection);

            String fileName = "Products.txt";
            ArrayList<Product> products = (ArrayList<Product>)Product.read(fileName);
            for ( Product product : products){
                productDAO.save(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
