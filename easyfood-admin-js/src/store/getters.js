const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  merchant: state => state.user.merchant,
  currentShop: state => state.user.currentShop
}
export default getters
