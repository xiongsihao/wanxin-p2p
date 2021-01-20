<template>
	<view class="uni-tab-bar">
		<view id="tab-bar" class="uni-swiper-tab box-dis-flex">
			<view v-for="(tab,index) in tabBars" :key="tab.id" class="swiper-tab-list" :class="tabIndex==index ? 'active' : ''"
			 :id="tab.id" :data-current="index" @click="tapTab">{{tab.name}}</view>
		</view>
		<swiper :current="tabIndex" class="swiper-box" :duration="300" @change="changeTab">
			<swiper-item v-for="(tab,index) in tabBars" :key="index">
				<scroll-view class="list" scroll-y @scrolltolower="loadMore(index)">
					<view class="contTop" v-if="index == 1">
						<image src="../../static/img/newHeadTop.png" mode="widthFix" class="topImg"></image>
					</view>
                    <view class="searchView" id="searchView" v-if="index == 1">
                        <view class="searchLab">
                            <view :class="{lab:true, act:mt == 0}" @tap="searchList('0')">全部</view>
                            <view :class="{lab:true, act:mt == 1}" @tap="searchList('1')">0-6个月</view>
                            <view :class="{lab:true, act:mt == 2}" @tap="searchList('2')">6-12个月</view>
                            <view :class="{lab:true, act:mt == 3}" @tap="searchList('3')">12个月以上</view>
                        </view>
                    </view>
					<block v-for="(newsitem,ind) in listData" v-if="listData.length > 0" :key="ind">
						<lend-cards :options="newsitem" @click="goDetail(`/pages/lend/details?ids=${String(newsitem.id)}`)"></lend-cards>
					</block>
					<view v-show="listData.length <= 0" class="noData">
						本区暂无数据
					</view>
					<view class="uni-tab-bar-loading" v-if="listData.length > 0">
						{{loadingText}}
					</view>
					<FooterMark></FooterMark>
				</scroll-view>
			</swiper-item>
		</swiper>
	</view>
</template>
<script>
	import LendCards from '../../components/LendCards.vue';
    import FooterMark from '../../components/FooterMark.vue';

	export default {
		components: {
			LendCards,
			FooterMark
		},
		data() {
			return {
			    mt: 0,
				userInfo:{},
				scrollLeft: 0,
				isClickChange: false,
				tabIndex: 1,
				newsitems: [],
				listData: [],
				newlistData:[],
				financeListData:[],
				transferListData:[],
				pageNo: '1',
				pageSize: '10',
                totalPage: 1,
				loadingText: '',
                searchData:{
                    name: '',
                    description: '',
                    startPeriod: undefined,
                    endPeriod: undefined
                },
                useSearch: false,
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
		onShow() {
		    this.newlistData = [];
		    this.pageNo = 1;
			this.getListData()
			this.getUserInfo()
		},
		methods: {
            inputHandle: function (event) {
                this.searchData[event.target.id] = event.target.value
            },
			goDetail(url) {
				// 是否开户
				if (this.userInfo.isBindCard) {
					uni.navigateTo({url});
				}else{
					uni.navigateTo({url:'/pages/lend/openStorage'});
					uni.setStorage({key: 'openSt', data: 'lend'})
				}
			},
			getListData(){
				const {pageNo, pageSize} = this
				this.request({
					url: `transaction/projects/indexes/q?pageNo=${pageNo}&pageSize=${pageSize}`,
					method: 'POST',
					params:{
						pageNo, pageSize,
						//status:1,
                        //projectStatus:'COLLECTING',
                        startPeriod: this.searchData.startPeriod ? this.searchData.startPeriod*30 : undefined,
                        endPeriod: this.searchData.endPeriod ? this.searchData.endPeriod*30 : undefined
					}
				}).then(res =>{
					if (res.data.code == 0) {
					    if (res.data.result) {
                            this.newlistData = this.newlistData.concat(res.data.result.content);
                            this.totalPage = res.data.result.totalPage;
                        } else {
                            this.newlistData = [];
                        }
                        this.listData = this.newlistData
                        this.loadingText= ''
                    }
				}).catch(err => {
					uni.showToast({
						icon: 'none',
						title: err.data.msg
					});
				})
			},
            searchList(mt){
                this.pageNo = 1;
                this.mt = mt
                this.newlistData = [];
                if (mt == 1){
                    this.searchData.startPeriod = 0
                    this.searchData.endPeriod = 6
                } else if (mt == 2){
                    this.searchData.startPeriod = 6
                    this.searchData.endPeriod = 12
                }else if (mt == 3){
                    this.searchData.startPeriod = 13
                    this.searchData.endPeriod = ''
                } else if (mt == 0){
                    this.searchData.startPeriod = ''
                    this.searchData.endPeriod = ''
                }

                this.getListData();
            },
			getUserInfo(){
				this.request({
					url: `consumer/my/consumers`,
				}).then(res => {
					this.userInfo = res.data.result
					uni.setStorage({key:'userInfo', data: res.data.result})
				}).catch(err => {
					uni.showToast({
                        icon: 'none',
                        title: err.data.msg
                    });
				})
			},
			loadMore(e) {
				setTimeout(() => {
					this.addData(e);
				}, 1200);
			},
			addData(e) {
				if (this.listData.length > 30) {
					this.loadingText = '没有更多了';
					return;
				}
                this.pageNo ++
				this.loadingText = '加载中...'
				this.getListData()
			},
			async changeTab(e) {
                if (!e.target.current) {
                    return;
                }
				let index = e.target.current;
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
				console.log(this.tabBars)
                console.log(index)
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
				this.tabIndex = index;
				this.setData(index)
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
			setData(index){
				if (index == 0) {
					this.listData = this.transferListData
				} else if (index == 1) {
					this.listData = this.newlistData
				} else {
					this.listData = this.financeListData
				}
			},
			async tapTab(e) { //点击tab-bar
				let tabIndex = e.target.dataset.current;
				if (this.tabIndex === tabIndex) {
					return false;
				} else {
					this.setData(tabIndex)
					let tabBar = await this.getElSize("tab-bar"),
						tabBarScrollLeft = tabBar.scrollLeft; //点击的时候记录并设置scrollLeft
					this.scrollLeft = tabBarScrollLeft;
					this.isClickChange = true;
					this.tabIndex = tabIndex;
				}
			},
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
    .searchView{
        margin: 50upx;
    }
	.noData{
		text-align: center;
		line-height: 200upx;
		color:$uni-text-color-grey;
	}
    .input-row{
        background: #f4f4f4;
        border-radius: 10upx;
        margin: 20upx;
        padding: 20upx;
    }
    .item{
        display: flex;
        border: solid 1px $uni-border-color;
        line-height: 58upx;
        font-size: 28upx;
        padding: 20upx 0upx;
        margin: 20upx 5upx;
    .uni-input{
        flex: 2;
    }
    }
    .submitBut {
        background: linear-gradient(130deg, #8a8fef, #4f57eb);
        padding: 5px 0px;
        margin: 0 30px;
        line-height: 2;
        border-radius: 50px;
        width: 80%;
        -webkit-box-flex: 1;
        -webkit-flex: 1;
        -ms-flex: 1;
        flex: 1;
        text-align: center;
        color: #fff;
    }
    .searchLab{
        display: flex;
        padding: 0px;
    .lab{
    // border:solid 1px #fff;
        flex: 1px;
        margin: 0 5px;
        font-size: 12px;
        border-radius: 4px;
        text-align: center;
        padding: 4px 0;
        color: #666;
    }
    .act{
        color:#fff;
        background:#cdd0ff;
    // border-color:#4f57eb
    }
    }
</style>
