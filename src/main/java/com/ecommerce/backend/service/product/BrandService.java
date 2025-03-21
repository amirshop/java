package com.ecommerce.backend.service.product;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.product.BrandDto;
import com.ecommerce.backend.entity.product.Brand;
import com.ecommerce.backend.mapper.product.BrandMapper;
import com.ecommerce.backend.repository.product.BrandRepository;
import com.ecommerce.backend.service.BaseService;
import com.ecommerce.backend.specification.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BrandService extends BaseService<Brand, BrandDto> {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public BrandService(BrandRepository brandRepository, BrandMapper brandMapper) {
        super(brandRepository, brandMapper::toDto);
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    public Optional<Brand> findById(UUID id) {
        return brandRepository.findById(id);
    }

    public Brand createBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public Brand updateBrand(UUID id, Brand brandDetails) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));

        brand.setName(brandDetails.getName());
        brand.setCountry(brandDetails.getCountry());
        brand.setDescription(brandDetails.getDescription());
        return brandRepository.save(brand);
    }

    public void deleteBrand(UUID id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));
        brandRepository.delete(brand);
    }

    @Override
    protected Specification<Brand> createSpecification(FilterCriteria filter) {
        return new GenericSpecification<>(filter);
    }

    public ResponseDto searchBrands(SearchDto requestDto) {
        return search(requestDto, BrandDto.class);
    }
}

