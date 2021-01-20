<template>
	<scroll-view scroll-y class="uni-padding-wrap uni-common-mt">
		<topBar title='身份认证' />
		<view class="uni-flex uni-column imgBox">
			<view class="imgIcon">
				<view class="flex-item flex-item-V" style="line-height: 200upx; width:100%; height:200upx;">
                    <image :style="frontImgSizeStyle" :mode="imgModel" :src="frontImgPath" @click="chooseImgAndRecognition('front')"></image>
                </view>
                <view class="flex-item flex-item-V desc">点击验证身份证 <text class="lab"> 照片面</text></view>
			</view>
			<view class="imgIcon">
				<view class="flex-item flex-item-V" style="line-height: 200upx; width:100%; height:200upx;">
					<image :style="backImgSizeStyle" :mode="imgModel" :src="backImgPath" @click="chooseImgAndRecognition('back')"></image>
				</view>
				<view class="flex-item flex-item-V desc">点击验证身份证 <text class="lab"> 国徽面</text></view>
			</view>
        </view>
		<view class="text">请核对自动识别结果，如有误请点击 <text style="color:#666;">更正或重新上传</text> </view>
		<view class="content">
			<view class="input-row topRow">
			    <view class="title" style="width: 730upx;"><text>姓名：</text><text>{{this.consumerDetails.fullname}}</text></view>
			    <m-input style="display: none;" type="text" clearable v-model="consumerDetails.fullname"></m-input>
			</view>
			<view class="input-row ">
			    <view class="title" style="width: 730upx;"><text>身份证号：</text><text>{{this.consumerDetails.idNumber}}</text></view>
			    <m-input style="display: none;" type="text" clearable v-model="consumerDetails.idNumber"></m-input>
			</view>

		</view>
		<view class="btn-row">
		    <button type="primary" class="primary buttonItem" @tap="saveDetails">确认提交</button>
		</view>
		<view class="footBox">
			<view class="topTie"><text class="tit">拍照注意事项</text></view>
			<view class="listes">
				<view class="items">
					<image src="../../static/img/identityBotAIcon.png" class="iconPng"></image>
					<view>标准</view>
				</view>
				<view class="items">
					<image src="../../static/img/identityBotBIcon.png" class="iconPng"></image>
					<view>相似背景</view>
				</view>
				<view class="items">
					<image src="../../static/img/identityBotCIcon.png" class="iconPng"></image>
					<view>反光强烈</view>
				</view>
			</view>
		</view>
	</scroll-view>
</template>

