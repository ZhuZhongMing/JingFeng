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

        <a-form-item label="模具名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'moldname', validatorRules.moldname]" placeholder="请输入模具名称"></a-input>
        </a-form-item>
          
        <a-form-item label="模具数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'moldnum', validatorRules.moldnum]" placeholder="请输入模具数量" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="说明" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'disp', validatorRules.disp]" placeholder="请输入说明"></a-input>
        </a-form-item>
          
        <a-form-item label="模具列数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'moldcolumn', validatorRules.moldcolumn]" placeholder="请输入模具列数"></a-input>
        </a-form-item>
          
        <a-form-item label="宽度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'width', validatorRules.width]" placeholder="请输入宽度" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="步骤" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'step', validatorRules.step]" placeholder="请输入步骤" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="生产厂家" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'manufacturer', validatorRules.manufacturer]" placeholder="请输入生产厂家"></a-input>
        </a-form-item>
          
        <a-form-item label="应用产品" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'applicable', validatorRules.applicable]" placeholder="请输入应用产品"></a-input>
        </a-form-item>
          
        
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  
  export default {
    name: "RMoldModal",
    components: { 
    },
    data () {
      return {
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
        moldname:{},
        moldnum:{},
        disp:{},
        moldcolumn:{},
        width:{},
        step:{},
        manufacturer:{},
        applicable:{},
        },
        url: {
          add: "/system/rMold/add",
          edit: "/system/rMold/edit",
        }
     
      }
    },
    created () {
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
          this.form.setFieldsValue(pick(this.model,'moldname','moldnum','disp','moldcolumn','width','step','manufacturer','applicable'))
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
        this.form.setFieldsValue(pick(row,'moldname','moldnum','disp','moldcolumn','width','step','manufacturer','applicable'))
      }
      
    }
  }
</script>