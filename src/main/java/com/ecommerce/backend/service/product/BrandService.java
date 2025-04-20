package com.ecommerce.backend.service.product;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.product.BrandDto;
import com.ecommerce.backend.entity.product.Brand;
import com.ecommerce.backend.entity.product.Tag;
import com.ecommerce.backend.mapper.product.BrandMapper;
import com.ecommerce.backend.repository.product.BrandRepository;
import com.ecommerce.backend.service.BaseService;
import com.ecommerce.backend.specification.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BrandService extends BaseService<Brand, BrandDto> {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public BrandService(BrandRepository brandRepository, BrandMapper brandMapper) {
        super(brandRepository, brandMapper::toDto);
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    public List<BrandDto> getAllBrands() {
        return brandRepository.findAll()
                .stream()
                .map(brandMapper::toDto)
                .collect(Collectors.toList());
    }

    public BrandDto getBrandById(UUID brandId) {
        return brandRepository.findById(brandId)
                .map(brandMapper::toDto)
                .orElse(null);
    }

    public BrandDto createBrand(BrandDto brandDto) {
        Brand brand = brandMapper.toEntity(brandDto);
        brand.setCreatedAt(new Date());
        brand.setUpdatedAt(new Date());
        Brand savedBrand = brandRepository.save(brand);
        return brandMapper.toDto(savedBrand);
    }

    public BrandDto updateBrand(UUID id, BrandDto brandDto) {
        return brandRepository.findById(id)
                .map(brand -> {
                    Optional.ofNullable(brandDto.getName())
                            .filter(name -> !name.isBlank())
                            .ifPresent(brand::setName);
                    Optional.ofNullable(brandDto.getDescription())
                            .filter(description -> !description.isBlank())
                            .ifPresent(brand::setDescription);
                    Optional.ofNullable(brandDto.getCountry())
                            .filter(country -> !country.isBlank())
                            .ifPresent(brand::setCountry);
                    Brand updatedBrand = brandRepository.save(brand);
                    return brandMapper.toDto(updatedBrand);
                })
                .orElse(null);
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

