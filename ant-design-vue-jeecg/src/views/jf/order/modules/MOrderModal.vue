<template>
  <a-drawer
    :title="title"
    :width="drawerWidth"
    @close="handleCancel"
    :visible="visible"
    :wrapStyle="{height: 'calc(100% - 108px)',overflow: 'auto',paddingBottom: '108px'}"
  >
  <!--<a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">-->
    <a-spin :spinning="confirmLoading">
      <!-- 主表单区域 -->
      <a-form :form="form">
        <a-row>

          <!--<a-col :span="12">
            <a-form-item label="订单编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'id', validatorRules.id]" placeholder="请输入客户编号"></a-input>

            </a-form-item>
          </a-col>-->

          <a-col :span="12">
            <a-form-item label="合同编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'contract', validatorRules.contract]" placeholder="请输入合同编号"></a-input>

            </a-form-item>
          </a-col>

          <a-col :span="12">
            <a-form-item label="客户编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <!--<a-input v-decorator="[ 'customerid', validatorRules.customerid]" placeholder="请输入客户编号"></a-input>-->
            <a-select placeholder="请选择" v-decorator="['customerid']">
              <a-select-option  v-for="(customer,index) in customerList" :key="customer.id" :value="customer.id">{{customer.cname}}</a-select-option>
            </a-select>
          </a-form-item>
          </a-col>
        
         <!-- <a-col :span="12">
            <a-form-item label="业务员" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-user-by-dep v-decorator="['salesman']" :trigger-change="true"/>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="部门编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-depart v-decorator="['deptid']" :trigger-change="true"/>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="支付方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'paymentid', validatorRules.paymentid]" placeholder="请输入支付方式"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="支付时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择支付时间" v-decorator="[ 'paymentdate', validatorRules.paymentdate]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="递送方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'deliverymethod', validatorRules.deliverymethod]" placeholder="请输入递送方式"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="递送费用" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'deliveryfee', validatorRules.deliveryfee]" placeholder="请输入递送费用" style="width: 100%"/>
            </a-form-item>
          </a-col>-->
        
          <!--<a-col :span="12">
            <a-form-item label="审核人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'auditperson', validatorRules.auditperson]" placeholder="请输入审核人"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="审核时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择审核时间" v-decorator="[ 'audittime', validatorRules.audittime]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>-->
        
          <a-col :span="24">
            <a-form-item label="备注" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-textarea v-decorator="['disp']" rows="4" placeholder="请输入备注"/>
            </a-form-item>
          </a-col>

          <a-col :span="24">
            <a-form-item label="合同附件" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-upload
                listType="picture-card"
                class="avatar-uploader"
                :showUploadList="false"
                :action="uploadAction"
                :data="{'isup':1}"
                :headers="headers"
                :beforeUpload="beforeUpload"
                @change="handleChange"
              >
                <!-- v-if="picUrl" model.accessory-->
                <img v-if="picUrl" :src="getAvatarView()" alt="附件" style="height:104px;max-width:300px"/>
                <div v-else>
                  <a-icon :type="uploadLoading ? 'loading' : 'plus'" />
                  <div class="ant-upload-text">上传</div>
                </div>
              </a-upload>
            </a-form-item>
            <!--{{model.accessory}}-->
          </a-col>

          <!--<a-col :span="12">
            <a-form-item label="结算货币" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'currency', validatorRules.currency]" placeholder="请输入结算货币"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="订单类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'ordertype', validatorRules.ordertype]" placeholder="请输入订单类别"></a-input>
            </a-form-item>
          </a-col>-->
        
        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="订单明细" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :selectValues="customerList"
            :ref="refKeys[0]"
            :loading="mOrderlistTable.loading"
            :columns="mOrderlistTable.columns"
            :dataSource="mOrderlistTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        <!--<span slot="action" slot-scope="text, record">
          <a>编辑</a>
          <a-divider type="vertical" />
        </span>-->

      </a-tabs>
      <!--{{mOrderlistTable.dataSource}}-->
      <div style="margin-top: 30px;">
        <a-button :style="{marginRight: '8px'}" @click="handleCancel">
          取消
        </a-button>
        <a-button @click="handleOk" type="primary">确定</a-button>
      </div>
    </a-spin>
   <!-- {{materialList}}-->
  <!--</a-modal>-->
  </a-drawer>

