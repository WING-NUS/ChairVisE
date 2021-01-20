<template>
  <div>
    <h1>ChairHub - View All Public Presentations Here</h1>
    <el-table :data="presentationList">
      <el-table-column label="Presentation Name">
        <template slot-scope="props">
          <el-button type="text" style="padding: 3px" @click="handleToPresentation(props.row)">
            {{ props.row.name }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="Description" prop="description"/>
      <el-table-column label="Chair" prop="chair.name"/>
      <el-table-column label="Created At">
        <template slot-scope="props">
          {{ new Date(props.row.createdAt).toLocaleDateString() }}
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import Axios from 'axios'

export default {
  name: 'ChairHub',
  data () {
    return {
      presentationList: []
    }
  },
  mounted () {
    Axios.get('/api/chairhub').then(res => {
      this.presentationList = res.data
    })
  },
  methods: {
    handleToPresentation (presentation) {
      this.$router.push('/presentation/' + presentation.id)
    }
  }
}
</script>

<style scoped>

</style>
