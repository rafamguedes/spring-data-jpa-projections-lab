package com.api.list.service;

import com.api.list.repository.ItemReportRepository;
import jakarta.servlet.ServletOutputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemReportService {

  private final ItemReportRepository repository;

  @Transactional(readOnly = true)
  public void generateCsv(ServletOutputStream outputStream) throws IOException {

    AtomicLong counter = new AtomicLong();

    // BOM UTF-8 para Excel
    outputStream.write(new byte[] {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});

    try (BufferedWriter writer =
        new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {

      writer.write(
          "Produto;"
              + "Descrição;"
              + "Quantidade;"
              + "Preço Compra;"
              + "Preço Venda;"
              + "Data Criação;"
              + "Data Atualização;"
              + "Fornecedor;"
              + "Descrição Fornecedor;"
              + "CNPJ;"
              + "E-mail;"
              + "Telefone\n");

      repository.streamItems(
          item -> {
            long current = counter.incrementAndGet();

            if (current % 10000 == 0) {
              log.info("Processados: {} | Memória: {} MB", current, usedMemoryMB());
            }

            try {

              writer.write(escape(item.getItemName()));
              writer.write(';');

              writer.write(escape(item.getItemDescription()));
              writer.write(';');

              writer.write(String.valueOf(item.getItemQuantity()));
              writer.write(';');

              writer.write(String.valueOf(item.getItemBuyPrice()));
              writer.write(';');

              writer.write(String.valueOf(item.getItemSellPrice()));
              writer.write(';');

              writer.write(escape(item.getSupplierName()));
              writer.write(';');

              writer.write(escape(item.getSupplierDescription()));
              writer.write(';');

              writer.write(escape(item.getSupplierCnpj()));
              writer.write(';');

              writer.write(escape(item.getSupplierEmail()));
              writer.write(';');

              writer.write(escape(item.getSupplierPhone()));

              writer.newLine();

            } catch (IOException e) {
              throw new RuntimeException(e);
            }
          });

      writer.flush();
    }
  }

  private String escape(String value) {
    if (value == null) {
      return "";
    }

    return value.replace(";", ",");
  }

  private long usedMemoryMB() {
    Runtime runtime = Runtime.getRuntime();

    return (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
  }
}
