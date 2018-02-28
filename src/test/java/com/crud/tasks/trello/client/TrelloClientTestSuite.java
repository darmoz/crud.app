package com.crud.tasks.trello.client;

import com.crud.tasks.domain.dto.CreateTrelloCardDto;
import com.crud.tasks.domain.dto.TrelloBoardDto;
import com.crud.tasks.domain.dto.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTestSuite {
    @InjectMocks
    private TrelloClient trelloClient;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private TrelloConfig trelloConfig;

    @Test
    public void getTrelloBoards() throws Exception {
        //given
        URI url = UriComponentsBuilder.fromHttpUrl("https://api.trello.com/1" + "/members/"
                + "dariuszmozgowoj" + "/boards")
                .queryParam("key", "0e00764b541fab870789683dd9f23061")
                .queryParam("token", "4eb746acef2bb20ba9651280d1fb33ca46aba80101302f757beb7f5497e54f8b")
                .queryParam("fields", "name,id")
                .queryParam("lists", "all").build().encode().toUri();
        List<TrelloBoardDto> list = new ArrayList<>();
        TrelloBoardDto board1 = new TrelloBoardDto("1","test1", new ArrayList<>());
        TrelloBoardDto board2 = new TrelloBoardDto("2","test2", new ArrayList<>());
        list.add(board1);
        list.add(board2);
        TrelloBoardDto[] arrayBoard = {board1, board2};

        when(restTemplate.getForObject(url,TrelloBoardDto[].class)).thenReturn(arrayBoard);
        //when
        List<TrelloBoardDto> retrievedList = trelloClient.getTrelloBoards();
        //then
        assertEquals(list.size(), retrievedList.size());
        assertEquals(list.get(0).getName(), retrievedList.get(0).getName());
        assertEquals(list.get(0).getId(), retrievedList.get(0).getId());
    }

    @Test
    public void getEmptyTrelloBoards() {
        //given
        URI url = UriComponentsBuilder.fromHttpUrl("https://api.trello.com/1" + "/members/"
                + "dariuszmozgowoj" + "/boards")
                .queryParam("key", "0e00764b541fab870789683dd9f23061")
                .queryParam("token", "4eb746acef2bb20ba9651280d1fb33ca46aba80101302f757beb7f5497e54f8b")
                .queryParam("fields", "name,id")
                .queryParam("lists", "all").build().encode().toUri();
        List<TrelloBoardDto> emptyList = new ArrayList<>();
        //when
        List<TrelloBoardDto> theList = trelloClient.getTrelloBoards();
        //then
        assertTrue(theList.isEmpty());
        assertEquals(emptyList.size(), theList.size());
    }

    @Test
    public void createNewCard() throws Exception {
        //given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test", "test", "top", "1");
        CreateTrelloCardDto createTrelloCardDto = new CreateTrelloCardDto("1", "test", "any");
        URI url = UriComponentsBuilder.fromHttpUrl("https://api.trello.com/1" + "/cards")
                .queryParam("key", "0e00764b541fab870789683dd9f23061")
                .queryParam("token", "4eb746acef2bb20ba9651280d1fb33ca46aba80101302f757beb7f5497e54f8b")
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getListId()).build().encode().toUri();

        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("https://api.trello.com/1");
        when(trelloConfig.getTrelloAppKey()).thenReturn("0e00764b541fab870789683dd9f23061");
        when(trelloConfig.getTrelloToken()).thenReturn("4eb746acef2bb20ba9651280d1fb33ca46aba80101302f757beb7f5497e54f8b");
        when(restTemplate.postForObject(url, null, CreateTrelloCardDto.class)).thenReturn(createTrelloCardDto);
        //when
        CreateTrelloCardDto card = trelloClient.createNewCard(trelloCardDto);
        //then
        assertEquals(createTrelloCardDto.getId(), card.getId());
        assertEquals(createTrelloCardDto.getName(), card.getName());
        assertEquals(createTrelloCardDto.getShortUrl(), card.getShortUrl());
        String http = trelloConfig.getTrelloApiEndpoint();
    }

}