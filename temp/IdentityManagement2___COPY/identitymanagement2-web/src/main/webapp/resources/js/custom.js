$(document).ready(function () {
	
	/*if (cookie.get('sidebarView') === 'hide'){
		if($('.left-side').hasClass('collapse-left')) {
			$('.navbar-btn').trigger("click");
		}
	} else {
		$('.navbar-btn').trigger("click");
	}*/
	
	
	/*Calendar.setup({
	    cont          : "calendar-container",
	    weekNumbers   : true,
	    selectionType : Calendar.SEL_MULTIPLE,
	    selection     : Calendar.dateToInt(new Date()),
	    showTime      : 12,
	    onSelect      : function() {
	        var count = this.selection.countDays();
	        if (count == 1) {
	            var date = this.selection.get()[0];
	            date = Calendar.intToDate(date);
	            date = Calendar.printDate(date, "%A, %B %d, %Y");
	            $("calendar-info").innerHTML = date;
	        } else {
	            $("calendar-info").innerHTML = Calendar.formatString(
	                "${count:no date|one date|two dates|# dates} selected",
	                { count: count }
	            );
	        }
	    },
	    onTimeChange  : function(cal) {
	        var h = cal.getHours(), m = cal.getMinutes();
	        // zero-pad them
	        if (h < 10) h = "0" + h;
	        if (m < 10) m = "0" + m;
	        $("calendar-info").innerHTML = Calendar.formatString("Time changed to ${hh}:${mm}", {
	            hh: h,
	            mm: m
	        });
	    }
	});*/
	
/*	Calendar.setup({
        trigger    : "calendar-inputField",
        inputField : "calendar-inputField",
        onSelect   : function() { this.hide() }
    });*/
	
	$("input[type='checkbox'], input[type='radio']").iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal'
    });
	
	if (typeof $.fn.slimScroll != 'undefined') {
		//Destroy if it exists
		$(".sidebar").slimScroll({destroy: true}).height("auto");
		//Add slimscroll
		$(".sidebar").slimscroll({
			height: ($(window).height() - $(".main-header").height()) + "px",
			color: "#596469",
			alwaysVisible: true,
			size: "11px"
		});
	}
});

var stack_topleft = 	{"dir1": "down", 	"dir2": "right", 	"push": "top"};
var stack_toplright = 	{"dir1": "down", 	"dir2": "left", 	"push": "top"};
var stack_bottomleft = 	{"dir1": "right", 	"dir2": "up", 		"push": "top"};
var stack_bar_top = 	{"dir1": "down", 	"dir2": "right", 	"push": "top", 	"spacing1": 0, "spacing2": 0};
var stack_bar_bottom = 	{"dir1": "up", 		"dir2": "right", 	"spacing1": 0, 	"spacing2": 0};
var stack_context = 	{"dir1": "down", 	"dir2": "left", 	"context": $("#stack-context")};

function showTopleftGrowl(type, title, message) {
    var opts = {
        title: title,
        text: message,
        addclass: "stack_toplright",
        styling: "bootstrap3",
        type: type,
        delay: 5000,
        animation: "slide",
        stack: stack_toplright
    };
    new PNotify(opts);
}

function showError(status){
	var msg = messages[status];
	if(!msg){
		msg = status;
	}
	showTopleftGrowl('error', 'Error', msg);
}

function transportFunction(params){
	var ajax = $.ajax(params).fail(failAjax);
	return ajax;
}

var failAjax = function(jqXHR, textStatus, error, btnId){
	if(!error || error != 'abort'){
		var msg = 'Command failed';
		
		if(btnId){
			$('#'+btnId).attr('disabled', false);
		}
		
		if(jqXHR.status > 0){
			msg = messages[jqXHR.status];
			if(!msg){
				msg = jqXHR.status;
			}
		}
		else {
			msg = messages[textStatus];
			if(!msg){
				msg = textStatus;
			}
		}
		showTopleftGrowl('error', 'Error', msg);
	}
};

function booleanRender(entity, type, full){
	if(entity){
		return '<div class="text-center"><i class="fa fa-check-circle fa-2x text-success"></i></div>';
	}
	return '<div class="text-center"><i class="fa fa-minus-circle fa-2x text-danger"></i></div>';
}

function viewShipmentRender(entity, type, full){
	return '<a href="/identitymanagement2-web/agent/shipment/view/'+entity+'">'+entity+'</a>';
}

function viewManifestRender(entity, type, full){
	return '<a href="/identitymanagement2-web/agent/manifest/view/'+entity+'">'+entity+'</a>';
}

function hierarchicalIndent(entity, type, full){
	var data = entity;
	if(full.sortField){
		data = '';
		$.each(full.sortField.split('-'), function(i, v){
			if(i > 0)
				data += '&nbsp;&nbsp;.&nbsp;&nbsp;';
		});
		
		data += " " + entity;
	}
	
	return data;
}

