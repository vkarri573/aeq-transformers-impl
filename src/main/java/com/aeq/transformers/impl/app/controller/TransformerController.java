package com.aeq.transformers.impl.app.controller;

import static com.aeq.transformers.impl.app.constants.BattleConstants.*;
import com.aeq.transformers.impl.app.model.Transformer;
import com.aeq.transformers.impl.app.service.TransformerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transformers")
public class TransformerController {
    @Autowired
    private TransformerService transformerService;

    @PostMapping
    public ResponseEntity<String> createTransformer(@Valid @RequestBody Transformer transformer){
        transformerService.createTransformer(transformer);
        return new ResponseEntity<>(STATUS_TRANSFORMER_CREATED, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> updateTransformer(@Valid @RequestBody Transformer transformer) {

        //Long transformerId = transformer.getId();
        transformerService.updateTransformer(transformer);
        return new ResponseEntity<>(STATUS_TRANSFORMER_UPDATED, HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTransformer(@RequestBody Transformer transformer){
        transformerService.deleteTransformer(transformer);
        return new ResponseEntity<>(STATUS_TRANSFORMER_DELETED, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransformerById(@PathVariable("id") Long id) {
        transformerService.deleteTransformerById(id);
        return new ResponseEntity<>(STATUS_TRANSFORMER_DELETED, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Transformer>> getAllTransformers() {
        List<Transformer> transformerList = transformerService.getAllTransformers();
        return new ResponseEntity<>(transformerList, HttpStatus.OK);
    }
}
