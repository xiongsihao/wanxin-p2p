<template>
	<view class="footerMark">
		<!-- #ifdef MP-WEIXIN -->
		<image src="../static/img/footerMark.png" mode="aspectFill" class="mark"></image>
		<!-- #endif -->
		<!-- #ifndef MP-WEIXIN -->
		<image src="../static/img/footerMark.png" mode="aspectFill" class="mark"></image>
		<!-- #endif -->
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
<style scoped lang="scss">
.footerMark{
	.mark{
		margin: 50upx 0;
		margin-left: 50%;
		transform: translateX(-50%);
		width: 291upx;
		height: 93upx;
	}
}
</style>
