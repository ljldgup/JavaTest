

var stompClient = null;
var record = new Map();
var myChart = null
var cat = []
var values = []
//这里等待文档就绪才能初始化，
$(document).ready(function(){
    myChart = echarts.init(document.getElementById('charts'));
    myChart.setOption(option);
});

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
            chartsUpdate(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});


option = {
    title : {
        text: 'monitor data',
    },
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['amount']
    },
    toolbox: {
        show : true,

    },
    calculable : true,
    xAxis : [
        {
            type : 'category',
            data : ['test']
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'amount',
            type:'bar',
            data:[1],
            markPoint : {
                data : [
                    {type : 'max', name: '最大值'}
                ]
            }
        }
    ]
};

function chartsUpdate(data){
    t = cat.indexOf(data)
    if(t==-1){
        cat.push(data)
        values.push(1)
    }
    else{
        values[t] += 1;
    }
    option = {
        title : {
            text: 'monitor data',
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['amount']
        },
        toolbox: {
            show : true,

        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                data : cat
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'amount',
                type:'bar',
                data:values,
                markPoint : {
                    data : [
                        {type : 'max', name: '最大值'}
                    ]
                }
            }
        ]
    };
    myChart.setOption(option)
}
