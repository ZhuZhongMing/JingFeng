<template>
  <!--<a-modal
    :title="title"
    :width="1300"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      &lt;!&ndash; 主表单区域 &ndash;&gt;
      <a-form :form="form">
        <a-row>

          &lt;!&ndash;<a-col :span="12">
            <a-form-item label="创建日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择创建日期" v-decorator="[ 'createTime', validatorRules.createTime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
            </a-form-item>
          </a-col>&ndash;&gt;

          <a-col :span="12">
            <a-form-item label="部门编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-depart v-decorator="['deptid']" :trigger-change="true"/>
            </a-form-item>
          </a-col>

          <a-col :span="12">
            <a-form-item label="业务员编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-user-by-dep v-decorator="['salesmanid']" :trigger-change="true"/>
            </a-form-item>
          </a-col>

          &lt;!&ndash;<a-col :span="12">
            <a-form-item label="计划种类编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'plantypeid', validatorRules.plantypeid]" placeholder="请输入计划种类编号"></a-input>
            </a-form-item>
          </a-col>&ndash;&gt;

          <a-col :span="12">
            <a-form-item label="业务日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择业务日期" v-decorator="[ 'saledate', validatorRules.saledate]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>

          <a-col :span="12">
            <a-form-item label="审核人编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              &lt;!&ndash;<a-input v-decorator="[ 'auditorid', validatorRules.auditorid]" placeholder="请输入审核人编号"></a-input>&ndash;&gt;
              <j-select-user-by-dep v-decorator="['auditorid']" :trigger-change="true"/>
            </a-form-item>
          </a-col>

          &lt;!&ndash;<a-col :span="12">
            <a-form-item label="审核日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择审核日期" v-decorator="[ 'auditdate', validatorRules.auditdate]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>

          <a-col :span="12">
            <a-form-item label="审核状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['auditstatus']" :trigger-change="true" dictCode="AuditStatus" placeholder="请选择审核状态"/>
            </a-form-item>
          </a-col>&ndash;&gt;

          <a-col :span="24">
            <a-form-item label="备注" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-textarea v-decorator="['disp']" rows="4" placeholder="请输入备注"/>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>

      &lt;!&ndash; 子表单区域 &ndash;&gt;
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="计划详情" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="mMainplanTable.loading"
            :columns="mMainplanTable.columns"
            :dataSource="mMainplanTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>

      </a-tabs>

    </a-spin>
  </a-modal>-->
  <page-layout :title="title">
    <a-card :bordered="false">
      <detail-list title="主计划详情">
        <detail-list-item term="计划编号">{{id}}</detail-list-item>
        <detail-list-item term="客户名称">{{datasource.cname}}</detail-list-item>
        <detail-list-item term="订单编号">{{datasource.orderid}}</detail-list-item>
        <detail-list-item term="创建人">{{datasource.createBy}}</detail-list-item>
        <detail-list-item term="创建时间">{{datasource.createTime}}</detail-list-item>
        <detail-list-item term="计划状态">{{datasource.planstatusid}}</detail-list-item>
        <detail-list-item term="物料名称">{{datasource.materialname}}</detail-list-item>
        <detail-list-item term="数量">{{datasource.number}}</detail-list-item>
      </detail-list>
      <a-divider style="margin-bottom: 24px"/>
      <detail-list title="主计划摘要">
        <detail-list-item term="主计划摘要编号">{{datasource.mainplanabstractid}}</detail-list-item>
        <detail-list-item term="业务员">{{datasource.salesmanid}}</detail-list-item>
      </detail-list>
      <a-divider style="margin-bottom: 24px"/>
      <!--<detail-list title="订单详情">
        <detail-list-item term="主计划摘要编号">付小小</detail-list-item>
        <detail-list-item term="联系电话">18100000000</detail-list-item>
        <detail-list-item term="常用快递">菜鸟仓储</detail-list-item>
        <detail-list-item term="取货地址">浙江省杭州市西湖区万塘路18号</detail-list-item>
        <detail-list-item term="备注">	无</detail-list-item>
      </detail-list>-->

      <div class="title">订单详情</div>
      <s-table
        v-if="order.data.length > 0"
        style="margin-bottom: 24px"
        :columns="orderListColumns"
        :data="func">

      </s-table>

      <!--<div class="title">退货进度</div>
      <s-table
        style="margin-bottom: 24px"
        :columns="scheduleColumns"
        :data="loadScheduleData">

        <template
          slot="status"
          slot-scope="status">
          <a-badge :status="status" :text="status | statusFilter"/>
        </template>

      </s-table>-->
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
    name: 'MyPlanModal',
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
        /*订单号*/
        orderid:'',
        order:{
          pageSize: 10,
          pageNo: 1,
          totalCount: 0,
          totalPage:0,
          data:[]
        },
        orderListColumns: [
          {
            title: '物料名称',
            dataIndex: 'materialname',
            key: 'materialname'
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
        title: '计划详情',
        visible: false,
        confirmLoading: false,
        datasource:{},
        url: {
          queryurl: "/system/mMainplanabstract/getByMainId",
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
            if (res.result.planstatusid == '0') {
              res.result.planstatusid = '等待确认'
            } else if (res.result.planstatusid == '1') {
              res.result.planstatusid = '正在进行'
            } else if (res.result.planstatusid == '1') {
              res.result.planstatusid = '计划完成'
            } else {
              res.result.planstatusid = ''
            }
            this.datasource = res.result
            this.orderid = res.result.orderid
            /*查询订单明细*/
            this.queryPageOrderList (this.orderid)
          }
        })
      },
      /*订单详情*/
      queryPageOrderList (orderid) {
        getAction(this.url.orderList.list,{'orderid':orderid,'pageSize':this.order.pageSize,'pageNo':this.order.pageNo}).then((res) => {
          if (res.success) {
            //this.$message.success(res.message)
            this.order.data = res.result.records
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