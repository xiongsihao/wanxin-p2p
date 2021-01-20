<template>
	<view class="content recharge">
		<topBar title="快捷充值" />
		<view class="cardsBlock">
			<view class="top">
				<view>￥ <text class="price">3999.00</text> 元</view>
				<view class="des">到账 5000.00 元</view>	
			</view>
			<view class="cont">
				<view class="item"><text class="tit">银行卡号</text> <m-input type="text" value="招商银行" disabled="" /></view>
				<view class="item"><text class="tit">手机号</text> <m-input type="text" value="135**987" disabled="" /></view>
				<view class="item"><text class="tit">验证码</text> <m-input type="text" placeholder="请输入验证码" /></view>
				<view class="item clborder"><text class="tit">交易密码</text> <m-input type="text" placeholder="请输入交易密码" /></view>
			</view>
			
		</view>
		<view class="des">
			温馨提示：招商银行不承担网贷平台的投融资标的物及投融资人的审核责任，不对网贷平台业务提供明示或模示的担保或连带责任，网贷平台的交易风险由投融资人自行承担，与银行无关。
		</view>
	</view>
</template>

<script>
	import topBar from '../../components/TopBar.vue'
	import mInput from '../../components/m-input.vue'
    export default {
		data(){
			return{}
		},
		components: {
			topBar,
			mInput
		},
        methods: {
            
        }
    }
</script>

<style lang="scss" scoped="true">
	.recharge{
		.cardsBlock{
			font-size: 28upx;
			padding: 0;
			.top{
				padding: 30upx;
				font-size: 28upx;
				border-bottom: solid 2upx $uni-border-color;
				.price{
					font-size: 58upx;
				}
				.des{
					color:$uni-text-color-grey;
					padding: 0;
					
				}
			}
			.cont{
				padding: 30upx;
				.item{
					display: flex;
					line-height: 80upx;
					border-bottom: solid 2upx $uni-border-color;
					.tit{
						display: block;
						width: 160upx;
					}
				}
			}
		}
		.des{
			padding: 0 40upx;
			color:$uni-text-color-grey;
			line-height: 40upx;
			font-size: 24upx;
		}
		
	}
</style>
