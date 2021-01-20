<template>
    <view class="content borrowCont">
        <view class="contTop">
			<image src="../../static/img/borrowTopIco.png" mode="widthFix" class="topImg"></image>
		</view>
		<block v-for="(newsitem,index) in newsitems" :key="index">
			<borrow-cards :options="newsitem" :type="index" @click="goDetail(newsitem)"></borrow-cards>
		</block>
		<FooterMark></FooterMark>
    </view>
</template>

<script>
    import { mapState } from 'vuex'
    import FooterMark from '../../components/FooterMark.vue';
	import BorrowCards from '../../components/BorrowCards.vue';
	
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
        computed: mapState(['forcedLogin', 'hasLogin', 'userName']),
		data() {
			return {
				scrollLeft: 0,
				isClickChange: false,
				tabIndex: 0,
				newsitems: [],
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
		
		onLoad() {
			this.newsitems = this.randomfn()
		},
		components: {
			FooterMark,
			BorrowCards
		},
		methods:{
			randomfn() {
				let ary = [];
				for (let i = 0, length = 10; i < length; i++) {
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
			},
			goDetail(){
				uni.navigateTo({
                    url: '/pages/borrow/borrow',
                });
			}
		}
    }
</script>

<style lang="scss" scoped>
    .borrowCont {
		margin-top: 30upx;
		overflow: hidden;
		.topImg{
			margin-left: 50%;
			transform: translateX(-50%);
			width: 688upx;
		}
	}
</style>
