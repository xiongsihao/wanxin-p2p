<template>
    <view class="content userCont">
		<!-- 头部信息 -->
        <view class="head">
			<view class="">
				<image src="../../static/img/icon.png" mode="widthFix" class="topImg"></image>
			</view>
			<view class="info">
				<view class="name">下午好 {{userInfo.fullname}}</view>
				<view class="label">万信金融账户</view>
			</view>
			<!-- <view class="more" @tap="outLogoin"> > </view> -->
		</view>
		<!-- 关联操作列表 -->
		<view class="cardsAction itemsBlock">
			<view>
				<view class="userLog"><image src="../../static/img/userLogA.png" mode="widthFix" class="log userLogA"></image></view>
				<view>银行卡</view>
			</view>
			<view>
				<view class="userLog"><image src="../../static/img/userLogB.png" mode="widthFix" class="log userLogB"></image></view>
				<view>账单</view>
			</view>
			<view>
				<view class="userLog"><image src="../../static/img/userLogC.png" mode="widthFix" class="log userLogC"></image></view>
				<view>卡券</view>
			</view>
			<view @tap="goPath('/pages/user/account')">
				<view class="userLog"><image src="../../static/img/userLogD.png" mode="widthFix" class="log userLogD"></image></view>
				<view>账户余额</view>
			</view>
		</view>
		<view class="itemsBlock clmargin">
			<view class="title">贷款总览</view>
			<ListItem line titleLab :title="lendInfo.repaidAmount" note="30天内待还 (元)" :des="allLend" />
			<ListItem line  title="借款记录" @click="goPath('/pages/borrow/log')" />
		</view>
		<view class="itemsBlock clmargin">
			<view class="title">理财总览</view>
			<ListItem line titleLab :title="borrowInfo.income" note="昨日收益 (元)" :des="totalMoney" />
			<!--<ListItem line title="债权" @click="goPath('/pages/user/claims')" />
			<ListItem line title="收款进度" @click="goPath('/pages/user/progress')" />-->
			<ListItem  title="出借记录" @click="goPath('/pages/user/myLendLog')" />
			<!-- <ListItem  title="出借记录" @click="goPath('/pages/user/lendLog')" /> -->
		</view>
		<view class="itemsBlock clmargin">
			<ListItem line title="风险测评" des="保守型" />
			<ListItem title="退出登录" @click="outLogoin" />
		</view>
		<FooterMark />
    </view>
</template>

<script>
	import FooterMark from '../../components/FooterMark.vue';
	import ListItem from '../../components/ListItem.vue'
    export default {
		data(){
			return {
				userInfo:{},
				lendInfo:{},
				borrowInfo:{},
				allLend: '',
				totalMoney: ''
			}
		},
		components: {
			FooterMark,
			ListItem
		},
		onLoad() {
			//this.getlendInfo()
			//this.getborrowInfo()
		},
		onShow() {
			this.getUserInfo()
            this.getlendInfo()
            this.getborrowInfo()
		},
        methods: {
			goPath:function (url){
				uni.navigateTo({ url });
			},
			getUserInfo: function(){
				this.request({
					url: `consumer/my/consumers`,
				}).then(res => {
					this.userInfo = res.data.result
					uni.setStorage({key:'userInfo',data: res.data.result})
				}).catch(err => {
					uni.showToast({
                        icon: 'none',
                        title: err.data.msg
                    });
				})
			},
			//退出
			outLogoin: function(){
				uni.clearStorage();
				this.goPath('/pages/user/login')
			},
			// 贷款总览
            getlendInfo:function(){
				this.request({
					url: 'transaction/my/projects/loanOverview',
				}).then(res => {
					this.lendInfo = res.data.result
					this.allLend = `总待还 ${res.data.result.totalRepaidAmount} 元`
				}).catch(err => {
					uni.showToast({
						icon: 'none',
						title: err.data.msg
					});
				})
			},
			// 理财总览
            getborrowInfo:function(){
				this.request({
					url: 'transaction/my/tenders/investOverview',
				}).then(res => {
					this.borrowInfo = res.data.result
					this.totalMoney = `总资产 ${res.data.result.totalMoney} 元`
				}).catch(err => {
					uni.showToast({
						icon: 'none',
						title: err.data.msg
					});
				})
			}
        }
    }
</script>

<style scoped lang="scss">
.userCont{
	.head{
		background: url('~@/static/img/userBan.png') top center no-repeat;
		background-size: contain;
		padding: 60upx 30upx 30upx 30upx;
		width: calc(100% - 60upx);
		height: 300upx;
		display:flex;
		color:#fff;
		justify-content: space-between;
		.info{
			flex: 1;
			text-align: left;
			margin-left: 30upx;
			.name{
				line-height: 1.8;
			}
			.label{
				display: inline-block;
				padding: 4upx 20upx;
				font-size: 28upx;
				background: #fff;
				border-radius: 100upx;
				text-align: center;
				color:$uni-color-primary;
		    }
		}
		.more{
			margin-top: 40upx;
		}
		.topImg{
			width: 128upx;
			height: 128upx;
			border-radius: 100%;
		}
	}
	.cardsAction{
		position: absolute;
		width: calc(100% - 120upx);
		top:180upx;
		background: #fff;
		display: flex;
		justify-content: space-around;
		text-align: center;
		line-height: 1.5;
		.icon{
			color:$uni-color-primary;
		}
	}
	.clmargin{
		padding: 0 30upx;
	}
	.title{
		font-size: 28upx;
		padding-top:30upx;
	}
	.userLog{
		display: flex;
		justify-content: center;
		align-items: center;
		height: 80upx;
		text-align: center;
	}
	.userLogA{
		width: 55upx;
		height: 42upx;
	}
	.userLogB{
		width: 45upx;
		height: 46upx;
	}
	.userLogC{
		width: 55upx;
		height: 63upx;
	}
	.userLogD{
		width: 52upx;
		height: 40upx;
	}
}
</style>
