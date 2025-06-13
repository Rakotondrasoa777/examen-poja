package com.exam.hazavao.endpoint.rest.controller.hazavao;

import com.exam.hazavao.endpoint.service.ChatGptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HazavaoController {
    private final ChatGptService chatGptService;

    public HazavaoController(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    @GetMapping("/hazavao")
    public String getDefinition(@RequestParam String teny) {
        return chatGptService.getDefinitionInMalagasy(teny);
    }
}
