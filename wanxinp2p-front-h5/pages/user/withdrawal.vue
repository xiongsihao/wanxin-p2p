<template>
	<view class="content withdrawal">
		<topBar title="提现" />
		
		<view class="recom box-dis-flex">
			<view class="labelItem labStateA">
				<view class="lab"><text class="icon">&#xe624;</text></view>
				<view class="but">提现申请</view>
			</view>
			<view class="labelItem interval">  • • • </view>
			<view class="labelItem labStateC">
				<view class="lab"><text class="icon">&#xe622;</text></view>
				<view class="but" >银行处理</view>
			</view>
			<view class="labelItem interval gray">  • • • </view>
			<view class="labelItem labStateC">
				<view class="lab" ><text class="icon">&#xe623;</text></view>
				<view class="but">提现成功</view>
			</view>
		</view>
		
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
			<view class="title">提现金额 <text class="gray">最高可提 {{balance}} 元</text></view>
			<view class="inputBox"><text class="monIcon">￥</text><m-input type="number" class="number" :value="amount" @input="setNumber" placeholder="请输入提现金额" />
			<text class="withdrawalBandle" @tap="setNumber('all')">全部提现</text>
			</view>
		</view>
		<view class="des"><text class="icon">&#xe610;</text>预计明日23:59之前提现到账 详见 <text class="withdrawalBandle"> 提现说明</text></view>
		<view class="butBox"><ButtonItems :type="subType ? 'big-blue' : 'big-gray'" size="14" value="提现" @click="submitHandle"></ButtonItems></view>
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
				amount:'',
				subType: false,
				balance:this.$route.query.balance
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
				if (event > Number(this.balance)){
					this.amount = ''
					this.subType = false
					uni.showToast({
						icon: 'none',
						title: '提现金额输入有误 请从新输入',
					});
					return false
				}
				event === 'all' ? this.amount = this.balance :this.amount = event
				this.subType = true
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
				if (this.amount > 0){
					uni.navigateTo({url:`/pages/user/confirmWithdrawal?amount=${this.amount}`});
				}
			}
        }
    }
</script>

<style lang="scss" scoped="true">
	.withdrawal{
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
		.recom{
		padding:30upx;
		margin:30upx;
		border-radius: 10upx;
		font-size: 24upx;
		.icon{
			font-size: 36upx;
		}
		.labelItem{
			flex: 3;
			text-align: center;
			.lab{
				width: 76upx;
				height: 76upx;
				margin: 0 auto;
				line-height: 76upx;
				text-align: center;
				border-radius: 100%;
				background: $uni-border-color;
				color:#fff;
			}
			.but{
				font-size: 28upx;
				margin-top: 30upx;
				padding:10upx 10upx;
				color:$uni-text-color-grey;
			}
		}
		.interval{
			flex: 1;
			color:$uni-color-blueb;
			padding-top:24upx;
		}
		.gray{
			color:$uni-border-color;
		}
		.labStateA{
			.lab{
				background:$uni-color-blueb;
			}
			.but{
				color:$uni-color-blueb;
			}
		}
		.labStateB{
			.lab{
				background:$uni-color-blue;
				position: relative;
			}
			.lab::before{
				content: '';
				position: absolute;
				left: 2upx;
				top:2upx;
				width: 64upx;
				height: 64upx;
				border-radius: 100%;
				border: solid 4upx #ffffff;
			}
			.but{
				border:solid 2upx #ccc;
				color:$uni-color-blue;
				border-radius: 100upx;
			}
		}
		.topHead{
			display: flex;
			.topInfo{
				flex: 1;
				color:$uni-text-color-grey;
				.param{
					font-size: 64upx;
					color:$uni-font-org;
				}
			}
			.butItem{
				margin-top: 26upx;
			}
		}
		.boot{
			display: flex;
			font-size: 32upx;
			margin-top: 20upx;
			.item{
				text-align: left;
				flex:1;
				color: $uni-font-main;
			}
		}
	}
	}
</style>
