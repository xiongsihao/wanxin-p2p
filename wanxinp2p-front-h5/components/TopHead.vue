<template>
	<view class="topHead">
		<view class="log"> 
			<!-- #ifdef MP-WEIXIN -->
			<image src="../static/img/logo.png" mode="aspectFit" class="imgLogo"></image> 
			<!-- #endif -->
			<!-- #ifndef MP-WEIXIN -->
			<image src="../static/img/logo.png" mode="aspectFit" class="imgLogo"></image> 
			<!-- #endif -->
		</view>
		<view class=""> <text class="icon">&#xe603;</text> </view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			};
		}
	}
</script>
<style lang="scss">
.topHead{
	background: $uni-color-primary;
	height: 100upx;
	line-height: 100upx;
	padding:0 30upx;
	display: flex;
	.log{
		flex: 1;
		padding-top: 10upx;
	}
	.imgLogo{
		width: 226upx; 
		height: 48upx;
	}
	.icon{
		color:#fff;
		font-size: 36upx;
	}
}
</style>
