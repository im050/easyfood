import Cookies from 'js-cookie'
import Base64 from '@/utils/base64'

const TokenKey = 'easyfood_token'
const ShopKey = 'easyfood_shop_id'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function getBase64Token(shopId) {
  const token = Base64.encode(getToken() + '|' + shopId)
  return token
}

export function getShopId() {
  return Cookies.get(ShopKey)
}

export function setShopId(shopId) {
  return Cookies.set(ShopKey, shopId)
}

export function removeShopId() {
  return Cookies.remove(ShopKey)
}
