<template>
	<view class="content pay">
		<topBar title="充值" />
		<view class="cardsBlock">
			<view class="backInfo">
				<image src="../../static/img/backIco.png" class="backIco"></image>
				<view>
					<view class="title">{{cardsInfo.bankName}} 尾号{{cardNumber}}</view>
					<view>单笔2000 单日100,000</view>
				</view>
			</view>
		</view>
		<view class="cardsBlock">
			<view class="title">充值金额</view>
			<view class="inputBox"><text class="monIcon">￥</text><m-input type="text" class="number" @input="setNumber" placeholder="请输入充值金额" /><text>元</text></view>
			<view class="allQuota">可用金额： {{$route.query.balance}}元</view>
		</view>
		<view class="butBox"><ButtonItems type="big-blue" size="14" value="充值" @click="submitHandle"></ButtonItems></view>
	</view>
</template>

<script>
	import topBar from '../../components/TopBar.vue'
	import mInput from '../../components/m-input.vue'
	import ButtonItems from "../../components/ButtonItems.vue"
    export default {
		data(){
			return{
				cardsInfo:{},
				cardNumber:'',
				amount:''
			}
		},
		components: {
			topBar,
			mInput,
			ButtonItems
		},
		onLoad() {
			this.getCardsInfo()	
		},
        methods: {
			setNumber(event){
				this.amount = event
			},
            getCardsInfo(){
				this.request({
					url: 'consumer/my/bank-cards',
				}).then(res => {
					this.cardsInfo = res.data.result
					let num = res.data.result.cardNumber
					this.cardNumber = num.substr(num.length-4)
				}).catch(err => {
					uni.showToast({
						icon: 'none',
						title: err.data.msg
					});
				})
			},
			submitHandle(){
				if(this.amount <= 0 ){
					uni.showToast({
						icon: 'none',
						title: '请输入正缺的充值金额！',
					});
					return false
				}
				this.request({
					url: 'consumer/my/recharge-records',
					params:{
						amount: this.amount,
						callbackUrl:`${this.utils.getBaseUrl()}/#/pages/common/success?st=2`
					}
				}).then(res => {
					const urlData = res.data.result
					const url = `${urlData.depositoryUrl}?serviceName=${urlData.serviceName}&platformNo=${urlData.platformNo}&reqData=${urlData.reqData}&signature=${urlData.signature}`
					window.location.href = url
				}).catch(err => {
					uni.showToast({
						icon: 'none',
						title: err.data.msg
					});
				})
			}
        }
    }
</script>

<style lang="scss" scoped="true">
	.pay{
		.backInfo{
			display: flex;
			font-size: 28upx;
			color:$uni-text-color-grey;
			line-height: 42upx;
			.backIco{
				width: 84upx;
				height: 84upx;
				border-radius: 100%;
				margin-right: 20upx;
			}
			.title{
				color:$uni-text-color-greyb;
			}
		}
		.inputBox{
			display: flex;
			.monIcon{
				position: relative;
				top:5px;
				margin-right: 10upx;
			}
		}
		.title{
			margin-bottom: 30upx;
			
		}
		.number{
			flex: 1;
		}
		.allQuota{
			padding: 20upx 0;
			color:$uni-text-color-grey;
			margin-top:10upx;
			border-top:solid 1px $uni-border-color;
		}
		.butBox{
			padding: 10px 30upx;
			
		}
	}
</style>
