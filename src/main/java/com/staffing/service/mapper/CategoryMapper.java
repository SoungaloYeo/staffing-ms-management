package com.staffing.service.mapper;

import com.staffing.domain.Category;
import com.staffing.domain.Product;
import com.staffing.service.dto.CategoryDTO;
import com.staffing.service.dto.ProductDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {
    @Mapping(target = "products", source = "products", qualifiedByName = "productTitleSet")
    CategoryDTO toDto(Category s);

    @Mapping(target = "removeProduct", ignore = true)
    Category toEntity(CategoryDTO categoryDTO);

    @Named("productTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    ProductDTO toDtoProductTitle(Product product);

    @Named("productTitleSet")
    default Set<ProductDTO> toDtoProductTitleSet(Set<Product> product) {
        return product.stream().map(this::toDtoProductTitle).collect(Collectors.toSet());
    }
}
