<template>
	<view class="content plan">
		<topBar title='还款计划'/>
		<view class="title">
			每期等额还款，共计 12期，还款日将按期自动扣款
		</view>
		<view class="items" v-for="(it,ind) in num">
			<view class="tit">
				<view><text class="pri">100.41</text> 元</view>
				<view>04月19日</view>
			</view>
			<view class="des">含息费20.90 元</view>
		</view>
	</view>
</template>
<script>
	import topBar from '../../components/TopBar.vue'
	
	export default {
		components: {
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
	.plan{
		.title{
			color:$uni-text-color-grey;
			line-height: 100upx;
			font-size: 28upx;
			padding:0 30upx;
		}
		.items{
			box-shadow: 0 0 4upx 0 $uni-border-color;
			padding:30upx;
			font-size: 28upx;
			.tit{
				color:$uni-text-color-greyb;
				display: flex;
				justify-content: space-between;
				.pri{
					font-size: 40upx;
				}
			}
			.des{
				color:$uni-text-color-grey;
				line-height: 60upx;
			}
		}
	}
</style>

