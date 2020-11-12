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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
@SuppressWarnings("NotNullNullableValidation")
public class MyPostHandler extends AbstractHandler {

    @Override
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        baseRequest.setHandled(true);
        var params = request.getParameterMap();
        if (params.size() > 0) {
            response.setContentType("text/plain");
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Test7", "postgres", "2357")) {
                System.out.println("Connection Ok.");
                final ProductDAO productDAO = new ProductDAO(connection);
                Set s = params.entrySet();
                Iterator it = s.iterator();
                String name= null, organization=null;
                int id=0, quantity=0;
                while (it.hasNext()) {
                    Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) it.next();
                    String[] value = entry.getValue();
                    String key = entry.getKey();
                    switch (key) {
                        case "name": {
                            if (value.length > 0) {
                                name = (value[0]);
                            }
                            break;
                        }
                        case "organization": {
                            if (value.length > 0) {
                                organization = (value[0]);
                            }
                            break;
                        }
                        case "id": {
                            if (value.length > 0) {
                                id = Integer.parseInt(value[0]);
                            }
                            break;
                        }
                        case "quantity": {
                            if (value.length > 0) {
                                quantity = Integer.parseInt(value[0]);
                            }
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                }
                productDAO.save(new Product(id,name,organization,quantity));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            baseRequest.setHandled(true);
        }
    }
}
