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


@Service
public class TransformerService {

    @Autowired
    private TransformerRepository transformerRepo;

    public void createTransformer(Transformer transformer){
        transformerRepo.save(transformer);
    }

    public void updateTransformer(Transformer transformer){
        if(findTransformerById(transformer.getId()) != null)
           transformerRepo.save(transformer);
    }

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

    public void deleteTransformer(Transformer transformer) {
        if(findTransformerById(transformer.getId()) != null)
           transformerRepo.delete(transformer);
    }

    public void deleteTransformerById(Long transformerId){
        if(findTransformerById(transformerId) != null)
           transformerRepo.deleteById(transformerId);
    }

    public List<Transformer> getAllTransformers() {
        List<Transformer> transformersList = new ArrayList<>();
        transformerRepo.findAll().forEach(transformer -> transformersList.add(transformer));
        return transformersList;
    }

}
