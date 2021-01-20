<template>
	<view class="uni-tab-bar claims">
		<topBar title='借还记录' />
		<view id="tab-bar" class="uni-swiper-tab box-dis-flex">
			<view v-for="(tab,index) in tabBars" :key="tab.id" class="swiper-tab-list" :class="tabIndex==index ? 'active' : ''"
			 :id="tab.id" :data-current="index" @click="tapTab">{{tab.name}}</view>
		</view>
		<swiper :current="tabIndex" class="swiper-box" :duration="300" @change="changeTab">
			<swiper-item v-for="(tab,index) in tabBars" :key="index">
				<scroll-view class="list" scroll-y @scrolltolower="loadMore(index)">
					<view class="cards" v-for="(tab,ind) in showData" :key="ind">
						<view class="cont">
							<view class="row" @tap="goPath('/')">
								<view class="col">
									<view><text class="price">{{tab.amount}}</text> 元</view>
									<view class="gray"> {{tabIndex == 0 ? utils.dateFormat({date:tab.createDate, rule:'y年m月d日'}) : utils.dateFormat({date:tab.repaymentDate, rule:'y年m月d日'})}} {{tabIndex == 0 ? '借款' : '还款'}} </view>
								</view>
								<view class="date"> {{tabIndex == 0 ? `共 ${tab.period / 30} 期` : ``}} </view>
								<view class="act" v-show="tabIndex == 0">
									<ButtonItems type="med-gray-empty" value="待审核" v-if="tab.status == 0" />
									<ButtonItems type="med-gray-empty" @click="goPath('/pages/borrow/borrowDet')" value="募集中" v-if="tab.status == 1 && tab.projectStatus == 'COLLECTING'" />
									<ButtonItems type="med-blue-empty" @click="goPath('/pages/borrow/borrowDet')" value="还款中" v-if="tab.status == 1 && tab.projectStatus == 'REPAYING'" />
									<ButtonItems type="med-blue-empty" @click="goPath('/pages/borrow/borrowDet')" value="已完成" v-if="tab.status == 1 && tab.projectStatus == 'FINISH' " />
								</view>
								<view class="act" v-show="tabIndex != 0"> 
									<text @click="goPath('/pages/borrow/borrowDet')" >{{tab.withholdWay}} > </text>
								</view>
							</view>
						</view>
					</view>
				</scroll-view>
			</swiper-item>
		</swiper>
	</view>
</template>
<script>
	import ButtonItems from '../../components/ButtonItems.vue';
	import topBar from '../../components/TopBar.vue'
	export default {
		components: {
			ButtonItems,
			topBar
		},
		data() {
			return {
				scrollLeft: 0,
				isClickChange: false,
				tabIndex: 0,
				newsitems: [],
				pageNo: 1,
				pageSize: 10,
				order: 'asc',
				sortBy: '',
				pageNoB: 1,
				pageSizeB: 10,
				orderB: 'asc',
				sortByB: '',
				loanData: [],
				borrowData: [],
				showData:[],
				tabBars: [{
					name: '借款记录',
					id: 'guanzhu'
				}, {
					name: '还款记录',
					id: 'tuijian'
				}]
			}
		},
		onLoad() {
			this.getData()
		},
		methods: {
			goPath(url){
				uni.navigateTo({url})
			},
			getData(){
				if (this.tabIndex == 0) {
					this.getLoanData()
				} else {
					this.getBorrowData()
				}
			},
			// 还款
			getBorrowData(){
				this.request({
					url: `repayment/my/repaymentRecords/q?pageNo=${this.pageNo}&pageSize=${this.pageSize}&order=${this.order}&sortBy=${this.sortBy}`,
					method:'POST',
					params:{
						pageNo: this.pageNoB, 
						pageSize: this.pageSizeB,
						order: this.orderB,
						sortBy: this.sortByB
					}
				}).then(res =>{
					this.borrowData = this.borrowData.concat(res.data.result.content)
					this.showData = this.borrowData
				}).catch(err => {
					uni.showToast({
						icon: 'none',
						title: err.data.msg
					});
				})
			},
			// 借款
			getLoanData() {
				this.request({
					url: `transaction/my/projects/q?pageNo=${this.pageNo}&pageSize=${this.pageSize}&order=${this.order}&sortBy=${this.sortBy}`,
					params:{
						pageNo: this.pageNo, 
						pageSize: this.pageSize,
						order: this.order,
						sortBy: this.sortBy
					}
				}).then(res =>{
					this.loanData = this.loanData.concat(res.data.result.content)
					this.showData = this.loanData
				}).catch(err => {
					uni.showToast({
						icon: 'none',
						title: err.data.msg
					});
				})
			},
			loadMore(e) {
				// this.addData(e);
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
				if (index == 0){
					this.showData = this.loanData.length >0 ? this.loanData : this.getLoanData()
				}else{
					this.showData = this.borrowData.length >0 ? this.borrowData : this.getBorrowData()
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
	.claims{
		.cards{
			padding: 10upx 0;
			font-size: 28upx;
			border-bottom: solid 2upx $uni-border-color;
			.cont{
				padding: 0 30upx;
				.row{
					line-height: 60upx;
					padding:20upx 0;
					display: flex;
					.col{
						flex:1;
						.price{
							font-size: 40upx;
						}
					}
					.date,.act{
						display: flex;
						align-items: center;
						text-align: center;
						line-height: 30upx;
					}
					.date{
						padding-right: 40upx;
						color: $uni-text-color-greyb;
					}
				}
				.gray{
					color:$uni-text-color-grey;
				}
			}
		}
	}
</style>

