import request from '@/utils/request'

// 列表请求
export function getList(params) {
  return request({
    url: `/transaction/projects/q?pageNo=${params.pageNo}&pageSize=${params.pageSize}`,
    method: 'post',
    params
  })
}
// 管理员审核标的信息
export function loanVerify(params) {
  return request({
    url: `/transaction/m/projects/${params.id}/projectStatus/${params.approveStatus}?commission=0`,
    method: 'put',
    params
  })
}
// 满标审核
export function lendVerify(params) {
  return request({
    url: `/transaction/m/loans/${params.id}/projectStatus/${params.approveStatus}?commission=0`,
    method: 'put',
    params
  })
}
