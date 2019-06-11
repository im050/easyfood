import request from '@/utils/request'

export function getFoods(params) {
  return request({
    url: '/food/list',
    method: 'get',
    params
  })
}

export function getFood(shopId, foodId) {
  return request({
    url: '/food/detail',
    method: 'get',
    params: {
      shopId, foodId
    }
  })
}

export function addFood(food) {
  return request({
    url: '/food/add',
    method: 'post',
    data: food
  })
}

export function editFood(food) {
  return request({
    url: '/food/edit',
    method: 'post',
    data: food
  })
}
