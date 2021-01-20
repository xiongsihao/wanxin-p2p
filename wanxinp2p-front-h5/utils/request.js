// 后端地址和文件服务地址
const BaseUrl = 'http://127.0.0.1:53010/'
const FileServiceUrl = 'http://127.0.0.1:56082/farming'

// 测试
// const BaseUrl = 'http://127.0.0.1:53010/'
// const FileServiceUrl = 'http://127.0.0.1:56082/farming/'

// 参数： url:请求地址  param：请求参数  method：请求方式 callBack：回调函数
function request({url='', params={}, method='GET'}) {

    let token = "";

    uni.getStorage({
        key: 'access_token',
        success: function(res) {
            token = res.data
        }
    });

    let state = token === "" ? true : false

    if (url === 'uaa/oauth/token' ||
        url === 'account/sms' || url.search('account/sms/') == 0 ||
        url === 'account/mobiles' || url.search('account/mobiles/') == 0){
        state = false
    }

    if (state){
        uni.showToast({
            icon: 'none',
            title: '登录失效，请重新登录！'
        });
        uni.navigateTo({url:'/pages/user/login'})
    }
    let header = {
        'Accept': 'application/json',
        'Access-Control-Allow-Origin':'*',
        'Content-Type': 'application/json',
    }
    if(url == 'uaa/oauth/token') {
        header = {...header, 'Content-Type': 'application/x-www-form-urlencoded' }
    }

    if(token) {
        header = {...header, 'Authorization': `Bearer ${token}`,}
    }

    const requestRes = new Promise((resolve, reject) => {
        uni.request({
            url: BaseUrl+url,
            data: params,
            header: header,
            method: method,
            success: (res) => {
                if (res.data.code == 0 ){
                    resolve(res)
                }else{
                    if (res.data.msg.indexOf('Access token expired') > -1) {
                        uni.clearStorage();
                        uni.navigateTo({url:'/pages/user/login'})
                    } else {
                        reject(res)
                    }
                }
            },
            fail: (err) => {
                const error = {data:{msg:err}}
                reject(error)
            }
        });
    })
    return requestRes
}

export default {
    request:  function (url, param, method){
        return request(url, param, method)
    },
    baseUrl: BaseUrl,
    fileServiceUrl: FileServiceUrl
}