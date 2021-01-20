<template>
	<view class="button" :class="['but-'+type]" :style="{color:color,'font-size':fontSize}" @click="onClick()">{{value}}</view>
</template>

<script>
	export default {
		props: {
			/**
			 * 图标类型 big min med-blue orange grey 
			 */
			type: String,
			/**
			 * 图标颜色
			 */
			color: String,
			/**
			 * 图标大小
			 */
			size: {
				type: [Number, String],
				default: 12
			},
			value: {
				type: String,
				default: '确定'
			}
		},
		computed: {
			fontSize() {
				var size = Number(this.size)
				size = isNaN(size) ? 12 : size
				return `${size}px`
			}
		},
		methods: {
			onClick() {
				this.$emit('click')
			}
		}
	}
</script>

<style>
	.button{
		color:#ffffff;
		text-align: center;
		display: inline-block;
	}
	.but-med-blue {
		background: linear-gradient(130deg, #8a8fef, #4f57eb);
		padding: 10upx 40upx;
		border-radius: 100upx;	
	}
	.but-med-orange {
		background: #4f57eb;
		background: linear-gradient(130deg, #ffbe03, #ff9b00);
		padding: 10upx 40upx;
		border-radius: 100upx;	
	}
	.but-med-blue-empty {
		color: #4f57eb;
		padding: 6upx 30upx;
		border-radius: 100upx;	
		border:solid 1px #4f57eb;
	}
	.but-med-gray-empty {
		color: #C8C7CC;
		padding: 6upx 30upx;
		border-radius: 100upx;
		border:solid 1px #C8C7CC;
	}
	.but-big-orange {
		background: linear-gradient(130deg, #ffbe03, #ff9b00);
		padding: 10upx 40upx;
		border-radius: 100upx;
		flex: 1;
	}
	.but-big-blue {
		background: linear-gradient(130deg, #8a8fef, #4f57eb);
		padding: 10upx 0upx;
		line-height: 2;
		border-radius: 100upx;
		width: 100%;
		flex: 1;
	}
	.but-big-gray {
		background: #dbdee7;
		padding: 10upx 0upx;
		line-height: 2;
		border-radius: 100upx;
		width: 100%;
		flex: 1;
	}
</style>
