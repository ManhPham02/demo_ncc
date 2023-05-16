package com.example.emp.TestBeanScope;

import com.example.emp.TestBeanScope.entity.Preson;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;


public class BeanScopeController {

    private static final String NAME = "John Smith";
    private static final String NAME_OTHER = "Anna Jones";

    @GetMapping("/singleton")
    public void singleton() {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("/scope/scopes.xml");
        Preson preson1 = applicationContext.getBean("personSingleton", Preson.class);
        preson1.setName(NAME);
        System.out.println(preson1.getName());

        Preson preson2 = applicationContext.getBean("personSingleton", Preson.class);
        System.out.println(preson2.getName());
    }

    @GetMapping("/prototype")
    public void prototype() {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("/scope/scopes.xml");
        Preson preson1 = applicationContext.getBean("personPrototype", Preson.class);
        Preson preson2 = applicationContext.getBean("personPrototype", Preson.class);
        preson1.setName(NAME);
        preson2.setName(NAME_OTHER);
        System.out.println(preson1.getName());
        System.out.println(preson2.getName());
    }
}