function shipmentStateLabel(state, locale) {

	var userLocale = $('input[name=user-locale]').val();
	if(locale){
		userLocale = locale;
	}
	
	if(!userLocale){
		userLocale = 'fa_IR';
	}
	
	var value = shipmentStateLabelValue(state, locale);
	
	var d = '';
	if (state === 'DRAFT') {
		d += 'label-primary';  
	} else if (state === 'CONFIRMED_PICKUP_REQ') {
		d += 'bg-orange';
	} else if (state === 'ISSUED') {
		d += 'label-success';
	} else if (state === 'MANIFESTED') {
		d += 'bg-maroon';
	} else if (state === 'VOIDED') {
		d += 'label-danger';
	} else if (state === 'IN_WAREHOUSE') {
		d += 'label-warning';
	} else if (state === 'IN_TRANSIT') {
		d += 'label-warning';
	} else if (state === 'OUT_FOR_DELIVERY') {
		d += 'label-warning';
	} else if (state === 'OUT_FOR_PICKUP') {
		d += 'label-warning';
	} else if (state === 'DELIVERED') {
		d += 'bg-olive';
	} else if (state === 'NOT_DELIVERED') {
		d += 'bg-teal';
	} else if (state === 'RETURNED') {
		d += 'bg-navy';
	} else if (state === 'MASTERED') {
		d += 'label-primary';
	} else if (state === 'OFFLOADED') {
		d += 'label-danger';
	} else if (state === 'ASSIGNED_FOR_PICKUP') {
		d += 'bg-maroon';
	} else if (state === 'NOT_PICKEDUP') {
		d += 'bg-teal';
	} else if (state === 'CANCELED_PICKUP') {
		d += 'bg-navy';
	} else if (state === 'PICKEDUP') {
		d += 'bg-olive';
	} else if (state === 'READY_FOR_ISSUE') {
		d += 'label-primary';
	}
	
	d = '<span class="label '+d+'">'+ value +'</span>';
	
	return d;
}

function shipmentStateLabelValue(state, locale) {

	var userLocale = $('input[name=user-locale]').val();
	if(locale){
		userLocale = locale;
	}
	
	if(!userLocale){
		userLocale = 'fa_IR';
	}
	
	var d = '';
	if (state === 'DRAFT') {
		d += (userLocale === 'fa_IR' ? 'پیش نویس' : 'Draft');  
	} else if (state === 'CONFIRMED_PICKUP_REQ') {
		d += (userLocale === 'fa_IR' ? 'درخواست جمع آوری' : 'Pickup Requested');
	} else if (state === 'ISSUED') {
		d += (userLocale === 'fa_IR' ? 'صادر شده' : 'Issued');
	} else if (state === 'MANIFESTED') {
		d += (userLocale === 'fa_IR' ? 'مانیفست شده' : 'Manifested');
	} else if (state === 'VOIDED') {
		d += (userLocale === 'fa_IR' ? 'باطل' : 'Voided');
	} else if (state === 'IN_WAREHOUSE') {
		d += (userLocale === 'fa_IR' ? 'در انبار' : 'In Warehouse');
	} else if (state === 'IN_TRANSIT') {
		d += (userLocale === 'fa_IR' ? 'در حال جابجایی' : 'In Transit');
	} else if (state === 'OUT_FOR_DELIVERY') {
		d += (userLocale === 'fa_IR' ? 'خروج برای تحویل' : 'Out for delivery');
	} else if (state === 'OUT_FOR_PICKUP') {
		d += (userLocale === 'fa_IR' ? 'خروج برای جمع آوری' : 'Out for pickup');
	} else if (state === 'DELIVERED') {
		d += (userLocale === 'fa_IR' ? 'تحویل شد' : 'Delivered');
	} else if (state === 'NOT_DELIVERED') {
		d += (userLocale === 'fa_IR' ? 'تحویل نشد' : 'Not Delivered');
	} else if (state === 'RETURNED') {
		d += (userLocale === 'fa_IR' ? 'مرجوع شد' : 'Returned');
	} else if (state === 'MASTERED') {
		d += (userLocale === 'fa_IR' ? 'Mastered' : 'Mastered');
	} else if (state === 'OFFLOADED') {
		d += (userLocale === 'fa_IR' ? 'Offloaded' : 'Offloaded');
	} else if (state === 'ASSIGNED_FOR_PICKUP') {
		d += (userLocale === 'fa_IR' ? 'انتساب برای جمع آوری' : 'Assigned for pickup');
	} else if (state === 'NOT_PICKEDUP') {
		d += (userLocale === 'fa_IR' ? 'جمع آوری نشد' : 'Not Pickuped');
	} else if (state === 'CANCELED_PICKUP') {
		d += (userLocale === 'fa_IR' ? 'لغو جمع آوری' : 'Canceled Pickup');
	} else if (state === 'PICKEDUP') {
		d += (userLocale === 'fa_IR' ? 'جمع آوری شده' : 'Pickedup');
	} else if (state === 'READY_FOR_ISSUE') {
		d += (userLocale === 'fa_IR' ? 'آماده برای صدور' : 'Ready for Issue');
	}
	return d;
}

