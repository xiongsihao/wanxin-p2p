<template>
	<view :class="disabled ? 'uni-list-item--disabled' : ''" :hover-class="disabled || showSwitch ? '' : 'uni-list-item--hover'" class="uni-list-item" @click="onClick">
		<view :class="{'list-line' : line, 'uni-list-item__container': true}">
			<view v-if="thumb" class="uni-list-item__icon">
				<image :src="thumb" class="uni-list-item__icon-img" />
			</view>
			<view v-else-if="showExtraIcon" class="uni-list-item__icon">
				<m-icon :color="extraIcon.color" :size="extraIcon.size" :type="extraIcon.type" />
			</view>
			<view class="uni-list-item__content">
				<view :class="{'uni-list-item__content-title':true, 'uni-list-title-lab': titleLab}">{{ title }}</view>
				<view v-if="note" class="uni-list-item__content-note">{{ note }}</view>
				<text v-if="des" :class="{'uni-list-des': true, 'uni-list-des-add': note}">{{ des }}</text>
			</view>
			<view v-if="showBadge || showArrow || showSwitch" class="uni-list-item__extra">
				<!-- <uni-badge v-if="showBadge" :type="badgeType" :text="badgeText" /> -->
				<switch v-if="showSwitch" :disabled="disabled" :checked="switchChecked" @change="onSwitchChange" />
				<m-icon v-if="showArrow" :size="20" color="#bbb" type="arrowright" />
			</view>
		</view>
	</view>
</template>

<script>
	import mIcon from './m-icon/m-icon.vue'
	export default {
		name: 'UniListItem',
		components: {
			mIcon,
			// uniBadge
		},
		props: {
			title: {
				default: ''
			}, // 列表标题
			titleLab: {
				type: Boolean,
				default: false
			},// 特殊标题
			note: {
				type: String,
				default: ''
			}, // 列表描述
			line:{
				type: Boolean,
				default: false
			}, //是否显示下划线
			des:{
				type: String,
				default: ''
			},
			disabled: { // 是否禁用
				type: Boolean,
				default: false
			},
			showArrow: { // 是否显示箭头
				type: Boolean,
				default: true
			},
			showBadge: { // 是否显示数字角标
				type: Boolean,
				default: false
			},
			showSwitch: { // 是否显示Switch
				type: Boolean,
				default: false
			},
			switchChecked: { // Switch是否被选中
				type: Boolean,
				default: false
			},
			badgeText: {
				type: [String, Number],
				default: ''
			}, // badge内容
			badgeType: { // badge类型
				type: String,
				default: 'success'
			},
			thumb: {
				type: String,
				default: ''
			}, // 缩略图
			showExtraIcon: { // 是否显示扩展图标
				type: Boolean,
				default: false
			},
			extraIcon: {
				type: Object,
				default () {
					return {
						type: 'contact',
						color: '#000000',
						size: 20
					}
				}
			}
		},
		data() {
			return {

			}
		},
		methods: {
			onClick() {
				this.$emit('click')
			},
			onSwitchChange(e) {
				this.$emit('switchChange', e.detail)
			}
		}
	}
</script>

<style>
	@charset "UTF-8";

	.uni-list-item {
		font-size: 32upx;
		position: relative;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		align-items: center;
		color:#888;
	}

	.uni-list-item--disabled {
		opacity: .3
	}
	.uni-list-des{
		position: absolute;
		right: 50upx;
		top:26upx;
		font-size: 28upx;
	}
	.uni-list-des-add{
		top:56upx;
	}
	.uni-list-title-lab{
		font-size: 48upx !important;
		color:#4389f2 !important;	
	}
	.uni-list-item--hover {
		background-color: #f1f1f1
	}

	.uni-list-item__container {
		padding: 24upx 0upx;
		width: 100%;
		box-sizing: border-box;
		flex: 1;
		position: relative;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center
	}

	.list-line:after {
		position: absolute;
		z-index: 3;
		right: 0;
		bottom: 0;
		left: 0upx;
		height: 1px;
		content: '';
		-webkit-transform: scaleY(.5);
		transform: scaleY(.5);
		background-color: #c8c7cc;
	}

	.uni-list-item__content {
		flex: 1;
		overflow: hidden;
		display: flex;
		flex-direction: column
	}

	.uni-list-item__content-title {
		font-size: 28upx;
		text-overflow: ellipsis;
		white-space: nowrap;
		color: inherit;
		line-height: 1.5;
		overflow: hidden
	}

	.uni-list-item__content-note {
		color: #999;
		font-size: 24upx;
		white-space: normal;
		display: -webkit-box;
		-webkit-box-orient: vertical;
		-webkit-line-clamp: 2;
		overflow: hidden
	}

	.uni-list-item__extra {
		width: 25%;
		display: flex;
		flex-direction: row;
		justify-content: flex-end;
		align-items: center
	}

	.uni-list-item__icon {
		margin-right: 18upx;
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center
	}

	.uni-list-item__icon-img {
		height: 52upx;
		width: 52upx
	}

	.uni-list>.uni-list-item:last-child .uni-list-item-container:after {
		height: 0
	}
</style>