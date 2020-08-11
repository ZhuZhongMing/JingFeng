<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <!--<a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="产品名称">
              <a-input placeholder="请输入产品名称" v-model="queryParam.productname"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="产品型号">
              <a-input placeholder="请输入产品型号" v-model="queryParam.producttype"></a-input>
            </a-form-item>
          </a-col>
        <template v-if="toggleSearchStatus">
        <a-col :md="6" :sm="8">
            <a-form-item label="产品规格">
              <a-input placeholder="请输入产品规格" v-model="queryParam.productsize"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="产品颜色">
              <a-input placeholder="请输入产品颜色" v-model="queryParam.productcolor"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="产品长度（节）">
              <a-input placeholder="请输入产品长度（节）" v-model="queryParam.productlength"></a-input>
            </a-form-item>
          </a-col>
          </template>
          <a-col :md="6" :sm="8" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>
      </a-form>-->
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <!--<a-button type="primary" icon="download" @click="handleExportXls('产品信息')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>-->
      <!--<a-dropdown v-if="selectedRowKeys.length > 0">
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

        <template slot="bom" slot-scope="text, record">
          <a-button
            :ghost="true"
            type="primary"
            icon="edit"
            size="small"
            @click="showModel(record)">
            BOM设置
          </a-button>
        </template>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <mProductMap-modal ref="modalForm" @ok="modalFormOk"></mProductMap-modal>
    <!-- BOM表单区域 -->
    <a-modal
      title="BOM设置"
      :width="800"
      :visible="visible"
      :confirmLoading="confirmLoading"
      @ok="handleOk"
      @cancel="handleCancel"
      cancelText="关闭">
