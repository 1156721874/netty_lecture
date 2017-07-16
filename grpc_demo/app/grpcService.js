var PROTO_FILE_PATH = 'E://Study//webstormworkspace//grpc_demo//proto//Student.proto';
var grpc = require('grpc');
var grpcService = grpc.load(PROTO_FILE_PATH).com.ceaser.proto;
var service = new grpc.Server();
service.addService(grpcService.StudentService.service,{
    getRealNameByUserName : getRealNameByUserName,
    getStudentsByAge :getStudentsByAge,
    getStudentsWraperByAge:getStudentsWraperByAge,
    biTalk:biTalk

});

service.bind('localhost:8899',grpc.ServerCredentials.createInsecure());
service.start();

function  getRealNameByUserName(call,callback){
console.log(call.request.username);
    callback(null,{realname:'李四'});
}

function getStudentsByAge(){

}

function getStudentsWraperByAge(){

}

function biTalk(){

}