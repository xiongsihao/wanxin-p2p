<template>
	<view :class="TopBar">
		<view class="goBack" v-if="!noBack" @click="goBack()">  <m-icon :color="color" type="arrowleft" />  </view>
		<view class="tit">{{title}}</view>
		<view class="subTit" @tap="subClick" v-if="sub">{{sub}}</view>
	</view>
</template>

<script>
	import mIcon from './m-icon/m-icon.vue'
	export default {
		props:{
			title:{
				type: String,
				default: ''
			},
			type:{
				type: String,
				default: ''
			},
			sub:{
				type: String,
				default: ''
			},
			noBack:{
				type: Boolean,
				default: false
			}
		},
		data(){
			return{
				
			}
		},
		computed:{
			TopBar(){
				return this.type == 'blue' ? 'TopBar blue' : 'TopBar'
			},
			color(){
				return this.type == 'blue' ? '#fff' : '#666'
			}
		},
		components:{
			mIcon
		},
		methods:{
			goBack:function(){
				uni.navigateBack();
			},
			subClick:function() {
				this.$emit('click')
			}
		}
	}
</script>

<style scoped="true" lang="scss">
	.TopBar{
		width: 100%;
		height: 90upx;
		background: #fff;
		line-height: 90upx;
		text-align: center;
		font-size: 32upx;
		color: #666;
		position: relative;
		box-shadow: 0 2upx 4upx 4upx #f4f4f4;
		.goBack{
			position: absolute;
			left:20upx;
			line-height: 90upx;
			font-size: 32upx;
		}
		.tit{
			text-align: center;
		}
		.subTit{
			position: absolute;
			font-size: 26upx;
			color:$uni-color-primary;
			top:2upx;
			right:20upx;
			line-height: 90upx;
		}
	}
	.blue{
		background: $uni-color-primary;
		color:#fff;
		box-shadow: none;
	}
</style>
