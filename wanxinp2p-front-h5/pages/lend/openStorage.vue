<template>
	<view class="content openStorage">
		<topBar title="开通存管"></topBar>
		<view class="labTitle">您正在使用招商银行开户功能</view>
		<view class="cardsBlock">
			<view class="item">
                <view class="lab">证件类型</view>
                <input class="uni-input" focus :value="Certificates" disabled placeholder="请输入证件类型" />
            </view>
			<view class="item">
                <view class="lab">客户姓名</view>
                <input id="fullname" class="uni-input" @input="inputHandle" :value="data.fullname" placeholder="请输入本人姓名，核实后不可修改" />
            </view>
			<view class="item clborder">
                <view class="lab ">证件号</view>
                <input type="idcard" class="uni-input" id="idNumber" @input="inputHandle" :value="data.idNumber" placeholder="请输入本人证件号码，核实后不可修改" />
            </view>
		</view>
		<view class="labTitle">温馨提示：若银行卡为二类账户，将会影响提现，具体账户类型
可咨询发卡行</view>
		<view class="cardsBlock">
			<view class="item">
                <view class="lab">银行卡号</view>
                <input type="number" class="uni-input" id="cardNumber" :value="data.cardNumber" @input="inputHandle" placeholder="请输入本人储蓄卡卡号" />
            </view> 
			<view class="item clborder">
                <view class="lab">手机号</view>
                <input type="number" class="uni-input" id="mobile" :value="data.mobile" @input="inputHandle" placeholder="请输入银行预留手机号" />
            </view>
		</view>
		<view class="itBut"><ButtonItems :type="submitBut" @click="submitOpen ? submitHandle() : ''" size="14" value="确认协议并注册"></ButtonItems></view>
	</view>
</template>

<script>
import ButtonItems from '../../components/ButtonItems.vue'
import topBar from '../../components/TopBar.vue'
import store from '../../store/index.js'	

export default {
	data() {
        return {
            pickerHidden: true,
            chosen: '',
			submitOpen: false,
			Certificates: '身份证',
			data:{
				role: 'I',
				fullname: '',
				idNumber: '',
				mobile:'',
				cardNumber:'',
				callbackUrl:`${this.utils.getBaseUrl()}/#/pages/common/success?st=1`
			}
        }
    },
	computed:{
		submitBut: function(){
			return this.submitOpen ? 'big-blue' : 'big-gray'
		}
	},
	components:{
		ButtonItems,
		topBar
	},
    onShow() {
        this.getUserInfo()
        this.getCardsInfo()
    },
    methods: {
		inputHandle: function (event) {
			this.data[event.target.id] = event.target.value
			const {fullname,idNumber,mobile,cardNumber} = this.data
		    if (fullname != '' && idNumber.length == 18 && mobile.length == 11 && cardNumber.length >= 16 ){
			   this.submitOpen = true
		    }
		},
        getUserInfo: function(){
            this.request({
                url: `consumer/my/consumers`,
            }).then(res => {
                this.data.fullname = res.data.result.fullname
                if (res.data.result.idNumber) {
                    this.data.idNumber = res.data.result.idNumber
                }
            }).catch(err => {
                uni.showToast({
                    icon: 'none',
                    title: err.data.msg
                });
            })
        },
        getCardsInfo(){
            this.request({
                url: 'consumer/my/bank-cards',
            }).then(res => {
                if (res.data.result.cardNumber) {
                    this.data.cardNumber = res.data.result.cardNumber
                    this.data.mobile = res.data.result.mobile
                    const {fullname,idNumber,mobile,cardNumber} = this.data
                    if (fullname != '' && idNumber.length == 18 && mobile.length == 11 && cardNumber.length >= 16 ){
                        this.submitOpen = true
                    }
                }
            }).catch(err => {
                uni.showToast({
                    icon: 'none',
                    title: err.data.msg
                });
            })
        },
		submitHandle: function () {
			//TODO 模拟实现----------
			uni.showLoading({
				title: '正在联网对姓名、身份证、手机号、银行卡号进行一致性验证...',
				mask: true
			});
            const _this = this;
			setTimeout(function () {
				uni.hideLoading()
                _this.request({
                    url: 'consumer/my/consumers',
                    method: 'POST',
                    params: {..._this.data}
                }).then(res => {
                    store.commit('setOpenStorageData', res.data.result)
                    const urlData = res.data.result
                    const url = `${urlData.depositoryUrl}?serviceName=${urlData.serviceName}&platformNo=${urlData.platformNo}&reqData=${urlData.reqData}&signature=${urlData.signature}`
                    window.location.href = url
                }).catch(err => {
                    uni.showToast({
                        icon: 'none',
                        title: err.data.msg
                    });
                })
			}, 5000);
		}
    }
}
</script>

<style lang="scss" scoped>
.openStorage{
	.labTitle{
		margin-top: 30upx;
		font-size: 24upx;
		color:$uni-text-color-grey;
	}
	.item{
		display: flex;
		border-bottom: solid 1px $uni-border-color;
		line-height: 58upx;
		font-size: 28upx;
		padding: 20upx 0upx;
		.lab{
			width: 140upx;
		}
		.uni-input{
			flex: 1;
		}
	}
	.itBut{
		margin:20upx auto;
		width: 86%;
	}
}
</style>
