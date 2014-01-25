
var mainContainer = '.canvas-container-content';

jQuery(function($){
    var canvas = document.getElementById('canvas-seat-control');
    var context = canvas.getContext('2d');

    var lastPercentage = {
        "middle-v" : 50,
        "middle-h" : 50,
        "bottom" : 50,
        "bottom-h" : 50,
        "bottom-v-head" : 50,
        "bottom-v-tail" : 50,
        "header-h" : 50,
        "header-v" : 50,
        "top" : 50
    };

    var seat = {
        point1 : {
            start : { x: 400, y: 50 }
        },
        point2 : {
            bezierStart : { x: 430, y: 50 },
            bezierEnd : { x: 430, y: 150 },
            end : { x: 405, y: 150 }
        },
        point3 : {
            bezierStart : { x: 440, y: 150 },
            bezierEnd : { x: 430, y: 400 },
            end : { x: 380, y: 410 }
        },
        point4 : {
            bezierStart : { x: 380, y: 420 },
            bezierEnd : { x: 100, y: 400 },
            end : { x: 100, y: 350 }
        },
        point5 : {
            bezierStart : { x: 100, y: 290 },
            bezierEnd : { x: 340, y: 350 },
            end : { x: 360, y: 370 }
        },
        point6 : {
            bezierStart : { x: 355, y: 340 },
            bezierEnd : { x: 355, y: 320 },
            end : { x: 355, y: 295 }
        },
        point7 : {
            bezierStart : { x: 340, y: 270 },
            bezierEnd : { x: 345, y: 245 },
            end : { x: 360, y: 230 }
        },
        point8 : {
            bezierStart : { x: 370, y: 150 },
            bezierEnd : { x: 395, y: 150 },
            end : { x: 395, y: 150 }
        },
        point9 : {
            bezierStart : { x: 370, y: 150 },
            bezierEnd : { x: 370, y: 50 },
            end : { x: 400, y: 50 }
        }
    }

    var newSeat = JSON.parse(JSON.stringify(seat));
    redrawSeat(seat);

    function redrawSeat(seat){
        var canvas = document.getElementById('canvas-seat-control');
        var context = canvas.getContext('2d');

        // clear
        context.clearRect(0, 0, canvas.width, canvas.height);

        // draw seat
        context.beginPath();
        context.moveTo(seat.point1.start.x, seat.point1.start.y);
        context.bezierCurveTo(seat.point2.bezierStart.x, seat.point2.bezierStart.y, seat.point2.bezierEnd.x, seat.point2.bezierEnd.y, seat.point2.end.x, seat.point2.end.y);
        context.bezierCurveTo(seat.point3.bezierStart.x, seat.point3.bezierStart.y, seat.point3.bezierEnd.x, seat.point3.bezierEnd.y, seat.point3.end.x, seat.point3.end.y);
        context.bezierCurveTo(seat.point4.bezierStart.x, seat.point4.bezierStart.y, seat.point4.bezierEnd.x, seat.point4.bezierEnd.y, seat.point4.end.x, seat.point4.end.y);
        context.bezierCurveTo(seat.point5.bezierStart.x, seat.point5.bezierStart.y, seat.point5.bezierEnd.x, seat.point5.bezierEnd.y, seat.point5.end.x, seat.point5.end.y);
        context.bezierCurveTo(seat.point6.bezierStart.x, seat.point6.bezierStart.y, seat.point6.bezierEnd.x, seat.point6.bezierEnd.y, seat.point6.end.x, seat.point6.end.y);
        context.bezierCurveTo(seat.point7.bezierStart.x, seat.point7.bezierStart.y, seat.point7.bezierEnd.x, seat.point7.bezierEnd.y, seat.point7.end.x, seat.point7.end.y);
        context.bezierCurveTo(seat.point8.bezierStart.x, seat.point8.bezierStart.y, seat.point8.bezierEnd.x, seat.point8.bezierEnd.y, seat.point8.end.x, seat.point8.end.y);
        context.bezierCurveTo(seat.point9.bezierStart.x, seat.point9.bezierStart.y, seat.point9.bezierEnd.x, seat.point9.bezierEnd.y, seat.point9.end.x, seat.point9.end.y);
        context.closePath();
        context.shadowColor = '#000';
        context.shadowBlur = 0;
        context.lineWidth = 1;
        context.fillStyle = '#fff';
        context.fill();
        context.strokeStyle = '#000';
        context.stroke();
    }

    $("#slider-bottom").pathslider({
        points     : [ 0,150,   0,0,   0,0,  325,150  ],
        value      : 50,
        rotateGrip : true,
        tolerance  : 3,
        range      : 30,
        curve      : { width:1, color:"#d0d0d0", cap:"round" },
        stop     : function(e, slider){

            sendAction("SP", slider.percent);

        }
    });
    $("#slider-bottom .pathslider-grip").hover(function(){
        $('#image-container > img').show().attr('src', 'resources/img/bottom.png');
    });

    $("#slider-top").pathslider({
        points     : [ 50,75,   50,-75,   -50,-75,  250,75  ],
        value      : 50,
        rotateGrip : true,
        tolerance  : 3,
        range      : 30,
        curve      : { width:1, color:"#d0d0d0", cap:"round" },
        stop     : function(e, slider){
            sendAction("PO", slider.percent);
        }
    });
    $("#slider-top .pathslider-grip").hover(function(){
        $('#image-container > img').show().attr('src', 'resources/img/top.png');
    });

    $("#slider-header-h").pathslider({
        points     : [ 75,100,   25,-50,   -25,-50,  200,100  ],
        value      : 50,
        rotateGrip : true,
        tolerance  : 3,
        range      : 30,
        curve      : { width:1, color:"#d0d0d0", cap:"round" },
        stop     : function(e, slider){
            sendAction("PZ", slider.percent);
        }
    });
    $("#slider-header-h .pathslider-grip").hover(function(){
        $('#image-container > img').show().attr('src', 'resources/img/header-h.png');
    });

    $("#slider-header-v").pathslider({
        points     : [ 125,100,   0,0,   0,0,  125,175  ],
        value      : 50,
        rotateGrip : true,
        tolerance  : 3,
        range      : 30,
        curve      : { width:1, color:"#d0d0d0", cap:"round" },
        stop     : function(e, slider){
            sendAction("ZG", slider.percent);
        }
    });
    $("#slider-header-v .pathslider-grip").hover(function(){
        $('#image-container > img').show().attr('src', 'resources/img/header-v.png');
    });

    $("#slider-middle-h").pathslider({
        points     : [ 75,75,   0,0,   0,0,  200,75  ],
        value      : 50,
        rotateGrip : true,
        tolerance  : 3,
        range      : 30,
        curve      : { width:1, color:"#d0d0d0", cap:"round" },
        stop     : function(e, slider){
            sendAction("GW", slider.percent);
        }
    });
    $("#slider-middle-h .pathslider-grip").hover(function(){
        $('#image-container > img').show().attr('src', 'resources/img/middle-h.png');
    });

    $("#slider-middle-v").pathslider({
        points     : [ 75,100,   0,0,   0,0,  75,175  ],
        value      : 50,
        rotateGrip : true,
        tolerance  : 3,
        range      : 30,
        curve      : { width:1, color:"#d0d0d0", cap:"round" },
        stop     : function(e, slider){
            sendAction("GG", slider.percent);
        }
    });
    $("#slider-middle-v .pathslider-grip").hover(function(){
        $('#image-container > img').show().attr('src', 'resources/img/middle-v.png');
    });

    $("#slider-bottom-h").pathslider({
        points     : [ 0,100,   0,0,   0,0,  125,100  ],
        value      : 50,
        rotateGrip : true,
        tolerance  : 3,
        range      : 30,
        curve      : { width:1, color:"#d0d0d0", cap:"round" },
        stop     : function(e, slider){
            sendAction("WF", slider.percent);
        }
    });
    $("#slider-bottom-h .pathslider-grip").hover(function(){
        $('#image-container > img').show().attr('src', 'resources/img/bottom-h.png');
    });

    $("#slider-bottom-v-head").pathslider({
        points     : [ 150,75,   0,0,   0,0,  150,150  ],
        value      : 50,
        rotateGrip : true,
        tolerance  : 3,
        range      : 30,
        curve      : { width:1, color:"#d0d0d0", cap:"round" },
        stop     : function(e, slider){

            sendAction("PF", slider.percent);

        }
    });
    $("#slider-bottom-v-head .pathslider-grip").hover(function(){
        $('#image-container > img').show().attr('src', 'resources/img/bottom-v-head.png');
    });

    $("#slider-bottom-v-tail").pathslider({
        points     : [ 100,100,   0,0,   0,0,  100,175  ],
        value      : 50,
        rotateGrip : true,
        tolerance  : 3,
        range      : 30,
        curve      : { width:1, color:"#d0d0d0", cap:"round" },
        stop     : function(e, slider){
            sendAction("UG", slider.percent);
        }
    });
    $("#slider-bottom-v-tail .pathslider-grip").hover(function(){
        $('#image-container > img').show().attr('src', 'resources/img/bottom-v-tail.png');
    });

    $('#btn-head').click(function(){
        $('#slider-header-h').toggleVisibility();
        $('#slider-header-v').toggleVisibility();
        var sign = $(this).find('i').hasClass('glyphicon-plus') ? 'glyphicon-minus' : 'glyphicon-plus';
        var signToRemove = $(this).find('i').hasClass('glyphicon-plus') ? 'glyphicon-plus' : 'glyphicon-minus';
        $(this).find('i').removeClass(signToRemove).addClass(sign);
    });

    $('#btn-middle').click(function(){
        $('#slider-middle-h').toggleVisibility();
        $('#slider-middle-v').toggleVisibility();
        var sign = $(this).find('i').hasClass('glyphicon-plus') ? 'glyphicon-minus' : 'glyphicon-plus';
        var signToRemove = $(this).find('i').hasClass('glyphicon-plus') ? 'glyphicon-plus' : 'glyphicon-minus';
        $(this).find('i').removeClass(signToRemove).addClass(sign);
    });

    $('#btn-bottom').click(function(){
        $('#slider-bottom-h').toggleVisibility();
        $('#slider-bottom-v-tail').toggleVisibility();
        $('#slider-bottom-v-head').toggleVisibility();
        var sign = $(this).find('i').hasClass('glyphicon-plus') ? 'glyphicon-minus' : 'glyphicon-plus';
        var signToRemove = $(this).find('i').hasClass('glyphicon-plus') ? 'glyphicon-plus' : 'glyphicon-minus';
        $(this).find('i').removeClass(signToRemove).addClass(sign);
    });

    $('#btn-calibrate').click(function(){
        sendCalibrate();
    });

    $(mainContainer).on('seat.pf', function(e, obj){

        console.log('pf moved');

        var percent = parseInt(obj.data);

        var difference = (lastPercentage['bottom-v-head'] - percent)/4;

        newSeat.point4.end.y -= difference;
        newSeat.point4.bezierEnd.y -= difference;
        newSeat.point5.bezierStart.y -= difference;
        redrawSeat(newSeat);

        lastPercentage['bottom-v-head'] = percent;

        $("#slider-bottom-v-head").pathslider(percent, function(slider){
//            slider.update();
        });

    }).on('seat.po', function(e, obj){

        console.log('po moved');
        var percent = parseInt(obj.data);
        var difference = (lastPercentage['top'] - percent)/4;

        newSeat.point1.start.x -= difference;
        newSeat.point2.bezierStart.x -= difference;
        newSeat.point2.bezierEnd.x -= difference;
        newSeat.point2.end.x -= difference;
        newSeat.point3.bezierStart.x -= difference;
        newSeat.point6.bezierEnd.x -= difference/2;
        newSeat.point6.end.x -= difference/2;
        newSeat.point7.bezierStart.x -= difference/2;
        newSeat.point7.bezierEnd.x -= difference/2;
        newSeat.point7.end.x -= difference/2;
        newSeat.point8.bezierStart.x -= difference/2;
        newSeat.point8.bezierEnd.x -= difference;
        newSeat.point8.end.x -= difference;
        newSeat.point9.end.x -= difference;
        newSeat.point9.bezierStart.x -= difference;
        newSeat.point9.bezierEnd.x -= difference;
        redrawSeat(newSeat);

        lastPercentage['top'] = percent;

        $("#slider-top").getpathslider().setSlider(percent)
        $("#slider-top").getpathslider().update();

    }).on('seat.sp', function(e, obj){

        var percent = parseInt(obj.data);

        var difference = (lastPercentage['bottom'] - percent)/4;

        for (var point in newSeat) {
            for (var desc in newSeat[point]) {
                for (var pos in newSeat[point][desc]) {
                    newSeat[point][desc].x -= difference;
                }
            }
        }
        redrawSeat(newSeat);

        $('.canvas-container-content > :not(canvas)').css('margin-left', '-=' + difference + 'px');

        lastPercentage['bottom'] = percent;

        $("#slider-bottom").getpathslider().setSlider(percent)
        $("#slider-bottom").getpathslider().update();

    }).on('seat.zg', function(e, obj){

        var percent = parseInt(obj.data);
        var difference = (lastPercentage['header-v'] - percent)/4;

        newSeat.point1.start.y -= difference;
        newSeat.point9.end.y -= difference;
        newSeat.point2.bezierStart.y -= difference;
        newSeat.point9.bezierEnd.y -= difference;
        redrawSeat(newSeat);

        lastPercentage['header-v'] = percent;

        $("#slider-header-v").getpathslider().setSlider(percent);
//        $("#slider-header-v").getpathslider().update();

    }).on('seat.wf', function(e, obj){

        var percent = parseInt(obj.data);
        var difference = (lastPercentage['bottom-h'] - percent)/4;

        newSeat.point4.end.x -= difference;
        newSeat.point4.bezierEnd.x -= difference;
        newSeat.point5.bezierStart.x -= difference;
        redrawSeat(newSeat);

        lastPercentage['bottom-h'] = percent;

        $("#slider-bottom-h").pathslider(percent, function(slider){
//            slider.update();
        });
    }).on('seat.gg', function(e, obj){

        var percent = parseInt(obj.data);
        var difference = (lastPercentage['middle-v'] - percent)/4;

        newSeat.point6.end.y -= difference;
        newSeat.point6.bezierEnd.y -= difference;
        newSeat.point7.bezierStart.y -= difference;
        newSeat.point7.end.y -= difference;
        newSeat.point7.bezierEnd.y -= difference;
        redrawSeat(newSeat);

        lastPercentage['middle-v'] = percent;

        $("#slider-middle-v").pathslider(percent, function(slider){
//            slider.update();
        });
    }).on('seat.ug', function(e, obj){

        var percent = parseInt(obj.data);
        var difference = (lastPercentage['bottom-v-tail'] - percent)/4;

        newSeat.point3.end.y -= difference;
        newSeat.point3.bezierEnd.y -= difference;
        newSeat.point4.bezierStart.y -= difference;
        newSeat.point5.end.y -= difference;
        newSeat.point5.bezierEnd.y -= difference;
        newSeat.point6.bezierStart.y -= difference;
        redrawSeat(newSeat);

        lastPercentage['bottom-v-tail'] = percent;

        $("#slider-bottom-v-tail").pathslider(percent, function(slider){
//            slider.update();
        });
    }).on('seat.gw', function(e, obj){

        var percent = parseInt(obj.data);
        var difference = (lastPercentage['middle-h'] - percent)/4;

        newSeat.point7.bezierStart.x -= difference;
        newSeat.point7.bezierEnd.x -= difference;
        redrawSeat(newSeat);

        lastPercentage['middle-h'] = percent;

        $("#slider-middle-h").pathslider(percent, function(slider){
//            slider.update();
        });
    }).on('seat.pz', function(e, obj){

        var percent = parseInt(obj.data);
        var difference = (lastPercentage['header-h'] - percent)/4;

        newSeat.point1.start.x -= difference;
        newSeat.point9.end.x -= difference;
        newSeat.point2.bezierStart.x -= difference;
        newSeat.point9.bezierEnd.x -= difference;
        redrawSeat(newSeat);

        lastPercentage['header-h'] = percent;

        $("#slider-header-h").pathslider(percent, function(slider){
//            slider.update();
        });
    });

});