<script>
	
	let token = "";
	uni.getStorage({
		key: 'access_token',
		success: function(res) {
			token = res.data 
		}
	});
	
	import topBar from '../../components/TopBar.vue'
	import ButtonItems from '../../components/ButtonItems.vue'
	import mInput from '../../components/m-input.vue'
	
	export default {
		data(){
			return{
				frontImgSizeStyle: "width:56upx;height:47upx;",
				backImgSizeStyle: "width:56upx;height:47upx;",
				imgModel:"aspectFit",
				frontImgPath:"../../static/img/identityIcon.png",
				backImgPath:"../../static/img/identityIcon.png",
				consumerDetails:{
					uploadToken:"",
					fileName: "",
					resourceKey: "",
					origin: "qiniu",
					idCardPhoto: "",
					idCardEmblem: "",
					flag: "",
					uploadDate: "",
					protect: "",
					fullname: "",
					idNumber: "",
                    fullname: ""
				}
			}
		},
		components: {
			topBar,
			mInput,
			ButtonItems
		},
		onReady() {
			this.applyUploadCertificate();
		},
		methods: {
			applyUploadCertificate(){
				this.request({
					url: `consumer/my/applyUploadCertificate`,
				}).then(res => {
					this.consumerDetails.uploadToken = res.data.result;
				}).catch(err => {
					uni.showToast({
			            icon: 'none',
			            title: '系统错误，暂时不能进行身份认证，请稍后再试！'
			        });
				})
			},
			chooseImgAndRecognition(flag){
				this.consumerDetails.flag=flag;
				uni.chooseImage({
					count:1,
					success: (res) => {
						const tempFilePaths = res.tempFilePaths;
						if(flag=="front"){
							this.frontImgSizeStyle="width:100%;height:100%;";
							this.frontImgPath=tempFilePaths[0];
						}
						if(flag=="back"){
							this.backImgSizeStyle="width:100%;height:100%;";
							this.backImgPath=tempFilePaths[0];
						}
						let uploadTask = uni.uploadFile({
							url: this.baseUrl + `zuul/consumer/my/imageRecognition`,
							filePath: tempFilePaths[0],
							name: 'file',
							formData: {
								'flag': flag,
							},
							header: {
								'Authorization': `Bearer ${token}`
							},
							success: (uploadFileRes) => {
								console.log(uploadFileRes);
								let resData=JSON.parse(uploadFileRes.data);
								uni.showToast({
									icon: 'none',
									title: resData.msg
								});
								if(resData.code==0){
									this.uploadImg(tempFilePaths[0]);
									if(flag=="front"){
										console.log(resData.result);
										this.consumerDetails.fullname=resData.result.idName;
										this.consumerDetails.idNumber=resData.result.idCard;
									}
								}
								else{
									if(flag=="front"){
										this.frontImgPath="../../static/img/identityIcon.png";
										this.frontImgSizeStyle="width:56upx;height:47upx;";
										this.consumerDetails.fullname="";
										this.consumerDetails.idNumber="";
									}
									if(flag=="back"){
										this.frontImgPath="../../static/img/identityIcon.png";
										this.frontImgSizeStyle="width:56upx;height:47upx;";
									}
								}
							},
							fail:(res)=>{
								if(flag=="front"){
									this.frontImgPath="../../static/img/identityIcon.png";
									this.frontImgSizeStyle="width:56upx;height:47upx;";
								}
								if(flag=="back"){
									this.frontImgPath="../../static/img/identityIcon.png";
									this.frontImgSizeStyle="width:56upx;height:47upx;";
								}
								uni.showToast({
									icon: 'none',
									title: '系统错误'
								});
							}
						});
						uploadTask.onProgressUpdate((res) => {
							uni.showLoading({
								title: '识别中...'+res.progress+'%',
								mask: true
							});
						});
					}
				});
			},
			
			uploadImg(path){
				let uploadTask = uni.uploadFile({
					url: this.fileServiceUrl + `/upload`,
					filePath: path,
					name: 'file',
					formData: {
						'flag': this.consumerDetails.flag,
						'origin':this.consumerDetails.origin,
						'uploadToken':this.consumerDetails.uploadToken,
						'resourceKey':this.consumerDetails.resourceKey
					},
					success: (res) => {
						let resData=JSON.parse(res.data);
						uni.showToast({
							icon: 'none',
							title: resData.msg
						});
						if(resData.code==0){
							this.consumerDetails.uploadDate=resData.result.uploadDate;
							if(this.consumerDetails.flag=="front"){
								this.consumerDetails.idCardPhoto=resData.result.resourceKey;
							}
							if(this.consumerDetails.flag=="back"){
								this.consumerDetails.idCardEmblem=resData.result.resourceKey;
							}
						}
						else{
							if(this.consumerDetails.flag=="front"){
								this.frontImgPath="../../static/img/identityIcon.png";
							this.frontImgSizeStyle="width:56upx;height:47upx;";
							}
							if(this.consumerDetails.flag=="back"){
								this.frontImgPath="../../static/img/identityIcon.png";
							this.frontImgSizeStyle="width:56upx;height:47upx;";
							}
						}
					},
					fail: (res) => {
						uni.showToast({
							icon: 'none',
							title: '系统错误'
						});
						if(this.consumerDetails.flag=="front"){
							this.frontImgPath="../../static/img/identityIcon.png";
							this.frontImgSizeStyle="width:56upx;height:47upx;";
						}
						if(this.consumerDetails.flag=="back"){
							this.backImgPath="../../static/img/identityIcon.png";
							this.backImgSizeStyle="width:56upx;height:47upx;";
						}
					}
				});
				
				uploadTask.onProgressUpdate((res) => {
					uni.showLoading({
						title: '上传中...'+res.progress+'%',
						mask: true
					});
				});
			},
			saveDetails(){
				if(this.consumerDetails.idCardPhoto=="" || this.consumerDetails.idCardEmblem==""){
					uni.showToast({
						icon: 'none',
						title: '请先上传两张身份证照片！'
					});
					return;
				}
				this.request({
					url: `consumer/my/saveConsumerDetails`,
					params: this.consumerDetails,
					method:'post'
				}).then(res => {
					uni.showToast({
					    icon: 'none',
					    title: res.data.result
					});
					setTimeout(()=>{
						uni.setStorage({key: 'openSt', data: 'borrow'});
                        uni.switchTab({url: '/pages/borrow/borrow'});
					},2000);
					
				}).catch(err => {
					uni.showToast({
				        icon: 'none',
				        title: err.data.msg
				    });
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	
	.imgBox{
		display: flex;
		margin: 0 14upx;
		.imgIcon{
			flex: 1;
			text-align: center;
			margin: 40upx 16upx;
			padding:60upx 0 40upx 0;
			border-radius: 10upx;
			box-shadow: 0 0 20upx 8upx #F4F4F4;
			.desc{
				text-align: center;
				font-size: 24upx;
				color: #999;
				.lab{
					margin-left: 4upx;
					color: #6971f9;
				}
			}
		}
	}
	.text{
		color: #999;
		font-size: 28upx;
		padding: 0 30upx;
	}
	.content{
		box-shadow: 0 0 20upx 8upx #F4F4F4;
		border-radius: 10upx;
		margin: 40upx 30upx;
		padding: 10px 30upx;
		font-size: 30upx;
		color:#999;
		.topRow{
			border-bottom: solid 1px #f4f4f4;
		}
		.title{
			padding: 12upx 0;
			display: flex;
			justify-content: space-between;
		}
	}
	.buttonItem{
		background: linear-gradient(130deg, #8a8fef, #4f57eb);
		border-radius: 100upx;
		height: 80upx;
		margin: 0 40upx;
		line-height: 80upx;
		font-size: 32upx;
	}
	.footBox{
		position: fixed;
		width: 100%;
		bottom: 60upx;
		padding: 30upx 0;
		.topTie{
			position: relative;
			margin: 30upx 30upx 100upx 30upx;
			border-bottom: solid 1px #f4f4f4;
			text-align: center;
			.tit{
				bottom:-26upx;
				font-size: 32upx;
				background: #fff;
				padding: 0 20upx;
				position: relative;
				color: #999;
			}
		}
		.listes{
			display: flex;
			.items{
				flex: 1;
				text-align: center;
				font-size: 28upx;
				color:#999;
				.iconPng{
					width: 57upx;
					height: 51upx;
				}
			}
		}
	}
</style>
