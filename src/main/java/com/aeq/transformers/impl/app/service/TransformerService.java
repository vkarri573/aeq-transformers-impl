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
    private TransformerRepository tr;

    public void createTransformer(Transformer transformer){
        tr.save(transformer);
    }

    public void updateTransformer(Transformer transformer){
        tr.save(transformer);
    }

    public void deleteTransformer(Transformer transformer){
        tr.delete(transformer);
    }

    public void deleteTransformerById(Long transformerId){
        tr.deleteById(transformerId);
    }

    public List<Transformer> getAllTransformers() {
        List<Transformer> transformerList = new ArrayList<>();
        tr.findAll().forEach(transformer -> transformerList.add(transformer));
        return transformerList;
    }

}