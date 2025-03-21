package com.ecommerce.backend.service.customer;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.customer.WishlistItemDto;
import com.ecommerce.backend.entity.customer.WishlistItem;
import com.ecommerce.backend.exception.ResourceNotFoundException;
import com.ecommerce.backend.mapper.customer.WishlistItemMapper;
import com.ecommerce.backend.repository.customer.WishlistItemRepository;
import com.ecommerce.backend.service.BaseService;
import com.ecommerce.backend.specification.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WishlistItemService extends BaseService<WishlistItem, WishlistItemDto> {

    private final WishlistItemRepository wishlistItemRepository;
    private final WishlistItemMapper wishlistItemMapper;

    public WishlistItemService(WishlistItemRepository wishlistItemRepository,
                               WishlistItemMapper wishlistItemMapper) {
        super(wishlistItemRepository, wishlistItemMapper::toDto);
        this.wishlistItemRepository = wishlistItemRepository;
        this.wishlistItemMapper = wishlistItemMapper;
    }

    public List<WishlistItemDto> getAllWishlistItems() {
        List<WishlistItem> wishlistItems = wishlistItemRepository.findAll();
        return wishlistItems.stream()
                .map(wishlistItemMapper::toDto)
                .collect(Collectors.toList());
    }

    public WishlistItemDto getWishlistItemById(UUID id) {
        return wishlistItemRepository.findById(id)
                .map(wishlistItemMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("wishlistItem", "id", id.toString()));
    }

    public WishlistItemDto createWishlistItem(WishlistItemDto request) {
        WishlistItem wishlistItem = wishlistItemMapper.toEntity(request);
        wishlistItem.setAddedAt(new Date());
        WishlistItem savedWishlistItem = wishlistItemRepository.save(wishlistItem);
        return wishlistItemMapper.toDto(savedWishlistItem);
    }

    public WishlistItemDto updateWishlistItem(UUID id, WishlistItemDto itemDetails) {
        return wishlistItemRepository.findById(id).map(item -> {
//            item.setCustomer(itemDetails.getCustomer());
//            item.setProduct(itemDetails.getProduct());
            item.setAddedAt(new Date());
            WishlistItem updated = wishlistItemRepository.save(item);
            return wishlistItemMapper.toDto(updated);
        }).orElseThrow(() -> new RuntimeException("WishlistItem not found with id " + id));
    }

    public void deleteWishlistItem(UUID id) {
        wishlistItemRepository.deleteById(id);
    }

    @Override
    protected Specification<WishlistItem> createSpecification(FilterCriteria filter) {
        return new GenericSpecification<>(filter);
    }

    public ResponseDto searchWishlistItems(SearchDto requestDto) {
        return search(requestDto, WishlistItemDto.class);
    }
}

