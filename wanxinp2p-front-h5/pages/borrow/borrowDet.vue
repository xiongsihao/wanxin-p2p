<template>
	<view class="content borrwDet">
		<topBar title='借款详情'/>
		<view class="head">
			<view class="">01月01日借款( 元)</view>
			<view class="row"><text class="price">1989.00</text> 未结算</view>
		</view>
		<view class="cardsBlock items">
			<view class="titItems clborder"> 
				<view class="item">
					<view>借款合同</view>
					<view class="icon cl-blue"> 查看 </view>
				</view>
			</view>
			<view class="titItems"> 
				<view class="item">
					<view>使用期限</view>
					<view class="icon"> 2019/03/19至2020/03/19 </view>
				</view>
			</view>
			<view class="titItems"> 
				<view class="item">
					<view>到账账户</view>
					<view class="icon"> 招商银行(3169) </view>
				</view>
			</view>
			<view class="titItems"> 
				<view class="item">
					<view>还款记录</view>
					<view class="icon cl-blue"> 查看 </view>
				</view>
			</view>
		</view>
		<view class="cardsBlock items" >
			<view class="titItems clborder">
				<view class="item">
					<view class="tit">按期还款计划</view>
					<view class="icon"> &#xe61b; </view>
				</view>
				<view class="des">还款日将自动扣款   扣款顺序为账户余额-储蓄卡</view>
			</view>
		</view>
		<view class="but"><ButtonItems type="big-blue" value="去还款" size="16"></ButtonItems></view>
	</view>
</template>
<script>
	import ButtonItems from '../../components/ButtonItems.vue'
	import topBar from '../../components/TopBar.vue'
	
	export default {
		components: {
			ButtonItems,
			topBar
		},
		data() {
			return {
				num: [1,2,3,4,5,6]
			}
		},
		methods: {
			goPath(url) {
				uni.switchTab(url);
			}
		}
	}
</script>

<style scoped lang="scss">
	.borrwDet{
		font-size: 28upx;
		padding-bottom: 120upx;
		.head{
			background: $uni-color-primary;
			color:#fff;
			padding:40upx;
			.row{
				margin: 40upx 10upx 0 0;
				.price{
					font-size: 100upx;
				}
			}
		}
		.items{
			margin:30upx 0 0;
			font-size: 28upx;
			color:$uni-text-color-grey;
			border-radius: 0;
			padding:0 30upx;
			.titItems{
				line-height: 60upx;
				padding:20upx 0;
				border-top:solid 1px $uni-border-color;
				.item{
					display: flex;
					justify-content: space-between;
				}
				.tit{
					color:$uni-text-color-greyb;
				}
				.des{
					color:$uni-text-color-grey;
				}
				.icon{
					font-size: 26upx;
				}
			}
		}
		.but{
				padding:40upx 80upx;
			}
	}
</style>

