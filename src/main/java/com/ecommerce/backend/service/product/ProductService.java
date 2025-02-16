package com.ecommerce.backend.service.product;

import com.ecommerce.backend.dto.product.request.ProductRequestDto;
import com.ecommerce.backend.dto.product.response.ProductResponseDto;
import com.ecommerce.backend.entity.product.Product;
import com.ecommerce.backend.entity.product.ProductCategory;
import com.ecommerce.backend.entity.product.Tag;
import com.ecommerce.backend.repository.product.ProductRepository;
import com.ecommerce.backend.repository.product.TagRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public List<ProductResponseDto> getAllProducts(String name, UUID categoryId) {
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

    public ProductResponseDto getProductById(UUID productId) {
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

        // Handle Tags
        Set<Tag> tags = new HashSet<>();
        if (productRequest.getTagIds() != null) {
            for (UUID tagId : productRequest.getTagIds()) {
                tagRepository.findById(tagId).ifPresent(tags::add);
            }
        }
        product.setTags(tags);

        Product saved = productRepository.save(product);
        return modelMapper.map(saved, ProductResponseDto.class);
    }

    public ProductResponseDto updateProduct(UUID productId, ProductRequestDto productRequest) {
        return productRepository.findById(productId)
                .map(existing -> {
                    existing.setName(productRequest.getName());
                    existing.setDescription(productRequest.getDescription());
                    existing.setPrice(productRequest.getPrice());
                    existing.setAvailableItemCount(productRequest.getAvailableItemCount());

                    // Update Tags
                    Set<Tag> tags = new HashSet<>();
                    if (productRequest.getTagIds() != null) {
                        for (UUID tagId : productRequest.getTagIds()) {
                            tagRepository.findById(tagId).ifPresent(tags::add);
                        }
                    }
                    existing.setTags(tags);

                    Product updated = productRepository.save(existing);
                    return modelMapper.map(updated, ProductResponseDto.class);
                })
                .orElse(null);
    }

    public void deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
    }
}

