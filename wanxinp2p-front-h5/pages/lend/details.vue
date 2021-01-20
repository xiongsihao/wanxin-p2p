<template>
	<view class="content lendDetails">
		<topBar title="借款"></topBar>
		<view class="topHead">
			<view class="lendInfo">
			<view class="box">
				<view><text class="num">{{details.annualRate * 100}}</text> %</view>
				<view class="des">约定借款年华利率</view>
				<view class="items">
					<view>期限{{details.period/30}}个月</view>
					<view> 风险等级B </view>
				</view>
				<view class="items">
					<view class="progressBar"><view class="progress" :style="proWid" ></view></view>
				</view>
				<view class="items">
					<view>总额：{{utils.priceFormat(details.amount)}}</view>
					<view> 剩余总额： {{utils.priceFormat(details.remainingAmount)}}</view>
				</view>
			</view>
			<view class="bot" @tap="goPath">
				<view>{{details.tenderCount}}人出借</view>
				<view> > </view>
			</view>
			</view>
			<view class="cardsBlock infoCont">  
			<view class="tit">
				债券满30天可申请转让
			</view>
			<view class="progressBarX"><view class="progressX" style="width: 80%;"></view></view>
			<view class="info">
				<view class="it">满标计息</view>
				<view class="it">每月收款</view>
				<view class="it">每月收款...</view>
				<view class="it">每月收款</view>
			</view>
		</view>
		
		</view>
		
		<view class="uni-tab-bar">
			<view id="tab-bar" class="uni-swiper-tab box-dis-flex">
				<view class="swiper-tab-list" :class="tabIndex==0 ? 'active' : ''" @click="tapTab(0)">原始借款人信息</view>
				<view class="swiper-tab-list" :class="tabIndex==1 ? 'active' : ''" @click="tapTab(1)">信用与认证</view>
				<view class="swiper-tab-list" :class="tabIndex==2 ? 'active' : ''" @click="tapTab(2)">收款计划</view>
			</view>
			<swiper :current="tabIndex" class="swiper-box" :duration="300" @change="changeTab">
				<swiper-item >
					<scroll-view class="cont" scroll-y>
						<view class="block">
							<view class="items"><view>姓名</view> <view>{{userInfo.fullname}}</view></view>
							<view class="items"><view>年龄</view> <view>{{userInfo.age}}</view></view>
							<view class="items"><view>性别</view> <view>{{userInfo.gender}}</view></view>
							<view class="items"><view>出生年月</view> <view>{{userInfo.birthday}}</view></view>
							<view class="items"><view>手机号</view> <view>{{utils.numReplaceStar(userInfo.mobile,3,7)}}</view></view>
							<view class="items"><view>身份证</view> <view>{{utils.numReplaceStar(userInfo.idNumber,4,14)}}</view></view>
							<image src="../../static/img/ov.png" mode="" class="waterMark"></image>
						</view>
					</scroll-view>
				</swiper-item>
				<swiper-item >
					<scroll-view class="cont" scroll-y >
						<view class="block">
							<view class="noData">
								暂无数据！
							</view>
						</view>
						<view class="block" v-if= "creditData.data">
							<view class="items"><view>姓名</view> <view>张三</view></view>
							<view class="items"><view>年龄</view> <view>12</view></view>
							<view class="items"><view>性别</view> <view>男</view></view>
							<view class="items"><view>出生年月</view> <view>89-7-12</view></view>
							<image src="../../static/img/ov.png" mode="" class="waterMark"></image>
						</view>
						<view class="block" v-if="creditData.data">
							<view class="title">
								工作信息
							</view>
							<view class="items"><view>姓名</view> <view>张三</view></view>
							<view class="items"><view>年龄</view> <view>12</view></view>
							<view class="items"><view>性别</view> <view>男</view></view>
							<view class="items"><view>出生年月</view> <view>89-7-12</view></view>
						</view>
					</scroll-view>
				</swiper-item>
				<swiper-item >
					<scroll-view class="cont" scroll-y>
						<view class="block">
							<view class="items"><view>期次</view> <view>本金(元)</view><view>利息(元)</view> <view>状态</view></view>
							<view class="items" v-for="(item, index) in planData" :key="index" >
								<view>{{item.planItem}}期</view><view>{{item.amount.toFixed(2)}}</view> <view>{{item.interest.toFixed(2)}}</view> <view>{{item.st}}</view>
							</view>
						</view>
					</scroll-view>
				</swiper-item>
			</swiper>
		</view>
		<view class="ButList" v-if="details.remainingAmount > 0">
			<view class="inpbox"><input type="number" class="inpMonay" v-model="money" :placeholder="`金额不得高于${details.remainingAmount}`"> <text>元</text></view>
			<view class="but" @tap="submitHandle">授权出借</view>
		</view>
		<view class="ButList" v-if="details.remainingAmount <= 0">
			<view class="gray">已售完</view>
		</view>
	</view>
