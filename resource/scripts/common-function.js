function create_token(){
	$.get("randomToken", function(result){
	    $("#token").val(result); 
	  });
}
$(function(){
	$("select").each(function(){
		var select=$(this);
		if(select.attr("child")!=null){
			select.change(function(){
				$.get(select.attr("url")+select.val(), function(result){
					$("#"+select.attr("child")).empty();
					$("#"+select.attr("child")).append(result); 
				  });
			});
		}
		if(select.attr("preload")!=null){
			$.get(select.attr("url"), function(result){
				select.empty();
				select.append(result); 
				if(select.attr("defaultValue")!=null){
					select.val(select.attr("defaultValue"));
				}
			  });
		}
		
	});
});
$(document).ready(function()
        {
            $(".validate").validate();
	$("select").each(function(){
		if($(this).attr("defaultValue")!=null){
			$(this).val($(this).attr("defaultValue"));
		}
	});
            $("th .i-checks").on("ifChanged",function(){
                if($(this).is(":checked")){
                    $("td .i-checks").iCheck('check');
                }else{
                    $("td .i-checks").iCheck('uncheck');
                }
            });
            $( '.ladda-button' ).ladda( 'bind', { timeout: 100000 } );
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });

            $("input[type=text]").each(function(){
		if($(this).val()==null||$(this).val()==""){
			$(this).val($(this).attr("defaultValue"));
		}
	});
	$("textarea").each(function(){
		if($(this).html()==null||$(this).html()==""){
			$(this).html($(this).attr("defaultValue"));
		}
	});
        });
$(function(){
	 
	$(window).scroll(function(){  //只要窗口滚动,就触发下面代码 
		var scrollt = document.documentElement.scrollTop + document.body.scrollTop; //获取滚动后的高度 
		if( scrollt >200 ){  //判断滚动后高度超过200px,就显示  
			$("#gotop").fadeIn(400); //淡出     
		}else{      
			$("#gotop").stop().fadeOut(400); //如果返回或者没有超过,就淡入.必须加上stop()停止之前动画,否则会出现闪动   
		}
	});
	$("#gotop").click(function(){ //当点击标签的时候,使用animate在200毫秒的时间内,滚到顶部
			$("html,body").animate({scrollTop:"0px"},200);
	});
});

