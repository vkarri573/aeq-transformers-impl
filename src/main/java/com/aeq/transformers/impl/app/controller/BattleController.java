package com.aeq.transformers.impl.app.controller;

import com.aeq.transformers.impl.app.model.FinalGameResult;
import com.aeq.transformers.impl.app.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/battles")
public class BattleController {

    @Autowired
    private BattleService battleService;

    @GetMapping
    public FinalGameResult conductGame(@RequestParam List<Long> transformerIds) throws Exception {
        return battleService.conductGame(transformerIds);
    }
}
