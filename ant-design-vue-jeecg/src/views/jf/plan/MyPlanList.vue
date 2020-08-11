<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <!--<a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-form layout="inline" @submit.prevent="searchQuery">
            <a-row :gutter="24">
              <a-col :md="6" :sm="12">
                <a-form-item label="账号">
                  <a-input placeholder="请输入账号查询" v-model="queryParam.salesmanid"></a-input>
                </a-form-item>
              </a-col>
            </a-row>
          </a-form>
        </a-row>
      </a-form>-->
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">

    </div>

    <!-- table区域-begin -->
    <div>



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

        <span slot="action" slot-scope="text, record">
          <!--<a @click="handleEdit(record)">签到</a>-->
          <a v-if="record.planstatusid == 0" @click="handclickOK(record)">确认计划</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <!--<a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>-->
                <a @click="showModal(record)">查看详情</a>
              </a-menu-item>
            </a-menu>

          </a-dropdown>
        </span>
      </a-table>
    </div>
    <a-modal
      title="计划详情"
      :visible="visible"
      @cancel="handleCancel"
      :footer="null"
      :width="1200"
    >
      <my-plan-modal :id="id"></my-plan-modal>
    </a-modal>

  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import MyPlanModal from './modules/MyPlanModal'
  import { putAction } from '@/api/manage'
  export default {
    name: "MyPlanList",
    mixins:[JeecgListMixin],
    components:{
      MyPlanModal
    },
    data () {
      return {
        /*查看详情的id主键*/
        id:'',
        /*弹窗状态*/
        visible:false,
        description: '个人计划',
        // 表头
        columns: [
          {
            title:'物料编号',
            align:"center",
            dataIndex: 'materialname'
          },
          {
            title:'数量',
            align:"center",
            dataIndex: 'number'
          },
          {
            title:'开始时间',
            align:"center",
            dataIndex: 'starttime'
          },
          {
            title:'完成时间',
            align:"center",
            dataIndex: 'finishtime'
          },
          {
            title:'备注',
            align:"center",
            dataIndex: 'disp'
          },
          {
            title:'创建日期',
            align:"center",
            dataIndex: 'createTime'
          },
          {
            title:'创建人',
            align:"center",
            dataIndex: 'createBy'
          },
          {
            title:'计划状态',
            align:"center",
            dataIndex: 'planstatusid',
            // && text != 1 && text != 2
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['planstatusid'], text+"")
              }
            }
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/system/mMainplanabstract/listByid",
          delete: "",
          deleteBatch: "",
          exportXlsUrl: "",
          importExcelUrl: "",
        },
        dictOptions:{
         isautotaskassign:[],
         isstop:[],
         planstatusid:[],
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
        initDictOptions('IsAutoTaskAssign').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'isautotaskassign', res.result)
          }
        })
        initDictOptions('IsStop').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'isstop', res.result)
          }
        })
        initDictOptions('planstatusid').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'planstatusid', res.result)
          }
        })
      },
      /* 查看详情 */
      showModal (record) {
        this.id = record.id
        this.visible = true;
      },
      /* 点击确认 */
      handclickOK (record) {
        let mMainplan = {
          'id':record.id,
          'planstatusid':1
        }
        //console.log("sss:"+JSON.stringify(mMainplan))
        putAction('/system/mMainplanabstract/okPlan',mMainplan).then((res) => {
          if (res.success) {

            this.$message.success(res.message);
            this.loadData();
          }
        })
      },
      handleCancel(e) {
        this.id = ''
        this.visible = false;
      },
       
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
