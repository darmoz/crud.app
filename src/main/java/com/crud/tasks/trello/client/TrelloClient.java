package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreateTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Value("${trello.app.username}")
    private String trelloUserName;

    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards() {

        Optional<TrelloBoardDto[]> boardsResponse = Optional.ofNullable(restTemplate.getForObject(getUri(),
                TrelloBoardDto[].class));

        if (boardsResponse.isPresent()) {
            return Arrays.asList(boardsResponse.get());
        }
        return new ArrayList<>();
    }

    public CreateTrelloCard createNewCard(TrelloCardDto trelloCardDto) {

        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/cards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getListId()).build().encode().toUri();

        return restTemplate.postForObject(url, null, CreateTrelloCard.class);
    }

    private URI getUri() {
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + trelloUserName + "/boards")
                    .queryParam("key", trelloAppKey)
                    .queryParam("token", trelloToken)
                    .queryParam("fields", "name,id")
                    .queryParam("lists", "all").build().encode().toUri();
    }

}