</template>

<script>

  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import Vue from 'vue'

  import pick from 'lodash.pick'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import JDate from '@/components/jeecg/JDate'  
  import JSelectDepart from '@/components/jeecgbiz/JSelectDepart'
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import JEditableTable from '@/components/jeecg/JEditableTable'
  import { VALIDATE_NO_PASSED, validateFormAndTables } from '@/utils/JEditableTableUtil'
  import { httpAction, getAction,postAction } from '@/api/manage'
  /*框架封装好的字段重复校验api*/
  import { duplicateCheck } from '@/api/api'

  /*物料列表*/
  const materialList = []
  function getMaterialList () {
    getAction('/system/mProductMap/getAllList',null).then((res)=>{
      if(res.success){
        /*清空*/
        materialList.splice(0)
        //this.materialList = res.result
        for (let i = 0; i < res.result.length ; i++) {
          //console.log("id:"+res.result[i].id+"  name:"+res.result[i].materialname)
          materialList.push({
            value : res.result[i].id,
            title : res.result[i].productname
          })

        }
      }
    });
  }

  export default {
    name: 'MOrderModal',
    components: {
      JDate,
      JSelectDepart,
      JSelectUserByDep,
      JEditableTable
    },
    data() {
      return {
        picUrl: "",
        uploadLoading:false,
        headers:{},
        drawerWidth:document.documentElement.clientWidth * 0.9,
        /*客户列表*/
        customerList:[],

        title: '操作',
        visible: false,
        form: this.$form.createForm(this),
        confirmLoading: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 },
          span: 6
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 18 },
          span: 16

        },
        labelCol2: {
          span: 3
        },
        wrapperCol2: {
          span: 20
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
          contract:{
            rules: [
              {required: true, message: '请输入合同编号!'},
              //{validator: this.validateCode}
            ]
          },
          id:{},
          customerid:{},
          salesman:{},
          deptid:{},
          paymentid:{},
          paymentdate:{},
          deliverymethod:{},
          deliveryfee:{},
          auditperson:{},
          audittime:{},
          disp:{},
          currency:{},
          ordertype:{},
        },
        refKeys: ['mOrderlist', ],
        tableKeys:['mOrderlist', ],
        activeKey: 'mOrderlist',
        // 订单明细
        mOrderlistTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '产品编号',
              key: 'materialencode',
              type: FormTypes.select,
              defaultValue: '',
              placeholder: '请输入${title}',
              options:materialList,
              //options: [{value:'1',title:'男'},{value:'0',title:'女'}],
              //options: [{value:1,label:'男'},{value:0,label:'女'}],
              //scopedSlots: { customRender: 'action'}
            },
            {
              title: '订单数量',
              key: 'ordernum',
              type: FormTypes.inputNumber,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            /*{
              title: '方式',
              key: 'model',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },*/
            {
              title: '交货日期',
              key: 'finishdate',
              type: FormTypes.date,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '备注',
              key: 'disp',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
          ]
        },
        url: {
          fileUpload: window._CONFIG['domianURL']+"/sys/common/upload",
          imgerver: window._CONFIG['domianURL']+"/sys/common/view",
          add: "/system/mOrder/add",
          edit: "/system/mOrder/edit",
          mOrderlist: {
            list: '/system/mOrder/queryMOrderlistByMainId'
          },
        }
      }
    },
    created () {
      const token = Vue.ls.get(ACCESS_TOKEN);
      this.headers = {"X-Access-Token":token}

      getMaterialList()
    },
    computed:{
      uploadAction:function () {
        return this.url.fileUpload;
      }
    },
    mounted () {
      this.getAll()
      //this.getMaterialList ()
    },
    methods: {
      /*重复字段校验*/
      validateCode(rule, value, callback){
        /*let pattern = /^[a-z|A-Z][a-z|A-Z|\d|_|-]{0,}$/;
        if(!pattern.test(value)){
          callback('编码必须以字母开头，可包含数字、下划线、横杠');
        } else {*/
          var params = {
            tableName: "m_order",
            fieldName: "contract",
            fieldVal: value,
            dataId: this.model.contract
          };
          duplicateCheck(params).then((res)=>{
            if(res.success){
              callback();
            }else{
              callback(res.message);
            }
          })
        /*}*/
      },

      beforeUpload: function(file){
        var fileType = file.type;
        //alert(fileType+"    "+typeof fileType)
        if(fileType.indexOf('image')<0){
          this.$message.warning('请上传图片');
          return false;
        }
        //TODO 验证文件大小
      },
      handleChange (info) {
        this.picUrl = "";
        this.model.accessory　= "";
        if (info.file.status === 'uploading') {
          this.uploadLoading = true;
          return
        }
        if (info.file.status === 'done') {
          var response = info.file.response;
          this.uploadLoading = false;
          //console.log(response);
          if(response.success){
            this.model.accessory = response.message;
            //console.log("response:"+response.message)
            this.picUrl = "Has no pic url yet";
          }else{
            this.$message.warning(response.message);
          }
        }
      },
      getAvatarView(){
        //console.log("路径："+this.model.accessory)
        return this.url.imgerver +"/"+ this.model.accessory;
      },
      /*获取物料下拉框内容*/
      getMaterialList () {
        getAction('/system/mMaterial/getMaterialList',null).then((res)=>{
          if(res.success){
            /*清空*/
            this.materialList.splice(0)
            //this.materialList = res.result
            for (let i = 0; i < res.result.length ; i++) {

              //console.log("id:"+res.result[i].id+"  name:"+res.result[i].materialname)
              this.materialList.push({
                value : res.result[i].id,
                title : res.result[i].materialname
              })

            }
          }
        });
      },
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'id','contract','accessory','customerid','salesman','deptid','paymentid','paymentdate','deliverymethod','deliveryfee','auditperson','audittime','disp','currency','ordertype')
        //console.log(fieldval)
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.mOrderlist.list, params, this.mOrderlistTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          mOrderlistList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
      /*获取客户下拉框内容*/
      getAll () {
        getAction('/system/mCustomer/getAll',null).then((res)=>{
          if(res.success){
            /*清空*/
            this.customerList.splice(0)
            this.customerList = res.result
          }
        });
      },
      /** 获取所有的editableTable实例 */
      getAllTable() {
        if (!(this.refKeys instanceof Array)) {
          throw this.throwNotArray('refKeys')
        }
        let values = this.refKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },

      /** 遍历所有的JEditableTable实例 */
      eachAllTable(callback) {
        // 开始遍历
        this.getAllTable().then(tables => {
          tables.forEach((item, index) => {
            if (typeof callback === 'function') {
              callback(item, index)
            }
          })
        })
      },

      /** 当点击新增按钮时调用此方法 */
      add() {

        /*this.picUrl = "";
        this.model.accessory = "";*/

        if (typeof this.addBefore === 'function') this.addBefore()
        // 默认新增空数据
        let rowNum = this.addDefaultRowNum
        if (typeof rowNum !== 'number') {
          rowNum = 1
          console.warn('由于你没有在 data 中定义 addDefaultRowNum 或 addDefaultRowNum 不是数字，所以默认添加一条空数据，如果不想默认添加空数据，请将定义 addDefaultRowNum 为 0')
        }
        this.eachAllTable((item) => {
          item.add(rowNum)
        })
        if (typeof this.addAfter === 'function') this.addAfter(this.model)
        this.edit({})
      },
      /** 当点击了编辑（修改）按钮时调用此方法 */
      edit(record) {
        if (typeof this.editBefore === 'function') this.editBefore(record)
        this.visible = true
        this.activeKey = this.refKeys[0]
        this.form.resetFields()

        this.model = Object.assign({}, record)
        //console.log("sss   "+JSON.stringify(this.model.contract))

        if (record.accessory != '' && record.accessory != null) {
          this.picUrl = "Has no pic url yet";
          //this.model.accessory = "";
        }

        if (typeof this.editAfter === 'function') this.editAfter(this.model)
      },
      /** 关闭弹窗，并将所有JEditableTable实例回归到初始状态 */
      close() {
        this.picUrl = "";

        this.visible = false
        //this.materialList.splice(0)
        materialList.splice(0)
        this.eachAllTable((item) => {
          item.initialize()
        })
        this.$emit('close')
      },

      /** 查询某个tab的数据 */
      requestSubTableData(url, params, tab) {
        tab.loading = true
        getAction(url, params).then(res => {
          tab.dataSource = res.result || []

          //console.log("子表数据："+res.result)

        }).finally(() => {
          tab.loading = false
        })
      },
      /** 发起请求，自动判断是执行新增还是修改操作 */
      request(formData) {
        let url = this.url.add, method = 'post'
        if (this.model.id) {
          url = this.url.edit
          method = 'put'
        }
        this.confirmLoading = true
        //console.log("OK验证1111  :"+JSON.stringify(formData)+"   "+method)


        httpAction(url,formData, method).then((res) => {
          if (res.success) {
            //this.$message.success(res.message)

            for (let i = 0; i <formData.mOrderlistList.length  ; i++) {
              formData.mOrderlistList[i].orderid = res.result.id
              postAction('/system/mOrder/saveOrUpdate',formData.mOrderlistList[i]).then((res) => {
                if (res.success) {
                  if ((i+1) == formData.mOrderlistList.length){
                    this.$message.success(res.message)
                    this.$emit('ok')
                    this.close()
                  }
                }
              })
            }
            //this.$emit('ok')
            //this.close()
          } else {
            this.$message.warning(res.message)
          }
        }).finally(() => {
          this.confirmLoading = false
        })


      },

      /* --- handle 事件 --- */

      /** ATab 选项卡切换事件 */
      handleChangeTabs(key) {
        // 自动重置scrollTop状态，防止出现白屏
        getRefPromise(this, key).then(editableTable => {
          editableTable.resetScrollTop()
        })
      },
      /** 关闭按钮点击事件 */
      handleCancel() {
        this.close()
      },
      /** 确定按钮点击事件 */
      handleOk() {
        /** 触发表单验证 */
        this.getAllTable().then(tables => {
          /** 一次性验证主表和所有的次表 */
          return validateFormAndTables(this.form, tables)
        }).then(allValues => {
          if (typeof this.classifyIntoFormData !== 'function') {
            throw this.throwNotFunction('classifyIntoFormData')
          }
          let formData = this.classifyIntoFormData(allValues)


            // 发起请求
            return this.request(formData)



        }).catch(e => {
          if (e.error === VALIDATE_NO_PASSED) {
            // 如果有未通过表单验证的子表，就自动跳转到它所在的tab
            this.activeKey = e.index == null ? this.activeKey : this.refKeys[e.index]
          } else {
            console.error(e)
          }
        })
      },

      /* --- throw --- */

      /** not a function */
      throwNotFunction(name) {
        return `${name} 未定义或不是一个函数`
      },

      /** not a array */
      throwNotArray(name) {
        return `${name} 未定义或不是一个数组`
      }

    }
  }
</script>

<style scoped>
</style>