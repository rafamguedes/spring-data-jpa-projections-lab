package com.api.list.controller;

import com.api.list.service.ItemReportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ItemReportController {

  private final ItemReportService service;

  @GetMapping("/items")
  public void exportItems(HttpServletResponse response) throws Exception {

    response.setContentType("text/csv");

    response.setHeader("Content-Disposition", "attachment; filename=items.csv");

    service.generateCsv(response.getOutputStream());
  }
}
