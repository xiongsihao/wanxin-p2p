<template>
	<view class="content account">
		<view class="topHead">
			<topBar title="账户余额" type="blue"></topBar>
			<view class="acc">账户余额(元)</view>
			<view class="pir">{{userInfo.balance}}</view>
		</view>
		<view class="frozenPirce">
			<view>冻结金额：{{userInfo.freezeAmount}}</view>
			<view class="icon">&#xe61b;</view>
		</view>
		<view class="action">
			<view class="add" @tap="goPath(`/pages/user/pay?balance=${userInfo.balance}`)">
				<text class="icon" >&#xe614;</text>充值
			</view>
			<view class="res" @tap="goPath(`/pages/user/withdrawal?balance=${userInfo.balance}`)">
				<text class="icon">&#xe615;</text>提现
			</view>
		</view>
	</view>
</template>

<script>
	import topBar from '../../components/TopBar.vue'
    export default {
		data(){
			return{
				userInfo:{}
			}
		},
		components: {
			topBar
		},
		onLoad() {
			this.getData()
		},
        methods: {
            goPath(url){
				uni.getStorage({key:'userInfo',success(res) {
					if (res.data.isBindCard){
						uni.navigateTo({url})
					} else {
						uni.navigateTo({url: '/pages/lend/openStorage'})
					}
				}})
				
			},
			getData(){
				this.request({
					url:'consumer/my/balances',
				}).then(res => {
					this.userInfo = res.data.result
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

<style lang="scss">
	.account{
		color:#fff;
		font-size: 24upx;
		.topHead{
			padding:0upx 30upx 30upx 0upx;
			background: $uni-color-primary;
			.acc{
				padding:30upx 30upx 0 30upx;
			}
			.pir{
				font-size: 70upx;
				padding:30upx;
			}
			
		}
		.frozenPirce{
			background: #4f69eb;
			padding:0 30upx;
			line-height: 100upx;
			display: flex;
			justify-content: space-between;
			.icon{
				font-size: 24upx;
			}
		}
		.action{
			box-shadow: 0 4upx 4upx 0upx  $uni-border-color;
			padding: 30upx;
			line-height: 40upx;
			display: flex;
			color:#5b63f1;
			text-align: center;
			.add,.res{
				flex:1;
			}
			.add{
				border-right: solid 1px $uni-border-color;
			}
			.icon{
				font-size: 24upx;
				margin-right: 6upx;
			}
		}
	}
</style>
