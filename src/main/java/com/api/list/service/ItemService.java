package com.api.list.service;

import com.api.list.repository.ItemRepository;
import com.api.list.service.dto.ItemJPQLDTO;
import com.api.list.service.dto.ItemObjectDTO;
import com.api.list.service.dto.ItemTupleDTO;
import com.api.list.service.projection.ItemProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public List<ItemTupleDTO> findWithTupleProjection() {
        return itemRepository.findWithTupleProjection();
    }

    public List<ItemObjectDTO> findWithObjectProjection() {
        return itemRepository.findWithObjectProjection();
    }

    public List<ItemProjection> findWithInterfaceProjection() {
        return itemRepository.findWithInterfaceProjection();
    }

    public List<ItemJPQLDTO> findWithJPQLProjection() {
        return itemRepository.findWithJPQLProjection();
    }
}
