package com.yakut.azone.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yakut
 */
public class Task extends Thread {

    private static Task task = null;
    private List<LittleTask> tasks = new ArrayList<>();

    public interface LittleTask {

        public void execute();
    }

    @Override
    public void run() {
        while(true){
            try{
                for(LittleTask little:tasks){
                    little.execute();
                }
                Thread.sleep(1000);
            }catch(Exception e){
                
            }
        }
    }

    private static Task getTask() {
        if (task == null) {
            task = new Task();
            task.start();
        }
        return task;
    }

    private Task() {
    }

    public static void addTask(LittleTask littleTask) {
        getTask().tasks.add(littleTask);
    }

}
