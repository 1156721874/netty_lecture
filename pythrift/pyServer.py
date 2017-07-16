# coding=utf-8

from py.thrift.generated import PersonService
from PersonServiceImpl import PersonServiceImpl

from thrift import Thrift

from thrift.server import  TNonblockingServer
from thrift.server import TServer
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import  TCompactProtocol

try:
    personServerHandler = PersonServiceImpl()
    processer = PersonService.Processor(personServerHandler)
    socket = TSocket.TServerSocket(host='127.0.0.1',port=8899)
    transportFactory = TTransport.TFramedTransportFactory()
    protocalFactory = TCompactProtocol.TCompactProtocolFactory()

    server = TServer.TSimpleServer(processer,socket,transportFactory,protocalFactory)
    server.serve()

except Thrift.TException as tx:
    print('----')
