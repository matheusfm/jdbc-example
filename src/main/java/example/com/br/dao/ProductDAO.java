package example.com.br.dao;

import example.com.br.model.Product;
import example.com.br.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author matheus
 */
public class ProductDAO {
    private Connection connection;

    public ProductDAO() throws Exception {
        this.connection = ConnectionFactory.getConnection();
        System.out.println("Connected successfully");
    }

    /**
     * @param product
     * @return inserted product id
     */
    public Integer insertProduct(Product product) {
        PreparedStatement statement = null;
        ResultSet generatedKeys;
        String sql = "insert into product (name, description, price) values (?, ?, ?);";
        try {
            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.executeUpdate();
            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                return generatedKeys.getInt("id");
            return null;
        } catch (Exception e) {
            System.err.println("Error inserting product: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            closeConnection(statement, null);
        }
    }

    /**
     * @param product with id
     * @return true if the product has been updated
     */
    public boolean updateProduct(Product product) {
        if (product.getId() == null)
            throw new IllegalArgumentException("Product without id");
        PreparedStatement statement = null;
        String sql = "update product set name = ?, description = ?, price = ? where id = ?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getId());
            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            System.err.println("Error updating product: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection(statement, null);
        }
        return false;
    }

    /**
     * @param productId
     * @return product
     */
    public Product getProduct(Integer productId) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Product product = null;
        String sql = "select * from product where id = ?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, productId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price")
                );
            }
        } catch (Exception e) {
            System.err.println("Error getting product: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection(statement, resultSet);
        }
        return product;
    }

    /**
     * @return list of all products
     */
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<Product>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "select * from product;";
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                products.add(new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price")
                ));
            }
        } catch (Exception e) {
            System.err.println("Error listing products: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection(statement, resultSet);
        }
        return products;
    }

    /**
     * @param productId
     * @return true if the product has been deleted
     */
    public boolean deleteProduct(Integer productId) {
        PreparedStatement statement = null;
        String sql = "delete from product where id = ?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, productId);
            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            System.err.println("Error deleting product: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection(statement, null);
        }
        return false;
    }

    private void closeConnection(PreparedStatement statement, ResultSet resultSet) {
        try {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        } catch (Exception e) {
            System.err.println("Error closing connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
