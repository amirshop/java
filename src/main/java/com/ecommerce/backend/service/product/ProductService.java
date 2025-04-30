package com.ecommerce.backend.service.product;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.product.ProductDto;
import com.ecommerce.backend.entity.product.Product;
import com.ecommerce.backend.entity.product.ProductCategory;
import com.ecommerce.backend.entity.product.ProductVariant;
import com.ecommerce.backend.entity.product.Tag;
import com.ecommerce.backend.exception.ResourceAlreadyExistsException;
import com.ecommerce.backend.exception.ResourceNotFoundException;
import com.ecommerce.backend.mapper.product.ProductMapper;
import com.ecommerce.backend.mapper.product.ProductVariantMapper;
import com.ecommerce.backend.repository.product.ProductRepository;
import com.ecommerce.backend.service.BaseService;
import com.ecommerce.backend.specification.GenericSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService extends BaseService<Product, ProductDto> {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductVariantMapper productVariantMapper;
    private final TagService tagService;
    private final ProductCategoryService productCategoryService;
    private final ProductVariantService productVariantService;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper,
                          ProductVariantMapper productVariantMapper, TagService tagService,
                          ProductCategoryService productCategoryService,
                          ProductVariantService productVariantService) {

        super(productRepository, productMapper::toDto);
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productVariantMapper = productVariantMapper;
        this.tagService = tagService;
        this.productCategoryService = productCategoryService;
        this.productVariantService = productVariantService;
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
        checkExistSlug(product.getSlug());
        Product savedProduct = productRepository.save(product);

        if (productRequest.getVariants() != null) {
            //TODO: find better way for save product and productVariant together
            List<ProductVariant> variants = productRequest.getVariants().stream()
                    .map(productVariant -> {
                        ProductVariant v = productVariantMapper.toEntity(productVariant);
                        v.setProduct(savedProduct);
                        return productVariantService.saveVariant(v);
                    })
                    .collect(Collectors.toList());
            savedProduct.setVariants(variants);
        }

        Set<Tag> tags = productRequest.getTags().stream()
                .map(tagService::findById)
                .collect(Collectors.toSet());
        savedProduct.setTags(tags);

        Set<ProductCategory> categories = productRequest.getCategories().stream()
                .map(productCategoryService::findCategoryById)
                .collect(Collectors.toSet());
        savedProduct.setCategories(categories);

        return productMapper.toDto(savedProduct);
    }

    private void checkExistSlug(String slug) {
        if (productRepository.existsBySlug(slug)) {
            throw new ResourceAlreadyExistsException("slug in product already exists");
        }
    }

    @Transactional
    public ProductDto updateProduct(UUID productId, ProductDto productRequest) {
        Product existingProduct = productRepository.findById(productId)
                .map(product -> {
                    Optional.ofNullable(productRequest.getSlug())
                            .filter(slug -> !slug.isBlank())
                            .filter(this::isSlugUnique)
                            .ifPresent(product::setSlug);
                    Optional.ofNullable(productRequest.getName())
                            .filter(name -> !name.isBlank())
                            .ifPresent(product::setName);
                    Optional.ofNullable(productRequest.getDescription())
                                    .filter(description -> !description.isBlank())
                            .ifPresent(product::setDescription);

                    product.getTags().clear();
                    product.getCategories().clear();

                    return productRepository.save(product);
                })
                .orElseThrow(() -> new ResourceNotFoundException("product", "id", productId.toString()));

        Set<Tag> newTags = productRequest.getTags().stream()
                .map(tagService::findById)
                .collect(Collectors.toSet());
        existingProduct.setTags(newTags);

        Set<ProductCategory> newCategories = productRequest.getCategories().stream()
                .map(productCategoryService::findCategoryById)
                .collect(Collectors.toSet());
        existingProduct.setCategories(newCategories);

        productVariantService.deleteVariantsByProductId(productId);

        List<ProductVariant> newVariants = Optional.ofNullable(productRequest.getVariants())
                .orElse(Collections.emptyList())
                .stream()
                .map(productVariantDto -> {
                    ProductVariant productVariant = productVariantMapper.toEntity(productVariantDto);
                    productVariant.setProduct(existingProduct);
                    return productVariantService.saveVariant(productVariant);
                })
                .collect(Collectors.toList());
        existingProduct.setVariants(newVariants);

        return productMapper.toDto(existingProduct);
    }

    private boolean isSlugUnique(String slug) {
        return !productRepository.existsBySlug(slug);
    }

    @Transactional
    public void deleteProduct(UUID productId) {
        checkExistsProduct(productId);
        productVariantService.deleteVariantsByProductId(productId);
        productRepository.deleteById(productId);
    }

    public void checkExistsProduct(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("product", "id", productId.toString());
        }
    }


    @Override
    protected Specification<Product> createSpecification(FilterCriteria filter) {
        return new GenericSpecification<>(filter);
    }

    public ResponseDto searchProducts(SearchDto requestDto) {
        return search(requestDto, ProductDto.class);
    }
}

