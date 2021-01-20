<template>
	<view class="uni-tab-bar progress">
		<topBar title='收款进度' />
		<view id="tab-bar" class="uni-swiper-tab box-dis-flex">
			<view v-for="(tab,index) in tabBars" :key="tab.id" class="swiper-tab-list" :class="tabIndex==index ? 'active' : ''"
			 :id="tab.id" :data-current="index" @click="tapTab">{{tab.name}}</view>
		</view>
		<swiper :current="tabIndex" class="swiper-box" :duration="300" @change="changeTab">
			<swiper-item v-for="(tab,index1) in newsitems" :key="index1">
				<scroll-view class="list" scroll-y @scrolltolower="loadMore(index1)">
					<view class="title">
						<view>待收本金5,858.86元</view>
						<view>待收利息58.86元</view>
					</view>
					<view class="cardsBlock cards">
						<view class="tit">
							<view>1期</view>
							<view>应收日：19-3-23</view>
						</view>
						<view class="cont">
							<view class="row">
								<view class="col">8.23元</view>
								<view class="col text-algin-cn">57566元</view>
								<view class="col text-algin-rt">4.23元</view>
							</view>
							<view class="row gray">
								<view class="col">收款金额</view>
								<view class="col text-algin-cn">本金</view>
								<view class="col text-algin-rt">利息</view>
							</view>
						</view>
					</view>
				</scroll-view>
			</swiper-item>
		</swiper>
	</view>
