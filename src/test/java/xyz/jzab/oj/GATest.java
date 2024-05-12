package xyz.jzab.oj;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.jzab.oj.ga.GA;
import xyz.jzab.oj.ga.GAPaper;

import java.util.List;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@SpringBootTest
public class GATest {
    @Resource
    GA ga;

    @Test
    public void main(){
        Person person = new Teacher();
        System.out.println(person.toString());
    }
}

class Person{
    @Override
    public String toString() {
        return "P";
    }
}

class Teacher extends Person{
    @Override
    public String toString() {
        return "T";
    }
}