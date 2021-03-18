package com.aeq.transformers.impl.app.service;

import com.aeq.transformers.impl.app.exception.TransformerNotFoundException;
import com.aeq.transformers.impl.app.model.Transformer;
import com.aeq.transformers.impl.app.repository.TransformerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Provides business logic for Transformer API.
 */
@Service
public class TransformerService {

    @Autowired
    private TransformerRepository transformerRepo;

    /**
     * Creates Transformer.
     *
     * @param transformer contains Transformer details to create.
     * @return created transformer.
     */
    public Transformer createTransformer(Transformer transformer){
        return transformerRepo.save(transformer);
    }

    /**
     * Updates Transformer.
     *
     * @param transformer contains Transformer details to update.
     * @return updated transformer.
     * @throws TransformerNotFoundException if Transformer is not available at database.
     */
    public Transformer updateTransformer(Transformer transformer) {
        if(findTransformerById(transformer.getId()) != null)
           return transformerRepo.save(transformer);
        else
            throw new TransformerNotFoundException("Transformer is not found with id: "+transformer.getId());
    }

    /**
     * Finds a transformer by ID.
     *
     * @param transformerId whose presence to be tested at database.
     * @return Transformer with details retrieved from database.
     * @throws TransformerNotFoundException if Transformer is not available at database.
     */
    public Transformer findTransformerById(Long transformerId) {
        Optional<Transformer> result = transformerRepo.findById(transformerId);
        Transformer transformer = null;
        try {
            transformer = result.get();
        } catch(NoSuchElementException exception) {
            throw new TransformerNotFoundException("Transformer is not found with id: "+transformerId);
        }
        return transformer;
    }

    /**
     * Deletes Transformer.
     *
     * @param transformer which is to be deleted.
     */
    public void deleteTransformer(Transformer transformer) {
        if(findTransformerById(transformer.getId()) != null)
           transformerRepo.delete(transformer);
    }

    /**
     * Deletes transformer by ID.
     *
     * @param transformerId Id of the Transformer which is to be deleted.
     */
    public void deleteTransformerById(Long transformerId){
        if(findTransformerById(transformerId) != null)
           transformerRepo.deleteById(transformerId);
    }

    /**
     * Retrieves all transformers.
     *
     * @return list of transformers available at database.
     */
    public List<Transformer> getAllTransformers() {
        List<Transformer> transformersList = new ArrayList<>();
        transformerRepo.findAll().forEach(transformer -> transformersList.add(transformer));
        return transformersList;
    }

}
