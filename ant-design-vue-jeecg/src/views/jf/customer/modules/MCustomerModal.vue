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

        <!--<a-row>

            <a-form-item label="客户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'cname', validatorRules.cname]" placeholder="请输入客户名称"></a-input>
            </a-form-item>

            <a-form-item label="简称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'abcname', validatorRules.abcname]" placeholder="请输入简称"></a-input>
            </a-form-item>

            <a-form-item label="检索码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'indexcode', validatorRules.indexcode]" placeholder="请输入检索码"></a-input>
            </a-form-item>


            <a-form-item label="客户类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'ctype', validatorRules.ctype]" placeholder="请输入客户类型"></a-input>
            </a-form-item>

            <a-form-item label="是否客户" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['isclient']" :trigger-change="true" dictCode="IsClient" placeholder="请选择是否客户"/>
            </a-form-item>

            <a-form-item label="是否供应商" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['issupplier']" :trigger-change="true" dictCode="IsSupplier" placeholder="请选择是否供应商"/>
            </a-form-item>

            <a-form-item label="客户分类1" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'categorytype', validatorRules.categorytype]" placeholder="请输入客户分类1"></a-input>
            </a-form-item>

            <a-form-item label="所属区域" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'carea', validatorRules.carea]" placeholder="请输入所属区域"></a-input>
            </a-form-item>

            <a-form-item label="首次合作时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择首次合作时间" v-decorator="[ 'firstcodate', validatorRules.firstcodate]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>

            <a-form-item label="客户分类2" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'categorytype2', validatorRules.categorytype2]" placeholder="请输入客户分类2"></a-input>
            </a-form-item>

            <a-form-item label="是否外协" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['isassist']" :trigger-change="true" dictCode="IsAssist" placeholder="请选择是否外协"/>
            </a-form-item>

            <a-form-item label="客户地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'caddress', validatorRules.caddress]" placeholder="请输入客户地址"></a-input>
            </a-form-item>

            <a-form-item label="邮政编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'postcode', validatorRules.postcode]" placeholder="请输入邮政编码"></a-input>
            </a-form-item>

            <a-form-item label="法人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'cmanager', validatorRules.cmanager]" placeholder="请输入法人"></a-input>
            </a-form-item>

            <a-form-item label="联系人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'contactperson', validatorRules.contactperson]" placeholder="请输入联系人"></a-input>
            </a-form-item>

            <a-form-item label="联系人电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'telephone', validatorRules.telephone]" placeholder="请输入联系人电话"></a-input>
            </a-form-item>

            <a-form-item label="邮箱" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'email', validatorRules.email]" placeholder="请输入邮箱"></a-input>
            </a-form-item>

            <a-form-item label="传真" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'fax', validatorRules.fax]" placeholder="请输入传真"></a-input>
            </a-form-item>

            <a-form-item label="银行" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'bank', validatorRules.bank]" placeholder="请输入银行"></a-input>
            </a-form-item>

            <a-form-item label="开户账号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'account', validatorRules.account]" placeholder="请输入开户账号"></a-input>
            </a-form-item>

            <a-form-item label="付款方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'paymentkind', validatorRules.paymentkind]" placeholder="请输入付款方式"></a-input>
            </a-form-item>

            <a-form-item label="付款周期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'paymentcircle', validatorRules.paymentcircle]" placeholder="请输入付款周期" style="width: 100%"/>
            </a-form-item>

            <a-form-item label="结算货币" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'currency', validatorRules.currency]" placeholder="请输入结算货币"></a-input>
            </a-form-item>

            <a-form-item label="税号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'taxno', validatorRules.taxno]" placeholder="请输入税号"></a-input>
            </a-form-item>

            <a-form-item label="营业执照" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'license', validatorRules.license]" placeholder="请输入营业执照"></a-input>
            </a-form-item>

            <a-form-item label="信用等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'creditgrade', validatorRules.creditgrade]" placeholder="请输入信用等级"></a-input>
            </a-form-item>

            <a-form-item label="信用额度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'creditlimit', validatorRules.creditlimit]" placeholder="请输入信用额度" style="width: 100%"/>
            </a-form-item>

            <a-form-item label="业务员" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'salesman', validatorRules.salesman]" placeholder="请输入业务员"></a-input>
            </a-form-item>

            <a-form-item label="启用状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'cstatus', validatorRules.cstatus]" placeholder="请输入启用状态"></a-input>
            </a-form-item>

            <a-form-item label="企业网址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'website', validatorRules.website]" placeholder="请输入企业网址"></a-input>
            </a-form-item>

            <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['disp']" rows="4" placeholder="请输入备注"/>
            </a-form-item>

            <a-form-item label="描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['description']" rows="4" placeholder="请输入描述"/>
            </a-form-item>

        </a-row>-->

        <a-row>
          <a-col :span="8">
            <a-form-item label="客户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'cname', validatorRules.cname]" placeholder="请输入客户名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="简称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'abcname', validatorRules.abcname]" placeholder="请输入简称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="首次合作时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择首次合作时间" v-decorator="[ 'firstcodate', validatorRules.firstcodate]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
        </a-row>
        <!--<a-row>
          <a-col :span="8">
            <a-form-item label="客户类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'ctype', validatorRules.ctype]" placeholder="请输入客户类型"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="是否客户" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['isclient']" :trigger-change="true" dictCode="IsClient" placeholder="请选择是否客户"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="是否供应商" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['issupplier']" :trigger-change="true" dictCode="IsSupplier" placeholder="请选择是否供应商"/>
            </a-form-item>
          </a-col>
        </a-row>-->
        <!--<a-row>
          <a-col :span="8">
            <a-form-item label="客户分类1" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'categorytype', validatorRules.categorytype]" placeholder="请输入客户分类1"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="所属区域" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'carea', validatorRules.carea]" placeholder="请输入所属区域"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="检索码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'indexcode', validatorRules.indexcode]" placeholder="请输入检索码"></a-input>
            </a-form-item>
          </a-col>
        </a-row>-->
        <!--<a-row>
          <a-col :span="8">
            <a-form-item label="客户分类2" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'categorytype2', validatorRules.categorytype2]" placeholder="请输入客户分类2"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="是否外协" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['isassist']" :trigger-change="true" dictCode="IsAssist" placeholder="请选择是否外协"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="客户地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'caddress', validatorRules.caddress]" placeholder="请输入客户地址"></a-input>
            </a-form-item>
          </a-col>
        </a-row>-->
        <a-row>
          <a-col :span="8">
            <a-form-item label="联系人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'contactperson', validatorRules.contactperson]" placeholder="请输入联系人"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="联系人电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'telephone', validatorRules.telephone]" placeholder="请输入联系人电话"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="邮箱" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'email', validatorRules.email]" placeholder="请输入邮箱"></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <!--<a-row>
          <a-col :span="8">
            <a-form-item label="邮政编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'postcode', validatorRules.postcode]" placeholder="请输入邮政编码"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="法人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'cmanager', validatorRules.cmanager]" placeholder="请输入法人"></a-input>
            </a-form-item>
          </a-col>
          &lt;!&ndash;<a-col :span="8">
            <a-form-item label="企业网址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'website', validatorRules.website]" placeholder="请输入企业网址"></a-input>
            </a-form-item>
          </a-col>&ndash;&gt;
        </a-row>-->
        <!--<a-row>
          <a-col :span="8">
            <a-form-item label="银行" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'bank', validatorRules.bank]" placeholder="请输入银行"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="开户账号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'account', validatorRules.account]" placeholder="请输入开户账号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="付款方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'paymentkind', validatorRules.paymentkind]" placeholder="请输入付款方式"></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="8">
            <a-form-item label="付款周期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'paymentcircle', validatorRules.paymentcircle]" placeholder="请输入付款周期" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="结算货币" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'currency', validatorRules.currency]" placeholder="请输入结算货币"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="税号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'taxno', validatorRules.taxno]" placeholder="请输入税号"></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="8">
            <a-form-item label="营业执照" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'license', validatorRules.license]" placeholder="请输入营业执照"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="信用等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'creditgrade', validatorRules.creditgrade]" placeholder="请输入信用等级"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="信用额度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'creditlimit', validatorRules.creditlimit]" placeholder="请输入信用额度" style="width: 100%"/>
            </a-form-item>
          </a-col>
        </a-row>-->
        <!--<a-row>
          <a-col :span="8">
            <a-form-item label="业务员" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'salesman', validatorRules.salesman]" placeholder="请输入业务员"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="启用状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'cstatus', validatorRules.cstatus]" placeholder="请输入启用状态"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="传真" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'fax', validatorRules.fax]" placeholder="请输入传真"></a-input>
            </a-form-item>
          </a-col>
        </a-row>-->
        <a-row>
          <a-col :span="8">
            <a-form-item label="企业网址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'website', validatorRules.website]" placeholder="请输入企业网址"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['description']" rows="4" placeholder="请输入描述"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['disp']" rows="4" placeholder="请输入备注"/>
            </a-form-item>
          </a-col>
        </a-row>


        <!--<a-form-item label="删除标识" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['delFlag']" :trigger-change="true" dictCode="del_flag" placeholder="请选择删除标识"/>
        </a-form-item>-->
          
        
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  
  export default {
    name: "MCustomerModal",
    components: { 
      JDate,
      JDictSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        // width:800,
        width: window.screen.width * 0.7,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 18 },
        },

        confirmLoading: false,
        validatorRules:{
        cname:{},
        abcname:{},
        indexcode:{},
        issupplier:{},
        isclient:{},
        ctype:{},
        categorytype:{},
        carea:{},
        firstcodate:{},
        categorytype2:{},
        isassist:{},
        caddress:{},
        postcode:{},
        cmanager:{},
        contactperson:{},
        telephone:{},
        email:{},
        fax:{},
        bank:{},
        account:{},
        paymentkind:{},
        paymentcircle:{},
        currency:{},
        taxno:{},
        license:{},
        creditgrade:{},
        creditlimit:{},
        salesman:{},
        cstatus:{},
        description:{},
        disp:{},
        website:{},
        delFlag:{},
        },
        url: {
          add: "/system/mCustomer/add",
          edit: "/system/mCustomer/edit",
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
          this.form.setFieldsValue(pick(this.model,'cname','abcname','indexcode','issupplier','isclient','ctype','categorytype','carea','firstcodate','categorytype2','isassist','caddress','postcode','cmanager','contactperson','telephone','email','fax','bank','account','paymentkind','paymentcircle','currency','taxno','license','creditgrade','creditlimit','salesman','cstatus','description','disp','website','delFlag'))
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
        this.form.setFieldsValue(pick(row,'cname','abcname','indexcode','issupplier','isclient','ctype','categorytype','carea','firstcodate','categorytype2','isassist','caddress','postcode','cmanager','contactperson','telephone','email','fax','bank','account','paymentkind','paymentcircle','currency','taxno','license','creditgrade','creditlimit','salesman','cstatus','description','disp','website','delFlag'))
      }
      
    }
  }
</script>