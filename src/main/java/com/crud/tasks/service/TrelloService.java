package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class TrelloService {

    private static final String SUBJECT = "Task: New Trello card";

    @Autowired
    private TrelloClient trelloClient;

    @Autowired
    private SimpleMailService simpleMailService;

    @Autowired
    private AdminConfig adminConfig;

    public List<TrelloBoardDto> fetchTrelloBoards() {
        return trelloClient.getTrelloBoards();
    }

    public CreateTrelloCardDto createTrelloCard(final TrelloCardDto trelloCardDto) {
        CreateTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);

        ofNullable(newCard).ifPresent(card ->simpleMailService.send(new Mail(
                adminConfig.getAdminMail(),
                SUBJECT,
                "New card: " + trelloCardDto.getName() + " has been created on your Trello account")
        ));


        return newCard;
    }
}