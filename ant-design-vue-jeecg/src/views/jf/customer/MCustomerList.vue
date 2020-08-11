<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <!--<a-button type="primary" icon="download" @click="handleExportXls('客户信息')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>-->
    </div>

    <!-- table区域-begin -->
    <div>
      <!--<div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>-->

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">
        
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <mCustomer-modal ref="modalForm" @ok="modalFormOk"></mCustomer-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import MCustomerModal from './modules/MCustomerModal'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  export default {
    name: "MCustomerList",
    mixins:[JeecgListMixin],
    components: {
      MCustomerModal
    },
    data () {
      return {
        description: '客户信息管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'客户名称',
            align:"center",
            dataIndex: 'cname'
          },
          {
            title:'简称',
            align:"center",
            dataIndex: 'abcname'
          },
          /*{
            title:'检索码',
            align:"center",
            dataIndex: 'indexcode'
          },*/
          /*{
            title:'是否供应商',
            align:"center",
            dataIndex: 'issupplier',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['issupplier'], text+"")
              }
            }
          },
          {
            title:'是否客户',
            align:"center",
            dataIndex: 'isclient',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['isclient'], text+"")
              }
            }
          },
          {
            title:'客户类型',
            align:"center",
            dataIndex: 'ctype'
          },
          {
            title:'客户分类1',
            align:"center",
            dataIndex: 'categorytype'
          },*/
          /*{
            title:'所属区域',
            align:"center",
            dataIndex: 'carea'
          },*/
          {
            title:'首次合作时间',
            align:"center",
            dataIndex: 'firstcodate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          /*{
            title:'客户分类2',
            align:"center",
            dataIndex: 'categorytype2'
          },
          {
            title:'是否外协',
            align:"center",
            dataIndex: 'isassist',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['isassist'], text+"")
              }
            }
          },
          {
            title:'客户地址',
            align:"center",
            dataIndex: 'caddress'
          },
          {
            title:'邮政编码',
            align:"center",
            dataIndex: 'postcode'
          },
          {
            title:'法人',
            align:"center",
            dataIndex: 'cmanager'
          },*/
          {
            title:'联系人',
            align:"center",
            dataIndex: 'contactperson'
          },
          {
            title:'联系人电话',
            align:"center",
            dataIndex: 'telephone'
          },
          {
            title:'邮箱',
            align:"center",
            dataIndex: 'email'
          },
          /*{
            title:'传真',
            align:"center",
            dataIndex: 'fax'
          },
          {
            title:'银行',
            align:"center",
            dataIndex: 'bank'
          },
          {
            title:'开户账号',
            align:"center",
            dataIndex: 'account'
          },
          {
            title:'付款方式',
            align:"center",
            dataIndex: 'paymentkind'
          },
          {
            title:'付款周期',
            align:"center",
            dataIndex: 'paymentcircle'
          },
          {
            title:'结算货币',
            align:"center",
            dataIndex: 'currency'
          },
          {
            title:'税号',
            align:"center",
            dataIndex: 'taxno'
          },
          {
            title:'营业执照',
            align:"center",
            dataIndex: 'license'
          },
          {
            title:'信用等级',
            align:"center",
            dataIndex: 'creditgrade'
          },
          {
            title:'信用额度',
            align:"center",
            dataIndex: 'creditlimit'
          },*/
          /*{
            title:'业务员',
            align:"center",
            dataIndex: 'salesman'
          },*/
          /*{
            title:'启用状态',
            align:"center",
            dataIndex: 'cstatus'
          },
          {
            title:'描述',
            align:"center",
            dataIndex: 'description'
          },
          {
            title:'备注',
            align:"center",
            dataIndex: 'disp'
          },*/
          {
            title:'企业网址',
            align:"center",
            dataIndex: 'website'
          },
          /*{
            title:'删除标识',
            align:"center",
            dataIndex: 'delFlag',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['delFlag'], text+"")
              }
            }
          },*/
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/system/mCustomer/list",
          delete: "/system/mCustomer/delete",
          deleteBatch: "/system/mCustomer/deleteBatch",
          exportXlsUrl: "/system/mCustomer/exportXls",
          importExcelUrl: "system/mCustomer/importExcel",
        },
        dictOptions:{
         issupplier:[],
         isclient:[],
         isassist:[],
         delFlag:[],
        } 
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
        initDictOptions('IsSupplier').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'issupplier', res.result)
          }
        })
        initDictOptions('IsClient').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'isclient', res.result)
          }
        })
        initDictOptions('').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'isassist', res.result)
          }
        })
        initDictOptions('').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'delFlag', res.result)
          }
        })
      }
       
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>