package com.api.list.controller;

import com.api.list.service.ItemService;
import com.api.list.service.dto.ItemJPQLDTO;
import com.api.list.service.dto.ItemObjectDTO;
import com.api.list.service.dto.ItemTupleDTO;
import com.api.list.service.projection.ItemProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/tuple")
    public ResponseEntity<List<ItemTupleDTO>> getByTupleProjection() {
        return ResponseEntity.ok().body(itemService.findWithTupleProjection());
    }

    @GetMapping("/object")
    public ResponseEntity<List<ItemObjectDTO>> getByObjectProjection() {
        return ResponseEntity.ok().body(itemService.findWithObjectProjection());
    }

    @GetMapping("/interface")
    public ResponseEntity<List<ItemProjection>> getByInterfaceProjection() {
        return ResponseEntity.ok(itemService.findWithInterfaceProjection());
    }

    @GetMapping("/jpql")
    public ResponseEntity<List<ItemJPQLDTO>> getByJPQLProjection() {
        return ResponseEntity.ok(itemService.findWithJPQLProjection());
    }
}
