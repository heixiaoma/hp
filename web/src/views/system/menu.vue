<template>
  <div class="app-container">

    <el-button icon="el-icon-plus"  style="margin-bottom: 10px" type="primary" @click="handleAddMenu">添加菜单</el-button>
    <el-table
      :data="menuList"
      style="width: 100%;margin-bottom: 20px;"
      row-key="id"
      border
      default-expand-all
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column
        prop="title"
        label="菜单名字"
      />
      <el-table-column
        prop="name"
        label="名字"
      />
      <el-table-column
        prop="component"
        label="组件"
      />
      <el-table-column
        prop="path"
        label="路径"
      />
      <el-table-column
        prop="redirect"
        label="重定向"
      />
      <el-table-column
        prop="sort"
        label="排序"
      />
      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="handleEdit(scope)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDelete(scope)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="dialogVisible" :title="dialogType==='edit'?'编辑菜单':'添加菜单'">
      <el-form :model="menu" label-width="80px" label-position="left">

        <el-form-item label="上级菜单">
          <el-cascader
            v-model="parentId"
            :options="options"
            :props="{ checkStrictly: true }"
            clearable
          />
        </el-form-item>
        <el-form-item label="路径">
          <el-input v-model="menu.path" placeholder="Path" />
        </el-form-item>

        <el-form-item label="组件">
          <el-input v-model="menu.component" placeholder="Component" />
        </el-form-item>

        <el-form-item label="重定向">
          <el-input v-model="menu.redirect" placeholder="redirect" />
        </el-form-item>

        <el-form-item label="名字">
          <el-input v-model="menu.name" placeholder="name" />
        </el-form-item>

        <el-form-item label="菜单名字">
          <el-input v-model="menu.title" placeholder="title" />
        </el-form-item>

        <el-form-item label="图标">
          <el-input v-model="menu.icon" placeholder="icon" />
        </el-form-item>

        <el-form-item label="排序">
          <el-input v-model="menu.sort" placeholder="icon" />
        </el-form-item>
      </el-form>
      <div style="text-align:right;">
        <el-button type="danger" @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="confirmMenu">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { deepClone } from '@/utils'
import { getListTop, addMenu, getMenuList, updateMenu, deleteMenu } from '@/api/menu'

const defaultMenu = {
  parentId: '',
  icon: '',
  title: '',
  name: '',
  redirect: '',
  component: '',
  sort: 0,
  path: ''
}

export default {

  data() {
    return {
      options: [],
      parentId: '',
      menu: Object.assign({}, defaultMenu),
      menuList: [],
      dialogVisible: false,
      dialogType: 'new',
      checkStrictly: false,
      defaultProps: {
        children: 'children',
        label: 'title'
      }
    }
  },
  created() {
    this.getListTop()
    this.getMenuList()
  },
  methods: {
    async getListTop() {
      const res = await getListTop()
      this.options = res.data
    },
    async getMenuList() {
      const res = await getMenuList()
      this.menuList = res.data
    },
    handleAddMenu() {
      this.menu = Object.assign({}, defaultMenu)
      this.dialogType = 'new'
      this.dialogVisible = true
    },
    handleEdit(scope) {
      this.dialogType = 'edit'
      this.dialogVisible = true
      this.checkStrictly = true
      this.menu = deepClone(scope.row)
      this.parentId = [this.menu.parentId]
    },
    handleDelete({ $index, row }) {
      this.$confirm('确定要删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async() => {
          await deleteMenu(row.id)
          this.$message({
            type: 'success',
            message: '删除成功'
          })
          this.getMenuList()
        })
        .catch(err => {
          console.error(err)
        })
    },
    async confirmMenu() {
      this.menu.parentId = this.parentId[this.parentId.length - 1]
      const isEdit = this.dialogType === 'edit'
      if (isEdit) {
        await updateMenu(this.menu)
        this.$notify({
          title: 'Success',
          dangerouslyUseHTMLString: true,
          message: `编辑成功`,
          type: 'success'
        })
      } else {
        const { data } = await addMenu(this.menu)
        console.log(data)
        this.$notify({
          title: 'Success',
          dangerouslyUseHTMLString: true,
          message: `编辑成功`,
          type: 'success'
        })
      }
      this.getListTop()
      this.getMenuList()
      this.menu = defaultMenu
      this.dialogVisible = false
    }
  }
}
</script>

<style lang="scss" scoped>
  .app-container {
    .roles-table {
      margin-top: 30px;
    }
    .permission-tree {
      margin-bottom: 30px;
    }
  }
</style>
