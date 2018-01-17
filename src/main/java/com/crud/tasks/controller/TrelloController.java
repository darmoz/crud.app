package com.crud.tasks.controller;

import com.crud.tasks.domain.CreateTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trello")
@CrossOrigin("*")
public class TrelloController {

    @Autowired
    private TrelloClient trelloClient;

    @RequestMapping(method = RequestMethod.GET, value = "getTrelloBoards")
    public List<TrelloBoardDto> getTrelloBoards() {
        return trelloClient.getTrelloBoards();
    }
 /*   public void getTrelloBoards() {

        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

        trelloBoards.stream()
                .filter(f ->f.getId() != null && f.getName() != null)
                .filter(f -> f.getName().contains("Kodilla"))
                .forEach(trelloBoardDto -> {
                    System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());
                    System.out.println("This board contains lists: ");
                    trelloBoardDto.getLists().forEach(trelloList -> System.out.println(trelloList.getName() + " - "
                            + trelloList.getId() + " - " + trelloList.isClosed()));
                });
    }*/

    @RequestMapping(method = RequestMethod.POST, value = "createTrelloCard")
    public CreateTrelloCard createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return  trelloClient.createNewCard(trelloCardDto);
    }

}
