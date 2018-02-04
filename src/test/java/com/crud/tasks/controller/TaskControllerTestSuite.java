package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DbService dbService;


    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void testGetTask() throws Exception {
        //given
        TaskDto taskDto = new TaskDto(1L, "test", "test_content");
        Task task = new Task(1L,"test", "test_content");

        //when & then
        when(dbService.getTaskById(task.getId())).thenReturn(Optional.ofNullable(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        mockMvc.perform(get("/v1/task/getTask?taskId={taskId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.title", is("test")))
                .andExpect(jsonPath("$.content", is("test_content")));
    }

    @Test
    public void testGetTasks() throws Exception {
        //given
        Task task = new Task(1L,"test", "test_content");
        Task task2 = new Task(2L,"test2", "test2_content");
        List<Task> list = new ArrayList<>();
        list.add(task);
        list.add(task2);

        TaskDto taskDto = new TaskDto(1L,"test", "test_content");
        TaskDto task2Dto = new TaskDto(2L,"test2", "test2_content");
        List<TaskDto> listDto = new ArrayList<>();
        listDto.add(taskDto);
        listDto.add(task2Dto);

        when(dbService.getAllTasks()).thenReturn(list);
        when(taskMapper.mapToTaskDtoList(list)).thenReturn(listDto);
        //when & then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("test2")))
                .andExpect(jsonPath("$[1].content", is("test2_content")));
    }

   @Test
    public void testPostTask() throws Exception {
        //given
        TaskDto taskDto = new TaskDto(1L,"test", "something to do");
        Task task = new Task(1L,"test", "something to do");

       when(dbService.saveTask(ArgumentMatchers.any(Task.class))).thenReturn(task);
       when(taskMapper.mapToTask(taskDto)).thenReturn(task);
       Gson gson = new Gson();
       String jsonContent = gson.toJson(taskDto);
       //String bodyContent = "{\"id\":1,\"title\":\"test\",\"content\":\"test_content\"}";
        // when & then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
       System.out.println(jsonContent);
    }

    @Test
    public void testDeleteTask() throws Exception {
        //given
        Task task = new Task(1L,"test", "test_content");
        TaskDto taskDto = new TaskDto(1L, "test", "test_content");
        when(dbService.getTaskById(task.getId())).thenReturn(Optional.ofNullable(task));
        doNothing().when(dbService).deleteTask(task);
        //when & then
        mockMvc.perform(delete("/v1/task/deleteTask?taskId={taskId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                ;
    }


}
