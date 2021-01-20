<template>
	<view class="content borrow">
		<topBar title='借钱' />
		<view><image src="../../static/img/borrowBanner.png" mode="aspectFill" class="banner"></image></view>
		<view class="cardsBlock">
			<view class="title"> 申请借款金额(元) </view>
			<view class="price"><input name="money" type="number" v-model="amount" :value="amount" @blur="blurHandle"  @input="setAmount" placeholder="" /> </view>
			<view class="lineNum"><view>{{utils.priceFormat(2000)}}</view><view>{{utils.priceFormat(userInfo.loanAmount)}}</view></view>
			<view class="progBox" id="progBox" @touchstart="touchStateHandle" @touchmove="touchmoveHandle" @touchend="touchendHandle">
				<view class="progressBar">
					<view :class="touch?'progress pos' : 'progress'"  :style="position"></view>
				</view>
			</view>
		</view>
		<view class="itemsBlock clmargin"><ListItem class="selectItems" :des="period + ' 个月'" title="借款期限" @click="showSinglePicker"/></button></view>
		<view class="itemsBlock clmargin"><ListItem class="selectItems" des="个人生活消费" title="借款用途" /></view>
		<view class=""><view class="labTitle" @tap="redioHandle"><label class="radio cl-main"><radio value="r2" class="redioCk" />我已阅读产品详情页面，知悉并接受产品申请条件及费用说
明，并同意《借款相关协议》</label></view></view>
		<view class="but"><ButtonItems :value="start == 1 ? '立即申请' : '审核中'" :type="subState && start == 1 ? 'big-blue' : 'big-gray'" @click="subState && start == 1 ? submitHandle(): ''" /></view>
		<selectItems :themeColor="themeColor" ref="mpvuePicker" :mode="mode" :deepLength="deepLength" :pickerValueDefault="pickerValueDefault"
		 @onConfirm="onConfirm"  :pickerValueArray="pickerValueArray"></selectItems>
	</view>
</template>

