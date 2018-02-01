package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class FacadeMapperTestSuite {
    @InjectMocks
    private TrelloMapper trelloMapper;

    @Test
    public void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("exampleCard", "Card", "1", "11");
        TrelloCard trelloCard = new TrelloCard("exampleCard", "Card", "1", "11");

        //When
        TrelloCard mappedCard = trelloMapper.mapToCard(trelloCardDto);
        TrelloCardDto dtoCard = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals(trelloCardDto.getName(), dtoCard.getName());
        assertEquals(trelloCard.getName(), mappedCard.getName());
    }

    @Test
    public void testMapToList() {
        //Given
        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(new TrelloList("1", "Card", false));

        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1", "Card", false));

        //When
        List<TrelloList> mappedList = trelloMapper.mapToList(trelloListDto);
        List<TrelloListDto> theList = trelloMapper.mapToListDto(trelloList);

        //Then
        assertEquals(trelloList.get(0).getName(), mappedList.get(0).getName());
        assertEquals(trelloListDto.get(0).getName(), theList.get(0).getName());
    }
    @Test
    public void testMapToBoard() {
        //Given
        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(new TrelloList("1", "Card", false));

        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1", "Card", false));

        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(new TrelloBoard("1", "board1", trelloList));

        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(new TrelloBoardDto("1", "board1", trelloListDto));

        //When
        List<TrelloBoard> mappedBoardList = trelloMapper.mapToBoard(trelloBoardDtos);
        List<TrelloBoardDto> theBoardList = trelloMapper.mapToBoardDto(trelloBoardList);

        //Then
        assertEquals(trelloBoardList.get(0).getName(), mappedBoardList.get(0).getName());
        assertEquals(trelloBoardDtos.get(0).getName(), theBoardList.get(0).getName());
    }
}
