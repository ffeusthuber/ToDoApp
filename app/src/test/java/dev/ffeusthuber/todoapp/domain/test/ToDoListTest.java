package dev.ffeusthuber.todoapp.domain.test;
import junit.framework.TestCase;


import java.util.ArrayList;

import dev.ffeusthuber.todoapp.feature_todo.domain.model.Task;
import dev.ffeusthuber.todoapp.feature_todo.domain.model.ToDoList;

public class ToDoListTest extends TestCase {

    public void testPopulateListFromDB() {
    }

    public void testAddTask() {
        ToDoList toDoList = new ToDoList(new ArrayList<>());
        Task testTask = new Task("TestTask");

        toDoList.addTask(testTask);

        assertEquals(1, toDoList.getTasks().size());
        assertEquals(toDoList.getTasks().get(0),testTask);
    }
}