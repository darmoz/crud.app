package com.crud.tasks.controller;

import com.crud.tasks.domain.dto.CreateTrelloCardDto;
import com.crud.tasks.domain.dto.TrelloBoardDto;
import com.crud.tasks.domain.dto.TrelloCardDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/trello")
@CrossOrigin("*")
public class TrelloController {

    @Autowired
    private TrelloFacade trelloFacade;

    @RequestMapping(method = RequestMethod.GET, value = "/boards")
    public List<TrelloBoardDto> getTrelloBoards() {
        return trelloFacade.fetchTrelloBoards();
    }


    @RequestMapping(method = RequestMethod.POST, value = "/cards")
    public CreateTrelloCardDto createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return  trelloFacade.createCard(trelloCardDto);
    }

}