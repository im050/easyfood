import request from '@/utils/request'
import qs from 'qs'

export function login(data) {
  return request({
    url: '/auth/login',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function getInfo() {
  return request({
    url: '/merchant/info',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/auth/logout',
    method: 'post'
  })
}
