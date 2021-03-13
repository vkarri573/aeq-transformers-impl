package com.aeq.transformers.impl.app.service;

import com.aeq.transformers.impl.app.model.Transformer;
import com.aeq.transformers.impl.app.repository.TransformerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TransformerService {

    @Autowired
    private TransformerRepository transformerRepo;

    public void createTransformer(Transformer transformer){
        transformerRepo.save(transformer);
    }

    public void updateTransformer(Transformer transformer){
        transformerRepo.save(transformer);
    }

    public void deleteTransformer(Transformer transformer){
        transformerRepo.delete(transformer);
    }

    public void deleteTransformerById(Long transformerId){
        transformerRepo.deleteById(transformerId);
    }

    public List<Transformer> getAllTransformers() {
        List<Transformer> transformersList = new ArrayList<>();
        transformerRepo.findAll().forEach(transformer -> transformersList.add(transformer));
        return transformersList;
    }

}
