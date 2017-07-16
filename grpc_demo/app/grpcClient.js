var PROTO_FILE_PATH = 'E://Study//webstormworkspace//grpc_demo//proto//Student.proto';
var grpc = require('grpc');
var grpcService = grpc.load(PROTO_FILE_PATH).com.ceaser.proto;
var client  = new grpcService.StudentService('localhost:8899',grpc.credentials.createInsecure());
client.getRealNameByUserName({username:'lis'},function (error,respData) {
    console.log(respData);
})
