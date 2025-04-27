package com.ecommerce.backend.service.product;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.product.ProductDto;
import com.ecommerce.backend.dto.product.ProductVariantDto;
import com.ecommerce.backend.entity.product.Product;
import com.ecommerce.backend.entity.product.ProductVariant;
import com.ecommerce.backend.mapper.product.ProductMapper;
import com.ecommerce.backend.mapper.product.ProductVariantMapper;
import com.ecommerce.backend.repository.product.ProductRepository;
import com.ecommerce.backend.service.BaseService;
import com.ecommerce.backend.specification.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService extends BaseService<Product, ProductDto> {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductVariantMapper productVariantMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper,
                          ProductVariantMapper productVariantMapper) {
        super(productRepository, productMapper::toDto);
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productVariantMapper = productVariantMapper;
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(UUID productId) {
        return productRepository.findById(productId)
                .map(productMapper::toDto)
                .orElse(null);
    }

    public ProductDto createProduct(ProductDto productRequest) {
        Product product = productMapper.toEntity(productRequest);

        // Handle nested variant relationships
        if (product.getVariants() != null) {
            product.getVariants().forEach(variant -> {
                variant.setProduct(product);
            });
        }

//        // Handle Tags
//        Set<Tag> tags = new HashSet<>();
//        if (productRequest.getTags() != null) {
//            for (TagDto tag : productRequest.getTags()) {
//                tagRepository.findById(tag.getId()).ifPresent(tags::add);
//            }
//        }
//        product.setTags(tags);

        Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }

    public ProductDto updateProduct(UUID productId, ProductDto productRequest) {
        return productRepository.findById(productId)
                .map(existingProduct -> {
                    existingProduct.setName(productRequest.getName());
                    existingProduct.setDescription(productRequest.getDescription());

                    // Update variants: For simplicity, we remove all old variants and add new ones.
                    // In a real-world scenario, you might want to perform a diff and update instead.
                    existingProduct.getVariants().clear();
                    if (productRequest.getVariants() != null) {
                        for (ProductVariantDto variantDTO : productRequest.getVariants()) {
                            ProductVariant variant = productVariantMapper.toEntity(variantDTO);
                            variant.setProduct(existingProduct);
                            existingProduct.getVariants().add(variant);
                        }
                    }

//                    // Update Tags
//                    Set<Tag> tags = new HashSet<>();
//                    if (productRequest.getTags() != null) {
//                        for (TagDto tag : productRequest.getTags()) {
//                            tagRepository.findById(tag.getId()).ifPresent(tags::add);
//                        }
//                    }
//                    existingProduct.setTags(tags);

                    Product updated = productRepository.save(existingProduct);
                    return productMapper.toDto(updated);
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

    public ResponseDto searchProducts(SearchDto requestDto) {
        return search(requestDto, ProductDto.class);
    }
}

