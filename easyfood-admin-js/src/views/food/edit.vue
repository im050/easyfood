<template>
  <div class="app-container">
    <el-page-header class="page-header" @back="goBack" :content="headerTitle"></el-page-header>
    <el-form :model="foodForm" ref="foodForm" label-width="100px">
      <div class="form-wrapper">
        <div class="form-left">
          <h2>基本信息</h2>
        </div>
        <div class="form-right">
          <el-form-item label="分类" required prop="menuId">
            <el-select v-model="foodForm.menuId" placeholder="请选择活动区域">
              <el-option v-for="menu in menus" :key="menu.id" :label="menu.name" :value="menu.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="名称" required prop="name">
            <el-input v-model="foodForm.name"></el-input>
          </el-form-item>
          <el-form-item label="描述" prop="description">
            <el-input
              type="textarea"
              placeholder="请输入内容"
              v-model="foodForm.description"
              maxlength="30"
              show-word-limit
            ></el-input>
          </el-form-item>
          <el-form-item label="商品图片" prop="pic" required>
            <el-upload
              class="avatar-uploader"
              :headers="uploadHeaders"
              :action="uploadAction"
              :show-file-list="false"
              :on-success="handleUploadSuccess"
            >
              <img v-if="foodForm.pic" :src="foodForm.pic" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="价格" prop="price" required>
            <el-input v-model="foodForm.price">
              <template slot="append">元</template>
            </el-input>
          </el-form-item>
          <el-form-item label="上架" prop="enabled">
            <el-switch v-model="foodForm.enabled"></el-switch>
          </el-form-item>
          <el-form-item label="商品单位" prop="unit">
            <el-input v-model="foodForm.unit"></el-input>
          </el-form-item>
          <el-form-item label="库存" prop="stock" required>
            <el-input v-model="foodForm.stock"></el-input>
          </el-form-item>
          <el-form-item label="最大库存" prop="maxStock" required>
            <el-input v-model="foodForm.maxStock"></el-input>
          </el-form-item>
          <el-form-item label="次日置满库存" prop="autoUpdateStock">
            <el-switch v-model="foodForm.autoUpdateStock"></el-switch>
          </el-form-item>
        </div>
      </div>
      <div class="form-wrapper">
        <div class="form-left">
          <h2>商品属性</h2>
        </div>
        <div class="form-right">
          <el-button
            style="margin-bottom: 15px;"
            type="primary"
            @click="addAttrInput"
            plain
            size="small"
            icon="el-icon-plus"
          >添加属性</el-button>
          <div class="attrs" v-for="(attr, index) in foodForm.attrs" :key="index">
            <div class="parent-attr">
              <el-form inline style="padding-bottom: 0 !important;">
                <el-form-item label="属性名称">
                  <el-input
                    :rules="{
      required: true, message: '属性名不能为空', trigger: 'blur'
    }"
                    :prop="'foodForm.attrs['+index+'].name'"
                    placeholder="输入属性名称"
                    style="width: 120px;"
                    v-model="foodForm.attrs[index].name"
                  ></el-input>
                </el-form-item>
                <el-form-item label="必选" prop="required">
                  <el-switch v-model="foodForm.attrs[index].required"></el-switch>
                </el-form-item>
                <el-form-item label="多选" prop="multiple">
                  <el-switch v-model="foodForm.attrs[index].multiple"></el-switch>
                </el-form-item>
                <el-form-item label="">
                  <el-button
                    type="danger"
                    @click="removeAttrInput(index)"
                    icon="el-icon-delete"
                    size="mini"
                  >移除属性</el-button>
                </el-form-item>
                <div class="sub-attrs">
                  <el-form-item
                    v-for="(subAttr, subIndex) in foodForm.attrs[index].attrs"
                    :key="subIndex"
                    :label="'选项' + (subIndex+1)"
                    prop="required"
                  >
                    <el-input
                      :rules="{
      required: true, message: '选项名不能为空', trigger: 'blur'
    }"
                      :prop="'foodForm.attrs['+index+'].attrs['+subIndex+'].name'"
                      style="width: 220px;"
                      placeholder="请输入选项名称"
                      v-model="subAttr.name"
                    >
                      <template slot="prepend">名称</template>
                    </el-input>
                    <el-input style="width: 220px;" placeholder="0.00" v-model="subAttr.markup">
                      <template slot="prepend">加价</template>
                      <template slot="append">元</template>
                    </el-input>
                    <el-button
                      type="danger"
                      @click="removeSubAttrInput(index, subIndex)"
                      icon="el-icon-delete"
                    ></el-button>
                  </el-form-item>
                  <div class="add-sub-attr">
                    <el-button
                      type="primary"
                      @click="addSubAttrInput(index)"
                      plain
                      circle
                      icon="el-icon-plus"
                    ></el-button>
                  </div>
                </div>
              </el-form>
            </div>
          </div>
          <el-button
            style="margin-bottom: 15px;"
            type="primary"
            @click="addAttrInput"
            plain
            v-if="foodForm.attrs.length > 0"
            size="small"
            icon="el-icon-plus"
          >添加属性</el-button>
        </div>
      </div>
      <div class="form-wrapper submit">
        <div class="form-left"></div>
        <div class="form-right">
          <el-form-item>
            <el-button @click="goBack">取消</el-button>
            <el-button
              :loading="isSubmitted"
              type="primary"
              @click="formType == 'add' ? addFood() : editFood()"
            >保存</el-button>
          </el-form-item>
        </div>
      </div>
    </el-form>
  </div>
