package dev.ffeusthuber.todoapp;
import junit.framework.TestCase;


import java.util.ArrayList;

public class ToDoListTest extends TestCase {

    public void testPopulateListFromDB() {
    }

    public void testAddTask() {
        ToDoList toDoList = new ToDoList(new ArrayList<>());
        int previousListSize = toDoList.getTasks().size();
        toDoList.addTask(new Task("TestTask"));
        int newListSize = toDoList.getTasks().size();
        assertEquals(newListSize, previousListSize+1);

    }
}