
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import BasicCompanyManager from "./components/listers/BasicCompanyCards"
import BasicCompanyDetail from "./components/listers/BasicCompanyDetail"

import SalesSalesOrderManager from "./components/listers/SalesSalesOrderCards"
import SalesSalesOrderDetail from "./components/listers/SalesSalesOrderDetail"


export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/basics/companies',
                name: 'BasicCompanyManager',
                component: BasicCompanyManager
            },
            {
                path: '/basics/companies/:id',
                name: 'BasicCompanyDetail',
                component: BasicCompanyDetail
            },

            {
                path: '/sales/salesOrders',
                name: 'SalesSalesOrderManager',
                component: SalesSalesOrderManager
            },
            {
                path: '/sales/salesOrders/:id',
                name: 'SalesSalesOrderDetail',
                component: SalesSalesOrderDetail
            },



    ]
})