$(function(){
	var notclick=true;
	var shak=function(){
		$("#gobottom").fadeIn(1000,function(){
		if(notclick)	
		$("#gobottom").stop().fadeOut(1000,shak);
		
	});
	}
	$(window).scroll(function(){
		var scrollt = document.documentElement.scrollTop + document.body.scrollTop;
		if( scrollt>$(document).height()-$(window).height()-10){ 
			$("#gobottom").stop().fadeOut(400);
		}else{ 
            shak();
		}
	});
	$("#gobottom").find("span").html("点我可<br>到底部");
	$("#gobottom").click(function(){ //当点击标签的时候,使用animate在200毫秒的时间内,滚到顶部
			$("html,body").animate({scrollTop:$(document).height()},200);
			notclick=false;
	});
});
$(function(){
	$("iframe.autoSize").load(function()  
        {  
            $(this).height($(this).contents().find("body").height());  
        }  
    );  
});
$(function(){
	$("input.validate-integer").each(function(){
		if($(this).val()==""){
			$(this).val("0");
		}
	});
});
/*$(function(){
	$("select").each(function(){
		if($(this).is(":visible")==true){
		var length=0;
		$(this).find("option").each(function(){
			length++;
		});
		if(length>20){
		var offset=$(this).offset();
		$("body").append($("<input type='button' id='metrobtn_"+$(this).attr("name")+"' value='■' style='position:absolute;left:"+(offset.left+$(this).width()-20)+"px;top:"+(offset.top)+"px'>"));
		var names=new Array();
		var select=$(this);
		$(this).find("option").each(function(){
			if($(this).text()!=""){
				var s=$(this).text().substring(0,1);
				var has=false;
				for(var i=0;i<names.length;i++){
					if(names[i].value==s){
						has=true;
						break;
					}
				}
				if(!has)names.push({value:s,py:makePy(s.trim())});
			}
			
		});
		var metroIndexText="<div class='metropanel' style='width:"+$(this).width()+"px;position:absolute;left:"+(offset.left)+"px;top:"+(offset.top+20)+"px'><div><input type='text' id='queryfield'></div><div id='button_panel'>";
		for(var i=0;i<names.length;i++){
			metroIndexText+="<input type='button' value='"+names[i].value+"'>";
		}
		metroIndexText+="</div></div>";
		var metroIndex=$(metroIndexText);
		$("#metrobtn_"+$(this).attr("name")).click(function(event){
			$(".metropanel").remove();
			$(".metrolist").remove();
			event.stopPropagation();
			$("body").append(metroIndex);
			$("#queryfield").click(function(event){
				event.stopPropagation();
			});
			$("#queryfield").keyup(function(event){
			});
			$(".metropanel").find("input[type=button]").click(function(event){
				event.stopPropagation();
				var button=$(this);
				var metroList="<div class='metrolist' style='width:"+select.width()+"px;position:absolute;left:"+(offset.left)+"px;top:"+(offset.top+20)+"px'><table width='100%'>";
				select.find("option").each(function(){
					if($(this).text().substring(0,1)==button.val()){
						metroList+="<tr><td><a myvalue='"+$(this).val()+"'>"+$(this).text()+"</a></td></tr>";
					}
				});
				metroList+="</table></div>";
				$("body").append($(metroList));
				$(".metrolist").find("a").click(function(event){
					event.stopPropagation();
					select.val($(this).attr("myvalue"));
					$(".metrolist").remove();
					select.trigger("change");
				});
				$(".metropanel").remove();
			});
		});
		$(document).click(function(){
			$(".metropanel").remove();
			$(".metrolist").remove();
		});
	}
		}
	});
});*/
$(function(){
	$("select").each(function(){
		if($(this).is(":visible")==true){
		var length=0;
		$(this).find("option").each(function(){
			length++;
		});
		if(length>20){
		var offset=$(this).offset();
		$("body").append($("<input type='text' id='queryfield_"+$(this).attr("name")+"'  style='text-transform:uppercase;text-align:right;background-color:transparent;border:none;position:absolute;width:"+($(this).width()-20)+"px;height:"+$(this).height()+"px;left:"+(offset.left)+"px;top:"+(offset.top)+"px'>"));
		var names=new Array();
		var select=$(this);
		$(this).find("option").each(function(){
			if($(this).text()!=""){
				var s=$(this).text().trim();
				names.push({value:s,option:$(this).val(),py:makePy(s)});
			}
			
		});
		$("#queryfield_"+$(this).attr("name")).blur(function(event){$(this).val("")});
		$("#queryfield_"+$(this).attr("name")).keyup(function(event){
			$(".metrolist").remove();
			var metroList="<div class='metrolist' style='width:"+select.width()+"px;position:absolute;left:"+(offset.left)+"px;top:"+(offset.top+20)+"px'><table width='100%'>";
			metroList+="<tr><td><a myvalue=''>无</a></td></tr>";
				for(var i=0;i<names.length;i++){
					var has=false;
					for(var j=0;j<names[i].py.length;j++){
						if(names[i].py[j].indexOf($(this).val().toUpperCase())==0){has=true;break;}
					}
					if(has){
						metroList+="<tr><td><a myvalue='"+names[i].option+"'>"+names[i].value+"</a></td></tr>";
					}
				}
				metroList+="</table></div>";
				$("body").append($(metroList));
				$(".metrolist").find("a").click(function(event){
					event.stopPropagation();
					select.val($(this).attr("myvalue"));
					$(".metrolist").remove();
					select.trigger("change");
				//	$("#queryfield_"+select.attr("name")).val($(this).text());
				});
				$(".metropanel").remove();
		});
		$(document).click(function(){
			$(".metrolist").remove();
		});
	}
		}
	});
});
$(function(){
	var gostep=true;
	var step=function(){
		$("#persent").load("step.jsp",function(){
			var length=new Number($("#persent").html());
			$("#step_content").animate({width:length+"%"},500,function(){
				if(length==100){
					$("#step_state").remove();
					gostep=false;
				}
			});
		});
		if(gostep)
		setTimeout(step, 500);
	}
	$(".percent-export").each(function(){
		$(this).click(function(){
			gostep=true;
			$("body").append($("<div id='step_state'><div id='cover'></div><div  style='padding:10px;margin:60px auto;width:600px;font-size:18px;color:#fff;text-align:center;background:#000;overflow:hidden;'>处理中，请稍后...<div id='persent' style='display:inline'>0</div>%<div style='background:#fff;width:100%'><div id='step_content' style='height:20px;width:0px;background:#339933;'></div></div></div></div>"));
			$("#step_state").css({
			left:"0px",
			top:"0px",
			width:$("body").width()+"px",
			height:$("body").height()+"px",
			position:"absolute"
			});
			setTimeout(step, 500);
		});
	});
});
$(function(){
	var theimg=$("<div style='display:none;position:absolute;z-index:9999;'><img width='180'/></div>");
	 $("body").append(theimg);
	$("body").delegate(".preview","mouseover",function(){
		var thisoffset=$(this).offset();
		theimg.css({left:(thisoffset.left+$(this).width())+"px",top:thisoffset.top+"px"});
		 theimg.find("img").attr("src",$(this).attr("imgsrc"));
		  theimg.show(100);
		});
	$("body").delegate(".preview","mouseout",function(){
		theimg.hide(100);
	});
    initdatepicker();
});
/*
 * 创建时间选择器
 */
