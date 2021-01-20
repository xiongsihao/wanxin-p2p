<template>
	<view class="borrowCardsCont">
		<view class="cardsBlock finish"  v-if="count">
			<view class="title"> 这里是标题 </view>
			<view class="head">
				<view>
					<view>可借额度（元）</view>
					<view class="price"> 2,0000</view>
					<view class="all"> 总额度 10000（元）</view>
				</view>
				<view class="butItem">
					<ButtonItems type="med-orange" value="去借款" @tap='click()'></ButtonItems>
				</view>
			</view>
			<view class="foot">
				<view>
					<view>本期待还金额 1,900.00 元</view>
					<view class="time">还款日 2019-05-05</view>
				</view>
				<view class="butItem">
					<ButtonItems type="med-blue-empty" value="去还款" @tap='goPath()'></ButtonItems>
				</view>
			</view>
		</view>
		<view class="cardsBlock">
			<view class="title">
				这里是标题
			</view>
			<view class="info">
				<view>
					<view class="price"> 2,0000</view>
					<view> 最高额度（元）</view>
				</view>
				<view class="butItem">
					<ButtonItems type="med-blue" value="立即申请" @tap='click()'></ButtonItems>
				</view>
			</view>
		</view>
		
	</view>
</template>

<script>
	import ButtonItems from './ButtonItems.vue';
	export default {
		props:{
			options: Object,
			type: Number
		},
		data() {
			return {
				data: '222'
			};
		},
		computed: {  
			count() {
				return (this.type == 0)  
			}  
		},
		components: {
			ButtonItems
		},
		methods:{
			click:function(){
				this.$emit('click')
			},
			goPath:function(){
				uni.navigateTo({
                    url: '/pages/borrow/repayment',
                });
			}
		}
	}
</script>

<style lang="scss">
	.borrowCardsCont{
		.title{
			margin-bottom: 10upx;
		}
		.info{
			display: flex;
			justify-content: space-between;
			color:$uni-text-color-grey;
			.price{
				font-size: 54upx;
				color: $uni-color-primary;
			}
			.butItem{
				padding-top:50upx; 
			}
		}
		.finish{
			margin-bottom: 40upx;
			padding:0;
			.title{
				border-bottom: solid 2upx $uni-border-color;
				padding:30upx;
			}
			.head{
				display: flex;
				justify-content: space-between;
				margin:30upx 30upx 0;
				padding-bottom:30upx;
				border-bottom: solid 2upx $uni-border-color;
				.price{
					font-size: 54upx;
					color: $uni-color-primary;
				}
				.all{
					color:$uni-text-color-grey;
				}
			}
			.foot{
				display: flex;
				justify-content: space-between;
				padding:30upx;
				.time{
					color:$uni-text-color-grey;
					margin-top: 10upx;
				}
			}
			.butItem{
				display: flex;
				align-items: center;
			}
		}
	}
</style>
