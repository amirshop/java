package com.ecommerce.backend.service.product;

import com.ecommerce.backend.dto.product.CatalogDto;
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

    public List<CatalogDto> getAllCatalogs() {
        List<Catalog> catalogs = catalogRepository.findAll();
        return catalogs.stream()
                .map(catalog -> modelMapper.map(catalog, CatalogDto.class))
                .collect(Collectors.toList());
    }

    public CatalogDto getCatalogById(UUID catalogId) {
        return catalogRepository.findById(catalogId)
                .map(catalog -> modelMapper.map(catalog, CatalogDto.class))
                .orElse(null);
    }

    public CatalogDto createCatalog(CatalogDto catalogRequest) {
        Catalog catalog = modelMapper.map(catalogRequest, Catalog.class);
        Catalog saved = catalogRepository.save(catalog);
        return modelMapper.map(saved, CatalogDto.class);
    }

    public CatalogDto updateCatalog(UUID catalogId, CatalogDto catalogRequest) {
        return catalogRepository.findById(catalogId)
                .map(existing -> {
                    existing.setName(catalogRequest.getName());
                    existing.setLastUpdated(catalogRequest.getLastUpdated());
                    // Optionally update the list of products if provided.
                    Catalog updated = catalogRepository.save(existing);
                    return modelMapper.map(updated, CatalogDto.class);
                })
                .orElse(null);
    }

    public void deleteCatalog(UUID catalogId) {
        catalogRepository.deleteById(catalogId);
    }
}

