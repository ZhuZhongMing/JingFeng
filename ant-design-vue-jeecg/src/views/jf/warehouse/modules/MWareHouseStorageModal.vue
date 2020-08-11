<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="物料编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!--<j-dict-select-tag type="list" v-decorator="['materialencode']" :trigger-change="true" dictCode="" placeholder="请选择物料编号"/>-->
          <a-select placeholder="请选择" v-decorator="['materialencode']">
            <a-select-option  v-for="(material,index) in materialList" :key="material.id" :value="material.id">{{material.materialname}}</a-select-option>
          </a-select>
        </a-form-item>
          
        <a-form-item label="仓库编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!--<j-dict-select-tag type="list" v-decorator="['warehouseid']" :trigger-change="true" dictCode="" placeholder="请选择仓库编号"/>-->
          <a-select placeholder="请选择" v-decorator="['warehouseid']">
            <a-select-option  v-for="(house,index) in houseList" :key="house.id" :value="house.id">{{house.whname}}</a-select-option>
          </a-select>
        </a-form-item>
          
        <a-form-item label="当前库存" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'currentstorage', validatorRules.currentstorage]" placeholder="请输入当前库存" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="删除标识" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['delFlag']" :trigger-change="true" dictCode="del_flag" placeholder="请选择删除标识"/>
        </a-form-item>
          
        
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction,getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  
  export default {
    name: "MWareHouseStorageModal",
    components: { 
      JDictSelectTag,
    },
    data () {
      return {
        /*物料信息列表*/
        materialList: [],
        /*仓库信息列表*/
        houseList: [],
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        validatorRules:{
        materialencode:{},
        warehouseid:{},
        currentstorage:{},
        delFlag:{},
        },
        url: {
          add: "/system/mWareHouseStorage/add",
          edit: "/system/mWareHouseStorage/edit",
        }
     
      }
    },
    created () {
    },
    mounted () {
      this.getMaterialList ()
      this.getHouseList ()
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'materialencode','warehouseid','currentstorage','delFlag'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
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
            console.log("表单提交数据",formData)
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
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'materialencode','warehouseid','currentstorage','delFlag'))
      },
      /*获取物料下拉框内容*/
      getMaterialList () {
        getAction('/system/mMaterial/getMaterialList',null).then((res)=>{
          if(res.success){
            /*清空*/
            this.materialList.splice(0)
            this.materialList = res.result
          }
        });
      },
      /*获取仓库下拉框内容*/
      getHouseList () {
        getAction('/system/mWareHouse/getHouseList',null).then((res)=>{
          if(res.success){
            /*清空*/
            this.houseList.splice(0)
            this.houseList = res.result
          }
        });
      },
      
    }
  }
</script>