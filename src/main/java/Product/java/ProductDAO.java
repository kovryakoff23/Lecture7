package Product.java;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements DAO<Product> {
    Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Product get(int number) {
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT Id, Name, Organization, Quantity FROM  Product WHERE Id = " + number)) {
                while (rs.next()) {
                    return new Product(rs.getInt("Id"),rs.getString("Name"), rs.getString("Organization"), rs.getInt("Quantity"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        throw new IllegalStateException("Record with Code " + number + "not found");
    }

    @Override
    public List<Product> getAll() {
        final List<Product> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT Id, Name, Organization, Quantity  FROM  Product")) {
                while (rs.next()) {
                    result.add(new Product(rs.getInt("Id"), rs.getString("Name"), rs.getString("Organization"), rs.getInt("Quantity")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    @Override
    public void save(Product entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Product(Id, Name, Organization, Quantity) VALUES(?,?,?,?)")) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getOrganization());
            preparedStatement.setInt(4, entity.getQuantity());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Product entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE  Product SET Name = ?, Organization = ?, Quantity   = ?  WHERE Id  = ?")) {
            preparedStatement.setInt(4, entity.getId());
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getOrganization());
            preparedStatement.setInt(3, entity.getQuantity());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Product entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM  Product WHERE Id = ?")) {
            preparedStatement.setInt(1, entity.getId());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Record with Code = " + entity.getId() + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