</template>

<script>
	import ButtonItems from '../../components/ButtonItems.vue'
	import ListItem from '../../components/ListItem.vue'
	import topBar from '../../components/TopBar.vue'
	import store from '../../store/index.js'	
	
	export default {
	data() {
        return {
            scrollLeft: 0,
			isClickChange: false,
			tabIndex: 0,
			ids: this.$route.query.ids,
			details: {},
			userInfo:{},
			creditData: {},
			newsitems: [],
			planData: [],
			objName:[],
			money:'',
			tabBars: [{
				name: '金融智投',
				id: 'guanzhu'
			}, {
				name: '新标专区',
				id: 'tuijian'
			}, {
				name: '转让专区',
				id: 'tiyu'
			}]
        }
    },
	computed:{
		proWid(){
			return `width:${(this.details.amount - this.details.remainingAmount)/this.details.amount*100}%`
		}
	},
	components:{
		ButtonItems,
		ListItem,
		topBar
	},
	onLoad(){
		this.getDetils()
	},
    methods: {
        submitHandle: function () {
			if (this.money && 0 < this.money &&  this.money <= Number(this.details.remainingAmount)){
				uni.navigateTo({url:`/pages/lend/confirmLend?id=${this.ids}&amount=${this.money}&objName=${this.objName}`})
			}else{
				uni.showToast({
					icon: 'none',
					title: '请输入正确的出借额！',
				});
			}
			
		},
		goPath(){
			uni.navigateTo({
				url: `/pages/lend/lendLog?id=${this.$route.query.ids}`
			});
		},
		getDetils(){
			this.request({
				url: `transaction/projects/${this.ids}`,
			}).then(res => {
				const dat = res.data.result[0]
				this.details = dat
				this.computPlanData(dat, dat.amount)
				this.objName = this.details.name
				store.commit('setLendInfo', res.data.result[0])
				this.getUserInfo(this.details.consumerId)
			}).catch(err => {
				uni.showToast({
					icon: 'none',
					title: err.data.msg
				});
			})
		},
		computPlanData(dat, amount){
			let planData = []
			for (let i=1; i <= dat.period/30; i++ ){
				let arr = {planItem: i, amount: amount/(dat.period/30), interest:amount*dat.annualRate/(dat.period/30), st:'待还'}
				planData.push(arr)
			}
			this.planData = planData
		},
		getUserInfo(id){
			this.request({
				url: `consumer/my/borrowers/${id}`,
			}).then(res => {
				this.userInfo = res.data.result
			}).catch(err => {
				uni.showToast({
					icon: 'none',
					title: err.data.msg
				});
			})
		},
		async changeTab(e) {
			let index = e.target.current;
			if (this.isClickChange) {
				this.tabIndex = index;
				this.isClickChange = false;
				return;
			}
			this.isClickChange = false;
			this.tabIndex = index;
		},
		getElSize(id) { //得到元素的size
			return new Promise((res, rej) => {
				uni.createSelectorQuery().select("#" + id).fields({
					size: true,
					scrollOffset: true
				}, (data) => {
					res(data);
				}).exec();
			})
		},
		async tapTab(int) { //点击tab-bar
			let tabIndex = int;
			if (this.tabIndex === tabIndex) {
				return false;
			} else {
				let tabBar = await this.getElSize("tab-bar"),
					tabBarScrollLeft = tabBar.scrollLeft; //点击的时候记录并设置scrollLeft
				this.scrollLeft = tabBarScrollLeft;
				this.isClickChange = true;
				this.tabIndex = tabIndex;
			}
		}
    },
	watch:{
		money(stVal,oldVal) {
			this.computPlanData(this.details, stVal)
		}
	}
}
</script>
<style lang="scss" scoped>
	.uni-tab-bar{
		 height: calc(100vh - 100upx);
		 margin-bottom: 100upx;
	}
	.swiper-tab-list{
		flex: 1;
	}
	.uni-swiper-tab{
		border-bottom: solid 1px $uni-border-color;
	}
	.uni-tab-bar-loading {
		text-align: center;
		font-size: 28upx;
		color: #999;
	}
	.uni-scroll-view{
		height: 100vh;
	}
