package com.ecommerce.backend.service.product;

import com.ecommerce.backend.dto.product.request.CatalogRequestDto;
import com.ecommerce.backend.dto.product.response.CatalogResponseDto;
import com.ecommerce.backend.entity.product.Catalog;
import com.ecommerce.backend.repository.product.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final CatalogRepository catalogRepository;

    private final ModelMapper modelMapper;

    public List<CatalogResponseDto> getAllCatalogs() {
        List<Catalog> catalogs = catalogRepository.findAll();
        return catalogs.stream()
                .map(catalog -> modelMapper.map(catalog, CatalogResponseDto.class))
                .collect(Collectors.toList());
    }

    public CatalogResponseDto getCatalogById(UUID catalogId) {
        return catalogRepository.findById(catalogId)
                .map(catalog -> modelMapper.map(catalog, CatalogResponseDto.class))
                .orElse(null);
    }

    public CatalogResponseDto createCatalog(CatalogRequestDto catalogRequest) {
        Catalog catalog = modelMapper.map(catalogRequest, Catalog.class);
        Catalog saved = catalogRepository.save(catalog);
        return modelMapper.map(saved, CatalogResponseDto.class);
    }

    public CatalogResponseDto updateCatalog(UUID catalogId, CatalogRequestDto catalogRequest) {
        return catalogRepository.findById(catalogId)
                .map(existing -> {
                    existing.setName(catalogRequest.getName());
                    existing.setLastUpdated(catalogRequest.getLastUpdated());
                    // Optionally update the list of products if provided.
                    Catalog updated = catalogRepository.save(existing);
                    return modelMapper.map(updated, CatalogResponseDto.class);
                })
                .orElse(null);
    }

    public void deleteCatalog(UUID catalogId) {
        catalogRepository.deleteById(catalogId);
    }
}

