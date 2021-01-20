/*** 金额隔三位加逗号 ***/
function priceFormat(num) {
	return num && parseFloat(num).toLocaleString()
}
// 获取服务源地址
function getBaseUrl() {
	return window.location.origin
}
/*** 小数点处理 ***/

function numberPoint(num,n) { 
	return num && Number(num).toFixed(n)
}
/**** 手机号,身份证部分换**处理  ****/
function numReplaceStar(num,st,end){
	const str = String(num)
	 const lastDat = str.substr(0, st) + '*****' + str.substr(end, str.length)
	 return lastDat
}

// 日期转化
function dateFormat({date = '', rule = 'y-m-d h:m:s'}){
	let time = new Date(date)
	let fullTime = rule.replace(/y/, time.getFullYear())
	fullTime = fullTime.replace(/m/, time.getMonth() + 1)
	fullTime = fullTime.replace(/d/, time.getDate())
	fullTime = fullTime.replace(/h/, time.getHours())
	fullTime = fullTime.replace(/m/, time.getMinutes())
	fullTime = fullTime.replace(/s/, time.getSeconds())
	return fullTime
}

module.exports = { priceFormat, numberPoint, getBaseUrl, dateFormat,numReplaceStar}
