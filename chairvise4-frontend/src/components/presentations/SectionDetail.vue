<template>
  <el-col :sm="24" :md="12" :lg="12" :xl="8" class="sectionDetail">
    <el-card class="sectionCard">
      <el-row v-if="!isInEditMode">
        <el-col :span="18">{{ section.name }}</el-col>
        <el-col :span="6" v-if="editable">
          <el-button-group style="margin-left: 10px">
            <el-button icon="el-icon-edit" size="mini" @click="handleEdit"></el-button>
            <el-button icon="el-icon-download" size="mini"></el-button>
            <el-button icon="el-icon-delete" size="mini" @click="handleDelete"></el-button>
          </el-button-group>
        </el-col>
      </el-row>
      <el-row v-else-if="editable">
        <el-col :span="4"><p>Chart Name: </p></el-col>
        <el-col :span="12">
          <el-input placeholder="Please insert a name for this chart." v-model="newName"/>
        </el-col>
        <el-col :span="4">
          <el-button-group>
            <el-button icon="el-icon-check" size="mini" @click="handleUpdate"></el-button>
            <el-button icon="el-icon-close" size="mini" @click="handleEdit"></el-button>
          </el-button-group>
        </el-col>
      </el-row>
      <div v-if="section.id === 'author_country_rank'">
        <bar-chart-author-country-rank :section-num="sectionNum" :db-name="conferenceDBName"/>
      </div>
      <div v-else-if="section.id === 'author_country_rank_doughnut_chart'">
        <doughnut-chart-author-country-distribution :section-num="sectionNum" :db-name="conferenceDBName"/>
      </div>
      <div v-else-if="section.id === 'submission_accept_distribution'">
        <doughnut-submission-accept :section-num="sectionNum" :db-name="conferenceDBName"/>
      </div>
      <div v-else-if="section.id === 'submission_keywords_word_cloud'">
        <word-cloud-submission-keywords :section-num="sectionNum" :db-name="conferenceDBName"/>
      </div>
      <div v-else-if="section.id === 'top_submission_review_score'">
        <bar-chart-top-score-submission :section-num="sectionNum" :db-name="conferenceDBName"/>
      </div>
      <!--      Add new chart section here-->
      <div v-else>
        <el-input v-model="newDescription" placeholder="Please add descriptions for this chart."></el-input>
      </div>
      <small v-if="!isInEditMode">{{ section.description === '' ? ' ' : section.description }}</small>
      <el-input v-else placeholder="description" v-model="newDescription"/>
    </el-card>
  </el-col>
</template>

<script>
import BarChartAuthorCountryRank from '@/components/presentations/SectionDetail/BarChartAuthorCountryRank'
import DoughnutChartAuthorCountryDistribution
  from '@/components/presentations/SectionDetail/DoughnutAuthorCountryDistribution'
import DoughnutSubmissionAccept from '@/components/presentations/SectionDetail/DoughnutSubmissionAccept'
import WordCloudSubmissionKeywords from '@/components/presentations/SectionDetail/WordCloudSubmissionKeywords'
import BarChartTopScoreSubmission from '@/components/presentations/SectionDetail/BarChartTopScoreSubmission'

export default {
  name: 'SectionDetail',
  components: {
    BarChartAuthorCountryRank,
    DoughnutChartAuthorCountryDistribution,
    DoughnutSubmissionAccept,
    WordCloudSubmissionKeywords,
    BarChartTopScoreSubmission
  },
  props: {
    conferenceDBName: {
      type: Number
    },
    sectionNum: Number,
    editable: Boolean
  },
  data () {
    return {
      isInEditMode: false,
      newName: '',
      newDescription: ''
    }
  },
  computed: {
    sectionList () {
      return this.$store.state.presentation.sectionList
    },
    section () {
      return this.$store.state.presentation.sectionList[this.sectionNum]
    }
  },
  methods: {
    handleEdit () {
      this.newName = this.section.name
      this.newDescription = this.section.description
      this.isInEditMode = !this.isInEditMode
    },
    handleDelete () {
      // console.log(this.sectionNum)
      this.$store.commit('deleteSection', this.sectionNum)
      this.$store.dispatch('updatePresentationSection')
    },
    handleUpdate () {
      this.$store.commit('updateChartData', {
        id: this.sectionNum,
        result: {
          name: this.newName,
          description: this.newDescription
        }
      })
      this.$store.dispatch('updatePresentationSection')
      this.isInEditMode = false
    }
    // getDataCallback (result) {
    //   this.chartData = result
    // },
    // getChartData (chartId, chartType, presentationId) {
    //   charts[chartId].getData(presentationId, this.chartData, this.getDataCallback)
    // }
  },
  mounted () {
    // console.log(this.sectionDetail)
    // this.getChartData(this.sectionDetail.sectionId, this.sectionDetail.sectionType, this.presentationId)
  }
}
</script>

<style scoped>
.sectionCard {
  height: 520px;
}

.sectionDetail {
  height: max-content;
}
</style>
