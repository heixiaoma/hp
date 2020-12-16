import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/system/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/system/user/info',
    method: 'get',
    params: { token }
  })
}

export function getUserList(page, pageSize) {
  return request({
    url: '/system/user/list?page=' + page + '&pageSize=' + pageSize,
    method: 'get'
  })
}

export function deleteUser(id) {
  return request({
    url: '/system/user/delete?id=' + id,
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/system/user/logout',
    method: 'post'
  })
}

export function editUser(data) {
  return request({
    url: '/system/user/edit',
    method: 'post',
    data
  })
}
export function addUser(data) {
  return request({
    url: '/system/user/add',
    method: 'post',
    data
  })
}

export function multiDelete(data) {
  return request({
    url: '/system/user/multiDelete',
    method: 'post',
    data
  })
}