.lendDetails{
	background: #fff;
	.topHead{
		font-size: 28upx;
		background: #f4f4f4;
		line-height: 70upx;
		.lendInfo{
		margin: 32upx;
		box-shadow: 0 0 4upx 4upx  $uni-border-color;
		border-radius: 10upx;
		.num{
			font-size: 64upx;
		}
		.des{
			margin-bottom: 30upx;
			line-height: 40upx;
		}
		.box{
			position: relative;
			color:#fff;
			padding:30upx;
			border-radius: 10upx 10upx 0upx 0upx;
			background: linear-gradient(0deg, #8a8fef, #4f57eb);
			.items{
				display: flex;
				justify-content: space-between;
			}
		}
		.bot{
			border-radius: 0upx 0upx 10upx 10upx;
			padding: 10upx 30upx;
			color:$uni-text-color-greyb;
			background: #fff;
			display: flex;
			justify-content: space-between;
		}
	}
	}
	.infoCont{
		color:$uni-text-color-grey;
		background: #fff;
		.tit{
			margin-left: 30%;
			padding-bottom: 14upx;
			padding-left: 4upx;
			border-left: solid 2upx $uni-border-color;
		}
		.info{
			display: flex;
			.it{
				flex:1;
				padding-top: 14upx;
				padding-left: 4upx;
				border-left: solid 2upx $uni-border-color;
			}
		}
	}
	.cont{
		 background: #f4f4f4;
		 height: calc(100vh - 200upx);
		.block{
			position: relative;
			background: #fff;
			margin-bottom: 30upx;
			color: $uni-text-color-grey;
			font-size: 28upx;
			line-height: 2;
			padding: 20upx 0;
			.title{
				padding:0 30upx;
				border-bottom: solid 2upx $uni-border-color;
				line-height: 2.2;
				color:$uni-text-color-greyb;
			}
			.waterMark{
				position: absolute;
				width: 157upx;
				height: 157upx;
				right: 100upx;
				bottom:40upx;
			}
			.items{
				padding:0 30upx;
				display: flex;
				justify-content: space-between;
			}
			.tit{
				display: flex;
			}
		}
	}
	.progressBar{
		position: relative;
		background: rgba(255,255,255,0.3);
		height:4upx;
		width: 100%;
	}
	.progress{
		position: absolute;
		content: '';
		left: 0upx;
		height:4upx;
		width: 0;
		background: #fff;
		transition: 1s;
	}
	.progress::before{
		position: absolute;
		content: '';
		top:-1px;
		right: 0upx;
		height:8upx;
		width: 8upx;
		border-radius: 100%;
		background: #fff;
	}
	.progressBarX{
		position: relative;
		background:$uni-border-color;
		height:12upx;
		width: 100%;
		border-radius: 12upx;
	}
	.progressX{
		position: absolute;
		content: '';
		left: 0upx;
		height:12upx;
		width: 0;
		background: linear-gradient(180deg, #ffffff, #4f57eb);
		border-radius: 12upx;
		transition: 1s;
	}
	.ButList{
		position: fixed;
		z-index: 99;
		width: 100%;
		bottom:0;
		left:0;
		border-top:solid 2upx $uni-border-color;
		line-height: 100upx;
		background: #fff;
		display: flex;
		font-size: 28upx;
		.inpbox{
			padding: 0 30upx;
			flex:1;
			display: flex;
			.inpMonay{
				flex: 1;
				margin-top: 24upx;
			}
		}
		.but{
			background: $uni-color-primary;
			padding:0 60upx;
			color:#fff;
		}
		.gray{
			background: #dbdee7;
			color:#fff;
			width: 100%;
			text-align: center;
		}
	}
	.noData{text-align: center}
}
</style>
