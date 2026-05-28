package com.api.list.repository;

import com.api.list.entity.Item;
import com.api.list.service.dto.ItemJPQLDTO;
import com.api.list.service.projection.ItemProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, ItemCustomRepository {

    @Query(value = """
        SELECT
            i.name AS itemName,
            s.name AS supplierName,
            i.quantity AS quantity
        FROM items i
        INNER JOIN supplier s ON i.supplier_id = s.id
    """, nativeQuery = true)
    List<ItemProjection> findWithInterfaceProjection();

    @Query("""
        SELECT new com.api.list.service.dto.ItemJPQLDTO(
            i.name,
            s.name,
            i.quantity
        )
        FROM Item i
        INNER JOIN i.supplier s
        ORDER BY i.name DESC
    """)
    List<ItemJPQLDTO> findWithJPQLProjection();
}
