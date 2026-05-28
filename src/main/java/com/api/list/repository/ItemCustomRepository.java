package com.api.list.repository;

import com.api.list.service.dto.ItemDTO;
import com.api.list.service.dto.ItemObjectDTO;
import com.api.list.service.dto.ItemTupleDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemCustomRepository {
    List<ItemTupleDTO> findWithTupleProjection();
    List<ItemObjectDTO> findWithObjectProjection();
    List<ItemDTO> findWithDTOProjection();
}
