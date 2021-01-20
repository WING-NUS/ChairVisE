<template>
  <div>
    <presentation-brief v-bind:editable="editable"/>
    <section-list-edit v-bind:editable="editable"/>
  </div>
</template>

<script>
import PresentationBrief from '@/components/presentations/PresentationDetails/PresentationBrief'
import SectionListEdit from '@/components/presentations/SectionListEdit'

export default {
  name: 'PresentationDetail',
  components: {
    SectionListEdit,
    PresentationBrief
  },
  data () {
    return {
    }
  },
  computed: {
    sectionList () {
      return this.curPresentation.sectionList
      // const result = []
      // for (const e in this.chartList) {
      //   result.push({
      //     sectionId: this.chartList[e].name,
      //     metadata: this.chartList[e].metadata
      //   })
      // }
      // return result
    },
    curPresentation () {
      return this.$store.state.presentation
    },
    editable () {
      return this.$store.state.presentation.editable
    }
  },
  beforeCreate () {
    if (this.$route.params.id !== null) {
      this.$store.dispatch('getPresentation', this.$route.params.id)
    }
  },
  methods: {
  }
}
</script>

<style scoped>

</style>
