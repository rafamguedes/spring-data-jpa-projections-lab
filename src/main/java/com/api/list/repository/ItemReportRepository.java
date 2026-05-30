package com.api.list.repository;

import com.api.list.service.dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.function.Consumer;

@Repository
@RequiredArgsConstructor
public class ItemReportRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public void streamItems(Consumer<ItemDTO> consumer) {

    String sql =
        """
              SELECT
                  i.name AS item_name,
                  i.description AS item_description,
                  i.quantity AS item_quantity,
                  i.buy_price AS item_buy_price,
                  i.sell_price AS item_sell_price,
                  i.created_at AS item_created_at,
                  i.updated_at AS item_updated_at,
                  s.name AS supplier_name,
                  s.description AS supplier_description,
                  s.cnpj AS supplier_cnpj,
                  s.email AS supplier_email,
                  s.phone AS supplier_phone
              FROM items i
              INNER JOIN supplier s ON s.id = i.supplier_id
              WHERE 1 = 1
              ORDER BY i.name
        """;

    MapSqlParameterSource params = new MapSqlParameterSource();

    jdbcTemplate.getJdbcTemplate().setFetchSize(1000);

    jdbcTemplate.query(
        sql,
        params,
        (ResultSet rs) -> {
          ItemDTO dto =
              new ItemDTO(
                  rs.getString("item_name"),
                  rs.getString("item_description"),
                  rs.getInt("item_quantity"),
                  rs.getBigDecimal("item_buy_price"),
                  rs.getBigDecimal("item_sell_price"),
                  rs.getString("supplier_name"),
                  rs.getString("supplier_description"),
                  rs.getString("supplier_cnpj"),
                  rs.getString("supplier_email"),
                  rs.getString("supplier_phone"));

          consumer.accept(dto);
        });
  }
}