</template>
<script>
	import ButtonItems from '../../components/ButtonItems.vue'
	import LendCards from '../../components/LendCards.vue';
	import topBar from '../../components/TopBar.vue'
	const tpl = {
		data0: {
			"datetime": "40分钟前",
			"article_type": 0,
			"title": "uni-app行业峰会频频亮相，开发者反响热烈!",
			"source": "DCloud",
			"comment_count": 639
		},
		data1: {
			"datetime": "一天前",
			"article_type": 1,
			"title": "DCloud完成B2轮融资，uni-app震撼发布!",
			"image_url": "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/shuijiao.jpg?imageView2/3/w/200/h/100/q/90",
			"source": "DCloud",
			"comment_count": 11395
		},
		data2: {
			"datetime": "一天前",
			"article_type": 2,
			"title": "中国技术界小奇迹：HBuilder开发者突破200万",
			"image_url": "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/muwu.jpg?imageView2/3/w/200/h/100/q/90",
			"source": "DCloud",
			"comment_count": 11395
		},
		data3: {
			"article_type": 3,
			"image_list": [{
				"url": "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/cbd.jpg?imageView2/3/w/200/h/100/q/90",
				"width": 563,
				"height": 316
			}, {
				"url": "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/muwu.jpg?imageView2/3/w/200/h/100/q/90",
				"width": 641,
				"height": 360
			}, {
				"url": "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/shuijiao.jpg?imageView2/3/w/200/h/100/q/90",
				"width": 640,
				"height": 360
			}],
			"datetime": "5分钟前",
			"title": "uni-app 支持使用 npm 安装第三方包，生态更趋丰富",
			"source": "DCloud",
			"comment_count": 11
		},
		data4: {
			"datetime": "2小时前",
			"article_type": 4,
			"title": "uni-app 支持原生小程序自定义组件，更开放、更自由",
			"image_url": "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/cbd.jpg?imageView2/3/w/200/h/100/q/90",
			"source": "DCloud",
			"comment_count": 69
		}
	};

	export default {
		components: {
			ButtonItems,
			LendCards,
			topBar
		},
		data() {
			return {
				scrollLeft: 0,
				isClickChange: false,
				tabIndex: 0,
				newsitems: [],
				tabBars: [{
					name: '已收期次（4）',
					id: 'guanzhu'
				}, {
					name: '待收期次（8）',
					id: 'tuijian'
				}]
			}
		},
		onLoad() {
			this.newsitems = this.randomfn()
		},
		methods: {
			goDetail(e) {
				uni.navigateTo({
					url: '/pages/lend/details'
				});
			},
			close(index1, index2) {
				uni.showModal({
					content: '是否删除本条信息？',
					success: (res) => {
						if (res.confirm) {
							this.newsitems[index1].data.splice(index2, 1);
						}
					}
				})
			},
			loadMore(e) {
				setTimeout(() => {
					this.addData(e);
				}, 1200);
			},
			addData(e) {
				if (this.newsitems[e].data.length > 30) {
					this.newsitems[e].loadingText = '没有更多了';
					return;
				}
				for (let i = 1; i <= 10; i++) {
					this.newsitems[e].data.push(tpl['data' + Math.floor(Math.random() * 5)]);
				}
			},
			async changeTab(e) {
				let index = e.target.current;
				if (this.newsitems[index].data.length === 0) {
					this.addData(index)
				}
				if (this.isClickChange) {
					this.tabIndex = index;
					this.isClickChange = false;
					return;
				}
				let tabBar = await this.getElSize("tab-bar"),
					tabBarScrollLeft = tabBar.scrollLeft;
				let width = 0;

				for (let i = 0; i < index; i++) {
					let result = await this.getElSize(this.tabBars[i].id);
					width += result.width;
				}
				let winWidth = uni.getSystemInfoSync().windowWidth,
					nowElement = await this.getElSize(this.tabBars[index].id),
					nowWidth = nowElement.width;
				if (width + nowWidth - tabBarScrollLeft > winWidth) {
					this.scrollLeft = width + nowWidth - winWidth;
				}
				if (width < tabBarScrollLeft) {
					this.scrollLeft = width;
				}
				this.isClickChange = false;
				this.tabIndex = index; //一旦访问data就会出问题
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
			async tapTab(e) { //点击tab-bar
				let tabIndex = e.target.dataset.current;
				if (this.newsitems[tabIndex].data.length === 0) {
					this.addData(tabIndex)
				}
				if (this.tabIndex === tabIndex) {
					return false;
				} else {
					let tabBar = await this.getElSize("tab-bar"),
						tabBarScrollLeft = tabBar.scrollLeft; //点击的时候记录并设置scrollLeft
					this.scrollLeft = tabBarScrollLeft;
					this.isClickChange = true;
					this.tabIndex = tabIndex;
				}
			},
			randomfn() {
				let ary = [];
				for (let i = 0, length = this.tabBars.length; i < length; i++) {
					let aryItem = {
						loadingText: '加载更多...',
						data: []
					};
					if (i < 1) {
						for (let j = 1; j <= 10; j++) {
							aryItem.data.push(tpl['data' + Math.floor(Math.random() * 5)]);
						}
					}
					ary.push(aryItem);
				}
				return ary;
			}
		}
	}
</script>

<style scoped lang="scss">
	.swiper-tab-list{
		flex: 1;
	}
	.topImg{
		width: 700upx;
		margin-left: 50%;
		transform: translateX(-50%);
	}
	.uni-tab-bar-loading {
		text-align: center;
		font-size: 28upx;
		color: #999;
	}
	.uni-swiper-tab{
		border-bottom: solid 2upx $uni-border-color;
	}
	.progress{
		.title{
			display: flex;
			justify-content: space-between;
			padding: 30upx 34upx 0;
			font-size:24upx;
			color:$uni-text-color-grey;
		}
		.cards{
			padding-top: 10upx;
			font-size: 28upx;
			.tit{
				display: flex;
				justify-content: space-between;
				border-bottom: solid 2upx $uni-border-color;
				line-height: 60upx;	
			}
			.cont{
				padding-top: 20upx;
				.row{
					line-height: 40upx;
					display: flex;
					.col{
						flex:1;
					}
				}
				.gray{
					color:$uni-text-color-grey;
				}
			}
		}
	}
</style>

