import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/loan/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },

  {
    path: '/loanAdmin',
    component: Layout,
    redirect: '/example/loan',
    name: 'loanAdmin',
    meta: { title: '准备中借款管理', icon: 'example' },
    children: [
      {
        path: 'loan',
        name: 'loan',
        component: () => import('@/views/loan/index'),
        meta: { title: '准备中待审核', icon: 'example' }
      }
    ]
  },
  {
    path: '/lendAdmin',
    component: Layout,
    redirect: '/lendAdmin/lend',
    name: 'lendAdmin',
    meta: { title: '满标放款管理', icon: 'example' },
    children: [
      {
        path: 'lend',
        name: 'lend',
        component: () => import('@/views/lend/index'),
        meta: { title: '满标放款审核', icon: 'table' }
      }
    ]
  },
    {
        path: '/consumerAdmin',
        component: Layout,
        redirect: '/example/consumer',
        name: 'consumerAdmin',
        meta: { title: '客户管理', icon: 'example' },
        children: [
            {
                path: 'temp',
                name: 'consumer',
                component: () => import('@/views/temp/index'),
                meta: { title: '客户基本信息管理', icon: 'example' }
            },
            {
                path: 'temp',
                name: 'consumer',
                component: () => import('@/views/temp/index'),
                meta: { title: '客户基本信息详细页', icon: 'example' }
            },
            {
                path: 'temp',
                name: 'consumer',
                component: () => import('@/views/temp/index'),
                meta: { title: '借款客户信息管理', icon: 'example' },
                children: [
                    {
                        path: 'temp',
                        name: 'consumer',
                        component: () => import('@/views/temp/index'),
                        meta: { title: '借款信用等级设置', icon: 'example' }
                    },
                    {
                        path: 'temp',
                        name: 'consumer',
                        component: () => import('@/views/temp/index'),
                        meta: { title: '信用额度管理', icon: 'example' }
                    },
                    {
                        path: 'temp',
                        name: 'consumer',
                        component: () => import('@/views/temp/index'),
                        meta: { title: '信用额度调整', icon: 'example' }
                    },
                ]
            },
            {
                path: 'temp',
                name: 'consumer',
                component: () => import('@/views/temp/index'),
                meta: { title: '用户认证管理', icon: 'example' },
                children: [
                    {
                        path: 'temp',
                        name: 'consumer',
                        component: () => import('@/views/temp/index'),
                        meta: { title: '手机人工认证', icon: 'example' }
                    },
                    {
                        path: 'temp',
                        name: 'consumer',
                        component: () => import('@/views/temp/index'),
                        meta: { title: '手机审核记录', icon: 'example' }
                    },
                    {
                        path: 'temp',
                        name: 'consumer',
                        component: () => import('@/views/temp/index'),
                        meta: { title: '实名认证', icon: 'example' }
                    },
                    {
                        path: 'temp',
                        name: 'consumer',
                        component: () => import('@/views/temp/index'),
                        meta: { title: '邮箱认证', icon: 'example' }
                    },
                    {
                        path: 'temp',
                        name: 'consumer',
                        component: () => import('@/views/temp/index'),
                        meta: { title: '银行卡认证', icon: 'example' }
                    },
                    {
                        path: 'temp',
                        name: 'consumer',
                        component: () => import('@/views/temp/index'),
                        meta: { title: '视频认证', icon: 'example' }
                    },
                ]
            },
        ]
    },
    {
        path: '/loanAdmin',
        component: Layout,
        redirect: '/example/loan',
        name: 'loanAdmin',
        meta: { title: '借款管理', icon: 'example' },
        children: [
            {
                path: 'temp',
                name: 'loan',
                component: () => import('@/views/temp/index'),
                meta: { title: '预热中的借款', icon: 'example' }
            },
            {
                path: 'temp',
                name: 'loan',
                component: () => import('@/views/temp/index'),
                meta: { title: '进行中的借款', icon: 'example' }
            },
            {
                path: 'temp',
                name: 'loan',
                component: () => import('@/views/temp/index'),
                meta: { title: '准备中借款', icon: 'example' }
            },
            {
                path: 'temp',
                name: 'loan',
                component: () => import('@/views/temp/index'),
                meta: { title: '准备中借款管理', icon: 'example' },
                children: [
                    {
                        path: 'loan',
                        name: 'loan',
                        component: () => import('@/views/temp/index'),
                        meta: { title: '准备中待确认', icon: 'example' }
                    },
                    {
                        path: 'loan',
                        name: 'loan',
                        component: () => import('@/views/temp/index'),
                        meta: { title: '准备中操作记录', icon: 'example' }
                    },
                ]
            },
        ]
    },

    {
        path: '/repaymentAdmin',
        component: Layout,
        redirect: '/example/repayment',
        name: 'repaymentAdmin',
        meta: { title: '还款管理', icon: 'example' },
        children: [
            {
                path: 'temp',
                name: 'repayment',
                component: () => import('@/views/temp/index'),
                meta: { title: '当日待还款', icon: 'example' }
            },
            {
                path: 'temp',
                name: 'repayment',
                component: () => import('@/views/temp/index'),
                meta: { title: '待还款借款', icon: 'example' }
            },
            {
                path: 'temp',
                name: 'repayment',
                component: () => import('@/views/temp/index'),
                meta: { title: '还款中借款', icon: 'example' }
            },
            {
                path: 'temp',
                name: 'repayment',
                component: () => import('@/views/temp/index'),
                meta: { title: '已还款借款', icon: 'example' }
            },
            {
                path: 'temp',
                name: 'repayment',
                component: () => import('@/views/temp/index'),
                meta: { title: '提前还款记录', icon: 'example' }
            },
        ]
    },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
