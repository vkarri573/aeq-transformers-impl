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
    private TransformerService ts;

    @PostMapping
    public void createTransformer(Transformer transformer){
      ts.createTransformer(transformer);
    }

    @PutMapping
    public void updateTransformer(Transformer transformer){
        ts.updateTransformer(transformer);
    }

    @DeleteMapping
    public void deleteTransformer(Transformer transformer){
        ts.deleteTransformer(transformer);
    }

    @DeleteMapping("/{id}")
    public void deleteTransformerById(@PathVariable("id") Long id) {
        ts.deleteTransformerById(id);
    }

    @GetMapping
    public List<Transformer> getAllTransformers() {
        return ts.getAllTransformers();
    }

}