// websocket stuff
var subSocket;

$(function () {
    var localhost_url = 'http://pawelszczerbicki.pl:8080/armchair/rest/message/device';
    var content = $('#content');
    var input = $('#input');
    var status = $('#status');
    var statusLed = $('#connection-status-led');
    var logged = false;
    var socket = $.atmosphere;
    var request = { url: localhost_url,
        contentType: "application/json",
        logLevel: 'debug',
        transport: 'websocket',
        trackMessageLength: true,
        fallbackTransport: 'long-polling'};

    request.onOpen = function (response) {
        //TODO FLOWER show diode on frontend
        console.log("connected to WS, add some diode on frontend");
    };

    var calibrating = false;

    request.onMessage = function (response) {
        var msgBody = response.responseBody;
        console.log("response body: " + msgBody);
        onAnyMessage();
        try {
            var msg = jQuery.parseJSON(msgBody);
        } catch (e) {
            console.log('This doesn\'t look like a valid JSON: ', msgBody);
            return;
        }
        var type = msg.type;

        if (type === "HEARTBEAT") {
             //TODO FLOWER - do nothing, or show somethink on diode
            onHeartbeat();
        } else if (type === "RESPONSE") {

            if(msg.data === 'START_MOVING') {
                blockUI();
            }
            else if(msg.data === 'STOP_MOVING') {
                if(!calibrating)
                    $.unblockUI();
            }
            else {

                switch (msg.code) {
                    case "PF":
                        $(mainContainer).trigger('seat.pf', { data : msg.data });
                        break;
                    case "PO":
                        $(mainContainer).trigger('seat.po', { data : msg.data });
                        break;
                    case "SP":
                        $(mainContainer).trigger('seat.sp', { data : msg.data });
                        break;
                    case "ZG":
                        $(mainContainer).trigger('seat.zg', { data : msg.data });
                        break;
                    case "WF":
                        $(mainContainer).trigger('seat.wf', { data : msg.data });
                        break;
                    case "GG":
                        $(mainContainer).trigger('seat.gg', { data : msg.data });
                        break;
                    case "UG":
                        $(mainContainer).trigger('seat.ug', { data : msg.data });
                        break;
                    case "GW":
                        $(mainContainer).trigger('seat.gw', { data : msg.data });
                        break;
                    case "PZ":
                        $(mainContainer).trigger('seat.pz', { data : msg.data });
                        break;

                }

//                if(!calibrating)
//                    $.unblockUI();
            }

            //TODO FLOWER serice it!
            console.log("it is state of element" + msg.data);
            console.log("it is chair element" + msg.code);
        } else if(type === "CALIBRATE") {
            if(msg.data === "START") {
                console.log("calibrating started");
                calibrating = true;
                //blockUI();
            }
            else if(msg.data === "STOP") {
                console.log("calibrating finished");
                calibrating = false;
                $.unblockUI();
            }
        } else if (type === "LOG") {
            $('#textarea-log').append(msg.data + '\n').scrollTop($('#textarea-log')[0].scrollHeight);
            console.log("logs from device" + msg.data)
        }
    };


    request.onClose = function (response) {
        logged = false;
    }

    request.onError = function (response) {
        statusLed.removeClass('led-green').addClass('led-red');
        content.html($('<p>', { text: 'Sorry, but there\'s some problem with your '
            + 'socket or the server is down' }));
    };

    subSocket = socket.subscribe(request);

    function onHeartbeat() {
        flicker(statusLed, "led-green", "led-blue", 200);
    }

    function onAnyMessage() {
        flicker(statusLed, "led-green", "led-orange", 100);
    }

    function flicker(object, removeClass, addClass, time) {
        object.removeClass(removeClass).addClass(addClass);
        setTimeout(function () {
            object.removeClass(addClass).addClass(removeClass);
        }, time);
    }
});

function sendAction(code, data) {
    subSocket.push(jQuery.stringifyJSON({data: data, type: 'ACTION', code: code }));
}

function sendCalibrate() {
    subSocket.push(jQuery.stringifyJSON({type: 'CALIBRATE'}));
}

function blockUI(){
    $.blockUI({
        message: 'Seat is moving...',
        css: {
            border: 'none',
            padding: '15px',
            backgroundColor: '#000',
            '-webkit-border-radius': '10px',
            '-moz-border-radius': '10px',
            opacity: .5,
            color: '#fff'
        },
        overlayCSS:  {
            backgroundColor: '#666',
            opacity:         0.1,
            cursor:          'wait'
        }
    });
}

jQuery.fn.toggleVisibility = function() {
    if ($(this).css('visibility') == 'hidden'){
        $(this).css('visibility', 'visible');
    } else {
        $(this).css('visibility', 'hidden');
    }
}
