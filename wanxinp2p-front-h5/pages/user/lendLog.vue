<template>
	<view class="content">
		<topBar title='出借记录' />
		<scroll-view class="lendLog" scroll-y @scrolltolower="loadMore()">
			<view class="cardsBlock" v-for="(item, index) in listDatas" :key="index">
				<view class="title">
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
			<view class="load" v-if="loading">
				{{loadingText}}
			</view>
		</scroll-view>
	</view>
</template>

<script>
	import topBar from '../../components/TopBar.vue'
    export default {
		data(){
			return{
				dataInfo:{},
				pageNo: 1,
				pageSize: 10,
				sortBy:'',
				loadingText: '',
				listDatas: [],
				loading:false
			}
		},
		components: {
			topBar
		},
		onLoad() {
			this.getDataInfo()
		},
        methods: {
            getDataInfo(){
				this.request({
					url: `transaction/my/tenders/q`,
					method: 'POST',
					params: {
						pageNo:this.pageNo,
						pageSize:this.pageSize,
						sortBy:this.sortBy
					}
				}).then(res => {
					let listDatas = this.listDatas
					this.listDatas = listDatas.concat(res.data.result.content)
					this.dataInfo = res.data.result
					this.loadingText= ''
				    this.loading = false
				}).catch(err => {
					uni.showToast({
						icon: 'none',
						title: err.data.msg
					});
				})
			},
			dataAdd(){
				if (this.pageNo == this.dataInfo.totalPage){
					this.loadingText= '没有更多了'
					this.loading = true
				}else{
					this.pageNo++
					this.getDataInfo()
				}
			},
			loadMore(e){
				this.loadingText= '数据加载中...'
				this.loading = true
				this.dataAdd()
			}
        }
    }
</script>

<style lang="scss" scoped>
	.lendLog{
		padding-top: 10upx;
		font-size: 28upx;
		height: calc(100vh - 100upx);
		.title{
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
		.load{
			line-height: 100upx;
			text-align: center;
			color: $uni-text-color-grey;
		}
	}
</style>