<script>
	import topBar from '../../components/TopBar.vue'
	import ListItem from '../../components/ListItem.vue'
	import ButtonItems from '../../components/ButtonItems.vue'
	import selectItems from '../../components/selectItems.vue'
	
    export default {
		data(){
			return{
				userInfo:{},
				statePosition: '',
				start: 1,
				endPosition: '',
				currentPosition: '',
				position:{width:'0px'},
				touch: false,
				amount:'2000',
				subState: false,
				period:'12',
				description: '个人生活消费',
				themeColor: '#007AFF',
				mode: '',
				deepLength: 1,
				pickerValueDefault: [0],
				pickerValueArray:[],
				pickerSingleArray: [{
						label: '3',
						value: 1
					},
					{
						label: '6',
						value: 2
					},
					{
						label: '12',
						value: 3
					},
					{
						label: '24',
						value: 4
					}
				],
			}
		},
		components: {
			topBar,
			ListItem,
			ButtonItems,
			selectItems,
		},
		onShow() {
			this.getUserInfo()
			//this.getQualifications()
            if (this.userInfo.isIdCardAuth == 0) {
                this.goPath('/pages/user/identityAuth')
            }
		},
		onReady() {
			this.getElSize().then(res => {
				this.endPosition = res.width + this.statePosition
			})
		},
        methods: {
            goPath(url){
				uni.navigateTo({ url });
			},
			getQualifications(){
				this.request({
					url: `transaction/my/qualifications`,
					method: 'GET',
				}).then(res => {
					this.start = res.data.result
				}).catch(err => {
					uni.showToast({
						icon: 'none',
						title: err.data.msg
					});
				})
			},
			submitHandle(){
				const data = {amount:this.amount, period:this.period*30, description:this.description}
				if (this.userInfo.isBindCard) {
                    //if (this.userInfo.isIdCardAuth) {
                        this.request({
                            url: `transaction/my/projects`,
                            method: 'POST',
                            params: data
                        }).then(res => {
                            this.goPath('/pages/borrow/audit?title="申请成功"')
                        }).catch(err => {
                            uni.showToast({
                                icon: 'none',
                                title: err.data.msg
                            });
                        })
                    //} else {
                    //    this.goPath('/pages/user/identityAuth')
                    //}
				} else {
                    this.goPath('/pages/lend/openStorage')
					uni.setStorage({key: 'openSt', data: 'borrow'})
				}
			},
			showSinglePicker() {
				this.pickerValueArray = this.pickerSingleArray
				this.mode = 'selector'
				this.deepLength = 1
				this.pickerValueDefault = [0]
				this.$refs.mpvuePicker.show()
			},
			// 确定
			onConfirm(e) {
				this.period = e.label
			},
			blurHandle(){
				if (this.amount < 2000 ){
					this.amount = 2000
				}
			},
			setAmount(event){
				const st = event.target.value
				this.amount = st
				if ( Number(st) > Number(this.userInfo.loanAmount)){
					uni.showToast({
                        icon: 'none',
                        title: '申请金额超出上限，请重新输入'
                    });
					this.amount = this.userInfo.loanAmount
				}
				
				this.amountToPosition({st})
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
			touchStateHandle(event){
				this.statePosition = Number(event.currentTarget.offsetLeft)
				this.touch = true
				this.getElSize().then(res => {
					this.endPosition = Number(res.width) + this.statePosition
					this.setMovePosition(event.changedTouches[0].pageX)
				})
			},
			touchmoveHandle(event){
				this.setMovePosition(event.changedTouches[0].pageX)
			},
			touchendHandle(){
				this.touch = false
			},
			setMovePosition(st, obj){
				let wd = 0;
				let ps = st
				
				if (ps<= this.statePosition){
					wd = 0
				}else if (ps >= this.endPosition){
					wd = this.endPosition - this.statePosition
				}else{
					wd = st - this.statePosition
				}
				this.currentPosition = wd
				this.position = `width: ${wd}px`
				if (!obj) {
					this.amountToPosition({wd})
				}
			},
			amountToPosition({wd,st}){
				if (wd){
					let perc = wd / (this.endPosition - this.statePosition)
					let num = ((this.userInfo.loanAmount - 2000) * perc + 2000)/100
					this.amount = num.toFixed(0) * 100
				}else if (st){
					let perc = st / (this.userInfo.loanAmount - 2000)
					let num = (this.endPosition - this.statePosition) * perc
					this.setMovePosition(num, true)
				}
				
			},
			getElSize() { //得到元素的size
				return new Promise((res, rej) => {
					uni.createSelectorQuery().select("#progBox").fields({
						size: true,
						scrollOffset: true
					}, (data) => {
						res(data);
					}).exec();
				})
			},
			redioHandle(){
				this.subState = true
			}
        }
    }
</script>

<style lang="scss" scoped>
	.borrow{
		.banner{
			width: 723upx;
			height: 164upx;
			margin-top:20upx;
			margin-left: 50%;
			transform: translateX(-50%);
		}
		.title{
			text-align: center;
			font-size: 28upx;
			line-height: 2;
			margin-bottom: 40upx;
		}
		.price{
			text-align: center;
			font-size: 64upx;
			margin-bottom:40upx;
			color:$uni-color-primary;
		}
		.lineNum{
			color: $uni-text-color-grey;
			display: flex;
			justify-content: space-between;
		}
		.but{
			padding: 80upx;
		}
		.labTitle{
			margin-top: 30upx;
			font-size: 24upx;
			margin: 5px 20px 0px 20px;
			color:$uni-text-color-grey;
		}
		.redioCk{
			position: relative;
			top:-4upx;
			transform:scale(0.6); 
		}
		.clmargin{
			padding: 0 30upx;
		}
	}
	.progBox{
		padding:10upx 0upx;
	}
	.progressBar{
		position: relative;
		background: rgba(79,87,235,0.5);
		height:4upx;
		width: 100%;
	}
	.progress{
		position: absolute;
		content: '';
		left: 0upx;
		height:4upx;
		width: 0;
		background: #4f57eb;
		// transition: 1ms;
	}
	.progress::before{
		position: absolute;
		content: '';
		top:-1px;
		right: 0upx;
		height:8upx;
		width: 8upx;
		border-radius: 100%;
		background: #4f57eb;
	}
	.pos::before{
		position: absolute;
		content: '';
		top:-30upx;
		right: -30upx;
		height:60upx;
		width: 60upx;
		border-radius: 100%;
		background: #4f57eb;
	}
</style>
