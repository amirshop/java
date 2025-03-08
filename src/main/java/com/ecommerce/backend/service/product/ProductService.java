package com.ecommerce.backend.service.product;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.product.ProductDto;
import com.ecommerce.backend.dto.product.TagDto;
import com.ecommerce.backend.entity.product.Product;
import com.ecommerce.backend.entity.product.ProductCategory;
import com.ecommerce.backend.entity.product.Tag;
import com.ecommerce.backend.mapper.ProductMapper;
import com.ecommerce.backend.repository.product.ProductRepository;
import com.ecommerce.backend.repository.product.TagRepository;
import com.ecommerce.backend.service.BaseService;
import com.ecommerce.backend.specification.GenericSpecification;
import com.ecommerce.backend.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService extends BaseService<Product, ProductDto> {

    private final ProductRepository productRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public List<ProductDto> getAllProducts(String name, UUID categoryId) {
        List<Product> products;
        // If filtering parameters are provided, use a custom query method.
        if (name != null || categoryId != null) {
            products = productRepository.findByNameContainingAndCategoryId(name, categoryId);
        } else {
            products = productRepository.findAll();
        }
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(UUID productId) {
        return productRepository.findById(productId)
                .map(product -> modelMapper.map(product, ProductDto.class))
                .orElse(null);
    }

    public List<Product> getProductsByCategory(ProductCategory category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public ProductDto createProduct(ProductDto productRequest) {
        Product product = modelMapper.map(productRequest, Product.class);

        // Handle Tags
        Set<Tag> tags = new HashSet<>();
        if (productRequest.getTags() != null) {
            for (TagDto tag : productRequest.getTags()) {
                tagRepository.findById(tag.getId()).ifPresent(tags::add);
            }
        }
        product.setTags(tags);

        Product saved = productRepository.save(product);
        return modelMapper.map(saved, ProductDto.class);
    }

    public ProductDto updateProduct(UUID productId, ProductDto productRequest) {
        return productRepository.findById(productId)
                .map(existing -> {
                    existing.setName(productRequest.getName());
                    existing.setDescription(productRequest.getDescription());
                    existing.setPrice(productRequest.getPrice());
                    existing.setAvailableItemCount(productRequest.getAvailableItemCount());

                    // Update Tags
                    Set<Tag> tags = new HashSet<>();
                    if (productRequest.getTags() != null) {
                        for (TagDto tag : productRequest.getTags()) {
                            tagRepository.findById(tag.getId()).ifPresent(tags::add);
                        }
                    }
                    existing.setTags(tags);

                    Product updated = productRepository.save(existing);
                    return modelMapper.map(updated, ProductDto.class);
                })
                .orElse(null);
    }

    public void deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
    }


    @Override
    protected Specification<Product> createSpecification(FilterCriteria filter) {
        return new GenericSpecification<>(filter);
    }

    // Expose a method that calls the generic search functionality
    public ResponseDto searchProducts(SearchDto requestDto) {
        return search(requestDto, ProductDto.class);
    }
}

