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
import com.ecommerce.backend.specification.ProductSpecification;
import com.ecommerce.backend.utils.FilterUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    private final ProductMapper productMapper;

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


    public ResponseDto searchProducts(SearchDto requestDto) {
        Pageable pageable;
        if (requestDto.getSortField() != null && !requestDto.getSortField().isEmpty()) {
            Sort sort = Sort.by("asc".equalsIgnoreCase(requestDto.getSortDirection())
                    ? Sort.Direction.ASC : Sort.Direction.DESC, requestDto.getSortField());
            pageable = PageRequest.of(requestDto.getPage(), requestDto.getSize(), sort);
        } else {
            pageable = PageRequest.of(requestDto.getPage(), requestDto.getSize());
        }

        Specification<Product> spec = Specification.where(null);

        // استخراج فیلدهای مجاز از DTO
        List<String> allowedFields = FilterUtils.getAllowedFilterFields(ProductDto.class);

        if (requestDto.getFilters() != null) {
            for (FilterCriteria filter : requestDto.getFilters()) {
                if (!allowedFields.contains(filter.getCol())) {
                    throw new IllegalArgumentException("فیلد " + filter.getCol() + " معتبر نیست.");
                }
                Specification<Product> filterSpec = ProductSpecification.createSpecificationForFilter(filter);
                if (filterSpec != null) {
                    spec = spec.and(filterSpec);
                }
            }
        }

        Page<Product> productPage = productRepository.findAll(spec, pageable);
        List<ProductDto> productDtos = productPage.getContent().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());

        ResponseDto response = new ResponseDto();
        response.setContent(productDtos);
        response.setPage(productPage.getNumber());
        response.setSize(productPage.getSize());
        response.setTotalElements(productPage.getTotalElements());
        response.setTotalPages(productPage.getTotalPages());

        return response;
    }
}

