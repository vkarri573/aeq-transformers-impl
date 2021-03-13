package com.aeq.transformers.impl.app.controller;

import com.aeq.transformers.impl.app.model.Transformer;
import com.aeq.transformers.impl.app.service.TransformerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transformers")
public class TransformerController {

    @Autowired
    private TransformerService transformerService;

    @PostMapping
    public void createTransformer(@RequestBody Transformer transformer){
        transformerService.createTransformer(transformer);
    }

    @PutMapping
    public void updateTransformer(@RequestBody Transformer transformer){
        transformerService.updateTransformer(transformer);
    }

    @DeleteMapping
    public void deleteTransformer(@RequestBody Transformer transformer){
        transformerService.deleteTransformer(transformer);
    }

    @DeleteMapping("/{id}")
    public void deleteTransformerById(@PathVariable("id") Long id) {
        transformerService.deleteTransformerById(id);
    }

    @GetMapping
    public List<Transformer> getAllTransformers() {
        return transformerService.getAllTransformers();
    }

}
