package com.selected.inventory_dashboard.dtovo.dtomapper;

import org.mapstruct.factory.Mappers;

public interface ItemDtoMapper {
    ItemDtoMapper INSTANCE = Mappers.getMapper(ItemDtoMapper.class);

//    @Mapping(source = "")
//    Item toItem(Item itemDto);
//    ItemResponse toItoResponse(Item item);
}
