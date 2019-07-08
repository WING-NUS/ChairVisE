<template>
  <div>
    <h4>Presentations created by me</h4>
    <el-menu :default-active="$route.path" v-loading="isLoading" router>
      <li key="__NEW__">
        <el-menu-item :index="'/analyze/__NEW__'">
          <i class="el-icon-edit-outline"></i>
          <span slot="title">
          New
        </span>
        </el-menu-item>
      </li>
      <li v-for="presentation in presentations" :key="presentation.id">
        <el-menu-item :index="`/analyze/${presentation.id}`">
          <i class="el-icon-document"></i>
          <span slot="title">
          {{ presentation.name }}
        </span>
        </el-menu-item>
      </li>
    </el-menu>
  </div>
</template>

<script>
  export default {
    name: 'ListOfPresentation',
    data() {
      return {}
    },
    watch: {
      'isError'() {
        if (!this.isError) {
          return
        }
        this.$notify.error({
          title: 'Presentation list API request fail',
          message: this.$store.state.presentation.presentationListStatus.apiErrorMsg,
          duration: 0
        });
      }
    },
    computed: {
      isLoading() {
        return this.$store.state.presentation.presentationListStatus.isLoading
          || this.$store.state.presentation.presentationFormStatus.isLoading
          || this.$store.state.section.sectionListStatus.isLoading
          || this.$store.state.section.sectionList.some(s => s.status.isLoading)
      },
      presentations() {
        return this.$store.state.presentation.presentationList
      },
      isError() {
        return this.$store.state.presentation.presentationListStatus.isApiError
      },
    },
    mounted() {
      this.$store.dispatch('getPresentationList')
    }
  }
</script>
