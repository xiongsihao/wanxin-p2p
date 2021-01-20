<template>
    <view class="content">
		<view class="banner">
			<image src="../../static/img/logoBanner.png" mode="aspectFill" class="bannerImg"></image>		
		</view>
        <view class="input-group">
            <view class="input-row ">
                <text class="icon">&#xe621;</text>
                <m-input class="m-input" type="text" focus @input='setmobile' id="mobile" placeholder="请输入手机号"></m-input>
            </view>
            <view class="input-row">
                <text class="icon">&#xe620;</text>
                <m-input type="text" @input="setPassword" id="password" v-model="password" placeholder="请输入验证码"> </m-input>
                <view class="getCode" v-show="start" @tap="getCode">点击发送</view>
                <view class="getCode" v-show="!start" >&nbsp;&nbsp;{{timeNum}}秒</view>
                <!--<ButtonItems id="codeKey" class="getCode" @tap="getCode" value="点击发送" size="14"></ButtonItems>-->
            </view>
        </view>
        <view class="btn-row">
            <ButtonItems type="big-blue" value="登录" @tap="login" size="16"></ButtonItems>
        </view>
		<view class="botLogBox">
			<image src="../../static/img/log2.png" class="botLog"></image>
		</view>

    </view>
</template>

<script>
    import service from '../../service.js'
	import ButtonItems from '../../components/ButtonItems.vue'
    import mInput from '../../components/m-input.vue'

    export default {
        components: {
            mInput,
			ButtonItems,
        },
        data() {
            return {
                providerList: [],
                hasProvider: false,
                start: true,
                timeNum: 60,
                mobile: '',
                password: '',
                codeKey: '',
                positionTop: 0,
				loginData:{
					grant_type: 'password',
					client_id: 'wanxin-p2p-web-h5',
					client_secret: 'itcasth5',
					domain: 'c',
					authenticationType: 'password'
				}
            }
        },
        methods: {
			setmobile(event){
				this.mobile = event
			},
			setPassword(event){
				this.password = event
			},
            login() {
                if (this.mobile.length != 11) {
                    uni.showToast({
                        icon: 'none',
                        title: '请输入正确的手机号！'
                    });
                    return;
                }
                if (this.password.length < 6) {
                    uni.showToast({
                        icon: 'none',
                        title: '最短为 6 个字符'
                    });
                    return;
                }
                const data = {
                    username:this.mobile,
                    password: this.password,
					mobile:this.mobile,
                    key: this.codeKey,
					...this.loginData
                };
                if (!this.codeKey) {
                    this.codeKey = 'codeKey'
                }
				this.request({
					url: `account/mobiles/${this.mobile}/key/${this.codeKey}/code/${this.password}`
				}).then(res => {
					if(res.data.result == 1){
						this.log(data)
					} else {
						this.reg(data)
					}
				}).catch(err => {
					uni.showToast({
						icon: 'none',
						title: err.data.msg,
					});
				})
            },
			reg(data){
                const _this = this;
				this.request({
						url: 'consumer/consumers',
						params: {...data, role: ''},
						method: 'POST'
					}).then(res => {
					    setTimeout(function () {
                            _this.log(data)
                        }, 1000)
					}).catch(err => {
						uni.showToast({
							icon: 'none',
							title: err.data.msg,
						});
					})
			},
			log(data){
				this.request({
					url: 'uaa/oauth/token',
					params: data,
					method: 'POST'
				}).then(res => {
					uni.setStorage({key:'loginData', data: data})
					uni.setStorage({key:'access_token', data: res.data.access_token})
					uni.switchTab({url: '/pages/main/main'});
				}).catch(err => {
					uni.showToast({
						icon: 'none',
						title: err.data.msg
					});
				})
			},
            setTime(){
                let _this = this
                let tm = setInterval(function(){
                    if (_this.timeNum == 1){
                        _this.start = true
                        clearInterval(tm)
                        _this.timeNum = 60
                    }else{
                        _this.timeNum--
                    }

                }, 1000)
            },
			getCode(){
				if(this.mobile.length == 11){
                    this.start = false
                    this.setTime()
                    this.request({
                        url: `account/sms/${this.mobile}`,
                        method: 'GET',
                    }).then(res => {
                        if (res.data.code == 0) {
                            if (res.data.result) {
                                this.codeKey = res.data.result.key
                            }
                            uni.showToast({
                                icon: 'none',
                                title: '验证码获取成功'
                            });
                        } else {
                            uni.showToast({
                                icon: 'none',
                                title: '验证码获取失败，请稍后重试'
                            });
                        }
                    }).catch(err => {
                        uni.showToast({
                            icon: 'none',
                            title: err.data.msg
                        });
                    })
				}else{
					uni.showToast({
                        icon: 'none',
                        title: '请输入正确的手机号！'
                    });
                    return;
				}
			}
        }
    }
</script>

<style lang="scss">
	.bannerImg{
		width: 100%;
	}
	.botLog{
		width: 286upx;
		height: 60upx;
		margin: 0 auto;
	}
	.input-group{
		margin: 30upx;
		border:none;
	}
	.input-group::before,.input-group::after{
		background-color:#fff;
	}
	.input-row{
		background: #f4f4f4;
		border-radius: 10upx;
		margin: 20upx;
		padding: 20upx;
		.icon{
			font-size: 40upx;
			color:#4f57eb;
		}
	}
	.getCode{
		font-size: 28upx;
		color:#4f57eb;padding-left: 50upx;
		border-left: solid 2upx $uni-border-color;
		height: 40upx;
		line-height: 40upx;
		min-width: 145upx;
		margin-top: 10upx; 
	}
    .getCode-gray {
        background: #dbdee7;
        font-size: 28upx;
        color:#4f57eb;padding-left: 50upx;
        border-left: solid 2upx $uni-border-color;
        height: 40upx;
        line-height: 40upx;
        min-width: 145upx;
        margin-top: 10upx;
    }
	.botLogBox{
		text-align: center;
		margin-top: 80upx;
	}
	.btn-row{
		padding: 0 80upx;
	}
    
</style>