function createTimeRangePicker(o){
    var startdate=o.find(".start");
    var enddate=o.find(".end");
    o.find("#date-range").daterangepicker({
        timePicker: true,
        timePickerIncrement: 10,
        format: 'YYYY-MM-DD HH:mm',
        startDate: startdate.val(),
        endDate: enddate.val(),
        locale: {
            applyLabel: '确定',
            cancelLabel: '取消',
            fromLabel: '从',
            toLabel: '至',
            weekLabel: '周',
            customRangeLabel: '日期范围',
            daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
            monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',
                '七月', '八月', '九月', '十月', '十一月', '十二月' ],
            firstDay: 0
        }
    }, function(start, end){
        o.find('#date-range .date-title').html(start.format('YYYY-MM-DD HH:mm') + ' 至 ' + end.format('YYYY-MM-DD HH:mm'));
        startdate.val(start.format('YYYY-MM-DD HH:mm'));
        enddate.val(end.format('YYYY-MM-DD HH:mm'));
        $('input[name=byTime]').prop('checked', true).trigger('change');
    });
}
/*
 * 创建时间选择器
 */
function createDateRangePicker(o){
    var startdate=o.find(".start");
    var enddate=o.find(".end");
    o.find("#date-range").daterangepicker({
        format: 'YYYY-MM-DD',
        startDate: startdate.val(),
        endDate: enddate.val(),
        locale: {
            applyLabel : '确定',
            cancelLabel : '取消',
            fromLabel : '起始时间',
            toLabel : '结束时间',
            customRangeLabel : '自定义',
            daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
            monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',
                '七月', '八月', '九月', '十月', '十一月', '十二月' ],
            firstDay : 1
        }
    }, function(start, end){
        o.find('#date-range .date-title').html(start.format('YYYY-MM-DD') + ' 至 ' + end.format('YYYY-MM-DD'));
        startdate.val(start.format('YYYY-MM-DD'));
        enddate.val(end.format('YYYY-MM-DD'));
        $('input[name=byTime]').prop('checked', true).trigger('change');
    });
}
/*
 * 实例日期选择器区域
 */
function initdatepicker() {

    $("div.date_picker").each(function(){
        var parent=$(this);
        if(parent.find(".start").val()==""){
            if(parent.attr("type")=="earlier")
                rangeback(parent,7);
            else
                range(parent,7);
        }
        createDateRangePicker(parent);
        $(this).find(".date-section a").click(function(){
            days=$(this).attr("value");
            range(parent,days);
            createDateRangePicker(parent);
            $('input[name=byTime]').prop('checked', true).trigger('change');
        });
        $(this).find(".earlier a").click(function(){
            days=$(this).attr("value");
            rangeback(parent,days);
            createDateRangePicker(parent);
            $('input[name=byTime]').prop('checked', true).trigger('change');
        });
    });
    $("div.time_picker").each(function(){
        var parent=$(this);
        if(parent.find(".start").val()==""){
            if(parent.attr("type")=="earlier")
                rangebacktime(parent,7);
            else
                rangetime(parent,7);
        }
        createTimeRangePicker(parent);
        $(this).find(".date-section a").click(function(){
            days=$(this).attr("value");
            rangetime(parent,days);
            createTimeRangePicker(parent);
            $('input[name=byTime]').prop('checked', true).trigger('change');
        });
        $(this).find(".earlier a").click(function(){
            days=$(this).attr("value");
            rangebacktime(parent,days);
            createTimeRangePicker(parent);
            $('input[name=byTime]').prop('checked', true).trigger('change');
        });
    });
}
/*
 * 设置日期区间
 */
function range(parent,days) {
    var start = moment().format('YYYY-MM-DD');
    var end = moment().add('days', 0 + days).format('YYYY-MM-DD');
    parent.find('#date-range .date-title').html(start + ' 至 ' + end);
    parent.find(".start").val(start);
    parent.find(".end").val(end);
}

/*
 * 设置日期区间
 */
function rangeback(parent,days) {
    var end = moment().format('YYYY-MM-DD');
    var start = moment().add('days', 0 - days).format('YYYY-MM-DD');
    parent.find('#date-range .date-title').html(start + ' 至 ' + end);
    parent.find(".start").val(start);
    parent.find(".end").val(end);
}
/*
 * 设置日期时间区间
 */
function rangetime(parent,days) {
    var start = moment().format('YYYY-MM-DD HH:mm');
    var end = moment().add('days', 0 + days).format('YYYY-MM-DD HH:mm');
    parent.find('#date-range .date-title').html(start + ' 至 ' + end);
    parent.find(".start").val(start);
    parent.find(".end").val(end);
}

/*
 * 设置日期时间区间
 */
function rangebacktime(parent,days) {
    var end = moment().format('YYYY-MM-DD HH:mm');
    var start = moment().add('days', 0 - days).format('YYYY-MM-DD HH:mm');
    parent.find('#date-range .date-title').html(start + ' 至 ' + end);
    parent.find(".start").val(start);
    parent.find(".end").val(end);
}