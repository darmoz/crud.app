package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbServiceTestSuite {


   @Autowired
    private DbService dbService;

   @Autowired
   private TaskRepository taskRepository;

    @Test
    public void getAllTasks() throws Exception {
        //given
        Task task1 = new Task("test1", "test1 text");
        Task task2 = new Task("test2", "test2 text");
        long countBefore = taskRepository.count();
        dbService.saveTask(task1);
        dbService.saveTask(task2);

        //when
        List<Task> retrievedTaskList = dbService.getAllTasks();
        //then
        assertEquals(countBefore+2, retrievedTaskList.size());
        //clean up
        dbService.deleteTask(task1);
        dbService.deleteTask(task2);
    }

    @Test
    public void getTaskById() throws Exception {
        //given
        Task task1 = new Task( "test1", "test1 text");
        Task savedTask = dbService.saveTask(task1);
        //when
        Optional<Task> retrievedTask = dbService.getTaskById(savedTask.getId());
        //then
        assertEquals(task1.getTitle(), retrievedTask.get().getTitle());
        assertEquals(task1.getContent(), retrievedTask.get().getContent());
        //clean up
        dbService.deleteTask(task1);
    }

    @Test
    public void saveTask() throws Exception {
        //given
        Task task1 = new Task( "test1", "test1 text");
        //when
        Task savedTask = dbService.saveTask(task1);
        Optional<Task> retrievedTask = dbService.getTaskById(savedTask.getId());
        //then
        assertEquals(task1.getTitle(), retrievedTask.get().getTitle());
        assertEquals(task1.getContent(), retrievedTask.get().getContent());
        //clean up
        dbService.deleteTask(task1);
    }

    @Test
    public void deleteTask() throws Exception {
        //given
        Task task1 = new Task("test1", "test1 text");
        long count = taskRepository.count();
        dbService.saveTask(task1);
        List<Task> currentList = dbService.getAllTasks();
        //when
        dbService.deleteTask(task1);
        //then
        assertEquals(count, currentList.size()-1);

    }

}