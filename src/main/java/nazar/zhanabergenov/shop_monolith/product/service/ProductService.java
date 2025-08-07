package nazar.zhanabergenov.shop_monolith.product.service;

import lombok.RequiredArgsConstructor;
import nazar.zhanabergenov.shop_monolith.order.exception.NotFoundException;
import nazar.zhanabergenov.shop_monolith.product.dto.ProductRequest;
import nazar.zhanabergenov.shop_monolith.product.entity.Product;
import nazar.zhanabergenov.shop_monolith.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Продукт с ID " + id + " не найден"));
    }

    @Transactional
    public Product createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setPrice(productRequest.price());
        product.setStockQuantity(productRequest.stockQuantity());
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, ProductRequest productRequest) {
        Product product = getProductById(id);
        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setPrice(productRequest.price());
        product.setStockQuantity(productRequest.stockQuantity());
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Продукт с ID " + id + " не найден");
        }
        productRepository.deleteById(id);
    }
}
