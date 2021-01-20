<template>
	<view class="content confirmWithdrawal">
		<topBar title="确认提现信息" />
		
		<view class="cardsBlock">
			<view class="items"><view class="tit">提现金额</view><view class="val">{{$route.query.amount}} 元</view></view>
			<view class="items"><view class="tit">手续费</view><view class="val">0.00 元</view></view>
			<view class="items"><view class="tit">实际提现金额</view><view class="val">{{$route.query.amount}} 元</view></view>
		</view>
		
		<view class="cardsBlock">
			<view class="items"><view class="tit">开户名</view><view class="val">{{fullName}}</view></view>
			<view class="items"><view class="tit">银行卡号</view><view class="val">{{cardsInfo.bankName}} (尾号{{cardNumber}})</view></view>
		</view>
		
		<view class="des"><text class="icon">&#xe610;</text>预计明日23:59之前提现到账 详见 <text class="withdrawalBandle"> 提现说明</text></view>
		<view class="butBox"><ButtonItems type="big-blue" size="14" value="确认提现" @click="submitHandle"></ButtonItems></view>
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
				amount:this.$route.query.amount,
				fullName: ''
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
			getCardsInfo(){
				this.request({
					url: 'consumer/my/bank-cards',
				}).then(res => {
					this.cardsInfo = res.data.result
					let num = res.data.result.cardNumber
					let name = res.data.result.fullName
					this.fullName = name.replace(name.charAt(0), '*')
					this.cardNumber = num.substr(num.length-4)
				}).catch(err => {
					uni.showToast({
						icon: 'none',
						title: err.data.msg
					});
				})
			},
			submitHandle(){
				this.request({
					url: 'consumer/my/withdraw-records',
					params:{
						amount: this.amount,
						callbackUrl:`${this.utils.getBaseUrl()}/#/pages/common/success?st=3`
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
	.confirmWithdrawal{
		.items{
			display: flex;
			justify-content: space-between;
			line-height: 80upx;
			font-size: 28upx;
			.tit{
				color:$uni-text-color-grey;
			}
		}
		.gray{
			color:$uni-text-color-grey;
			margin-left: 20upx;
		}
		.withdrawalBandle{
			color:$uni-color-primary;
			line-height: 50upx;
		}
		.des{
			color:$uni-text-color-grey;
			font-size: 24upx;
			padding:0 30upx;
			.icon{
				font-size: 24upx;
				margin-right: 10upx;
			}
		}
		.butBox{
			padding: 10px 30upx;
			
		}
	}
</style>

