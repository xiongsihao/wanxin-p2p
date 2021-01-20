<template>
	<view class="content confirmLend">
		<topBar title="出借确认" name="tit"></topBar>
		<view class="cardsBlock topCards">
			<view class="title">
				{{objName}}
			</view>
			<view class="info">
				<view class="top">
					<view class="tit"> 出借金额 </view>
					<view >￥ <text class="price">{{amount}}</text></view>
				</view>
				<view><text>账户余额：</text> {{userInfo.balance}} </view>
			</view>
			<!-- <view class="bot">
				历史参考收益 <text class="pir">222.22</text> 元（不代表未来收益）
			</view> -->
		</view>
		<view class="cardsBlock balance">
			<view class="">
				<radio value="r2" class="redioCk" checked/> 余额支付
			</view>
			<view>
				{{amount}}元
			</view>
		</view>
		<view class="labTitle"><label class="radio cl-main" @tap="redioHandle"><radio value="r2" class="redioCk"  />我同意《用户服务协议》《风险提示》</label></view>
		<view class="itBut"><ButtonItems :type="submitBut" @click="submitHandle" size="14" value="确认出借"></ButtonItems></view>
	</view>
</template>

<script>
	import topBar from '../../components/TopBar.vue'
	import ButtonItems from '../../components/ButtonItems.vue'
	import {
        mapState,
        mapMutations
    } from 'vuex'
	
	export default{
		data(){
			return{
				submitOpen: false,
				userInfo:{},
			} 
		},
		computed:{
			...mapState(['lendInfo']),
			amount(){
				return this.$route.query.amount
			},
			objName(){
				return this.$route.query.objName
			},
			submitBut() {
				return this.submitOpen ? 'big-blue' : 'big-gray'
			}
		},
		components:{
			topBar,
			ButtonItems
		},
		onLoad() {
			this.getInterface()
		},
		methods:{
			getInterface(){
				this.request({
					url:'consumer/my/balances',
				}).then(res => {
					this.userInfo = res.data.result
				}).catch(err => {
					uni.showToast({
						icon: 'none',
						title: err.data.msg
					});
				})
			},
			redioHandle(){
				this.submitOpen = true
			},
			submitHandle() {
				if (this.userInfo.balance < this.amount){
					uni.showToast({
						icon: 'none',
						title: '余额不足，请充值后再试！',
					});
				    return false; 
				}
				this.request({
					url:'transaction/my/tenders',
					method: 'POST',
					params:{
						id: this.$route.query.id,
						amount: this.amount
					}
				}).then(res => {
					uni.setStorage({key:'lendDataInfo',data: res.data.result,success() {
						uni.navigateTo({url:'/pages/lend/lendSuccess'})
					}})
				}).catch(err => {
					uni.showToast({
						icon: 'none',
						title: err.data.msg,
					});
				})
				
			}
		}
	}
</script>

<style lang="scss">
	.confirmLend{
		font-size: 28upx;
		.topCards{
			margin-top: 40upx;
			padding: 0;
			.title, .bot{
				padding: 0 30upx;
				font-size: 28upx;
				line-height: 3;
				border-bottom: solid 1px $uni-border-color;
			}
			.bot{
				padding: 0 30upx;
				font-size: 28upx;
				line-height: 3;
				border:none;
				border-top: solid 1px $uni-border-color;
			}
			.info{
				padding: 30upx;
				display: flex;
				justify-content: space-between;
				.top{
					.tit{
					  margin-bottom: 20upx;
					}
				}
				.price{
					font-size: 50upx;
				}
			}
		}
		.balance{
			display: flex;
			justify-content: space-between;
		}
		.labTitle{
			margin-top: 30upx;
			font-size: 24upx;
			margin: 5px 20px 0px 20px;
			color:$uni-text-color-grey;
		}
		.itBut{
			margin:20upx auto;
			width: 86%;
		}
		.redioCk{
			position: relative;
			top:-4upx;
			transform:scale(0.6); 
		}
	}
	
</style>
