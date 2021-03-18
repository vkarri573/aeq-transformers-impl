package com.aeq.transformers.impl.app.controller;

import static com.aeq.transformers.impl.app.constants.BattleConstants.*;
import com.aeq.transformers.impl.app.model.Transformer;
import com.aeq.transformers.impl.app.service.TransformerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Serves below Transformer CRUD operation requests.
 *
 * Create a Transformer
 * Update a Transformer
 * Delete a transformer
 * List transformers
 *
 */
@RestController
@RequestMapping("/transformers")
public class TransformerController {
    @Autowired
    private TransformerService transformerService;

    /**
     * Serves create Transformer request.
     *
     * @param transformer contains request payload with Transformer details to create.
     * @return 'Created' message if the transformer is created successfully.
     * @throws Exception when input validation fails.
     */
    @PostMapping
    public ResponseEntity<String> createTransformer(@Valid @RequestBody Transformer transformer){
        transformerService.createTransformer(transformer);
        return new ResponseEntity<>(STATUS_TRANSFORMER_CREATED, HttpStatus.CREATED);
    }

    /**
     * Servers update Transformer request.
     *
     * @param transformer contains request payload with Transformer details to update.
     * @return 'Updated' message if the transformer is updated successfully.
     * @throws com.aeq.transformers.impl.app.exception.TransformerNotFoundException
     *         if the transformer doesn't exist in the database.
     * @throws Exception when input validation fails.
     */
    @PutMapping
    public ResponseEntity<String> updateTransformer(@Valid @RequestBody Transformer transformer) {
        validateTransformerId(transformer.getId());
        transformerService.updateTransformer(transformer);
        return new ResponseEntity<>(STATUS_TRANSFORMER_UPDATED, HttpStatus.ACCEPTED);
    }

    /**
     * Servers delete Transformer request.
     *
     * @param transformer contains request payload with Transformer details to delete.
     * @return 'Deleted' message if the transformer is deleted successfully.
     * @throws com.aeq.transformers.impl.app.exception.TransformerNotFoundException
     *         if the transformer doesn't exist in the database.
     */
    @DeleteMapping
    public ResponseEntity<String> deleteTransformer(@RequestBody Transformer transformer){
        validateTransformerId(transformer.getId());
        transformerService.deleteTransformer(transformer);
        return new ResponseEntity<>(STATUS_TRANSFORMER_DELETED, HttpStatus.OK);
    }

    /**
     * Servers delete Transformer by ID request.
     *
     * @param id Transformer id to delete.
     * @return 'Deleted' message if the transformer is deleted successfully.
     * @throws com.aeq.transformers.impl.app.exception.TransformerNotFoundException
     *         if the transformer doesn't exist in the database.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransformerById(@PathVariable("id") @NotNull Long id) {
        transformerService.deleteTransformerById(id);
        return new ResponseEntity<>(STATUS_TRANSFORMER_DELETED, HttpStatus.OK);
    }

    /**
     * Servers Get Transformers request. Returns all Transformers present in database.
     *
     * @return List of transformers exist in database.
     */
    @GetMapping
    public ResponseEntity<List<Transformer>> getAllTransformers() {
        List<Transformer> transformerList = transformerService.getAllTransformers();
        return new ResponseEntity<>(transformerList, HttpStatus.OK);
    }

    /**
     * Validates Transformer ID for NOT NULL.
     *
     * @param transformerId which holds ID.
     * @throws Exception if the ID is NULL.
     */
    private void validateTransformerId(@NotNull(message = "Transformer Id is required") Long transformerId) {}
}
