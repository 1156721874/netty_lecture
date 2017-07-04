package com.ceaser.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TFileTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import thrift.generated.Person;
import thrift.generated.PersonService;

/**
 * Created by Administrator on 2017/6/10.
 */
public class ThriftClient {
    public static void main(String[] args) {
        try {
            TTransport transport = new TFastFramedTransport(new TSocket("127.0.0.1",8899),600);
            TProtocol tProtocol = new TCompactProtocol(transport);
            PersonService.Client client = new PersonService.Client(tProtocol);
            transport.open();
            Person person =  client.getPersonByUserName("zhansan");
            System.out.println(person.getUsername());
            System.out.println(person.getAge());
            System.out.println(person.isMarried());

            System.out.println("--------------------------------------------");
            Person person1 = new Person();
            person1.setUsername("李四" );
            person1.setMarried(false);
            person1.setAge(23);

            client.savePerson(person1);

            transport.close();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
/**
 * java client connect python server have a exception :Connection refused: connect
 * solution style:
 * https://stackoverflow.com/questions/16760741/apache-thrift-python-java-connection-refused
 **/