import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        /**
         * 是否需要强制登录
         */
        openStorageData: {},
		lendInfo: {}
    },
    mutations: {
        setOpenStorageData(state, storageData) {
			state.openStorageData = storageData
        },
		setLendInfo(state, userInfo){
			state.lendInfo = userInfo
		}
    }
})

export default store
