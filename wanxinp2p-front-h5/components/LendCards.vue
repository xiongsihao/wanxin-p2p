<template>
    <view class="lendCards">
        <view class="itemsBlock box-dis-flex">
            <view class="leftBox">
				<view class="title">{{options.name}}</view>
				<view class="infoBox">
					<view class="">
						<view class="cl-red"><text class="num">{{options.annualRate * 100}}</text> %</view>
						<view class="">约定借款年华利息</view>
					</view>
					<view class="time">
						<view class="">{{options.period}} 天</view>
						<view>期限</view>
					</view>
				</view>
			</view>
			<view class="ritBox">
				<view> 剩余 {{options.remainingAmount}} 元</view>
				<view class="butItem"><ButtonItems type="med-blue-empty" value="出借" @click="bindClick"></ButtonItems></view>
				<view> 持满30天可转让</view>
			</view>
        </view>
    </view>
</template>

<script>
	import ButtonItems from './ButtonItems.vue'
    export default {
        props: {
            data: {
                type: Object,
                default: function(e) {
                    return {}
                }
            },
			options: {
				type: Object,
				default: {}
			}
        },
		components:{
			ButtonItems
		},
        methods: {
            close(e) {
                this.$emit('close');
                e.stopPropagation();
            },
            bindClick() {
                this.$emit('click');
            }
        }
    }
</script>

<style scoped lang="scss">
    .lendCards {
		color:$uni-text-color-grey;
       .leftBox{
		   flex: 1;
		   .title{
			   color:$uni-text-color-greyb;
		   }
		   .infoBox{
			   display: flex;
			   justify-content: space-between;
			   .num{
				   font-size: 64upx;
				   line-height: 94upx;
			   }
			   .time{
				   line-height: 3;
				   width: 180upx;
				   text-align: center;
			   }
		   }
	   }
	   .ritBox{
		   text-align: right;
		   .butItem{
			   margin: 20upx;
		   }
	   }
    }

</style>
