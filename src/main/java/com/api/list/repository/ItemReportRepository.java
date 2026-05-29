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
                  s.name AS supplier_name
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
                  rs.getString("supplier_name"));

          consumer.accept(dto);
        });
  }
}
