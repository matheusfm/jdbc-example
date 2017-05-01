package example.com.br;

import example.com.br.dao.ProductDAO;
import example.com.br.model.Product;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author matheus
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDAOTest {
    private ProductDAO mProductDAO;
    private static Product mProduct;
    private static Integer mProductId;

    @Before
    public void setUp() throws Exception {
        this.mProductDAO = new ProductDAO();
        this.mProduct = new Product(mProductId, "Product Name Test", "Product Description Test", 10.90);
    }

    /**
     * insert product test
     */
    @Test
    public void test1() {
        Integer productId = mProductDAO.insertProduct(mProduct);
        assertTrue(productId != null);
        this.mProductId = productId;
    }

    /**
     * select product test
     */
    @Test
    public void test2() {
        Product product = mProductDAO.getProduct(mProductId);
        assertEquals(mProduct.getName(), product.getName());
        assertEquals(mProduct.getDescription(), product.getDescription());
        assertEquals(mProduct.getPrice(), product.getPrice());
    }

    /**
     * select all products test
     */
    @Test
    public void test3() {
        List<Product> products = mProductDAO.getProducts();
        for (Product product : products) {
            if (product.getId() == mProductId) {
                assertEquals(mProduct.getName(), product.getName());
                assertEquals(mProduct.getDescription(), product.getDescription());
                assertEquals(mProduct.getPrice(), product.getPrice());
            }
        }
    }

    /**
     * update product test
     */
    @Test
    public void test4() {
        assertEquals(true, mProductDAO.updateProduct(mProduct));
    }

    /**
     * delete product test
     */
    @Test
    public void test5() {
        assertEquals(true, mProductDAO.deleteProduct(mProductId));
    }
}
