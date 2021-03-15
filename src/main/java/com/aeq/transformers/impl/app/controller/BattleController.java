package com.aeq.transformers.impl.app.controller;

import com.aeq.transformers.impl.app.model.FinalGameResult;
import com.aeq.transformers.impl.app.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/battle")
public class BattleController {

    @Autowired
    private BattleService battleService;

    @GetMapping("/transformers")
    public ResponseEntity<FinalGameResult> conductGameForSelectedTransformers(@RequestParam List<Long> ids) {
        FinalGameResult finalGameResult = battleService.conductGameForSelectedTransformers(ids);
        return new ResponseEntity<>(finalGameResult, HttpStatus.OK);
    }

    @GetMapping("/transformers/all")
    public ResponseEntity<FinalGameResult> conductGameForAllTransformers() {
        FinalGameResult finalGameResult = battleService.conductGameForAllTransformers();
        return new ResponseEntity<>(finalGameResult, HttpStatus.OK);
    }
}
