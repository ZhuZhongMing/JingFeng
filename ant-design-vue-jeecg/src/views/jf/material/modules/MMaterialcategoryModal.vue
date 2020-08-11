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

        <a-form-item label="分类名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'mcategoryname', validatorRules.mcategoryname]" placeholder="请输入分类名称"></a-input>
        </a-form-item>
          
        <!--<a-form-item label="是否停止使用" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['isstop']" :trigger-change="true" dictCode="IsStop" placeholder="请选择是否停止使用"/>
        </a-form-item>-->
          
        <!--<a-form-item label="级别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number :min="0" v-decorator="[ 'level', validatorRules.level]" placeholder="请输入级别" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="上级编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          &lt;!&ndash;<a-input v-decorator="[ 'parentencode', validatorRules.parentencode]" placeholder="请输入上级编码"></a-input>&ndash;&gt;
          <a-select placeholder="请选择" v-decorator="['parentencode', validatorRules.parentencode]">
            <a-select-option :value="'null'">无上级编码</a-select-option>
            <a-select-option v-if="material.id != model.id"  v-for="(material,index) in list" :key="material.id" :value="material.id">{{material.id}}</a-select-option>
            <a-select-option v-if="material.id == model.id" disabled  v-for="(material,index) in list" :key="material.id" :value="material.id">{{material.id}}</a-select-option>
          </a-select>
        </a-form-item>-->
          
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['disp']" rows="4" placeholder="请输入备注"/>
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
    name: "MMaterialcategoryModal",
    components: { 
      JDictSelectTag,
    },
    data () {
      return {
        /*物料分类列表*/
        list:[],
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
          mcategoryname:{
            rules: [
              { required: true, message: '请输入分类名称!' },
            ]
          },
          isstop:{},
          level:{},
          parentencode:{
            rules: [
              { required: true, message: '请选择上级编码!' },
            ]
          },
          disp:{},
        },
        url: {
          add: "/system/mMaterialcategory/add",
          edit: "/system/mMaterialcategory/edit",
        }
     
      }
    },
    created () {
    },
    mounted () {
      this.getList()
    },
    methods: {
      /*获取物料信息列表*/
      getList() {
        getAction("/system/mMaterialcategory/getMaterialList",null).then((res)=>{
          if(res.success){
            this.list.splice(0)
            this.list = res.result
          }
        })
      },
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'mcategoryname','isstop','level','parentencode','disp'))
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
        this.form.setFieldsValue(pick(row,'mcategoryname','isstop','level','parentencode','disp'))
      }
      
    }
  }
</script>