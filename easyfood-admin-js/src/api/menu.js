import request from '@/utils/request'
import qs from 'qs'

export function getList() {
  return request({
    url: '/menu/list',
    method: 'get'
  })
}

export function addMenu(form) {
  return request({
    url: '/menu/add',
    method: 'post',
    data: form
  })
}

export function sortUpdate(params) {
  return request({
    url: '/menu/sortUpdate',
    method: 'post',
    data: params
  })
}

export function editMenu(data) {
  return request({
    url: '/menu/edit',
    method: 'post',
    data
  })
}

export function delMenu(menuId) {
  return request({
    url: '/menu/delete',
    method: 'post',
    data: qs.stringify({
      menuId
    })
  })
}
