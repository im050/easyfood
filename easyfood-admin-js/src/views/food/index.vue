<template>
  <div class="app-container">
    <el-container style="height: calc(100vh - 300px);">
      <el-header>
        <el-tabs v-model="tabName">
          <el-tab-pane label="全部商品" name="first"></el-tab-pane>
          <el-tab-pane label="库存不足" name="second"></el-tab-pane>
          <el-tab-pane label="已下架" name="third"></el-tab-pane>
        </el-tabs>
      </el-header>
      <el-container>
        <el-aside width="185px">
          <el-scrollbar class="menu-scroller" style="height: 100%">
            <ul class="menu-items" ref="dragMenus">
              <li
                @click="changeMenu(menu.id)"
                class="menu-item"
                :class="menu.id == menuActiveId ? 'menu-item-active' : ''"
                v-for="menu in menus"
                :key="menu.id"
              >
                <i class="el-icon-sort"></i>
                <span>{{ menu.name }}</span>
                <i @click.stop.prevent="editMenu(menu)" class="el-icon-edit-outline"></i>
              </li>
            </ul>
          </el-scrollbar>
        </el-aside>
        <el-container>
          <el-main>
            <div style="margin-bottom: 20px; text-align: right;">
              <el-button type="primary" icon="el-icon-edit-outline" @click="addFood">新建商品</el-button>
            </div>
            <!-- Note that row-key is necessary to get a correct row order. -->
            <el-table
              ref="dragTable"
              v-loading="listLoading"
              :data="foods"
              row-key="id"
              fit
              highlight-current-row
              style="width: 100%"
            >
              <el-table-column align="center" label="ID" width="65">
                <template slot-scope="scope">
                  <span>{{ scope.row.id }}</span>
                </template>
              </el-table-column>
              <el-table-column align="center" label="商品" width="80">
                <template slot-scope="scope">
                  <el-popover placement="right-start" width="200" trigger="hover">
                    <template scope="">
                      <el-image style="width: 100%; " :src="scope.row.pic" fit="fill"></el-image>
                      <el-upload
                        style="width: 100%; text-align: center; margin-top: 8px;"
                        action="https://jsonplaceholder.typicode.com/posts/"
                      >
                        <el-button size="middle" type="primary">更换</el-button>
                      </el-upload>
                    </template>
                    <el-image
                      slot="reference"
                      style="width: 54px; height: 54px"
                      :src="scope.row.pic"
                      fit="fill"
                    ></el-image>
                  </el-popover>
                </template>
              </el-table-column>
              <el-table-column align="center" label="" width="110">
                <template slot-scope="scope">
                  <span>{{ scope.row.name }}</span>
                </template>
              </el-table-column>
              <el-table-column align="center" label="价格" width="80">
                <template slot-scope="scope">
                  <span>￥{{ scope.row.price }}</span>
                </template>
              </el-table-column>
              <el-table-column align="center" label="库存" width="110">
                <template slot-scope="scope">
                  <span>{{ scope.row.stock }}</span>
                </template>
              </el-table-column>
              <el-table-column align="center" label="描述">
                <template slot-scope="scope">
                  <span>{{ scope.row.description }}</span>
                </template>
              </el-table-column>
              <el-table-column align="center" label="" width="80">
                <template slot-scope="scope">
                  <el-button size="small" @click="editFood(scope.row.id)" round>编辑</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-main>
        </el-container>
      </el-container>
    </el-container>
    <el-dialog
      v-loading="editMenuLoading"
      :title="menuFormType == 'add' ? '新增分类' : '编辑分类'"
      :visible.sync="dialogMenuVisible"
    >
      <el-form label-position="left" label-width="80px">
        <el-form-item label="分类名称">
          <el-input v-model="menuForm.name"></el-input>
        </el-form-item>
        <el-form-item label="描述信息">
          <el-input v-model="menuForm.description"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button
          style="float:left"
          @click="deleteMenu(menuForm.id)"
          :visible="menuFormType != 'add'"
          type="danger"
        >删除分类</el-button>
        <el-button @click="closeMenuDialog">取 消</el-button>
        <el-button @click="confirmEditMenu" type="primary">
          <template v-if="menuFormType == 'add'">新 增</template>
          <template v-else>保 存</template>
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getList, addMenu, editMenu, delMenu, sortUpdate } from "@/api/menu";
import { getFoods } from "@/api/food";
import Sortable from "sortablejs";
import { setTimeout, clearTimeout } from "timers";
import { close } from "fs";

