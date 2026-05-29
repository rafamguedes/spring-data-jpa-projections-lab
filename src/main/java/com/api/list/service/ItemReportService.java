package com.api.list.service;

import com.api.list.repository.ItemReportRepository;
import jakarta.servlet.ServletOutputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class ItemReportService {

  private final ItemReportRepository repository;

  public void generateCsv(ServletOutputStream outputStream) throws IOException {

    outputStream.write("Produto;Descrição;Fornecedor\n".getBytes(StandardCharsets.UTF_8));

    repository.streamItems(
        item -> {

          try {
            String line =
                escape(item.getItemName())
                    + ";"
                    + escape(item.getItemDescription())
                    + ";"
                    + escape(item.getSupplierName())
                    + "\n";

            outputStream.write(line.getBytes(StandardCharsets.UTF_8));

          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });

    outputStream.flush();
  }

  private String escape(String value) {
    if (value == null) {
      return "";
    }

    return value.replace(";", ",");
  }
}
