package com.api.list.repository.impl;

import com.api.list.entity.Item;
import com.api.list.repository.ItemCustomRepository;
import com.api.list.service.dto.ItemDTO;
import com.api.list.service.dto.ItemObjectDTO;
import com.api.list.service.dto.ItemTupleDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings("all")
public class ItemCustomRepositoryImpl implements ItemCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ItemTupleDTO> findWithTupleProjection() {
        var sql =
                "SELECT " +
                   "i.name AS itemName, " +
                   "i.description AS itemDescription, " +
                   "s.name AS supplierName, " +
                   "s.cnpj AS supplierCnpj " +
                "FROM items i " +
                "INNER JOIN supplier s ON i.supplier_id = s.id " +
                "WHERE 1=1 " +
                "ORDER BY i.name DESC";

        var query = entityManager.createNativeQuery(sql, Tuple.class);

        List<Tuple> results = query.getResultList();

        return results.stream()
                .map(tuple -> new ItemTupleDTO(
                        tuple.get("itemName", String.class),
                        tuple.get("itemDescription", String.class),
                        tuple.get("supplierName", String.class),
                        tuple.get("supplierCnpj", String.class)
                ))
                .toList();
    }

    @Override
    public List<ItemObjectDTO> findWithObjectProjection() {
        var sql =
                "SELECT " +
                        "i.name AS itemName, " +
                        "i.description AS itemDescription, " +
                        "s.name AS supplierName, " +
                        "s.cnpj AS supplierCnpj " +
                        "FROM items i " +
                        "INNER JOIN supplier s ON i.supplier_id = s.id " +
                        "WHERE 1=1 " +
                        "ORDER BY i.name DESC";

        var query = entityManager.createNativeQuery(sql);

        List<Object[]> results = query.getResultList();

        return results.stream()
                .map(obj -> new ItemObjectDTO(
                        (String) obj[0],
                        (String) obj[1],
                        (String) obj[2]
                ))
                .toList();
    }

    @Override
    public List<ItemDTO> findWithDTOProjection() {
        var sql =
                "SELECT " +
                        "i.name AS itemName, " +
                        "i.description AS itemDescription, " +
                        "s.name AS supplierName " +
                        "FROM items i " +
                        "INNER JOIN supplier s ON i.supplier_id = s.id " +
                        "WHERE 1=1 " +
                        "ORDER BY i.name DESC";

        var query = entityManager.createNativeQuery(sql, Item.ITEM_MAPPING_DTO);

        return query.getResultList();
    }
}