<!--{{bom}}-->
      <!--<a-spin :spinning="confirmLoading">
        <a-form :form="form">
          <a-row>
            <a-col :span="12">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="产品名称">
                <a-input placeholder="请输入产品名称" v-decorator="['productname', validatorRules.productname]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="产品型号">
                <a-input placeholder="请输入产品型号" v-decorator="['producttype', validatorRules.producttype]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="产品规格">
                <a-input placeholder="请输入产品规格" v-decorator="['productsize', validatorRules.productsize]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="产品颜色">
                <a-input placeholder="请输入产品颜色" v-decorator="['productcolor', validatorRules.productcolor]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="产品长度（节）">
                <a-input-number  :min="1" style="width: 100%" placeholder="请输入产品长度（节）" v-decorator="['productlength', validatorRules.productlength]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="上刀">
                &lt;!&ndash;<a-input placeholder="请输入上刀" v-decorator="['topblade', validatorRules.topblade]" />&ndash;&gt;
                <a-select placeholder="请选择" v-decorator="['topblade',validatorRules.topblade]">
                  <a-select-option  v-for="(material,index) in materialList" :key="material.id" :value="material.id">{{material.materialname}}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="下刀">
                &lt;!&ndash;<a-input placeholder="请输入下刀" v-decorator="['bottomblade', validatorRules.bottomblade]" />&ndash;&gt;
                <a-select placeholder="请选择" v-decorator="['bottomblade',validatorRules.bottomblade]">
                  <a-select-option  v-for="(material,index) in materialList" :key="material.id" :value="material.id">{{material.materialname}}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="连接片">
                &lt;!&ndash;<a-input placeholder="请输入连接片" v-decorator="['connectionblade', validatorRules.connectionblade]" />&ndash;&gt;
                <a-select placeholder="请选择" v-decorator="['connectionblade',validatorRules.connectionblade]">
                  <a-select-option  v-for="(material,index) in materialList1" :key="material.id" :value="material.id">{{material.materialname}}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="传动片">
                &lt;!&ndash;<a-input placeholder="请输入传动片" v-decorator="['driverblade', validatorRules.driverblade]" />&ndash;&gt;
                <a-select placeholder="请选择" v-decorator="['driverblade',validatorRules.driverblade]">
                  <a-select-option  v-for="(material,index) in materialList2" :key="material.id" :value="material.id">{{material.materialname}}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="铆钉">
                &lt;!&ndash;<a-input placeholder="请输入铆钉" v-decorator="['rivet', validatorRules.rivet]" />&ndash;&gt;
                <a-select placeholder="请选择" v-decorator="['rivet',validatorRules.rivet]">
                  <a-select-option  v-for="(material,index) in materialList3" :key="material.id" :value="material.id">{{material.materialname}}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="是否带反弹传动片">
                &lt;!&ndash;<a-input placeholder="请输入是否带反弹传动片0-否,1-是" v-decorator="['isresilient', {}]" />&ndash;&gt;
                <a-select placeholder="请选择" v-decorator="['isresilient',validatorRules.isresilient]">
                  <a-select-option :value="0">否</a-select-option>
                  <a-select-option :value="1">是</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </a-spin>-->
      <a-card :bordered="false">
        <detail-list title="产品详情" :col="4">
          <detail-list-item term="产品名称">{{bom.map.productname}}</detail-list-item>
          <detail-list-item term="产品型号">{{bom.map.producttype}}</detail-list-item>
          <detail-list-item term="产品规格">{{bom.map.productsize}}</detail-list-item>

          <detail-list-item term="产品颜色">{{bom.map.productcolor}}</detail-list-item>
          <detail-list-item term="产品长度">{{bom.map.productlength}}  节</detail-list-item>
          <detail-list-item term="是否带反弹传动片" v-if="bom.map.isresilient == 0 || bom.map.isresilient == '0'">否</detail-list-item>
          <detail-list-item term="是否带反弹传动片" v-if="bom.map.isresilient == 1 || bom.map.isresilient == '1'">是</detail-list-item>
          <!--<detail-list-item term="客户名称"><i style="color: #bbbbbb">暂未设置</i></detail-list-item>-->
        </detail-list>
        <a-divider style="margin-bottom: 4px"/>
        <detail-list title="物料详情" :col="2">
          <div  v-for="(i,index) in bom.list" :key="index" v-if="bom.list.length > 0">
            <span v-if="bom.map.bottomblade == bom.map.topblade">
              <detail-list-item term="名称" v-if="bom.map.topblade == i.materialencode">{{bom.map.topbladename}}</detail-list-item>
            </span>
            <span v-else>
              <detail-list-item term="名称" v-if="bom.map.topblade == i.materialencode">{{bom.map.topbladename}}</detail-list-item>
              <detail-list-item term="名称" v-if="bom.map.bottomblade == i.materialencode">{{bom.map.bottombladename}}</detail-list-item>
            </span>

            <detail-list-item term="名称" v-if="bom.map.connectionblade == i.materialencode">{{bom.map.connectionbladename}}</detail-list-item>
            <detail-list-item term="名称" v-if="bom.map.driverblade == i.materialencode">{{bom.map.driverbladename}}</detail-list-item>
            <detail-list-item term="名称" v-if="bom.map.rivet == i.materialencode">{{bom.map.rivetname}}</detail-list-item>
            <detail-list-item term="用量">
              <a-input-number :min="1" style="width: 100%" placeholder="请输入物料用量" v-model="i.number" />
            </detail-list-item>

            <!--<div v-if="i.mproductMap.isresilient == 1 || i.mproductMap.isresilient == '1'">
              <detail-list-item term="名称">{{i.mmaterialList.d.materialname}}</detail-list-item>
              <detail-list-item term="颜色">{{i.mmaterialList.d.color}}</detail-list-item>
              <detail-list-item term="单位">{{i.mmaterialList.d.purchaseorganize}}</detail-list-item>
              <detail-list-item term="数量">{{i.mmaterialList.d.number * i.mproductMap.number}}</detail-list-item>
            </div>-->
          </div>
        </detail-list>

      </a-card>
    </a-modal>
  </a-card>
</template>

