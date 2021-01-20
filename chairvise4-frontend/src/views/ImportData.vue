<template>
  <div>
    <el-card>
      <div class="section">
        <h2> Import your conference data </h2>
        <el-divider></el-divider>
        <div v-if="newConf">
          <p style="text-align: left">Please enter your conference name and year.</p>
          <el-form style="width: 500px; margin-top: 30px" label-width="180px" :model="newConfForm">
            <el-form-item label="Conference Name">
              <el-input v-model="newConfForm.conferenceName" placeholder="Please enter the conference Name"
                        style="width: 280px"/>
            </el-form-item>
            <el-form-item label="Conference Year">
              <el-date-picker v-model="newConfForm.conferenceYear" type="year" placeholder="Please select the year."
                              style="width: 280px"/>
            </el-form-item>
            <el-form-item label="Description">
              <el-input v-model="newConfForm.description" placeholder="[Optional] description for the conference"
                        style="width: 280px"/>
            </el-form-item>
            <el-form-item>
              <el-button type="success" @click="handleEnter" :disabled="formNotFilled">Next</el-button>
            </el-form-item>
          </el-form>
        </div>
        <div v-else>
          <div class="form-card">
            <label class="label" style="margin-right: 20px;">1. Select Your Data Type</label>
            <el-radio-group v-model="fileType" size="small">
              <el-radio-button :label="0">EasyChair</el-radio-button>
              <el-radio-button :label="1">SoftConf</el-radio-button>
            </el-radio-group>
          </div>
          <small>
            Please make sure your files are in csv format and have headers.
          </small>
          <div style="margin-top:20px; margin-bottom: 5px;">
            <label class="label">2. Upload your record in below</label>
            <el-row :gutter="15">
              <el-col :sm="24" :md="8" :lg="8" :xl="8">
                <el-upload drag action="" :auto-upload="false" :show-file-list="false" :multiple="false"
                           :on-change="authorUpload">
                  <p>Author Record <i class="el-icon-check" v-if="records.author"></i></p>
                  <i class="el-icon-upload"></i>
                  <div class="el-upload__text">Drop .csv file here or <em>click to upload</em></div>
                </el-upload>
              </el-col>
              <el-col :sm="24" :md="8" :lg="8" :xl="8">
                <el-upload drag action="" :auto-upload="false" :show-file-list="false" :multiple="false"
                           :on-change="reviewUpload">
                  <p>Review Record <i class="el-icon-check" v-if="records.review"></i></p>
                  <i class="el-icon-upload"></i>
                  <div class="el-upload__text">Drop .csv file here or <em>click to upload</em></div>
                </el-upload>
              </el-col>
              <el-col :sm="24" :md="8" :lg="8" :xl="8">
                <el-upload drag action="" :auto-upload="false" :show-file-list="false" :multiple="false"
                           :on-change="submissionUpload">
                  <p>Submission Record <i class="el-icon-check" v-if="records.submission"></i></p>
                  <i class="el-icon-upload"></i>
                  <div class="el-upload__text">Drop .csv file here or <em>click to upload</em></div>
                </el-upload>
              </el-col>
            </el-row>
          </div>
          <el-button type="success" size="medium" @click="handleSubmit">Finish</el-button>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
// import importDataUtils from '@/utils/importDataUtils'
import Papa from 'papaparse'

export default {
  name: 'ImportData',
  data () {
    return {
      conferenceName: null,
      conferenceYear: null,
      description: null,
      newConfForm: {
        conferenceName: null,
        conferenceYear: null,
        description: null
      },
      fileType: 0,
      tableType: 0,
      fileList: {
        author: null,
        review: null,
        submission: null
      },
      newConf: !this.$route.params.id,
      hasHeader: true,
      usingPredefinedMapping: false,
      records: {
        author: null,
        review: null,
        submission: null
      }
    }
  },
  mounted () {
  },
  computed: {
    formNotFilled () {
      return !(this.newConfForm.conferenceName && this.newConfForm.conferenceYear)
    },
    conferenceList () {
      return this.$store.state.conferenceList
    }
  },
  methods: {
    updateRecordCallback (tableType, data) {
      this.records[tableType] = data
    },
    authorUpload (file) {
      this.submit(file, this.fileType, 'author', this.updateRecordCallback)
    },
    reviewUpload (file) {
      this.submit(file, this.fileType, 'review', this.updateRecordCallback)
    },
    submissionUpload (file) {
      this.submit(file, this.fileType, 'submission', this.updateRecordCallback)
    },
    handleEnter () {
      const name = this.newConfForm.conferenceName
      const year = this.newConfForm.conferenceYear
      if (name && year) {
        for (const c in this.conferenceList) {
          if (this.conferenceList[c].name === name && this.conferenceList[c].year === year.getFullYear()) {
            this.$message.error('This conference is existing, please go to the Conference page.')
            return
          }
        }
        this.conferenceName = name
        this.conferenceYear = year.getFullYear()
        this.description = this.newConfForm.description
        this.newConfForm = {
          conferenceYear: null,
          conferenceName: null,
          description: null
        }
        this.newConf = false
      } else {
        this.$message.error('Please fill the data first.')
      }
    },
    submit (file, fileType, tableType, callback) {
      if (file === null) {
        return
      }
      // const name = this.conferenceName.replace(/\s/g, '') + this.conferenceYear
      Papa.parse(file.raw, {
        skipEmptyLines: true,
        header: this.hasHeader,
        transformHeader: function (header) {
          return header.replace(/\s/g, '').replace('#', 'id').toLowerCase()
        },
        complete: function (result) {
          callback(tableType, result.data)
        }
      })
    },
    handleSubmit () {
      console.log(this.records)
      if (this.$route.params.id) {
        this.$store.dispatch('updateConference', {
          id: this.$route.params.id,
          name: this.conferenceName,
          year: this.conferenceYear,
          description: this.description,
          records: JSON.stringify({
            author: this.records.author,
            review: this.records.review,
            submission: this.records.submission,
            fileType: this.fileType
          })
        })
      } else {
        this.$store.dispatch('addConference', {
          name: this.conferenceName,
          year: this.conferenceYear,
          description: this.description,
          records: JSON.stringify({
            author: this.records.author,
            review: this.records.review,
            submission: this.records.submission,
            fileType: this.fileType
          })
        })
      }
      this.$router.push({
        path: '/ConferenceList',
        params: {
          name: this.conferenceName,
          year: this.conferenceYear
        }
      })
    }
  }
}
</script>

<style scoped>
p {
  margin-bottom: 0px;
}

i.el-icon-upload {
  margin-top: 20px;
}

el-upload {
}
</style>
