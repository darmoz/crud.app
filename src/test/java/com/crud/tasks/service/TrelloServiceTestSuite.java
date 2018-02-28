package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.dto.CreateTrelloCardDto;
import com.crud.tasks.domain.dto.TrelloBoardDto;
import com.crud.tasks.domain.dto.TrelloCardDto;
import com.crud.tasks.domain.dto.TrelloListDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTestSuite {

    @InjectMocks
    private TrelloService trelloService;
    @Mock
    private TrelloClient trelloClient;
    @Mock
    private SimpleMailService simpleMailService;
    @Mock
    private AdminConfig adminConfig;

    @Test
    public void fetchTrelloBoards() throws Exception {
        //given
        List<TrelloListDto> trelloList = new ArrayList<>();
        trelloList.add(new TrelloListDto("1", "my_list", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "my_task", trelloList));

        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoards);
        //when
        List<TrelloBoardDto> boardList = trelloService.fetchTrelloBoards();
        //then
        assertEquals(trelloBoards.size(), boardList.size());
        assertEquals(trelloBoards.get(trelloBoards.size()-1).getId(),
                boardList.get(boardList.size()-1).getId());
        assertEquals(trelloBoards.get(trelloBoards.size()-1).getName(),
                boardList.get(boardList.size()-1).getName());
        assertEquals(trelloBoards.get(trelloBoards.size()-1).getLists(),
                boardList.get(boardList.size()-1).getLists());

    }

    @Test
    public void createTrelloCard() throws Exception {
        //given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test", "test1", "top", "1");
        CreateTrelloCardDto createTrelloCardDto = new CreateTrelloCardDto("1", "test", "any");


        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createTrelloCardDto);
        when(adminConfig.getAdminMail()).thenReturn("test@test.com");
        //when
        CreateTrelloCardDto cardDto = trelloService.createTrelloCard(trelloCardDto);
        //then
        assertEquals(createTrelloCardDto.getId(),cardDto.getId());
        assertEquals(createTrelloCardDto.getName(), cardDto.getName());
        assertEquals(createTrelloCardDto.getShortUrl(),cardDto.getShortUrl());
    }

}