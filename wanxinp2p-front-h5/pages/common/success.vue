<template>
	<view class="content success">
		<image class="icon" v-if="isOk" src="../../static/img/ok.png" ></image>
		<image class="icon" v-if="!isOk" src="../../static/img/error.png" ></image>
		<view class="title cl-blue">{{title}}</view>
		<view class="but"><ButtonItems type="big-blue" :value="but" size="14" @click="successHandle"></ButtonItems></view>
	</view>
</template>

<script>
	import ButtonItems from '../../components/ButtonItems.vue'
	export default {
		data() {
			return {
				title: '操作成功',
				but: '完成',
				st: this.$route.query.st,
				isOk: true, 
			}
		},
		onLoad() {
			if (this.$route.query.code == 0){
				if(this.st == 2){
					this.title = '充值成功'
				}else if(this.st == 3) {
					this.title = '提现成功'
				}else if(this.st == 1) {
					this.title = '开户成功'
				}
			}else{
				this.title = '操作失败'
				this.but = '返回重试'
			    this.isOk = false
			}
		},
		methods: {
			successHandle:function () {
				if (this.$route.query.code == 0){
					if(this.st == 2){
						this.title = '充值成功'
						uni.switchTab({url:'/pages/user/user'})
					}else if(this.st == 3) {
						this.title = '提现成功'
						uni.switchTab({url:'/pages/user/user'})
					}else if(this.st == 1){
						this.title = '开户成功'
						if (uni.getStorageInfoSync('openSt') == 'lend'){
							uni.switchTab({url:'/pages/lend/main'})
						}else if(uni.getStorageInfoSync('openSt') == 'borrow'){
							uni.switchTab({url:'/pages/borrow/borrow'})
						}else{
							uni.switchTab({url:'/pages/user/user'})
						}
					}else{
						uni.switchTab({url:'/'})
					}
					uni.setStorage({key: 'openSt', data: ''})
				}else{
					if(this.st == 1){
						uni.navigateTo({url: '/pages/lend/openStorage'})
					}else{
						uni.switchTab({url:'/pages/user/user'})
					}
				}
			}
		},
		components:{
			ButtonItems
		}
	}
</script>

<style scoped lang="scss">
	.success{
		padding: 200upx;
		text-align:  center;
		.icon{
			width: 139upx;
			height: 139upx;
			margin: 0 auto;
			line-height: 100upx;
		}
		.title{
			text-align: center;
			margin: 40upx 0;
		}
		.but{
			margin:20upx auto;
			width: 86%;
		}
	}
</style>
