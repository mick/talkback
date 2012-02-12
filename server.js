var express = require("express"),
    app = express.createServer(),
    io = require("socket.io"),
    settings = require("./settings"),
    httpProxy = require('http-proxy'),
    request = require('request');

app.use(express.bodyParser());

app.get('/', function(req, res){                
    res.render('index.ejs', { layout: false});
});

app.get('/room/:room', function(req, res){
    console.log("joining room"+ req.params.room);
    res.render('index.ejs', { layout: false});
});

app.use('/static', express.static(__dirname + '/static')); 

var port = process.env.PORT || 3000;
httpProxy.createServer(function (req, res, proxy) {
  //
  // Put your custom server logic here
  //
    if(req.url == "/http-bind"){
        console.log("proxying request to ejabberd");
        proxy.proxyRequest(req, res, {
            host: 'talkback.im',
            port: 5281
        });
    }else{

        proxy.proxyRequest(req, res, {
            host: 'localhost',
            port: port+1
        });
    }
}).listen(port);



app.listen(port+1, function() {
  console.log("Listening on " + port);
});



