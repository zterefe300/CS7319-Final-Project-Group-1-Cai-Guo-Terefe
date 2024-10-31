package com.selected.inventory_dashboard.dtovo.dtomapper;

import com.selected.inventory_dashboard.dtovo.res.ItemResponse;
import com.selected.inventory_dashboard.persistence.entity.Item;
import org.mapstruct.factory.Mappers;

public interface ItemDtoMapper {
    ItemDtoMapper INSTANCE = Mappers.getMapper(ItemDtoMapper.class);

    Item toItem(Item itemDto);
    ItemResponse toItoResponse(Item item);
}
