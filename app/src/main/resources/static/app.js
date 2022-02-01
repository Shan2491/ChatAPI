var stompClient = null;

function connect() {	
	var socket = new SockJS('/push-notification');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (message) {
	console.log('===========')
	console.log('===========' + comment)
            showComment(JSON.parse(message.body).message);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function showComment(comment) {
    $("#comment").append("<tr><td>" + comment + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });

});

