package com.ecommerce.backend.service.product;

import com.ecommerce.backend.entity.product.Catalog;
import com.ecommerce.backend.repository.product.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService {
    private final CatalogRepository catalogRepository;
    public List<Catalog> getAllCatalogs() { return catalogRepository.findAll(); }
}
