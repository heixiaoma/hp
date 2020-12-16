import request from '@/utils/request'

export function getList() {
  return request({
    url: '/system/module/list',
    method: 'get'
  })
}
export function getModuleList() {
  return request({
    url: '/system/module/getModuleList',
    method: 'get'
  })
}

export function sync() {
  return request({
    url: '/system/module/sync',
    method: 'get'
  })
}

