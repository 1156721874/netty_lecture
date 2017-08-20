package com.ceaser.thrift;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import thrift.generated.PersonService;

/**
 * Created by Administrator on 2017/6/10.
 */
public class ThriftService {
    public static void main(String[] args) throws Exception {
        TNonblockingServerSocket  socket = new TNonblockingServerSocket(8899);
        THsHaServer.Args arg = new THsHaServer.Args(socket).minWorkerThreads(2).maxWorkerThreads(5);
        ///THsHaServer half syn 半同步半异步server
        PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<>(new PersonServiceImpl());
        arg.protocolFactory(new TCompactProtocol.Factory());
        arg.transportFactory(new TFramedTransport.Factory());
        arg.processorFactory(new TProcessorFactory(processor));

        TServer tServer = new THsHaServer(arg);
        System.out.println("thrift service started!");
        tServer.serve();
    }
}
/**
protocol
传输协议，thrift客户端和服务器端远程调用时候传输数据时候采取的数据格式，例如

TBinaryProtocol 二进制格式

TCompactProtocol  高效和压缩的二进制格式

TDenseProtocoal  与TCompactProtocol相比，meta信息略有不同

TJSONProtocoal  JSON

TDebugProtocoal  text 格式 方便调试

server

服务器的作用就是把前面的processor,transport,protocol组合到一起，协同他们的工作，例如：

TSimplerServer 接受一个连接，处理连接请求，直到客户端关闭了连接，它才回去接受一个新的连接。

TNonblockingServer 使用非阻塞的I/O解决了TSimpleServer一个客户端阻塞其他所有客户端的问题。

THsHaServer（半同步/半异步的server）它使用一个单独的线程来处理网络I/O，一个独立的worker线程池来处理消息。这样，只要有空闲的worker线程，消息就会被立即处理，因此多条消息能被并行处理。

TThreadPoolServer 有一个专用的线程用来接受连接。一旦接受了一个连接，它就会被放入ThreadPoolExecutor中的一个worker线程里处理。

注意：使用何种server和需要使用某种server transport，transport可能是有要求的。

例如：TNonblockingServer.Args的构造方法中

public Args(org.apache.thrift.transport.TNonblockingServerTransport transport) 

可以看出使用TNonblockingServer需要使用TNonblockingServerTransport才行。

例如：使用THsHaServer时候server和client都需要设置transport为TFramedTransport

*/
