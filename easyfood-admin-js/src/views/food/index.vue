<template>
  <div class="app-container">
    <el-container style="height: calc(100vh - 300px);">
      <el-header>
        <div class="filter-container">
          <el-input
            v-model="menuForm.name"
            placeholder="菜单名称"
            style="width: 200px;"
            class="filter-item"
          />
          <el-input
            v-model="menuForm.description"
            placeholder="菜单描述"
            style="width: 300px;"
            class="filter-item"
          />
          <el-button
            class="filter-item"
            style="margin-left: 10px;"
            type="primary"
            icon="el-icon-edit"
            @click="handleCreate"
          >新增</el-button>
        </div>
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
              >{{ menu.name }}</li>
            </ul>
          </el-scrollbar>
        </el-aside>
        <el-container>
          <el-main>
            <!-- Note that row-key is necessary to get a correct row order. -->
            <el-table
              ref="dragTable"
              v-loading="listLoading"
              :data="menus"
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
              <el-table-column align="center" label="名称" width="200">
                <template slot-scope="scope">
                  <span>{{ scope.row.name }}</span>
                </template>
              </el-table-column>
              <el-table-column align="center" label="描述">
                <template slot-scope="scope">
                  <span>{{ scope.row.description }}</span>
                </template>
              </el-table-column>
              <el-table-column align="center" label="拖动" width="80">
                <template slot-scope="{}">
                  <svg-icon class="drag-handler" icon-class="drag"/>
                </template>
              </el-table-column>
            </el-table>
          </el-main>
        </el-container>
      </el-container>
    </el-container>
    <div class="show-d">
      <el-tag>The default order :</el-tag>
      {{ oldList }}
    </div>
    <div class="show-d">
      <el-tag>The after dragging order :</el-tag>
      {{ newList }}
    </div>
  </div>
</template>

<script>
import { getList, add } from "@/api/menu";
import Sortable from "sortablejs";

export default {
  name: "Food",
  data() {
    return {
      menus: null,
      menuActiveId: 0,
      listLoading: true,
      menuForm: {
        name: "",
        description: ""
      },
      sortable: null,
      oldList: [],
      newList: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    async handleCreate() {
      this.listLoading = true;
      this.menuForm.shopId = this.$store.state.user.currentShop;
      const { data } = await add(this.menuForm);
      this.menus.push(data);
      this.listLoading = false;
    },
    async getList() {
      this.listLoading = true;
      const { data } = await getList({
        shopId: this.$store.state.user.currentShop
      });
      this.menus = data;
      this.listLoading = false;
      this.oldList = this.menus.map(v => v.id);
      this.newList = this.oldList.slice();
      this.$nextTick(() => {
        this.setSort();
        if (this.menus.length > 0) this.setActive(this.menus[0].id);
      });
    },
    // 高亮菜单
    setActive(menuId) {
      this.menuActiveId = menuId;
    },
    // 更换菜单
    changeMenu(menuId) {
      this.setActive(menuId);
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
          const targetRow = this.menus.splice(evt.oldIndex, 1)[0];
          this.menus.splice(evt.newIndex, 0, targetRow);
          // for show the changes, you can delete in you code
          const tempIndex = this.newList.splice(evt.oldIndex, 1)[0];
          this.newList.splice(evt.newIndex, 0, tempIndex);
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
      &.menu-item-active,
      &:hover {
        color: $main-color;
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
