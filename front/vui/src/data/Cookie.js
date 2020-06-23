let cookie = {
    get:function (c_name) {
        if (document.cookie.length > 0) {
            var c_start = document.cookie.indexOf(c_name + "=")
            if (c_start != -1) {
                c_start = c_start + c_name.length + 1
                var c_end = document.cookie.indexOf(";", c_start)
                if (c_end == -1) c_end = document.cookie.length
                return unescape(document.cookie.substring(c_start, c_end))
            }
        }
        return ""
    },
    set:function(c_name, value, expireMinutes) {
        var exdate = new Date()
        exdate.setTime(exdate.getTime() + expireMinutes*60*1000)
        document.cookie = c_name + "=" + escape(value) +
            ((expireMinutes == null) ? "" : ";expires=" + exdate.toGMTString())
    },
    check:function (c_name) {
        var c_value = this.get(c_name)
        if (c_value != null && c_value != "") {
            return true;
        } 
        return false;
    },
	del:function(c_name){
		var exdate = new Date()
		exdate.setTime(exdate.getTime() - 10)
		document.cookie = c_name + "="+this.get(c_name)+";expires=" + exdate.toGMTString()
	}
}

export default cookie