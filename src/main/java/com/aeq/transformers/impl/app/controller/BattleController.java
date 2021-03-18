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

/**
 * Servers below Transformers battle requests.
 *
 * 1. Given a list of Transformer IDs, determine the winning team.
 * 2. Conduct game for all transformers, determine the winning team.
 */
@RestController
@RequestMapping("/battle")
public class BattleController {

    @Autowired
    private BattleService battleService;

    /**
     * Servers request to conduct game for selected transformers.
     *
     * @param ids contains list of Transformer IDs to conduct game for.
     * @return Game final result which includes number of batlles, winning team, loosing team,
     *         surviving members, from loosing team etc.
     */
    @GetMapping("/transformers")
    public ResponseEntity<FinalGameResult> conductGameForSelectedTransformers(@RequestParam List<Long> ids) {
        FinalGameResult finalGameResult = battleService.conductGameForSelectedTransformers(ids);
        return new ResponseEntity<>(finalGameResult, HttpStatus.OK);
    }

    /**
     * Servers request to conduct game for all transformers.
     *
     * @return Game final result which includes number of batlles, winning team, loosing team,
     *         surviving members, from loosing team etc.
     */
    @GetMapping("/transformers/all")
    public ResponseEntity<FinalGameResult> conductGameForAllTransformers() {
        FinalGameResult finalGameResult = battleService.conductGameForAllTransformers();
        return new ResponseEntity<>(finalGameResult, HttpStatus.OK);
    }
}
