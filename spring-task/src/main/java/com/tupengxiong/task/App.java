package com.tupengxiong.task;

import java.util.Date;

import org.springframework.stereotype.Component;
/**
 * test for spring task 
 * @author tupx
 * 	
 */
@Component
public class App {

    public void execute1(){
        System.out.printf("Task: %s, Current time: %s\n", 1, new Date().getTime());
    }

    public void execute2(){
        System.out.printf("Task: %s, Current time: %s\n", 2, new Date().getTime());
    }

    public void execute3(){
        System.out.printf("Task: %s, Current time: %s\n", 3, new Date().getTime());
    }

    public void execute4(){
        System.out.printf("Task: %s, Current time: %s\n", 4, new Date().getTime());
    }

    public void execute5(){
        System.out.printf("Task: %s, Current time: %s\n", 5, new Date().getTime());
    }

    public void execute6(){
        System.out.printf("Task: %s, Current time: %s\n", 6, new Date().getTime());
    }

    public void execute7(){
        System.out.printf("Task: %s, Current time: %s\n", 7, new Date().getTime());
    }

    public void execute8(){
        System.out.printf("Task: %s, Current time: %s\n", 8, new Date().getTime());
    }

    public void execute9(){
        System.out.printf("Task: %s, Current time: %s\n", 9, new Date().getTime());
    }

    public void execute10(){
        System.out.printf("Task: %s, Current time: %s\n", 10, new Date().getTime());
    }

    public void execute11(){
        System.out.printf("Task: %s, Current time: %s\n", 11, new Date().getTime());
    }

}