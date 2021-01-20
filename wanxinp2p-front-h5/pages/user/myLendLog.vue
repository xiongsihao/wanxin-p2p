<template>
	<view class="content mylendLog">
		<topBar title="出借记录" name="tit"></topBar>
		<scroll-view class="lendLogRoll" scroll-y @scrolltolower="loadMore()">
			<view class="cont">
				<view class="items" v-for="(item, ind) in listDatas" :key="ind">
					<view class="item">
						<view>{{item.projectName}}</view>
						<view>{{utils.priceFormat(item.amount)}} 元</view>
					</view>
					<view class="item">
						<view class="lab">{{item.createDate}}</view>
						<view class="lab">{{item.tenderStatus ? '投标成功': '投标失败' }}</view>
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
	export default{
		data(){
			return{
				dataInfo:{},
				pageNo: 1,
				pageSize: 10,
				sortBy: '',
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
					url: `transaction/my/tenders/q?pageNo=${this.pageNo}&pageSize=${this.pageSize}&sortBy=${this.sortBy}`,
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

<style scoped lang='scss'>
	.mylendLog{
		.lendLogRoll{
			height: calc(100vh - 100upx);
		}
		.load{
			line-height: 100upx;
			text-align: center;
			color: $uni-text-color-grey;
			font-size: 28upx;
		}
		.title{
			line-height: 80upx;
			font-size: 28upx;
			color:$uni-text-color-grey;
			padding:0 30upx;
			display: flex;
			justify-content: space-between;
			border-bottom: solid 1px $uni-border-color;
		}
		.cont{
			.items{
				border-bottom: solid 1px $uni-border-color;
				font-size: 28upx;
				line-height: 60upx;
				padding:10upx 0; 
				.item{
					display: flex;
					justify-content: space-between;
					padding:0 30upx;
					.lab{
						color:$uni-text-color-grey;
					}
				}
			}
		}
		.footerBut{
			background: #4f57eb;
			color:#fff;
			font-size: 32upx;
			line-height: 90upx;
			position: fixed;
			left: 0 ;
			bottom:0;
			width: 100%;
			text-align: center;
		}
	}
</style>
