import { login, logout, getInfo } from '@/api/user'
import { getToken, setToken, removeToken, getShopId, setShopId } from '@/utils/auth'
import { resetRouter } from '@/router'

const state = {
  token: getToken(),
  merchant: {},
  currentShop: 0
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_MERCHANT: (state, merchant) => {
    state.merchant = merchant
  },
  SET_CURRENT_SHOP: (state, shopId) => {
    console.log('触发')
    state.currentShop = shopId
    setShopId(shopId)
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password } = userInfo
    return new Promise((resolve, reject) => {
      login({ username: username.trim(), password: password }).then(response => {
        const { data } = response
        commit('SET_TOKEN', data)
        setToken(data)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo().then(response => {
        const { data } = response
        if (!data) {
          reject('Verification failed, please Login again.')
        }
        commit('SET_MERCHANT', data)
        let shopId = getShopId()
        if (shopId == null || shopId == 0) {
          shopId = data.shops[0].id
        }

        commit('SET_CURRENT_SHOP', shopId)

        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        commit('SET_TOKEN', '')
        removeToken()
        resetRouter()
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      removeToken()
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

