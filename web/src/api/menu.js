import request from '@/utils/request'

export function getListTop() {
  return request({
    url: '/system/menu/listTop',
    method: 'get'
  })
}

export function getMenuList() {
  return request({
    url: '/system/menu/list',
    method: 'get'
  })
}
export function getMenuRouterList() {
  return request({
    url: '/system/menu/menu',
    method: 'get'
  })
}

export function deleteMenu(id) {
  return request({
    url: '/system/menu/deleteMenu?id=' + id,
    method: 'get'
  })
}

export function updateMenu(data) {
  return request({
    url: '/system/menu/updateMenu',
    method: 'post',
    data
  })
}

export function addMenu(data) {
  return request({
    url: '/system/menu/addMenu',
    method: 'post',
    data
  })
}
