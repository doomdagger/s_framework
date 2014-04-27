/**
 * Created by Li He on 2014/4/26.
 */

var sock = new SockJS('http://localhost:8080/ecommerce/websocket/chat/');
sock.onopen = function() {
    console.log('open');
};
sock.onmessage = function(e) {
    console.log('message', e.data);
};
sock.onclose = function() {
    console.log('close');
};


