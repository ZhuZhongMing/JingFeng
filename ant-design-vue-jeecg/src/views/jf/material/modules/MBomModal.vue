<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
      
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="物料编码">
          <!--<a-input placeholder="请输入物料编码" v-decorator="['materialencode', {}]" />-->
          <a-select placeholder="请选择" v-decorator="['materialencode',{}]">
            <a-select-option  v-for="(material,index) in materialList" :key="material.id" :value="material.id">{{material.materialname}}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="父项">
          <a-input placeholder="请输入父项" v-decorator="['parentid', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="版本号">
          <a-input placeholder="请输入版本号" v-decorator="['version', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="子项">
          <a-input placeholder="请输入子项" v-decorator="['childid', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="子项版本号">
          <a-input placeholder="请输入子项版本号" v-decorator="['childversion', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="层次">
          <a-input-number v-decorator="[ 'level', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="是否叶子节点">
          <!--<a-input placeholder="请输入是否叶子节点" v-decorator="['isleaf', {}]" />-->
          <a-select placeholder="请选择" v-decorator="['isleaf',{}]">
            <a-select-option  :value="0">否</a-select-option>
            <a-select-option  :value="1">是</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="数量">
          <a-input-number v-decorator="[ 'number', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="位置">
          <a-input placeholder="请输入位置" v-decorator="['position', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="备注">
          <a-input placeholder="请输入备注" v-decorator="['disp', {}]" />
        </a-form-item>
        <!--<a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="删除标识0-正常,1-已删除">
          <a-input placeholder="请输入删除标识0-正常,1-已删除" v-decorator="['delFlag', {}]" />
        </a-form-item>-->
		
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction,getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "MBomModal",
    data () {
      return {
        /*物料信息列表*/
        materialList: [],
        title:"操作",
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
        form: this.$form.createForm(this),
        validatorRules:{
        },
        url: {
          add: "/system/mBom/add",
          edit: "/system/mBom/edit",
        },
      }
    },
    created () {
    },
    mounted () {
      this.getMaterialList ()
    },
    methods: {
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
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'materialencode','parentid','version','childid','childversion','level','isleaf','number','position','disp','delFlag'))
		  //时间格式化
        });

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
        })
      },
      handleCancel () {
        this.close()
      },


    }
  }
</script>

<style lang="less" scoped>

</style>