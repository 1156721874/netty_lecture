# coding=utf-8

from py.thrift.generated import PersonService
from py.thrift.generated import ttypes

from thrift import Thrift
from thrift.transport import TTransport
from thrift.protocol import TCompactProtocol
from thrift.transport import TSocket


try:
    tsocket = TSocket.TSocket('localhost', 8899)
    tsocket.setTimeout(600)
    transport = TTransport.TFramedTransport(tsocket)
    protocol = TCompactProtocol.TCompactProtocol(transport)
    client = PersonService.Client(protocol)

    transport.open()

    person = client.getPersonByUserName('张三')
    print(person.married)
    print(person.age)
    print(person.username)

    newperson = ttypes.Person()
    newperson.username = '李四'
    newperson.age = 24
    newperson.married = True

    client.savePerson(newperson)

    transport.close()
except Thrift.TException as tx:
    print('----')
