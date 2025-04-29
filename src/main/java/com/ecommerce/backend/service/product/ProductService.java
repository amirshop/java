package com.ecommerce.backend.service.product;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.product.ProductDto;
import com.ecommerce.backend.entity.product.Product;
import com.ecommerce.backend.entity.product.ProductVariant;
import com.ecommerce.backend.entity.product.Tag;
import com.ecommerce.backend.exception.ResourceAlreadyExistsException;
import com.ecommerce.backend.exception.ResourceNotFoundException;
import com.ecommerce.backend.mapper.product.ProductMapper;
import com.ecommerce.backend.mapper.product.ProductVariantMapper;
import com.ecommerce.backend.repository.product.ProductRepository;
import com.ecommerce.backend.repository.product.ProductVariantRepository;
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
    private final ProductVariantRepository productVariantRepository;
    private final TagService tagService;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper,
                          ProductVariantMapper productVariantMapper, ProductVariantRepository productVariantRepository, TagService tagService) {
        super(productRepository, productMapper::toDto);
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productVariantMapper = productVariantMapper;
        this.productVariantRepository = productVariantRepository;
        this.tagService = tagService;
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
                        return productVariantRepository.save(v);
                    })
                    .collect(Collectors.toList());
            savedProduct.setVariants(variants);
        }

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

                    // Update Tags
                    Set<Tag> tags = new HashSet<>();
                    if (productRequest.getTags() != null) {
                        for (UUID tagId : productRequest.getTags()) {
                            Tag tag = tagService.findById(tagId);
                            tags.add(tag);
                        }
                    }
                    product.setTags(tags);

                    return productRepository.save(product);
                }).orElseThrow(() -> new ResourceNotFoundException("product", "id", productId.toString()));

        productVariantRepository.deleteAllByProductId(productId);

        List<ProductVariant> newVariants = Optional.ofNullable(productRequest.getVariants())
                .orElse(Collections.emptyList())
                .stream()
                .map(productVariantDto -> {
                    ProductVariant productVariant = productVariantMapper.toEntity(productVariantDto);
                    productVariant.setProduct(existingProduct);
                    return productVariantRepository.save(productVariant);
                })
                .collect(Collectors.toList());
        existingProduct.setVariants(newVariants);

        return productMapper.toDto(existingProduct);
    }

    private boolean isSlugUnique(String slug) {
        return !productRepository.existsBySlug(slug);
    }


//    public ProductDTO update(UUID id, ProductDTO dto) {
//        Product existing = productRepo.findById(id)
//                .orElseThrow(() -> new NoSuchElementException("Product not found"));
//
//        // update simple fields
//        existing.setName(dto.getName());
//        existing.setSlug(dto.getSlug());
//        existing.setDescription(dto.getDescription());
//        productRepo.save(existing);
//
//        // 3) delete old variants
//        variantRepo.deleteAllByProduct_Id(id);
//
//        // 4) save new variants
//        List<ProductVariant> newVariants = Optional.ofNullable(dto.getVariants())
//                .orElse(Collections.emptyList())
//                .stream()
//                .map(vDto -> {
//                    ProductVariant v = variantMapper.toProductVariant(vDto);
//                    v.setProduct(existing);
//                    return variantRepo.save(v);
//                })
//                .collect(Collectors.toList());
//        existing.setVariants(newVariants);
//
//        return productMapper.toProductDTO(existing);
//    }


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

