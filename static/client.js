var BOSH_SERVICE = '/http-bind'
var connection = null;

function log(msg) 
{
    $('#log').append('<div></div>').append(document.createTextNode(msg));
}

function rawInput(data)
{
    log('RECV: ' + data);
}

function rawOutput(data)
{
    log('SENT: ' + data);
}

function onConnect(status)
{
    if (status == Strophe.Status.CONNECTING) {
	log('Strophe is connecting.');
    } else if (status == Strophe.Status.CONNFAIL) {
	log('Strophe failed to connect.');
	$('#connect').get(0).value = 'connect';
    } else if (status == Strophe.Status.DISCONNECTING) {
	log('Strophe is disconnecting.');
    } else if (status == Strophe.Status.DISCONNECTED) {
	log('Strophe is disconnected.');
	$('#connect').get(0).value = 'connect';
    } else if (status == Strophe.Status.CONNECTED) {
	log('Strophe is connected.');
        if(window.location.pathname.substr(0,6) == "/room/"){
            var room_name = window.location.pathname.replace("/room/", "");
            talkback.joinRoom(room_name+"@chat.talkback.im");            
        }else{
            talkback.joinRoom("test@chat.talkback.im");
        }

	//connection.disconnect();
    }
}


var talkback = {
    roster:{},
    init:function(){

    },

    sendMessage:function(){

    },

    recieveMessage:function(){


    },
    parseMessageContent:function(){
        // If there is a linked url, we try to embed it,
        // if we cant embed it, we might ask the server to help
        // if all else doesnt work.. we just show it.


        //Note, this should happen after the inital message has been
        // shown to the user, and the we enhance once we know more
        
    },
    joinRoom:function(room){
        connection.muc.join(room, "ME"+(new Date()).getTime(), talkback.roomMessage, talkback.roomPresence, "");
    },
    roomMessage:function(msg){
        var $msg = $(msg);
        var from = $msg.attr("from");
        var message = $msg.find("body").text();
        var $messagebox = $("<div class='messagebox'>"+from+":  "+message+"</div>");
        $("#content").append($messagebox);
        console.log(msg);
        return true;
    },
    roomPresence:function(msg){
        console.log("presence: ",$(msg).attr("from"));
        console.log("roster: ", talkback.roster);
        if($(msg).attr("from") in talkback.roster){
            if($(msg).find("photo").text() == talkback.roster[$(msg).attr("from")]){
                // we have the image and it is current
                return true;
            }
        }
        connection.vcard.get(talkback.vcardHandler, $(msg).attr("from"));
        return true;
    },
    sendRoomMessage:function(message){
        connection.muc.message("test@chat.talkback.im", "", message, "");
    },
    vcardHandler:function(stanza){
        var $canvas = $("<canvas width='60' height='60' style='background: #fff;'></canvas>");

        var $vCard = $(stanza).find("vCard");
        var img = $vCard.find('BINVAL').text();

        if( img == ""){
            return true;
        }
        var type = $vCard.find('TYPE').text();
        var img_src = 'data:'+type+';base64,'+img;
        //display image using localStorage
        
        var ctx = $canvas.get(0).getContext('2d');
        var img = new Image();   // Create new Image object
        img.onload = function(){
            // execute drawImage statements here
            ctx.drawImage(img,0,0, 60, 60);
            $("#roster").append($canvas);
        }
        img.src = img_src;
        return true;
    }


};

$(document).ready(function(){
        
    connection = new Strophe.Connection(BOSH_SERVICE);
    connection.rawInput = rawInput;
    connection.rawOutput = rawOutput;

	    connection.connect("guest.talkback.im",
			               "",
			              onConnect);

    connection.addHandler(function(stnz){
        console.log("GOT SOMETHING");
    });
    $("#message").keyup(function(e){
        console.log("keypress: ", e);
        if(e.which == 13){
            talkback.sendRoomMessage($("#message").val());
            $("#message").val("");
        }   
    });
    $("#sendmessage").click(function(){
        talkback.sendRoomMessage($("#message").val());
        $("#message").val("");

    });

});

