<template>
  <div class="app-container">

    <el-button icon="el-icon-plus"  style="margin-bottom: 10px" type="primary" @click="handleAddRole">添加角色</el-button>
    <el-table
      :data="roleList"
      style="width: 100%;margin-bottom: 20px;"
      row-key="id"
      border
    >
      <el-table-column
        prop="id"
        label="角色ID"
      />
      <el-table-column
        prop="name"
        label="角色名字"
      />
      <el-table-column
        prop="desc"
        label="描述"
      />
      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="handleEdit(scope)">编辑</el-button>
          <el-button type="primary" size="small" @click="handleMenu(scope)">菜单权限</el-button>
          <el-button type="primary" size="small" @click="handleModule(scope)">模块权限</el-button>
          <el-button type="danger" size="small" @click="handleDelete(scope)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="dialogVisible" :title="dialogType==='edit'?'编辑角色':'添加角色'">
      <el-form :model="role" label-width="80px" label-position="left">

        <el-form-item label="角色名字">
          <el-input v-model="role.name" placeholder="name" />
        </el-form-item>

        <el-form-item label="描述">
          <el-input v-model="role.desc" placeholder="desc" />
        </el-form-item>
      </el-form>
      <div style="text-align:right;">
        <el-button type="danger" @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="confirmRole">确定</el-button>
      </div>
    </el-dialog>

    <el-drawer
      title="菜单权限"
      :visible.sync="menu_permission"
      :with-header="false"
    >
      <el-tree
        ref="menuPermission"
        class="th"
        :data="menuList"
        show-checkbox
        node-key="id"
        :default-expanded-keys="[2, 3]"
        :default-checked-keys="[5]"
        :props="defaultProps"
      />

      <div class="btn_bottom">
        <el-button @click="menu_permission=false">取消</el-button>
        <el-button type="primary" @click="menuPermissionHandler">确定</el-button>
      </div>
    </el-drawer>

    <el-drawer
      title="模块权限"
      :visible.sync="module_permission"
      :with-header="false"
    >
      <el-tree
        ref="modulePermission"
        class="th"
        :data="moduleList"
        node-key="permission"
        show-checkbox
        :default-expanded-keys="[2, 3]"
        :default-checked-keys="[5]"
        :props="defaultProps"
      />

      <div class="btn_bottom">
        <el-button @click="module_permission=false">取消</el-button>
        <el-button type="primary" @click="modulePermissionHandler">确定</el-button>
      </div>
    </el-drawer>

  </div>
</template>

<script>
import { deepClone } from '@/utils'
import { getMenuList } from '@/api/menu'
import { getModuleList } from '@/api/module'
import {
  getList,
  addRole,
  updateRole,
  deleteRole,
  updateMenuPermission,
  updateModulePermission,
  getMenuPermission,
  getModulePermission
} from '@/api/role'

const defaultRole = {
  id: '',
  name: '',
  desc: ''
}

export default {

  data() {
    return {
      menu_permission: false,
      module_permission: false,
      options: [],
      role: Object.assign({}, defaultRole),
      roleId: null,
      roleList: [],
      menuList: [],
      moduleList: [],
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
    this.getList()
    this.getMenuList()
    this.getModuleList()
  },
  methods: {
    async getList() {
      const res = await getList()
      this.roleList = res.data
    },
    async getMenuList() {
      const res = await getMenuList()
      this.menuList = res.data
    },
    async getModuleList() {
      const res = await getModuleList()
      this.moduleList = res.data
    },
    menuPermissionHandler() {
      const checkedNodes = this.$refs.menuPermission.getCheckedNodes()
      const ids = []
      for (const data of checkedNodes) {
        ids.push(data.id)
      }
      const roleId = this.roleId
      updateMenuPermission({ ids, roleId }).then(res => {
        this.$message({
          type: 'success',
          message: '操作成功'
        })
      })
    },
    modulePermissionHandler() {
      const checkedNodes = this.$refs.modulePermission.getCheckedNodes()
      const ids = []
      for (const data of checkedNodes) {
        if (data.permission != null) {
          ids.push(data.permission)
        }
      }
      const roleId = this.roleId
      updateModulePermission({ ids, roleId }).then(res => {
        this.$message({
          type: 'success',
          message: '操作成功'
        })
      })
    },
    handleAddRole() {
      this.role = Object.assign({}, defaultRole)
      this.dialogType = 'new'
      this.dialogVisible = true
    },
    handleEdit(scope) {
      this.dialogType = 'edit'
      this.dialogVisible = true
      this.checkStrictly = true
      this.role = deepClone(scope.row)
    },
    handleMenu(scope) {
      this.roleId = scope.row.id
      this.menu_permission = true
      getMenuPermission(scope.row.id).then(res => {
        if (res.code === 200) {
          this.$refs.menuPermission.setCheckedKeys(res.data)
        }
      })
    },
    handleModule(scope) {
      this.module_permission = true
      this.roleId = scope.row.id
      getModulePermission(scope.row.id).then(res => {
        if (res.code === 200) {
          this.$refs.modulePermission.setCheckedKeys(res.data)
        }
      })
    },
    handleDelete({ $index, row }) {
      this.$confirm('确定要删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async() => {
          await deleteRole(row.id)
          this.$message({
            type: 'success',
            message: '删除成功'
          })
          this.getList()
        })
        .catch(err => {
          console.error(err)
        })
    },
    async confirmRole() {
      const isEdit = this.dialogType === 'edit'
      if (isEdit) {
        await updateRole(this.role)
        this.$notify({
          title: 'Success',
          dangerouslyUseHTMLString: true,
          message: `编辑成功`,
          type: 'success'
        })
      } else {
        await addRole(this.role)
        console.log(this.role)
        this.$notify({
          title: 'Success',
          dangerouslyUseHTMLString: true,
          message: `编辑成功`,
          type: 'success'
        })
      }
      this.getList()
      this.role = defaultRole
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

    .th {
      height: 95vh;
    }

    .btn_bottom {
      text-align: center;
      margin: 0 auto;
      justify-content: space-around;
    }
  }
</style>