<script>
  import MProductMapModal from './modules/MProductMapModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { putAction,getAction } from '@/api/manage'

  import PageLayout from '@/components/page/PageLayout'
  import STable from '@/components/table/'
  import DetailList from '@/components/tools/DetailList'
  import ABadge from "ant-design-vue/es/badge/Badge"
  const DetailListItem = DetailList.Item

  export default {
    name: "MProductMapList",
    mixins:[JeecgListMixin],
    components: {
      MProductMapModal,
      PageLayout,
      ABadge,
      DetailList,
      DetailListItem,
      STable
    },
    data () {
      return {
        bom:{
          map:{},
          list:[]
        },
        /*弹窗状态*/
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 8 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
          id:{},
          productname:{
            rules: [
              { required: true, message: '请输入产品名称' },
            ]
          },
          producttype:{
            rules: [
              { required: true, message: '请输入产品型号' },
            ]
          },
          productsize:{
            rules: [
              { required: true, message: '请输入产品规格' },
            ]
          },
          productcolor:{
            rules: [
              { required: true, message: '请输入产品颜色' },
            ]
          },
          productlength:{
            rules: [
              { required: true, message: '请输入产品长度' },
            ]
          },
          topblade:{
            rules: [
              { required: true, message: '请选择产品上刀' },
            ]
          },
          bottomblade:{
            rules: [
              { required: true, message: '请输入产品下刀' },
            ]
          },
          connectionblade:{
            rules: [
              { required: true, message: '请输入产品连接片' },
            ]
          },
          driverblade:{
            rules: [
              { required: true, message: '请输入产品传动片' },
            ]
          },
          rivet:{
            rules: [
              { required: true, message: '请输入产品铆钉' },
            ]
          },
          isresilient:{
            rules: [
              { required: true, message: '请选择是否带反弹传动片' },
            ]
          },
          createBy:{},
          createTime:{},
          updateBy:{},
          updateTime:{},
          delFlag:{},
        },
        description: '产品信息管理页面',
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
            title: '产品名称',
            align:"center",
            dataIndex: 'productname'
           },
		   {
            title: '产品型号',
            align:"center",
            dataIndex: 'producttype'
           },
		   {
            title: '产品规格',
            align:"center",
            dataIndex: 'productsize'
           },
		   {
            title: '产品颜色',
            align:"center",
            dataIndex: 'productcolor'
           },
		   {
            title: '产品长度（节）',
            align:"center",
            dataIndex: 'productlength'
           },
		   {
            title: '上刀',
            align:"center",
            dataIndex: 'topbladename'
           },
		   {
            title: '下刀',
            align:"center",
            dataIndex: 'bottombladename'
           },
		   {
            title: '连接片',
            align:"center",
            dataIndex: 'connectionbladename'
           },
		   {
            title: '传动片',
            align:"center",
            dataIndex: 'driverbladename'
           },
		   {
            title: '铆钉',
            align:"center",
            dataIndex: 'rivetname'
           },
		   {
            title: '是否带反弹传动片',
            align:"center",
            dataIndex: 'isresilient',
            customRender: function(recode) {
              if ( recode == 0 || recode == '0' ) {
                return '否';
              } else if ( recode == 1 || recode == '1' ) {
                return '是';
              } else {
                return recode;
              }

            }
       },
		   /*{
            title: '删除标识0-正常,1-已删除',
            align:"center",
            dataIndex: 'delFlag'
           },*/
          {
            title: 'BOM编辑',
            dataIndex: 'bom',
            align:"center",
            scopedSlots: { customRender: 'bom' },
            /*fixed: 'right',*/
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/system/mProductMap/list",
          delete: "/system/mProductMap/delete",
          deleteBatch: "/system/mProductMap/deleteBatch",
          exportXlsUrl: "system/mProductMap/exportXls",
          importExcelUrl: "system/mProductMap/importExcel",
       },
    }
  },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    mounted () {
      //this.getMaterialList ()
    },
    methods: {
      /*查看bom*/
      showModel(record) {
        //alert(record)
        //let that = this
        this.confirmLoading = true
        //alert(record.id+"   "+record.id)
        //this.modelTitle = '生产任务'+record.id//+","+record.materialname+"BOM表"
        getAction('/system/mBom/BomByMapId',{'id':record.id}).then((res)=>{
          if (res.success) {
            //this.$set(this.dictOptions, 'deptid', res.result)
            this.bom.map = record
            this.bom.list = res.result
            if (res.result.length > 0) {
              this.confirmLoading = false
              this.visible = true
            } else {
              this.confirmLoading = false
              this.visible = false
              this.$message.warning('请检查产品详情')
            }

          } else {
            this.$message.warning('请先设置明细')
          }
        });
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        /*this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
              method = 'put';
            }
            let formData = Object.assign(this.model, values);
            //时间格式化

            console.log(formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })



          }
        })*/
        console.log("编辑后BOM："+JSON.stringify(this.bom.list)+"                  "+this.bom.list.length)
        for (let i = 0; i < this.bom.list.length; i++) {
          console.log(i + "    " + JSON.stringify(this.bom.list[i]))
          let parm = {
            id:this.bom.list[i].id,
            number:this.bom.list[i].number,
          }
          putAction('/system/mBom/edit',parm).then((res)=>{
            if (res.success) {
              //console.log(i)
              if ((i+1) == this.bom.list.length) {
                this.$message.success('操作成功')
              }
            }
          }).finally(() => {
            that.confirmLoading = false;
            that.close();
          });
        }

      },
      handleCancel () {
        this.close()
      },

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>