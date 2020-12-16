<template>
  <div class="app-container">

    <el-button icon="el-icon-plus"  style="margin-bottom: 10px" type="primary" @click="handleAddRole">添加用户</el-button>

    <el-button v-if="showRemove"  style="margin-bottom: 10px" icon="el-icon-remove" type="primary" @click="handleMultieDelete">刪除选中</el-button>
    <el-table
      ref="userTable"
      :data="userList"
      style="width: 100%;margin-bottom: 20px;"
      row-key="id"
      border
      @selection-change="handleSelectionChange"
    >

      <el-table-column
        type="selection"
        width="55"
      />

      <el-table-column
        prop="id"
        label="用户ID"
      />
      <el-table-column label="头像" align="center">
        <template slot-scope="scope">
          <img v-if="scope.row.avatar" :src="scope.row.avatar" class="avatar">
        </template>
      </el-table-column>
      <el-table-column
        prop="username"
        label="用户名字"
      />
      <el-table-column
        prop="nickName"
        label="用户昵称"
      />
      <el-table-column label="角色" align="center">
        <template slot-scope="scope">
          <el-tag v-for="(item,index) in scope.row.roles" :key="index">{{ item.name }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.state==0" type="success">正常</el-tag>
          <el-tag v-else type="warning">冻结</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="handleEdit(scope)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDelete(scope)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="block">
      <el-pagination
        :current-page="currentPage"
        :page-sizes="[2,10,50,100]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <el-dialog :visible.sync="dialogVisible" :title="dialogType==='edit'?'编辑用户':'添加用户'">
      <el-form :model="user" label-width="80px" label-position="left">
        <el-form-item label="用户名字">
          <el-input v-model="user.username" placeholder="用户名字" />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="user.nickName" placeholder="用户昵称" />
        </el-form-item>
        <el-form-item label="头像">
          <el-input v-model="user.avatar" placeholder="头像地址" />
        </el-form-item>

        <el-form-item v-if="dialogType ==='new'" label="密码">
          <el-input v-model="user.password" placeholder="密码" />
        </el-form-item>
        <el-form-item label="账号状态">
          <template>
            <el-radio v-model="user.state" label="0">正常</el-radio>
            <el-radio v-model="user.state" label="1">冻结</el-radio>
          </template>
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="currentRole" multiple placeholder="选择角色">
            <el-option
              v-for="item in roleList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div style="text-align:right;">
        <el-button type="danger" @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="confirmRole">确定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { deepClone } from '@/utils'
import { getUserList, editUser, deleteUser, addUser, multiDelete } from '@/api/user'
import {
  getList
} from '@/api/role'

const defaultUser = {
  id: '',
  username: '',
  nickName: '',
  password: '123456',
  avatar: '',
  state: '0'
}

export default {

  data() {
    return {
      total: 0,
      pageSize: 10,
      currentPage: 1,
      select: [],
      showRemove: false,
      userList: [],
      roleList: [],
      currentRole: [],
      user: Object.assign({}, defaultUser),
      dialogVisible: false,
      dialogType: 'new'
    }
  },
  created() {
    this.getUserList()
    this.getList()
  },
  methods: {
    async getUserList() {
      const res = await getUserList(this.currentPage, this.pageSize)
      this.userList = res.data.list
      this.total = res.data.totalRow
    },
    async getList() {
      const res = await getList()
      this.roleList = res.data
    },
    async confirmRole() {
      this.user.roleIds = this.currentRole
      const isEdit = this.dialogType === 'edit'
      if (isEdit) {
        await editUser(this.user)
        this.$notify({
          title: 'Success',
          dangerouslyUseHTMLString: true,
          message: `编辑成功`,
          type: 'success'
        })
      } else {
        await addUser(this.user)
        this.$notify({
          title: 'Success',
          dangerouslyUseHTMLString: true,
          message: `添加成功`,
          type: 'success'
        })
      }
      this.getUserList()
      this.user = defaultUser
      this.dialogVisible = false
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getUserList()
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.getUserList()
    },
    handleAddRole() {
      this.user = Object.assign({}, defaultUser)
      this.dialogType = 'new'
      this.dialogVisible = true
    },
    handleEdit(scope) {
      this.dialogType = 'edit'
      this.dialogVisible = true
      this.user = deepClone(scope.row)
      this.currentRole = []
      for (const a of this.user.roles) {
        this.currentRole.push(a.id)
      }
    },
    handleSelectionChange(val) {
      this.select = val
      if (val.length > 0) {
        this.showRemove = true
      } else {
        this.showRemove = false
      }
    },
    handleMultieDelete() {
      const ids = []
      for (const a of this.select) {
        ids.push(a.id)
      }
      this.$confirm('确定要删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async() => {
          await multiDelete(ids)
          this.$message({
            type: 'success',
            message: '删除成功'
          })
          this.getUserList()
        })
        .catch(err => {
          console.error(err)
        })
    },
    handleDelete({ $index, row }) {
      this.$confirm('确定要删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async() => {
          await deleteUser(row.id)
          this.$message({
            type: 'success',
            message: '删除成功'
          })
          this.getUserList()
        })
        .catch(err => {
          console.error(err)
        })
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
    .avatar {
      height: 25px;
    }
  }
</style>
