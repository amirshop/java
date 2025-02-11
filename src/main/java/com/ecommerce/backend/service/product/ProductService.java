package com.ecommerce.backend.service.product;

import com.ecommerce.backend.dto.product.request.ProductRequestDto;
import com.ecommerce.backend.dto.product.response.ProductResponseDto;
import com.ecommerce.backend.entity.product.Product;
import com.ecommerce.backend.entity.product.ProductCategory;
import com.ecommerce.backend.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductResponseDto> getAllProducts(String name, Long categoryId) {
        List<Product> products;
        // If filtering parameters are provided, use a custom query method.
        if (name != null || categoryId != null) {
            products = productRepository.findByNameContainingAndCategoryId(name, categoryId);
        } else {
            products = productRepository.findAll();
        }
        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .collect(Collectors.toList());
    }

    public ProductResponseDto getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .orElse(null);
    }

    public List<Product> getProductsByCategory(ProductCategory category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public ProductResponseDto createProduct(ProductRequestDto productRequest) {
        Product product = modelMapper.map(productRequest, Product.class);
        Product saved = productRepository.save(product);
        return modelMapper.map(saved, ProductResponseDto.class);
    }

    public ProductResponseDto updateProduct(Long productId, ProductRequestDto productRequest) {
        return productRepository.findById(productId)
                .map(existing -> {
                    existing.setName(productRequest.getName());
                    existing.setDescription(productRequest.getDescription());
                    existing.setPrice(productRequest.getPrice());
                    existing.setAvailableItemCount(productRequest.getAvailableItemCount());
                    // Optionally update the associated category if needed.
                    Product updated = productRepository.save(existing);
                    return modelMapper.map(updated, ProductResponseDto.class);
                })
                .orElse(null);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}

