<template>
  <div>
    <h3>Create a new presentation for {{ conferenceName }} </h3>
    <el-form style="width: 500px; margin: 30px" :model="newPresentationForm" ref="newPresentationForm">
      <el-form-item label="Presentation Name">
        <el-input v-model="newPresentationForm.name"></el-input>
      </el-form-item>
      <el-form-item label="Description">
        <el-input v-model="newPresentationForm.description"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">Create</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'NewPresentation',
  data () {
    return {
      newPresentationForm: {
        name: '',
        description: ''
      }
    }
  },
  computed: {
    conferenceName () {
      return this.$route.params.conferenceName
    }
  },
  mounted () {
    console.log(this.$route)
    if (!this.$route.params.conferenceId) {
      this.$router.push('ConferenceList')
    }
  },
  methods: {
    onSubmit () {
      if (this.name !== '') {
        this.$store.dispatch('addPresentation', {
          conferenceId: this.$route.params.conferenceId,
          presentationName: this.newPresentationForm.name,
          description: this.newPresentationForm.description
        })
        // this.$router.push('/conference')
        // this.createPresentation(this.$route.params.conferenceId, this.newPresentationForm.name, this.newPresentationForm.description)
      }
    },
    createPresentation (conferenceId, presentationName, description) {
      // this.$router.push({
      //   path: '/presentation',
      //   params: {
      //     conference: {
      //       id: conferenceId,
      //       name: this.$route.params.conferenceName,
      //       year: this.$route.params.conferenceYear
      //     },
      //     presentation: {
      //       id: presentationId,
      //       name: presentationName,
      //       description: description,
      //       isNew: true
      //     }
      //   }
      // })
    }
  }
}
</script>

<style scoped>

</style>
