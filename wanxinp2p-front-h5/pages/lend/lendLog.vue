<template>
	<view class="content lendLog">
		<topBar title="出借记录" name="tit"></topBar>
		<scroll-view class="lendLogRoll" scroll-y @scrolltolower="loadMore()">
			<view class="title">
				<view>出借人/出借时间</view>
				<view>出借金额/出借方式</view>
			</view>
			<view class="cont">
				<view class="items" v-for="(item, ind) in listDatas" :key="ind">
					<view class="item">
						<view>{{item.consumerUsername}}</view>
						<view>{{utils.dateFormat({date:item.createDate, rule:'y-m-d h:m:s'})}}</view>
					</view>
					<view class="item">
						<view>{{item.amount}}</view>
						<view>{{item.tenderWay}}</view>
					</view>
				</view>
			</view>
			<view class="load" v-if="loading">
				{{loadingText}}
			</view>
		</scroll-view>
		<!-- <view class="footerBut" @tap="submitData">授权出借</view> -->
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
				sortBy:'',
				id:'',
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
					url: `transaction/tenders/projects/${this.$route.query.id}?pageNo=${this.pageNo}&pageSize=${this.pageSize}&sortBy=${this.sortBy}`,
					params: {
						pageNo:this.pageNo,
						pageSize:this.pageSize,
						sortBy:this.sortBy
					}
				}).then(res => {
					let listDatas = this.listDatas
					this.listDatas = listDatas.concat(res.data.result)
					this.dataInfo = res.data.result
					this.loadingText= ''
				    this.loading = false
				}).catch(err => {
					console.log(999,err)
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
	.lendLog{
		.lendLogRoll{
			height: calc(100vh - 100upx);
		}
		.load{
			line-height: 100upx;
			text-align: center;
			color: $uni-text-color-grey;
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
