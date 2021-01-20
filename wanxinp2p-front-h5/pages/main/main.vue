<template>
    <view class="content">
		<!-- logo - top -->
		<TopHeader></TopHeader>
		<!-- banner -->
		<view class="banner">
			<view class="page-section swiper">
				<view class="page-section-spacing">
					<swiper class="swiper" :indicator-dots="indicatorDots" circular="true" :autoplay="autoplay" :previous-margin="marginwt" :next-margin="marginwt" :interval="interval" :duration="duration">
						<swiper-item>
							<view class="swiper-item uni-bg-red"><image src="../../static/img/banner1.png" mode="widthFix" class="bannerImg"></image></view>
						</swiper-item>
						<swiper-item>
							<view class="swiper-item uni-bg-green"><image src="../../static/img/banner2.png" mode="widthFix" class="bannerImg"></image></view>
						</swiper-item>
						<swiper-item>
							<view class="swiper-item uni-bg-blue"><image src="../../static/img/banner3.png" mode="widthFix" class="bannerImg"></image></view>
						</swiper-item>
					</swiper>
				</view>
			</view>
        </view>
		<!-- 公告 -->
		<view class="notice">
			<view class="title cl-org"><text class="icon">&#xe604;</text> <text class="tit">公告</text> </view>
			<view class="cont">公告内容</view>
		</view>
		<!-- 页面快捷按钮 -->
		<view class="linkList">
			<view class="items"><view class="icon butItemA">&#xe602;</view><view>交流社区</view></view>
			<view class="items"><view class="icon butItemB">&#xe605;</view><view>任务中心</view></view>
			<view class="items"><view class="icon butItemC">&#xe606;</view><view>推荐有奖</view></view>
			<view class="items"><view class="icon butItemD">&#xe607;</view><view>积分商城</view></view>
		</view>
		<!-- 借款 info -->
		<view class="loanInfo">
			<view class="topBox">
				<view>
					<view>可借额度（元）</view>
					<view class="pirce">{{utils.priceFormat(userInfo.loanAmount)}}</view>
				</view>
				<view>
					<view class="text-algin-rt">产品详情 </view>
					<view class="but"><ButtonItems value="领取额度" type="med-blue" @tap="goTab('/pages/borrow/borrow')"></ButtonItems></view>
				</view>
			</view>
			<view class="footerBox">
				<view class="firstInfo">
					<view class="cl-main">12个月</view>
					<view class="pirce">借款期限</view>
				</view>
				<view class="lastInfo">
					<view class="cl-main">个人生活消费 </view>
					<view class="icons">借款用途</view>
				</view>
			</view>
		</view>
		<!-- 标题 -->
		<view class="labTitle">万信推荐</view>
		<!-- 推荐 -->
		<!-- 已登录 -->
		<view class="recom" v-if="loginState && userInfo.isBindCard">
			<view class="topHead">
				<view class="topInfo">
					<view class="param">
						<text>{{recommendData.annualRate ? recommendData.annualRate * 100 : 10}}</text> %
					</view>
					<view class="">
						历史参考扣费后年化率
					</view>
				</view>
				<view class="butItem">
					<ButtonItems  value="授权出租" type="med-orange" @tap="goTab('/pages/lend/main')"></ButtonItems>
				</view>
			</view>
			<view class="boot">
				<view class="item">30天后可转</view>
				<view class="item">90天起出借</view>
			</view>
		</view>
		<!-- 未登录 -->
		<view class="recom box-dis-flex" v-if="!loginState || !userInfo.isBindCard">
			<view class="labelItem labStateA">
				<view class="lab"><text class="icon">&#xe613;</text></view>
				<view class="but">注册账号</view>
			</view>
			<view class="labelItem interval">  • • • </view>
			<view class="labelItem labStateB">
				<view class="lab"><text class="icon">&#xe611;</text></view>
				<view class="but" @click="openStorage('open')">开通存管</view>
			</view>
			<view class="labelItem interval">  • • • </view>
			<view class="labelItem labStateC">
				<view class="lab" ><text class="icon">&#xe612;</text></view>
				<view class="but">授权出借</view>
			</view>
		</view>
		<!-- 运行时间 -->
		<view class="timeCont">
			<view class="year">
				<text class="cardBox">
					<text class="cards"> 1 </text>
				</text> 
				<text class="lab">年</text>
			</view>
			<view class="month">
				<text class="cardBox">
					<text class="cards"> 0 </text> <text class="cards"> 3 </text> 
				</text>	
				<text class="lab">月</text></view>
			<view class="day">
				<text class="cardBox">
					<text class="cards"> 1 </text> <text class="cards"> 5 </text> <text class="cards"> 9 </text>
				</text>
				<text class="lab">日</text></view>
		</view>
		<view class="timeLable"><text class="title">平台稳定运行时间</text></view>
		<view class="totalData">
			<view class="firstInfo">
				<view class="cl-main">121亿834万元</view>
				<view class="tit">累计交易金额</view>
			</view>
			<view class="lastInfo">
				<view class="cl-main">12亿834万元 </view>
				<view class="tit">出借人累计收益</view>
			</view>
		</view>
		<FooterMark></FooterMark>
    </view>
