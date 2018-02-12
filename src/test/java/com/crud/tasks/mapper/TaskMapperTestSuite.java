package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TaskMapperTestSuite {

    @InjectMocks
    private TaskMapper taskMapper;

    @Test
    public void testMapToTask() {
        //given
        TaskDto taskDto = new TaskDto(1L, "test", "top");
        //when
        Task mappedTask = taskMapper.mapToTask(taskDto);
        //then
        Assert.assertEquals(taskDto.getId(), mappedTask.getId());
        Assert.assertEquals(taskDto.getTitle(), mappedTask.getTitle());
        Assert.assertEquals(taskDto.getContent(), mappedTask.getContent());
    }

    @Test
    public void testMapToTaskDto() {
        //given
        Task task = new Task(1L, "test", "top");
        //when
        TaskDto mappedTaskDto = taskMapper.mapToTaskDto(task);
        //then
        Assert.assertEquals(task.getId(), mappedTaskDto.getId());
        Assert.assertEquals(task.getTitle(), mappedTaskDto.getTitle());
        Assert.assertEquals(task.getContent(), mappedTaskDto.getContent());
    }

    @Test
    public void testMapToTaskDtoList() {
        //given
        Task task1 = new Task(1L, "test1", "top");
        Task task2 = new Task(2L, "test2", "top");
        List<Task> listOfTask = new ArrayList<>();
        listOfTask.add(task1);
        listOfTask.add(task2);

        //when
        List<TaskDto> mappedListOfTaskDto = taskMapper.mapToTaskDtoList(listOfTask);
        //then
        Assert.assertEquals(2, mappedListOfTaskDto.size());
        Assert.assertEquals(listOfTask.get(0).getTitle(),mappedListOfTaskDto.get(0).getTitle());

    }
}
