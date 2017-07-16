var service   = require("../static_codegen/proto/Student_grpc_pb");
var messages = require("../static_codegen/proto/Student_pb");

var grpc = require("grpc");

var server  = new grpc.Server();

server.addService(service.StudentServiceService,{
    getRealNameByUserName : getRealNameByUserName,
    getStudentsByAge :getStudentsByAge,
    getStudentsWraperByAge:getStudentsWraperByAge,
    biTalk:biTalk
});

server.bind("localhost:8899",grpc.ServerCredentials.createInsecure());
server.start();

function getRealNameByUserName(call,callback){
    console.log(call.request.getUsername());

    var mymessage = new messages.MyResponse();
    mymessage.setRealname("赵六");
    callback(null,mymessage);
}

function getStudentsByAge(){

}

function getStudentsWraperByAge(){

}

function biTalk(){

}