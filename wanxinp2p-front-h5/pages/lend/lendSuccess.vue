<template>
	<view class="content lendSuccess">
		<topBar sub="完成" title="出借成功" noBack @click="goPath('/pages/lend/main')" name="tit"></topBar>
		<view class="cardsBlock successCard">
			<view class="title">购买成功</view>
			<view class="des">购买金额 {{callBackData.amount}}元</view>
			<view class="title">回款方式</view>
			<view class="des">{{callBackData.project.repaymentWay}}</view>
			<view class="title">预计收益 {{callBackData.expectedIncome}}元</view>
			<view class="last">年利率{{callBackData.project.annualRate}} 项目期限 {{callBackData.project.period/30}}个月</view>
		</view>
	</view>
</template>

<script>
	import topBar from '../../components/TopBar.vue'
	import ButtonItems from '../../components/ButtonItems.vue'
	export default{
		data(){
			return{
				submitOpen: true,
				callBackData: {}
			} 
		},
		computed:{
			submitBut: function(){
				return this.submitOpen ? 'big-blue' : 'big-gray'
			}
		},
		onLoad() {
			this.callBackData = uni.getStorageSync('lendDataInfo')
		},
		components:{
			topBar,
			ButtonItems
		},
		methods:{
			goPath(url){
				uni.switchTab({url})
			}
		}
	}
</script>

<style lang="scss">
	.lendSuccess{
		.successCard{
			padding-left:50upx;
			.title, .des, .last{
				position: relative;
				padding: 0 0 0 60upx;
				margin-bottom: 10upx;
				
			}
			.last{
				color:$uni-text-color-grey;
			}
			.des{
				color:$uni-text-color-grey;
				margin-bottom: 80upx;
			}
			.title::after{
				position: absolute;
				content: '';
				left: 0;
				top:8upx;
				width:20upx;
				height: 20upx;
				border-radius: 100%;
				background: #4f57eb;
			}
			.des::after{
				position: absolute;
				content: '';
				left: 10upx;
				top:4upx;
				width:2upx;
				height: 100upx;
				border-radius: 100%;
				background: #cdd0ff;
			}
		}
	}
</style>
