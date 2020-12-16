<template>
  <div class="app-container">

    <el-button type="primary"  style="margin-bottom: 10px" @click="sync">同步数据</el-button>
    <el-table
      :data="List"
      style="width: 100%;margin-bottom: 20px;"
      row-key="id"
      border
    >

      <el-table-column
        prop="name"
        label="名字"
      />
      <el-table-column
        prop="permission"
        label="权限标识"
      />
    </el-table>

  </div>
</template>

<script>
import { getList, sync } from '@/api/module'

export default {

  data() {
    return {
      parentId: '',
      List: []
    }
  },
  created() {
    this.getList()
  },
  methods: {
    async getList() {
      const res = await getList()
      this.List = res.data
    },
    async sync() {
      await sync()
      this.getList()
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