function chargePartyLabelValue(chargeParty, locale) {

	var userLocale = $('input[name=user-locale]').val();
	if(locale){
		userLocale = locale;
	}
	
	if(!userLocale){
		userLocale = 'fa_IR';
	}
	
	var d = '';
	if (chargeParty === 'SHIPPER') {
		d += (userLocale === 'fa_IR' ? 'پیشکرایه' : 'Shipper');  
	} else if (chargeParty === 'CONSIGNEE') {
		d += (userLocale === 'fa_IR' ? 'پسکرایه' : 'Consignee');
	} else if (chargeParty === 'THIRD_PARTY') {
		d += (userLocale === 'fa_IR' ? 'شخص ثالث' : '3rd Party');
	}
	return d;
}

function chargePartyLabel(chargeParty, locale) {

	var userLocale = $('input[name=user-locale]').val();
	if(locale){
		userLocale = locale;
	}
	
	if(!userLocale){
		userLocale = 'fa_IR';
	}
	
	var value = chargePartyLabelValue(chargeParty, locale);
	
	var d = '';
	if (chargeParty === 'SHIPPER') {
		d += 'bg-olive';  
	} else if (chargeParty === 'CONSIGNEE') {
		d += 'bg-red';
	} else if (chargeParty === 'THIRD_PARTY') {
		d += 'bg-yellow';
	}
	
	d = '<span class="label '+d+'">'+ value +'</span>';
	
	return d;
}

function shipmentTypeLabel(shipmentType) {
	var d = '';
	if (shipmentType == 'DOC') {
		d += '<span class="label bg-navy"><i class="fa fa-file"></i> Doc</span>';
	} else if (shipmentType == 'NON_DOC') {
		d += '<span class="label btn-linkedin"><i class="fa fa-th-large"></i> Non Doc</span>';
	}
	return d;
}

function chargePartyLabel(chargeParty) {
	var userLocale = $('input[name=user-locale]').val();
	var d = '';
	if (chargeParty == 'SHIPPER') {
		d += '<span class="label bg-olive">' + (userLocale === 'fa_IR' ? 'پیشکرایه' : 'Shipper') +'</span>';
	} else if (chargeParty == 'CONSIGNEE') {
		d += '<span class="label bg-red">' + (userLocale === 'fa_IR' ? 'پسکرایه' : 'Consignee') +'</span>';
	} else if (chargeParty == 'THIRD_PARTY') {
		d += '<span class="label bg-purple">' + (userLocale === 'fa_IR' ? 'شخص ثالث' : '3rd Party') +'</span>';
	}
	return d;
}

function thousandSeparator(data, type, full) {
	if(!data){
		return data;
	}
	var s ='';
	s += data;
	s = s.replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
	return s;
}

function dateRender(data, type, full) {
	if(!data){
		return data;
	}
	return dateValue(data);
}

function dateShortRender(data, type, full) {
	if(!data){
		return data;
	}
	return _dateValue(data, null, 'DD MMM');
}

function datetimeRender(data, type, full) {
	if(!data){
		return data;
	}
	return datetimeValue(data);
}

function datetimeValue(data, locale) {
	return _dateValue(data, locale, 'YYYY-MM-DD HH:mm');
}

function dateValue(data, locale) {
	return _dateValue(data, locale, 'YYYY-MM-DD');
}

function _dateValue(data, locale, format) {
	
	if(!data){
		return data;
	}
	
	var userLocale = $('input[name=user-locale]').val();
	if(locale){
		userLocale = locale;
	}
	
	if(!userLocale){
		userLocale = 'fa_IR';
	}
	
	if (typeof pDate !== 'undefined' && new Date() instanceof pDate) {
		/*if(userLocale == 'fa_IR'){
			var d = new Date(data, false);
			return moment(d).format(format);
		} else {
			var d = new Date(data, true);
			return moment(d).format(format);
		}*/
		var d = new Date(data, true);
		return moment(d).format(format);
	} else {
		return moment(data).lang('en').format(format);
	}
	
}

jQuery(function($) {
		$.extend({
	    form: function(url, data, method) {
	        if (method == null) method = 'POST';
	        if (data == null) data = {};
	
	        var form = $('<form>').attr({
	            method: method,
	            action: url
	         }).css({
	            display: 'none'
	         });
	
	        var addData = function(name, data) {
	            if ($.isArray(data)) {
	                for (var i = 0; i < data.length; i++) {
	                    var value = data[i];
	                    addData(name + '[]', value);
	                }
	            } else if (typeof data === 'object') {
	                for (var key in data) {
	                    if (data.hasOwnProperty(key)) {
	                        addData(name + '[' + key + ']', data[key]);
	                    }
	                }
	            } else if (data != null) {
	                form.append($('<input>').attr({
	                  type: 'hidden',
	                  name: String(name),
	                  value: String(data)
	                }));
	            }
	        };
	
	        for (var key in data) {
	            if (data.hasOwnProperty(key)) {
	                addData(key, data[key]);
	            }
	        }
	
	        return form.appendTo('body');
	    }
	});
});