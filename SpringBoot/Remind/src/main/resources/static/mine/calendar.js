
function getLocalTime(nTime){
    var format = '';
    format += nTime.getFullYear()+"-";
    format += (nTime.getMonth()+1)<10?"0"+(nTime.getMonth()+1):(nTime.getMonth()+1);
    format += "-";
    format += nTime.getDate()<10?"0"+(nTime.getDate()):(nTime.getDate());
    format += "T";
    format += nTime.getHours()<10?"0"+(nTime.getHours()):(nTime.getHours());
    format += ":";
    format += nTime.getMinutes()<10?"0"+(nTime.getMinutes()):(nTime.getMinutes());
    format += ":00";
    return format;
}

function getData(url, param, type){
    var result;
    $.ajax({
        async:false,
　　　　url : url,
　　　　type : 'get',
　　　　data : param,
　　　　dataType : type,

　　　　success: function(data){
            result = data
        },
        
        error:function(response){
          alert(response.tip);
        }
    });
    return result;
}

//从原始数据，到calendar中数据的转化
function getEvents(data){
    events=[];
    data.forEach(function(e){
        var tEvents = {};
        tEvents.id = e.id;
        tEvents.title = e.description;
        
        if(typeof(e.startDate) == 'string'){
            tEvents.start = new Date(e.startDate);
        }
        else {
            tEvents.start = e.startDate;
        }
        
        if(typeof(e.endDate) == 'string'){
            tEvents.end = new Date(e.endDate);
        }
        else {
            tEvents.end = e.endDate;
        }
        
        events.push(tEvents);
    });
    return events;
}

function queryByType(){
    var name = $("#query_type")[0].value;
    calendar.removeAllEvents()
    
    if( name =='' ) {
        getEvents(rawData).forEach(e => calendar.addEvent(e));
    }
    else{
        getEvents(rawData.filter(e => e.type == name)).forEach(e => calendar.addEvent(e));
    }
    
}

function addType(name) {
    var name = $("#query_type")[0].value;
    var param = {};
    param.name = name;
    result = getData('EventType/add', param, 'text');
    if(result.indexOf('Added') == -1 ) {
        alert(result);
    }
}

function deleteType(name) {
    var name = $("#query_type")[0].value;
    var param = {};
    param.name = name;
    result = getData('EventType/delete', param, 'text');
    if(result.indexOf('Deleted') == -1 ) {
        alert(result);
    }
    $("#query_type")[0].value = '';
}

function addEvent(startDate){
    
    return function(){
        var result;
        var param = {};

        param.type = $("#type")[0].value;
        param.description = $("#description")[0].value;
        param.startDate = startDate;
        
        if(param.description != '' && param.type !='') {
            result = getData('Event/add', param, 'text');
            if(result.indexOf('Added') != -1 ) {

                param.id = result.split(',')[1]
                rawData.push(param);
                calendar.addEvent(getEvents([param])[0]);
                
                $("#type")[0].value = '';
                $("#description")[0].value = '';

            }
            else {
                alert(result);
            }
            modal.style.display = "none";
        }
        else{
            $('#prompt')[0].innerHTML = '有空值,无法提交！！';
        }
    }
}


function updateEvent(uEvent){
    
    return function(){
        var result;
        var param = {};
        
        param.id = uEvent.id;
        param.type = $("#edit_type")[0].value;
        param.description = $("#edit_description")[0].value;
        
        if($("#edit_start")[0].value != '') {
           param.startDate = new Date($("#edit_start")[0].value);
        } else { 
            alert("开始日期为空！！");
            return;
        }
        
        if($("#edit_end")[0].value != '') {
           param.endDate = new Date($("#edit_end")[0].value);
        }
        
        if(param.description != '' && param.type != '') {
            result = getData('Event/update', param, 'text');
            if(result.indexOf('Updated') != -1 ) {
                
                tRaw = rawData.filter(e => e.id == uEvent.id)[0]
                tRaw.type = param.type;
                tRaw.description = param.description;
                tRaw.startDate = param.startDate;
                
                //注意js中这种情况，endDate没定义也不出错
                tRaw.endDate = param.endDate;
                
                //重新添加事件，防止时间起止变化。
                uEvent.remove();
                calendar.addEvent(getEvents([param])[0]);
                $('#updEvent')[0].onclick = null;
                $("#edit_type")[0].value = '';
                $("#edit_description")[0].value = '';
                $("#edit_start")[0].value = '';
                $("#edit_end")[0].value = '';
            }
            else {
                alert(result);
            }
            
        }
        else{
            $('#prompt')[0].innerHTML = '有空值！！无法提交'
        }
    }
}


function deleteEvent(dEvent){
    
    //尝试一下闭包，以减少全局变量的使用
    //用于设定点击删除函数的id
    //单个参数所以没办法进行柯力化
    return function (){
        var r = confirm("确认删除？")
        if (r == true){
            var result;
            var param = {};
            param.id = dEvent.id;
            
            if(param.description != '' && param.type !='') {
                result = getData('Event/delete', param, 'text');
                if(result.indexOf('Deleted') != -1 ) {
                    
                    //删除日历中的事件
                    dEvent.remove();
                    $('#delEvent')[0].onclick = null;
                    $("#edit_type")[0].value = '';
                    $("#edit_description")[0].value = '';
                    $("#edit_start")[0].value = '';
                    $("#edit_end")[0].value = '';
                }
            }
            else{
                alert('删除失败')
            }
        }
    }
    
}


//原始数据
var rawData = getData('Event/all', {}, 'json');

// 弹窗
var modal = $("#myModal")[0];
// 获取 <span> 元素，用于关闭弹窗 that closes the modal
// 点击 <span> (x), 关闭弹窗
var span = $("span.close")[0];
span.onclick = function() {
    modal.style.display = "none";
}

//点击其他地方，弹窗消失
//window.onclick = function(event) {
//    if (event.target == modal) {
//        modal.style.display = "none";
//    }
//}

//日历
var calendarEl = document.getElementById('calendar');

var calendar = new FullCalendar.Calendar(calendarEl, {
    plugins: [ 'interaction', 'dayGrid', 'timeGrid', 'list' ],
    header: {
        left: 'prev,next today',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth'
    },
    defaultDate: '2019-04-12',
    navLinks: true, // can click day/week names to navigate views
    selectable: true,
    selectMirror: true,
    
    select: function(arg) {
        $('#addEvent')[0].onclick = addEvent(arg.start);
        modal.style.display = "block";
    },
    
    editable: true,
    eventLimit: true, // allow "more" link when too many events
    events: getEvents(rawData),
    
    eventClick: function(info) {

        //cong原始数据中根据id筛出数据 
        tRaw = rawData.filter(e => e.id == info.event.id)[0]
        $("#edit_type")[0].value = tRaw.type;
        $("#edit_description")[0].value = tRaw.description;
        
        //目前能找到的合适的日期格式。。
        $("#edit_start")[0].value = getLocalTime(info.event.start);
        if( info.event.end != null ){
            $("#edit_end")[0].value = getLocalTime(info.event.end);
        }

        //将闭包返回函数给予删除按钮
        $('#updEvent')[0].onclick = updateEvent(info.event);
        $('#delEvent')[0].onclick = deleteEvent(info.event);

        // change the border color just for fun
        info.el.style.borderColor = 'red';
    }
});

calendar.render();