</template>

<script>
	import TopHeader from '../../components/TopHead.vue'
	import ButtonItems from '../../components/ButtonItems.vue'
	import FooterMark from '../../components/FooterMark.vue'
	
    export default {
		data() {
			return {
				background: ['color1', 'color2', 'color3'],
				indicatorDots: false,
				autoplay: false,
				interval: 2000,
				duration: 500,
				marginwt: '0upx',
				userInfo:{},
				loginState: false,
                recommendData: {}
			}
		},
		components:{
			TopHeader,
			ButtonItems,
			FooterMark
		},
        onShow() {
			this.getUserInfo(),
			this.getRecommendData()
        },
		methods:{
			openStorage: function(st){
				switch (st) {
					case 'reg':
						uni.navigateTo({url:'/pages/user/login'})
						break 
					case 'open': 
						uni.navigateTo({url:'/pages/lend/openStorage'})
						break
					default:
						uni.navigateTo({url:'/pages/user/login'})
				}
				
			},
            goTab(tabUrl){
                uni.switchTab({url: tabUrl})
            },
			
			getUserInfo: function(){
				const userInfo = uni.getStorageSync('userInfo')
				
				if (userInfo){
					this.userInfo = userInfo
					this.loginState = true
					return false;
				}
				this.request({
					url: `consumer/my/consumers`,
				}).then(res => {
					this.userInfo = res.data.result
					this.loginState = true
					uni.setStorage({key:'userInfo', data: res.data.result})
				}).catch(err => {
					uni.showToast({
                        icon: 'none',
                        title: err.data.msg
                    });
				})
			},
			
			getRecommendData:function(){
				this.request({
					url: `transaction/projects/q?up=1`,
					params: {up:1},
					method: 'POST'
				}).then(res => {
                    this.recommendData = res.data.result.content[0]
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
    .banner{
		margin-top: 20upx;
		.swiper{
			height:340upx;
		}
		.bannerImg{
		   width: 720upx;
		   margin:0 15upx;
		   border-radius: 40upx;
		}
	}
	.notice{
		display: flex;
		margin:40upx 30upx;;
		padding:14upx 20upx;
		border-radius: 10upx;
		box-shadow: 0 0 10upx $uni-border-color;
		font-size: 14px;
		.title{
			color:$uni-font-org;
			border-right: solid 1px $uni-border-color;
			padding-right: 20upx;
			height: 40upx;
			display: flex;
			.icon{
			    position: relative;
				 top:-2px;
				color: $uni-font-org;
			}
		}
		.cont{
			padding-left: 20upx;
		}
	}
	.linkList{
		display: flex;
		text-align: center;
		line-height: 80upx;
		justify-content: space-between;
		padding: 0 80upx;
		font-size: 14px;
		.butItemA,.butItemB,.butItemC,.butItemD{
			color:#fff;
			width: 80upx;
			height: 80upx;
			border-radius: 20upx;
			font-size: 32upx;
			background: $uni-color-com;
		}
		.butItemB{
			background: $uni-color-task;
		}
		.butItemC{
			background: $uni-color-rec;
		}
		.butItemD{
			background: $uni-color-int;
		}
		.items{
			display: flex;
			flex-direction: column;
			align-items: center;
		}
	}
	.loanInfo{
		padding:30upx;
		margin:30upx;
		border-radius: 10upx;
		font-size: 24upx;
		box-shadow: 0 0 10upx $uni-border-color;
		.topBox{
			display: flex;
			justify-content: space-between;
			border-bottom: solid 1px $uni-border-color;
			padding-bottom:20upx;
			color:$uni-text-color-grey;
			.pirce{
				color: $uni-color-primary;
				font-size: 64upx;
				margin-top:20upx; 
			}
			.but{
				margin-top:36upx; 
			}
		}
		.footerBox{
			display: flex;
			text-align: center;
			padding-top:20upx;
			font-size: 28upx;
			color:$uni-text-color-grey;
			line-height: 1.5;
			.firstInfo{
				flex: 1;
				border-right: solid 1px $uni-border-color;
			}
			.lastInfo{
				flex: 1;
			}
		}
	}
	.recom{
		padding:30upx;
		margin:30upx;
		border-radius: 10upx;
		font-size: 24upx;
		box-shadow: 0 0 10upx $uni-border-color;
		.labelItem{
			flex: 3;
			text-align: center;
			.lab{
				width: 76upx;
				height: 76upx;
				margin: 0 auto;
				line-height: 76upx;
				text-align: center;
				border-radius: 100%;
				background: $uni-border-color;
				color:#fff;
			}
			.but{
				font-size: 28upx;
				margin-top: 30upx;
				padding:10upx 10upx;
				color:$uni-text-color-grey;
			}
		}
		.interval{
			flex: 1;
			color:$uni-color-blueb;
			padding-top:24upx;
		}
		.labStateA{
			.lab{
				background:$uni-color-blueb;
			}
			.but{
				color:$uni-color-blueb;
			}
		}
		.labStateB{
			.lab{
				background:$uni-color-blue;
				position: relative;
			}
			.lab::before{
				content: '';
				position: absolute;
				left: 2upx;
				top:2upx;
				width: 64upx;
				height: 64upx;
				border-radius: 100%;
				border: solid 4upx #ffffff;
			}
			.but{
				border:solid 2upx #ccc;
				color:$uni-color-blue;
				border-radius: 100upx;
			}
		}
		.topHead{
			display: flex;
			.topInfo{
				flex: 1;
				color:$uni-text-color-grey;
				.param{
					font-size: 64upx;
					color:$uni-font-org;
				}
			}
			.butItem{
				margin-top: 26upx;
			}
		}
		.boot{
			display: flex;
			font-size: 32upx;
			margin-top: 20upx;
			.item{
				text-align: left;
				flex:1;
				color: $uni-font-main;
			}
		}
	}
	.timeCont{
		text-align: center;
		margin: 0 0 20upx 0;
		.year,.month,.day{
			position: relative;
			display: inline-block;
			font-size: 28upx;
			.lab{
				position: relative;
				bottom: -18upx;
				padding-right:10upx;
			}
		}
		.cards{
			font-size: 38upx;
			display: inline-block;
			color:$uni-font-main;
			text-align: center;
			margin: 8upx;
			width: 40upx;
			line-height: 60upx;
			border:solid 1px $uni-border-color;
			position: relative;
		}
		.cards::before{
			    content: ' ';
				position: absolute;
				width: 40upx;
				height: 2upx;
				left: 0upx;
				top: calc(50%);
				border-top:solid 2upx $uni-border-color;
			}
		.cardBox{
			position: relative;
		}
		.cardBox::before{
			content: ' ';
			position: absolute;
			display: inline-block;
			width: calc(100% - 18upx);
			height: 18upx;
			top: calc(50% - 10upx);
			left:4upx;
			z-index: 1;
			border-left:solid 6upx $uni-text-color-placeholder;
			border-right:solid 6upx $uni-text-color-placeholder;
		}
	}
	.timeLable{
		color: $uni-text-color-grey;
		text-align: center;
		position: relative;
		border-top:solid 1px $uni-border-color;
		margin: 30upx 60upx;
		.title{
			font-size: 28upx;
			position: relative;
			top: -30upx;
			display: inline-block;
			padding: 0 20upx;
			background: #fff;
		}
	}
	.totalData{
		display: flex;
		text-align: center;
		font-size: 28upx;
		color:$uni-text-color-grey;
		line-height: 1.8;
		.firstInfo{
			flex: 1;
			border-right: solid 1px $uni-border-color;
		}
		.lastInfo{
			flex: 1;
		}
		.tit{
			font-size: 24upx;
		}
	}
</style>
