<template>
	<view class="content confirmStorage">
		<topBar title="确认开通存管"></topBar>
		<view class="labTitle">您正在使用招商银行开户功能</view>
		<view class="cardsBlock">
			<view class="item">
                <view class="lab">证件类型</view>
                <input class="uni-input" value="身份证" disabled="" focus placeholder="身份证" />
            </view>
			<view class="item">
                <view class="lab">客户姓名</view>
                <input class="uni-input" focus :value="openStorageData.fullname" placeholder="请输入本人姓名，核实后不可修改" />
            </view>
			<view class="item clborder">
                <view class="lab ">证件号</view>
                <input class="uni-input" :value="openStorageData.idNumber" focus placeholder="请输入本人证件号码，核实后不可修改" />
            </view>
		</view>
		<view class="labTitle">温馨提示：若银行卡为二类账户，将会影响提现，具体账户类型
可咨询发卡行</view>
		<view class="cardsBlock">
			<view class="item">
                <view class="lab">银行卡号</view>
                <input class="uni-input" focus :value="openStorageData.cardNumber" placeholder="请输入本人储蓄卡卡号" />
            </view> 
			<view class="item">
                <view class="lab">手机号</view>
                <input class="uni-input" focus :value="openStorageData.mobile" placeholder="请输入银行预留手机号" />
            </view> 
			<view class="item clborder">
                <view class="lab">验证码</view>
                <input class="uni-input" focus placeholder="请输入银行预留手机号" />
				<view class="getCode" @tap="">获取验证码</view>
            </view> 
		</view>
		<view class="cardsBlock">
			<view class="item">
                <view class="lab">交易密码</view>
                <input class="uni-input" focus placeholder="请输入本人储蓄卡卡号" />
            </view> 
			<view class="item clborder">
                <view class="lab">确认交易密码</view>
                <input class="uni-input" focus placeholder="请输入银行预留手机号" />
            </view> 
		</view>
		<view class="labTitle">
			密码由6-18位英文字母、数字或者符号组成
		</view>
		<view class="labTitle">
			招商银行不承担网贷平台的投融资标的物及投融资人的审核责
任，不对网贷平台业务提供明示或模示的担保或连带责任，网
贷平台的交易风险由投融资人自行承担，与银行无关。
		</view>
		<view class="itBut"><ButtonItems :type="submitBut" @click="submitHandle" size="14" value="确认协议并注册"></ButtonItems></view>
		<view class="labTitle"><label class="radio cl-main"><radio value="r2" class="redioCk" />我同意《用户服务协议》《风险提示》</label></view>
	</view>
</template>

<script>
	import ButtonItems from '../../components/ButtonItems.vue'
	import topBar from '../../components/TopBar.vue'
	import {
        mapState,
        mapMutations
    } from 'vuex'
	
	export default {
	data() {
        return {
            pickerHidden: true,
            chosen: '',
			submitOpen: false,
			data: {
				
			}
        }
    },
	computed:{
		...mapState(['openStorageData']),
		submitBut: function(){
			return this.submitOpen ? 'big-blue' : 'big-gray'
		}
	},
	components:{
		ButtonItems,
		topBar
	},
    methods: {
		
		inputHandle: function () {
			this.submitOpen = true 
		},
        submitHandle: function () {
			return
			if (this.submitOpen) {
				uni.navigateTo({url:'/pages/common/success'})
			}
		}
    }
	}
</script>
<style lang="scss" scoped>
.confirmStorage{
	.labTitle{
		margin-top: 30upx;
		font-size: 24upx;
		margin: 5px 20px 0px 20px;
		color:$uni-text-color-grey;
	}
	.item{
		display: flex;
		border-bottom: solid 1px $uni-border-color;
		line-height: 58upx;
		font-size: 28upx;
		padding: 20upx 0upx;
		.lab{
			width: 200upx;
		}
		.uni-input{
			flex: 1;
		}
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
	.getCode{
		background: $uni-color-primary;
		color:#fff;
		padding:4upx 20upx;
	}
}
</style>
