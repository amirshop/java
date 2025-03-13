package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.utils.FilterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class BaseService<T, D> {

    // Repository must extend JpaSpecificationExecutor to support Specifications
    protected JpaSpecificationExecutor<T> repository;
    // Mapper to convert entity to its DTO
    protected Function<T, D> mapper;

    public BaseService(JpaSpecificationExecutor<T> repository, Function<T, D> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Each service should implement its own filter specification creation.
     */
    protected abstract Specification<T> createSpecification(FilterCriteria filter);

    /**
     * Performs the search based on the provided SearchDto and allowed DTO fields.
     *
     * @param requestDto the search request
     * @param dtoClass   the DTO class to extract allowed filter fields
     * @return ResponseDto containing search results and pagination details
     */
    public ResponseDto search(SearchDto requestDto, Class<?> dtoClass) {
        Pageable pageable = getPageable(requestDto);
        Specification<T> spec = buildSpecifications(requestDto, dtoClass);
        Page<T> pageResult = repository.findAll(spec, pageable);
        List<D> dtos = pageResult.getContent().stream()
                .map(mapper)
                .collect(Collectors.toList());
        return buildResponse(pageResult, dtos);
    }

    private Pageable getPageable(SearchDto requestDto) {
        if (requestDto.getSortField() != null && !requestDto.getSortField().isEmpty()) {
            Sort sort = Sort.by("asc".equalsIgnoreCase(requestDto.getSortDirection())
                    ? Sort.Direction.ASC : Sort.Direction.DESC, requestDto.getSortField());
            return PageRequest.of(requestDto.getPage(), requestDto.getSize(), sort);
        } else {
            return PageRequest.of(requestDto.getPage(), requestDto.getSize());
        }
    }

    private Specification<T> buildSpecifications(SearchDto requestDto, Class<?> dtoClass) {
        Specification<T> spec = Specification.where(null);
        // Extract allowed filter fields from the DTO
        List<String> allowedFields = FilterUtils.getAllowedFilterFields(dtoClass);
        if (requestDto.getFilters() != null) {
            for (FilterCriteria filter : requestDto.getFilters()) {
                if (!allowedFields.contains(filter.getCol())) {
                    throw new IllegalArgumentException("Field " + filter.getCol() + " is not allowed.");
                }
                Specification<T> filterSpec = createSpecification(filter);
                if (filterSpec != null) {
                    spec = spec.and(filterSpec);
                }
            }
        }
        return spec;
    }

    private ResponseDto buildResponse(Page<T> pageResult, List<D> dtos) {
        ResponseDto response = new ResponseDto();
        response.setContent(dtos);
        response.setPage(pageResult.getNumber());
        response.setSize(pageResult.getSize());
        response.setTotalElements(pageResult.getTotalElements());
        response.setTotalPages(pageResult.getTotalPages());
        return response;
    }
}

