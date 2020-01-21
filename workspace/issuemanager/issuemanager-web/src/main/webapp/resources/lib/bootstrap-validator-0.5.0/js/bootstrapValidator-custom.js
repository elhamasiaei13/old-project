;(function($) {
    $.fn.bootstrapValidator.i18n.nationalId = $.extend($.fn.bootstrapValidator.i18n.nationalId || {}, {
        'default': 'Please enter a valid national id'
    });

    $.fn.bootstrapValidator.validators.nationalId = {
        /**
         * Return true if the input value is a national id string.
         *
         * @param {BootstrapValidator} validator The validator plugin instance
         * @param {jQuery} $field Field element
         * @param {Object} options Can consist of the following keys:
         * - message: The invalid message
         * @returns {Boolean}
         */
        validate: function(validator, $field, options) {
            var value = $field.val();
            
            if(!value || value === ''){
            	return true;
            }
            
            var legal = $field.attr('data-bv-nationalId-legal');
            
            if(legal === undefined){
            	
            	if(value.length == 11){
            		legal = 'true';
            	} else {
            		legal = 'false';
            	}
            	
            }
            
            if(legal == 'true'){
            	if (!/^[0-9]{11}$/.test(value)) {
					return false;
				}
				
				var L=value.length;
				
				if(L<11 || parseInt(value,10)==0) {
					return false;
				}
				  
				if(parseInt(value.substr(3,6),10)==0) {
					return false;
				}
				var c = parseInt(value.substr(10,1),10);
				var d = parseInt(value.substr(9,1),10)+2;
				var z = new Array(29,27,23,19,17);
				var s = 0;
				for(var i=0;i<10;i++){
					s += (d+parseInt(value.substr(i,1),10))*z[i%5];
				}
				s = s%11;
				if(s==10) {
					s=0;
				}
				return (c==s);
            } else {
            	if (!/^[0-9]{10}$/.test(value)) {
            		return false;
            	}
            	
            	if(value=='1111111111' ||
            			value=='0000000000' ||
            			value=='2222222222' ||
            			value=='3333333333' ||
            			value=='4444444444' ||
            			value=='5555555555' ||
            			value=='6666666666' ||
            			value=='7777777777' ||
            			value=='8888888888' ||
            			value=='9999999999' ||
            			value=='0123456789' ){
            		return false;
            	}
            	c = parseInt(value.charAt(9));
            	n = parseInt(value.charAt(0))*10 +
            	parseInt(value.charAt(1))*9 +
            	parseInt(value.charAt(2))*8 +
            	parseInt(value.charAt(3))*7 +
            	parseInt(value.charAt(4))*6 +
            	parseInt(value.charAt(5))*5 +
            	parseInt(value.charAt(6))*4 +
            	parseInt(value.charAt(7))*3 +
            	parseInt(value.charAt(8))*2;
            	r = n - parseInt(n/11)*11;
            	if ((r == 0 && r == c) || (r == 1 && c == 1) || (r > 1 && c == 11 - r)){
            		return true;
            	} else {
            		return false;
            	}
            }
            
        }
    };
}(window.jQuery));

;(function($) {
    $.fn.bootstrapValidator.i18n.packing = $.extend($.fn.bootstrapValidator.i18n.packing || {}, {
        'default': 'ابعاد و یا وزن با بسته بندی انتخاب شده همخوانی ندارد'
    });

    $.fn.bootstrapValidator.validators.packing = {
        /**
         * Return true if the input value is a national id string.
         *
         * @param {BootstrapValidator} validator The validator plugin instance
         * @param {jQuery} $field Field element
         * @param {Object} options Can consist of the following keys:
         * - message: The invalid message
         * @returns {Boolean}
         */
        validate: function(validator, $field, options) {
            var value = $field.val();
            var index = $field.attr('data-index');
            
            if(!value || value === ''){
            	return true;
            }
            
            return checkWeightAndDimention(index);
        }
    };
}(window.jQuery));