package Login;

import Product.java.Product;
import Product.java.ProductDAO;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@SuppressWarnings("NotNullNullableValidation")
public final class MyGetHandler extends AbstractHandler {
    @Override
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        baseRequest.setHandled(true);
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Test7", "postgres", "2357")) {
            System.out.println("Connection Ok.");
            final ProductDAO productDAO = new ProductDAO(connection);

            List<Product> products = productDAO.getAll();
            for (Product product : products) {
                response.getWriter().println(product.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        baseRequest.setHandled(true);
    }
}