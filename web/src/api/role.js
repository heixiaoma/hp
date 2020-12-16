import request from '@/utils/request'

export function getList() {
  return request({
    url: '/system/role/list',
    method: 'get'
  })
}

export function getMenuPermission(roleId) {
  return request({
    url: '/system/role/getMenuPermission?roleId=' + roleId,
    method: 'get'
  })
}

export function getModulePermission(roleId) {
  return request({
    url: '/system/role/getModulePermission?roleId=' + roleId,
    method: 'get'
  })
}

export function addRole(data) {
  return request({
    url: '/system/role/add',
    method: 'post',
    data
  })
}

export function deleteRole(id) {
  return request({
    url: '/system/role/deleteRole?id=' + id,
    method: 'get'
  })
}

export function updateRole(data) {
  return request({
    url: '/system/role/updateRole',
    method: 'post',
    data
  })
}

export function updateMenuPermission(data) {
  return request({
    url: '/system/role/updateMenuPermission',
    method: 'post',
    data
  })
}

export function updateModulePermission(data) {
  return request({
    url: '/system/role/updateModulePermission',
    method: 'post',
    data
  })
}
