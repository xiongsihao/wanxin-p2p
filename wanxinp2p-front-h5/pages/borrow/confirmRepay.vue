<template>
	<view class="content repayment">
		<topBar title='确认还款' sub='借还记录' @click="goPath('/pages/borrow/log')"/>
		<view class="head">
			<view class="">01月01日借款( 元)</view>
			<view class="price">1989.00</view>
		</view>
		<view class="headFoot">
			<view>招商银行（3689）</view>
			<view class="titItems">
				<view class="item" @tap="goPath('/pages/borrow/borrowPlan')">
					<view>按期还款计划</view>
					<view class="icon"> &#xe61b; </view>
				</view>
				<view class="des">还款日将自动扣款   扣款顺序为账户余额-储蓄卡</view>
			</view>
			<view class="titItems"> 
				<view class="item" @tap="goPath('/pages/borrow/borrowPlan')">
					<view>按期还款计划</view>
					<view class="icon"> &#xe61b; </view>
				</view>
			</view>
		</view>
		<view class="cardsBlock items" >
			<view class="top">
				<view class="check"><checkbox value="cb" checked="true" /></view>
				<view class="info">
					<view><text class="pirce">6.00</text>元</view>
					<view>01月02日应还</view>
				</view>
				<view class="start">
					<view> 1/12期 <text class="icon">&#xe61b;</text></view>
					<view class="st cl-blue"> 正常 </view> <!-- <view class="cl-red"> 逾期 </view> -->
				</view>
			</view>
			<view class="foot">2019年01月01日借款1000.00元</view>
			<view class="footer">
				<view class="lt">
					<view>
						<text class="tit">合计（元）：</text>0.00
					</view>
					<view>元</view>
				</view>
				<view class="rt" @tab="goPath">确认还款</view>
			</view>
		</view>
	</view>
</template>
<script>
	import ListItem from '../../components/ListItem.vue'
	import topBar from '../../components/TopBar.vue'
	
	export default {
		components: {
			ListItem,
			topBar
		},
		data() {
			return {
				num: [1,2,3,4,5,6]
			}
		},
		methods: {
			goPath(url) {
				uni.navigateTo({url});
			}
		}
	}
</script>

<style scoped lang="scss">
	.repayment{
		font-size: 28upx;
		padding-bottom: 120upx;
		.head{
			background: $uni-color-primary;
			color:#fff;
			padding:40upx;
			.price{
				font-size: 100upx;
				margin: 40upx 0 0upx;
			}
		}
		.headFoot{
			line-height: 100upx;
			padding:0 40upx;
			box-shadow: 0 4upx 4upx 0upx $uni-border-color;
			color:$uni-text-color-greyb;
			.titItems{
				line-height: 60upx;
				padding:20upx 0;
				border-top:solid 1px $uni-border-color;
				.item{
					display: flex;
					justify-content: space-between;
				}
				.des{
					color:$uni-text-color-grey;
				}
				.icon{
					font-size: 26upx;
				}
			}
		}
		.items{
			margin:30upx 0upx 0 0;
			font-size: 28upx;
			color:$uni-text-color-grey;
			border-radius: 0;
			.top{
				padding:0 30upx 30upx;
				display: flex;
				width: calc(100% - 30upx);
				border-bottom: solid 1px $uni-border-color; 
				line-height: 60upx;
				.check{
					width: 100upx;
					display: flex;
					align-items: center;
					transform: scale(0.8);
				}
				.info{
					flex:1;
					.pirce{
						font-size: 40upx;
						color: $uni-text-color-greyb;
					}
				}
				.start{
					width: 200upx;
					text-align: right;
					.icon{
						font-size: 32upx;
						margin-left: 20upx;
					}
					.st{
						font-size: 24upx;
					}
				}
			}
			.foot{
				color:$uni-text-color-greyb;
				padding-top:30upx;
			}
		}
	}
</style>

