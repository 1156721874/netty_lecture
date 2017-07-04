package com.ceaser.thrift;

import org.apache.thrift.TException;
import thrift.generated.DataException;
import thrift.generated.Person;
import thrift.generated.PersonService;

/**
 * Created by Administrator on 2017/6/10.
 */
public class PersonServiceImpl implements PersonService.Iface {
    @Override
    public Person getPersonByUserName(String username) throws DataException, TException {
        System.out.println("getPersonByUserName - get client param  : "+username);
        Person person = new Person();
        person.setAge(20);
        person.setMarried(true);
        person.setUsername("张三");
        return  person;
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {
        System.out.println("savePerson - get client param  : ");
        System.out.println(person.getUsername());
        System.out.println(person.getAge());
        System.out.println(person.isMarried());
    }
}
