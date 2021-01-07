<template>
  <div>
    <el-card class="details-card" style="padding-bottom: 0;">
      <el-row>
        <el-col :span="16" style="padding-left: 50px">
          <el-form label-position="left" ref="presentationForm"
                   label-width="250px" :rules="rules"
                   :model="presentationForm" size="mini">
            <!--            <el-alert v-if="isError" :title="apiErrorMsg" type="error" show-icon class="errorMsg"/>-->

            <el-form-item label="Presentation Name: " :prop=" 'name' ">
              <b v-if="!isInEditMode">{{ presentationForm.name }}</b>
              <el-input v-model="presentationFormName" v-if="isInEditMode"></el-input>
            </el-form-item>
            <el-form-item label="Author: ">
              <el-tag>{{ presentationForm.author }}</el-tag>
            </el-form-item>
            <el-form-item label="Description: ">
              <div v-if="!isInEditMode" id="presentation-description">{{ presentationForm.description }}</div>
              <el-input type="textarea" autosize v-model="presentationFormDescription" v-if="isInEditMode"/>
            </el-form-item>
            <el-form-item v-if="isInEditMode">
              <el-button-group>
                <el-button type="primary" icon="el-icon-check" @click="updatePresentation()" v-if="isInEditMode">Save
                </el-button>
                <el-button type="info" icon="el-icon-close" @click="changeEditMode(false)"
                           v-if="isInEditMode">Cancel
                </el-button>
              </el-button-group>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col span="8">
          <div v-if="editable && !isInEditMode">
            <el-button-group style="margin-bottom: 20px">
              <el-button type="primary" @click="downloadPDF()" icon="el-icon-document">
                PDF
              </el-button>
              <el-button type="danger" @click="downloadPPTX()" icon="el-icon-data-board">
                Powerpoint
              </el-button>
            </el-button-group>
            <br/>
            <el-button-group>
              <el-button type="success" v-if="editable && !presentationSummary.isPublic" @click="handlePublic(true)">Make it public </el-button>
              <el-button type="success" v-if="editable && presentationSummary.isPublic" @click="handlePublic(false)">Make it private</el-button>
              <el-button type="primary" @click="changeEditMode(true)" icon="el-icon-edit"
                         v-if="!isInEditMode && editable">
                Edit
              </el-button>
              <el-button type="danger" v-if="editable"
                         icon="el-icon-delete" @click="deletePresentation()">
                Delete
              </el-button>
            </el-button-group>
          </div>
        </el-col>
      </el-row>
      <!--      <el-divider v-if="!isInEditMode"></el-divider>-->
      <el-row v-if="editable && !isInEditMode">

      </el-row>
    </el-card>

    <!--    <el-dialog title="Share with other users:" :visible.sync="isAccessControlDialogVisible" width="70%"-->
    <!--               :close-on-click-modal="false">-->
    <!--      <access-control-panel :presentationId="id"></access-control-panel>-->
    <!--    </el-dialog>-->
  </div>
</template>

<script>
import { downloadPDF } from '@/utils/pdfDownloader'
import { downloadPPTX } from '@/utils/pptxDownloader'
import { deepCopy } from '@/utils/utility'

export default {
  name: 'PresentationBrief',
  components: {},
  props: {
    editable: Boolean
  },
  data () {
    return {
      isInEditMode: false,
      rules: {
        name: [
          {
            required: true,
            message: 'Please enter presentation name',
            trigger: 'blur'
          },
          {
            min: 3,
            message: 'The length should be more than 3 character',
            trigger: 'blur'
          }
        ]
      },
      isPresentationEditable: true,
      presentationFormName: '',
      presentationFormDescription: '',
      isPublic: false
    }
  },
  computed: {
    isLogin () {
      return window.sessionStorage.getItem('token')
    },
    presentationForm () {
      return {
        name: this.$store.state.presentation.presentationSummary.name,
        author: this.$store.state.presentation.presentationSummary.author,
        description: this.$store.state.presentation.presentationSummary.description
      }
    },
    presentationSummary () {
      return this.$store.state.presentation.presentationSummary
    }
  },
  watch: {
    isInEditMode (target) {
      if (target) {
        this.presentationFormName = this.presentationForm.name
        this.presentationFormDescription = this.presentationForm.description
      }
    },
    isPublic (val) {
      if (val !== this.presentationSummary.isPublic) {
        this.$store.dispatch('setPresentationPublic', {
          id: this.presentationSummary.id,
          isPublic: val
        }).catch(() => {
          this.isPublic = !val
        })
      }
    }
  },
  mounted () {
    this.isPublic = this.presentationSummary.isPublic
    // console.log(this.editable)
    // this.isNewPresentation = this.presentationSummary.isNewPresentation
  },
  methods: {
    handlePublic (val) {
      this.$store.dispatch('setPresentationPublic', {
        id: this.presentationSummary.id,
        isPublic: val
      }).catch(() => {
        this.isPublic = !val
      })
    },
    changeEditMode (isEditing) {
      // if (isEditing === false) {
      //   this.presentationFormName = this.presentationForm.name
      //   this.presentationFormDescription = this.presentationForm.description
      // }
      this.isInEditMode = isEditing
    },
    updatePresentation () {
      if (this.presentationFormName !== this.presentationForm.name || this.presentationFormDescription !== this.presentationForm.description) {
        this.$store.dispatch('updatePresentationSummary', {
          id: this.presentationSummary.id,
          name: this.presentationFormName,
          description: this.presentationFormDescription
        })
      }
      this.isInEditMode = false
    },
    deletePresentation () {
      this.$store.dispatch('deletePresentation', { id: this.presentationSummary.id })
    },
    downloadPDF () {
      window.scrollTo(0, 0)
      const vm = this
      const wasPresentationEditable = deepCopy(vm.isPresentationEditable)
      vm.$store.commit('setIsPresentationEditable', false)
      vm.$store.commit('setPageLoadingStatus', true)

      this.$nextTick(() => {
        downloadPDF(vm.presentationFormName).then(() => {
          vm.$store.commit('setIsPresentationEditable', wasPresentationEditable)
          vm.$store.commit('setPageLoadingStatus', false)
        })
      })
    },
    downloadPPTX () {
      window.scrollTo(0, 0)
      const vm = this
      const wasPresentationEditable = deepCopy(vm.isPresentationEditable)
      vm.$store.commit('setIsPresentationEditable', false)
      vm.$store.commit('setPageLoadingStatus', true)

      this.$nextTick(() => {
        downloadPPTX(vm.presentationFormName).then(() => {
          vm.$store.commit('setIsPresentationEditable', wasPresentationEditable)
          vm.$store.commit('setPageLoadingStatus', false)
        })
      })
    }
  }
}
</script>

<style scoped>
div.el-card__header {
  padding: 0;
}

.formStyle {
  display: inline-block;
  text-align: left;
  margin-right: 8px;
}

.errorMsg {
  margin-bottom: 18px;
}

.el-form-item__label {
  font-weight: bold;
}

.el-card {
  margin-bottom: 10px;
}

.details-card {
  overflow-x: auto;
}

.download-section {
  text-align: center;
  vertical-align: middle;
  margin-top: 1.5rem;
}

.options-section {
  text-align: center;
  vertical-align: middle;
  margin-top: 1.5rem;
}

.v-divide {
  float: left;
  height: 2rem;
  margin-top: -1.5rem;
}
</style>
