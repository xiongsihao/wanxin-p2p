<template>
	<view class="uni-tab-bar audit">
		<topBar title='审核中' />
		<view class="cardsBlock">
			<view class="act">
				<view class="card"><text class="icon">&#xe616;</text></view>
				<view>填写基本信息</view>
			</view>
			<view class="ellipsis"> <text class="lab" v-for="(it, ind) in num" :key="ind">•</text> </view>
			<view class="notbegun">
				<view class="card"><text class="icon">&#xe618;</text></view>
				<view>信用审批</view>
			</view>
		</view>
		<view class="icoImg">
			<view class="timeIcon"><image src="../../static/img/timeIco.png" class="time"></image></view>
			<view class="">资料审核中</view>
		</view>
		<view class="butbox"><ButtonItems value="返回首页" @click="goPath" type="big-blue"/></view>
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
			goPath() {
				uni.switchTab({
					url: '/pages/borrow/borrow'
				});
			}
		}
	}
</script>

<style scoped lang="scss">
	.audit{
		.cardsBlock{
			margin-top: 40upx;
			text-align: center;
			display: flex;
			padding: 50upx 30upx;
			.ellipsis{
				flex: 1;
				color:#d2d7ff;
				line-height: 80upx;
				.lab{
					margin: 0 20upx;
				}
			}
			.act{
				color:#d2d7ff;
				.icon{
					font-size: 80upx;
					margin-bottom: 20upx;
				}
			}
			.notbegun{
				color:#ccc;
				.icon{
					font-size: 80upx;
					margin-bottom: 20upx;
				}
			}
		}
		.icoImg{
			text-align: center;
			color:$uni-color-primary;
			padding: 40upx 0;
			.timeIcon{
				.time{
					width: 139upx;
					height: 139upx;
				}
			}
		}
		.butbox{
			padding: 0 80upx;
		}
	}
</style>