export default {
  name: "Food",
  data() {
    return {
      menus: [],
      foods: [],
      tabName: "first",
      menuActiveId: 0,
      editMenuLoading: false,
      listLoading: true,
      dialogMenuVisible: false,
      menuFormType: "add",
      menuForm: {
        name: "",
        description: ""
      },
      sortable: null,
      oldList: [],
      newList: [],
      sortUpdateTimeout: null //用于延迟更新拖动顺序
    };
  },
  created() {
    this.getList().then(res => {
      //返回后选中之前的分类
      if (this.$route.params.menuId) {
        this.changeMenu(this.$route.params.menuId);
      }
    });
  },
  methods: {
    async handleCreate() {
      this.listLoading = true;
      this.menuForm.shopId = this.$store.state.user.currentShop;
      const { data } = await addMenu(this.menuForm);
      this.menus.push(data);
      this.listLoading = false;
    },
    // 获取菜单列表
    async getList() {
      const { data } = await getList({
        shopId: this.$store.state.user.currentShop
      });
      this.menus = data;
      this.oldList = this.menus.map(v => v.id);
      this.newList = this.oldList.slice();
      this.$nextTick(() => {
        this.setSort();
        let firstMenuId = this.menus[0].id;
        if (this.menus.length > 0) this.changeMenu(firstMenuId);
      });
    },
    openMenuDialog() {
      this.dialogMenuVisible = true;
    },
    editFood(foodId) {
      this.$router.push({
        name: "FoodEdit",
        params: { id: foodId, menus: this.menus }
      });
    },
    addFood() {
      this.$router.push({
        name: "FoodAdd",
        params: { menuId: this.menuActiveId, menus: this.menus }
      });
    },
    closeMenuDialog() {
      this.dialogMenuVisible = false;
    },
    // 获取商品列表
    async getFoods(menuId) {
      this.listLoading = true;
      const { data } = await getFoods({
        shopId: this.$store.state.user.currentShop,
        menuId
      }).catch(e => {
        this.listLoading = false;
      });
      this.foods = data;
      this.listLoading = false;
    },
    // 高亮菜单
    setActive(menuId) {
      this.menuActiveId = menuId;
    },
    // 更换菜单
    changeMenu(menuId) {
      this.setActive(menuId);
      this.getFoods(menuId);
    },
    // 编辑菜单
    editMenu(menu) {
      this.menuForm.name = menu.name;
      this.menuForm.shopId = this.$store.state.user.currentShop;
      this.menuForm.description = menu.description;
      this.menuForm.id = menu.id;
      this.menuFormType = "edit";
      this.openMenuDialog();
    },
    // 分类的删除
    deleteMenu(menuId) {
      this.$confirm(
        "是否确认删除该分类？删除分类后该分类下的所有商品也将被删除，且删除后无法复原。请谨慎操作。",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      ).then(() => {
        this.editMenuLoading = true;
        delMenu(this.$store.state.user.currentShop, this.menuId)
          .then(res => {
            this.$message({
              type: "success",
              message: "删除成功!"
            });
            this.editMenuLoading = false;
          })
          .catch(() => {
            this.editMenuLoading = false;
          });
      });
      // .catch(() => {
      //   this.$message({
      //     type: "info",
      //     message: "已取消删除"
      //   });
      // });
    },
    confirmEditMenu() {
      this.editMenuLoading = true;
      editMenu(this.menuForm)
        .then(res => {
          this.$message({
            message: "修改分类成功",
            type: "success"
          });
          this.editMenuLoading = false;
          this.closeMenuDialog();
          // 重新渲染页面数据
          for (let i in this.menus) {
            if (this.menus[i].id == this.menuForm.id) {
              this.menus[i].name = this.menuForm.name;
              this.menus[i].description = this.menuForm.description;
            }
          }
        })
        .catch(() => {
          this.editMenuLoading = false;
        });
    },
    setSort() {
      const el = this.$refs.dragMenus;
      this.sortable = Sortable.create(el, {
        ghostClass: "sortable-ghost", // Class name for the drop placeholder,
        setData: function(dataTransfer) {
          // to avoid Firefox bug
          // Detail see : https://github.com/RubaXa/Sortable/issues/1012
          dataTransfer.setData("Text", "");
        },
        onEnd: evt => {
          if (this.sortUpdateTimeout != null) {
            clearTimeout(this.sortUpdateTimeout);
            this.sortUpdateTimeout = null;
          }
          const targetRow = this.menus.splice(evt.oldIndex, 1)[0];
          this.menus.splice(evt.newIndex, 0, targetRow);
          // for show the changes, you can delete in you code
          const tempIndex = this.newList.splice(evt.oldIndex, 1)[0];
          this.newList.splice(evt.newIndex, 0, tempIndex);
          let sortParams = {
            shopId: this.$store.state.user.currentShop,
            ids: this.newList
          };
          this.sortUpdateTimeout = setTimeout(() => {
            sortUpdate(sortParams).then(res => {
              this.$message({
                message: "更新菜单分类顺序成功",
                type: "success"
              });
            });
          }, 2000);
        }
      });
    }
  }
};
</script>
<style lang="scss">
$border-color: #dfe4ee;
$main-color: #1989fa;
.menu-scroller {
  .el-scrollbar__view {
    position: absolute;
    width: 100%;
    height: 100%;
    z-index: 999;
    border-right: 1px solid $border-color;
  }
}
</style>

<style lang="scss" scoped>
$border-color: #dfe4ee;
$main-color: #1989fa;

.el-aside {
  .menu-items {
    list-style: none;
    padding-inline-start: 20px;
    margin: 0;
    li.menu-item {
      background: #fff;
      cursor: pointer;
      height: 40px;
      box-sizing: border-box;
      font-size: 14px;
      line-height: 40px;
      border: 1px solid transparent;
      border-right: none;
      position: relative;
      [class*=" el-icon-"],
      [class^="el-icon-"] {
        display: none;
      }
      &.menu-item-active,
      &:hover {
        color: $main-color;
      }
      &:hover {
        [class*=" el-icon-"],
        [class^="el-icon-"] {
          display: inline;
          position: absolute;
          top: 13px;
          &.el-icon-sort {
            left: -20px;
          }
          &.el-icon-edit-outline {
            right: 20px;
          }
        }
      }
      &.menu-item-active {
        border-top: 1px solid $border-color;
        border-bottom: 1px solid $border-color;
        margin-right: -1px;
      }
    }
  }
}

.el-main {
  padding-top: 0;
  padding-bottom: 0;
}
</style>


<style>
.sortable-ghost {
  opacity: 0.8;
  color: #1989fa !important;
  background: #dfe4ee !important;
}
</style>

<style scoped>
.icon-star {
  margin-right: 2px;
}
.drag-handler {
  width: 20px;
  height: 20px;
  cursor: pointer;
}
.show-d {
  margin-top: 15px;
}
</style>
