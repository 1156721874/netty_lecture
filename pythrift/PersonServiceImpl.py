# coding=utf-8
__author='Ceaserwang'

from py.thrift.generated import ttypes

class PersonServiceImpl:
    def getPersonByUserName(self,username):
        print('get client param :'+username)
        person = ttypes.Person()
        person.married = True
        person.username=username
        person.age = 24
        return person

    def savePerson(self,person):
        print('get client param : ')
        print(person.username)
        print(person.age)
        print(person.married)
