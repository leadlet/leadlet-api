package com.leadlet.service.mapper;

import com.leadlet.domain.Product;
import com.leadlet.service.dto.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DealMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    ProductDTO toDto(Product product);

    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
