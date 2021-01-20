<template>
	<view class="content borrwDet">
		<topBar title='还款详情'/>
		<view class="head">
			<view class="">01月01日借款( 元)</view>
			<view class="row"><text class="price">1989.00</text></view>
		</view>
		<view class="cardsBlock items">
			<view class="titItems clborder"> 
				<view class="item">
					<view>本金</view>
					<view class="icon"> 17200.00 </view>
				</view>
			</view>
			<view class="titItems"> 
				<view class="item">
					<view>息费</view>
					<view class="icon"> 18.00 </view>
				</view>
			</view>
			<view class="titItems"> 
				<view class="item">
					<view>罚息</view>
					<view class="icon"> 0.00 </view>
				</view>
			</view>
			<view class="titItems"> 
				<view class="item">
					<view>违约金</view>
					<view class="icon"> 0.00 </view>
				</view>
			</view>
			<view class="titItems"> 
				<view class="item">
					<view>还款期数</view>
					<view class="icon"> 3/12 期 </view>
				</view>
			</view>
			<view class="titItems"> 
				<view class="item">
					<view>借款详情</view>
					<view class="icon cl-blue"> 查看 </view>
				</view>
			</view>
		</view>
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
	}
</style>

