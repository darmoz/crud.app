package com.crud.tasks;


import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class TrelloValidatorTestSuite {
    @InjectMocks
    private TrelloValidator trelloValidator;

    @Mock
    private Appender mockAppender;

    @Captor
    private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

    @Before
    public void setup() {
        final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.addAppender(mockAppender);
    }

    @After
    public void teardown() {
        final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.detachAppender(mockAppender);
    }

    @Test
    public void testValidateCard() {
        //Given
        TrelloCard trelloCard = new TrelloCard("my_card", "card", "2", "1");
        //When
        trelloValidator.validateCard(trelloCard);
        //then
        verify(mockAppender).doAppend(captorLoggingEvent.capture());
        final LoggingEvent loggingEvent = captorLoggingEvent.getValue();
        assertThat(loggingEvent.getLevel(), is(Level.INFO));
        assertThat(loggingEvent.getFormattedMessage(),
                is("Application is working properly."));
    }

    @Test
    public void testValidateBoard() {
        //given
        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(new TrelloList("1", "test", false));
        TrelloBoard board1 = new TrelloBoard("1", "test",trelloList);
        List<TrelloBoard> boards = new ArrayList<>();
        boards.add(board1);
        //when
       List<TrelloBoard> result = trelloValidator.validateBoard(boards);
       //then
        assertEquals(board1.getName(),result.get(0).getName());
    }

    @Test
    public void testValidateEmptyBoard() {
        //given
        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(new TrelloList("1", "list", false));
        TrelloBoard board1 = new TrelloBoard("1", "board",trelloList);
        List<TrelloBoard> boards = new ArrayList<>();
        boards.add(board1);
        //when
        List<TrelloBoard> result = trelloValidator.validateBoard(boards);
        //then
        assertEquals(0,result.size());
    }

}