</template>

<script>
import { getBase64Token } from "@/utils/auth"; // get token from cookie
import { getFood, addFood, editFood } from "@/api/food";
import { getList } from "@/api/menu";
export default {
  data() {
    return {
      isSubmitted: false,
      uploadAction: "",
      uploadHeaders: {},
      foodForm: {
        name: null,
        description: null,
        enabled: true,
        price: 0,
        stock: 10000,
        maxStock: 10000,
        autoUpdateStock: true,
        shopId: 0,
        menuId: 0,
        attrs: [],
        pic: null,
        unit: "份",
        attrs: []
      },
      formType: "edit",
      menus: null,
      headerTitle: "编辑商品"
    };
  },
  mounted() {
    this.uploadHeaders = {
      "X-EasyFood-Token": getBase64Token(this.$store.state.user.currentShop)
    };
    this.uploadAction = process.env.VUE_APP_BASE_API + "/food/upload";
    if (!this.$route.params.hasOwnProperty("id")) {
      this.headerTitle = "新增商品";
      this.formType = "add";
      this.foodForm.menuId = this.$route.params.menuId;
      this.foodForm.shopId = this.$store.state.user.currentShop;
    } else {
      this.formType = "edit";
      this.getFoodDetail();
    }
    this.menus = this.$route.params.menus;
    if (this.menus == null || this.menus.length <= 0) {
      getList({ shopId: this.$store.state.user.currentShop }).then(res => {
        this.menus = res.data;
        this.pickDefaultMenuId();
      });
    } else {
      this.pickDefaultMenuId();
    }
  },
  methods: {
    handleUploadSuccess(data) {
      this.foodForm.pic = data.data.url;
      this.$message({
        type: "success",
        message: "上传图片成功"
      });
    },
    addFood() {
      this.isSubmitted = true;
      this.$refs["foodForm"].validate(valid => {
        if (valid) {
          addFood(this.foodForm).then(res => {
            this.isSubmitted = false;
            this.$router.push({
              name: "Food",
              params: { menuId: this.foodForm.menuId }
            });
          });
        } else {
          this.isSubmitted = false;
          return false;
        }
      });
    },
    addSubAttrInput(attrIndex) {
      this.foodForm.attrs[attrIndex].attrs.push({
        id: null,
        name: "",
        parentId: this.foodForm.attrs[attrIndex].id,
        shopId: this.$store.state.user.currentShop,
        markup: 0,
        multiple: false,
        required: false
      });
    },
    addAttrInput() {
      this.foodForm.attrs.push({
        id: null,
        name: "",
        parentId: 0,
        shopId: this.$store.state.user.currentShop,
        markup: 0,
        attrs: [
          {
            id: null,
            name: "",
            parentId: null,
            shopId: this.$store.state.user.currentShop,
            markup: 0,
            multiple: false,
            required: false
          }
        ],
        multiple: false,
        required: false
      });
    },
    removeAttrInput(index) {
      this.foodForm.attrs.splice(index, 1);
    },
    removeSubAttrInput(attrIndex, subIndex) {
      this.foodForm.attrs[attrIndex].attrs.splice(subIndex, 1);
    },
    async editFood() {
      this.isSubmitted = true;
      const { data } = await editFood(this.foodForm);
      this.isSubmitted = false;
      this.$router.push({
        name: "Food",
        params: { menuId: this.foodForm.menuId }
      });
    },
    goBack() {
      this.$router.push({
        name: "Food",
        params: { menuId: this.foodForm.menuId }
      });
    },
    pickDefaultMenuId() {
      if (this.foodForm.menuId == 0) this.foodForm.menuId = this.menus[0].id;
    },
    async getFoodDetail() {
      let foodId = this.$route.params.id;
      let shopId = this.$store.state.user.currentShop;
      const { data } = await getFood(shopId, foodId).catch(e => {
        console.log(e);
      });
      // 初始化数组容器
      if (!data.hasOwnProperty("attrs")) {
        data.attrs = [];
      }
      for (let i in data.attrs) {
        if (!data.attrs[i].hasOwnProperty("attrs")) {
          data.attrs[i].attrs = [];
        }
      }
      this.foodForm = data;
    }
  }
};
</script>

<style lang="scss" scoped>
.el-input,
.el-textarea {
  width: 280px;
}
.el-form {
  padding-bottom: 90px !important;
}

.parent-attr {
  margin-bottom: 30px;
  padding-top: 10px;
  .sub-attrs {
    border-radius: 5px;
    background: rgba(0, 0, 0, 0.03);
    padding: 20px;
    margin-bottom: 20px;
  }
}
</style>

