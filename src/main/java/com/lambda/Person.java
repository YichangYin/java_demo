package com.lambda;

import java.time.LocalDate;

/**
 * @program: java_demo
 * @description:
 * @author: Mr.Walloce
 * @create: 2019/08/12 15:12
 **/
public class Person {
    public enum Sex {
        MALE, FEMALE
    }

    String name;
    LocalDate birthday;
    Sex gender;
    String emailAddress;


}
