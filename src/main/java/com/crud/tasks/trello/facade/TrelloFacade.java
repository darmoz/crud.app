package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public class TrelloFacade {
    @Autowired
    private TrelloValidator trelloValidator;
    @Autowired
    private TrelloMapper trelloMapper;
    @Autowired
    private TrelloService trelloService;

    public List<TrelloBoardDto> fetchTrelloBoards() {

        List<TrelloBoard> trelloBoards =  trelloMapper.mapToBoard(trelloService.fetchTrelloBoards());
        List<TrelloBoard> filteredBoards = trelloValidator.validateBoard(trelloBoards);
        return trelloMapper.mapToBoardDto(filteredBoards);
    }

    public CreateTrelloCardDto createCard(final TrelloCardDto trelloCardDto) {

        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        trelloValidator.validateCard(trelloCard);
        return trelloService.createTrelloCard(trelloMapper.mapToCardDto(trelloCard));
    }
}
