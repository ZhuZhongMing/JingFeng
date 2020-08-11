<template>
  <page-layout :title="title">
    <a-card :bordered="false">
      <detail-list title="订单详情">

        <detail-list-item term="订单编号">{{id}}</detail-list-item>
        <detail-list-item term="客户名称" v-if="datasource.cname != null">{{datasource.cname}}</detail-list-item>
        <detail-list-item term="客户名称" v-else><i style="color: #bbbbbb">暂未设置</i></detail-list-item>

        <!--<detail-list-item term="业务员" v-if="datasource.salesman != null">{{datasource.salesman}}</detail-list-item>
        <detail-list-item term="客户名称" v-else><i style="color: #bbbbbb">暂未设置</i></detail-list-item>

        <detail-list-item term="部门编号" v-if="datasource.departName != null">{{datasource.departName}}</detail-list-item>
        <detail-list-item term="客户名称" v-else><i style="color: #bbbbbb">暂未设置</i></detail-list-item>

        <detail-list-item term="支付方式" v-if="datasource.paymentid != null">{{datasource.paymentid}}</detail-list-item>
        <detail-list-item term="客户名称" v-else><i style="color: #bbbbbb">暂未设置</i></detail-list-item>

        <detail-list-item term="支付时间" v-if="datasource.paymentdate != null">{{datasource.paymentdate}}</detail-list-item>
        <detail-list-item term="客户名称" v-else><i style="color: #bbbbbb">暂未设置</i></detail-list-item>

        <detail-list-item term="递送方式" v-if="datasource.deliverymethod != null">{{datasource.deliverymethod}}</detail-list-item>
        <detail-list-item term="客户名称" v-else><i style="color: #bbbbbb">暂未设置</i></detail-list-item>

        <detail-list-item term="递送费用" v-if="datasource.deliveryfee != null">{{datasource.deliveryfee}}</detail-list-item>
        <detail-list-item term="客户名称" v-else><i style="color: #bbbbbb">暂未设置</i></detail-list-item>

        <detail-list-item term="结算货币" v-if="datasource.currency != null">{{datasource.currency}}</detail-list-item>
        <detail-list-item term="客户名称" v-else><i style="color: #bbbbbb">暂未设置</i></detail-list-item>
-->
        <detail-list-item term="创建人" v-if="datasource.createBy != null">{{datasource.createBy}}</detail-list-item>
        <detail-list-item term="客户名称" v-else><i style="color: #bbbbbb">暂未设置</i></detail-list-item>

        <detail-list-item term="创建时间" v-if="datasource.createTime != null">{{datasource.createTime}}</detail-list-item>
        <detail-list-item term="客户名称" v-else><i style="color: #bbbbbb">暂未设置</i></detail-list-item>

        <detail-list-item term="备注" v-if="datasource.disp != null">{{datasource.disp}}</detail-list-item>
        <detail-list-item term="客户名称" v-else><i style="color: #bbbbbb">暂未说明</i></detail-list-item>

      </detail-list>

      <a-divider style="margin-bottom: 24px"/>

      <div class="title">订单明细</div>
      <s-table
        v-if="order.data.length > 0"
        style="margin-bottom: 24px"
        :columns="orderListColumns"
        :data="func">
      </s-table>
    </a-card>

  </page-layout>


</template>

<script>

  import pick from 'lodash.pick'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import JDate from '@/components/jeecg/JDate'  
  import JSelectDepart from '@/components/jeecgbiz/JSelectDepart'
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JEditableTable from '@/components/jeecg/JEditableTable'
  import { VALIDATE_NO_PASSED, validateFormAndTables } from '@/utils/JEditableTableUtil'
  import { httpAction, getAction,postAction } from '@/api/manage'

  import PageLayout from '@/components/page/PageLayout'
  import STable from '@/components/table/'
  import DetailList from '@/components/tools/DetailList'
  import ABadge from "ant-design-vue/es/badge/Badge"
  const DetailListItem = DetailList.Item

  export default {
    name: 'MOrderListInfoModal',
    components: {
      JDate,
      JSelectDepart,
      JSelectUserByDep,
      JDictSelectTag,
      JEditableTable,
      PageLayout,
      ABadge,
      DetailList,
      DetailListItem,
      STable
    },
    props:{
      id:{
        type : String,
        default : ''
      }
    },
    data() {
      return {
        order:{
          pageSize: 10,
          pageNo: 1,
          totalCount: 0,
          totalPage:0,
          data:[]
        },
        orderListColumns: [
          {
            title: '产品名称',
            dataIndex: 'productname',
            key: 'productname'
          },
          {
            title: '订单数量',
            dataIndex: 'ordernum',
            key: 'ordernum'
          },
          {
            title: '订单号',
            dataIndex: 'orderid',
            key: 'orderid'
          },
        ],
        title: '订单详情',
        visible: false,
        confirmLoading: false,
        datasource:{},
        url: {
          queryurl: "/system/mOrder/queryById",
          orderList: {
            list: '/system/mOrder/queryPageOrderList'
          },
        },
        // 加载数据方法 必须为 Promise 对象
        func:()=> {
          return new Promise((resolve => {
            //this.request(this.id)
            //this.queryPageOrderList (this.orderid)
            //console.log("order："+JSON.stringify(this.order))
            resolve(this.order)
          })).then(res => {
            return res
          })
        },

      }
    },
    computed:{
      /*totalPage: () =>{
        if((this.totalCount % this.pageSize) != 0){
          if (this.totalCount < this.pageSize) {
            return 1
          } else {
            return   this.totalCount / this.pageSize + 1
          }
        } else {
          return   this.totalCount / this.pageSize
        }
      }*/
    },
    created () {
      this.request(this.id)
    },
    mounted(){

    },
    methods: {
      /** 发起请求 */
      request (id) {

        getAction(this.url.queryurl,{'id':id}).then((res) => {
          if (res.success) {
            //this.$message.success(res.message)
            /*if (res.result.planstatusid == '0') {
              res.result.planstatusid = '等待确认'
            } else if (res.result.planstatusid == '1') {
              res.result.planstatusid = '正在进行'
            } else if (res.result.planstatusid == '1') {
              res.result.planstatusid = '计划完成'
            } else {
              res.result.planstatusid = ''
            }*/
            this.datasource = res.result
            /*查询订单明细*/
            //console.log("osdasdasdasrderisssssssssssssssssssssssssssssssd:"+res.result.id)
            this.queryPageOrderList (res.result.id)
          }
        })
      },
      /*订单详情*/
      queryPageOrderList (orderid) {

        getAction(this.url.orderList.list,{'orderid':orderid,'pageSize':this.order.pageSize,'pageNo':this.order.pageNo}).then((res) => {
          if (res.success) {
            //this.$message.success(res.message)
            this.order.data = res.result.records
            //console.log("mingx:"+JSON.stringify(res.result.records))
            /* 总条数 */
            this.order.totalCount = res.result.total
            this.order.pageNo = res.result.current
            this.order.totalPage = res.result.pages
            this.order.pageSize = res.result.size
          }
        })
      },



    }
  }
</script>

<style scoped>
</style>