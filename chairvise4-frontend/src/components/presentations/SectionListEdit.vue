<template>
  <el-card shadow="hover">
    <el-row :gutter="5">
      <section-detail class="presentation-section" v-for="(section, i) in sectionList" :sectionNum="i"
                      :key="i" v-bind:conference-d-b-name="conferenceId" v-bind:editable="editable"/>
    </el-row>
    <empty-section v-if="!sectionList || sectionList.length === 0"/>
    <el-row style="margin-top: 20px;">
      <el-select class="selectionInput" v-model="selectedNewSection" placeholder="Please select a section to add"
                 filterable v-if="editable">
        <el-option-group
          v-for="group in predefinedSections"
          :key="group.label"
          :label="group.label">
          <el-option
            v-for="item in group.options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-option-group>
      </el-select>
      <el-button type="primary" @click="addSection" v-if="editable">Add Section</el-button>
    </el-row>
  </el-card>
</template>

<script>
import SectionDetail from '@/components/presentations/SectionDetail'
import EmptySection from '@/components/presentations/EmptySection'
import PredefinedQueries from '@/utils/predefinedQueries'

export default {
  name: 'SectionListEdit',
  components: {
    SectionDetail,
    EmptySection
  },
  props: {
    editable: Boolean
  },
  data () {
    return {
      dataLoaded: false,
      selectedNewSection: null
    }
  },
  computed: {
    conferenceId () {
      return this.$store.state.presentation.conferenceId
    },
    sectionList () {
      return this.$store.state.presentation.sectionList
    },
    predefinedSections () {
      const sectionOptionsGroup = {}
      // grouping the predefined queries
      for (const key in PredefinedQueries) {
        if (!PredefinedQueries[key]) {
          continue
        }
        const groupName = PredefinedQueries[key].group
        if (sectionOptionsGroup[groupName] === undefined) {
          sectionOptionsGroup[groupName] = []
        }
        sectionOptionsGroup[groupName].push({
          value: key,
          label: PredefinedQueries[key].name
        })
      }
      // generate to format that element ui requires
      const sectionOptions = []
      for (const groupName in sectionOptionsGroup) {
        if (!sectionOptionsGroup[groupName]) {
          continue
        }
        sectionOptions.push({
          label: groupName,
          options: sectionOptionsGroup[groupName]
        })
      }
      return sectionOptions
    }
  },
  watch: {
    dataLoaded (value) {
      if (!value) {
        // TODO: call API to get data and store in IndexedDB
        this.isDataLoaded(this.setDataLoaded)
      }
    }
  },
  mounted () {
    // this.sectionList = this.sectionListParam
  },
  methods: {
    isDataLoaded (setDataLoaded) {
      const indexedDB = window.indexedDB || window.mozIndexedDB || window.webkitIndexedDB || window.msIndexedDB || window.shimIndexedDB
      const request = indexedDB.open(this.conferenceId)
      request.onerror = function (event) {
        console.log('error opening indexedDB')
      }
      request.onupgradeneeded = function (event, setDataLoaded) {
        const db = event.target.result
        if (!(db.objectStoreNames.contains('author') && db.objectStoreNames.contains('author') && db.objectStoreNames.contains('author'))) {
          setDataLoaded(true)
        }
      }
    },
    setDataLoaded (target) {
      this.dataLoaded = target
    },
    addSection () {
      // console.log(this.selectedNewSection)
      if (this.selectedNewSection.length === 0) {
        this.$notify.error({
          title: 'Error',
          message: 'Please select a section to add into presentation.'
        })
        return
      }
      this.$store.commit('addSection', {
        id: this.selectedNewSection,
        name: PredefinedQueries[this.selectedNewSection].name,
        description: PredefinedQueries[this.selectedNewSection].description,
        options: PredefinedQueries[this.selectedNewSection].options,
        record: {},
        others: PredefinedQueries[this.selectedNewSection].others
      })
      this.$store.dispatch('updatePresentationSection')
      this.selectedNewSection = ''
    }
  }
}
</script>

<style scoped>

</style>
