import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/menu/list',
    method: 'get',
    params
  })
}

export function add(form) {
  return request({
    url: '/menu/add',
    method: 'post',
    data: form
  })
}
