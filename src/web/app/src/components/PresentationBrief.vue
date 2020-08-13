<template>
  <div>
    <el-card class="details-card">
      <div slot="header" class="clearfix">
        <span> Presentation Details </span>
      </div>
      <el-row>
        <el-col :span="18">
          <el-form label-position="left" ref="presentationForm" 
                  label-width="150px" :rules="rules"
                  :model="presentationForm" v-loading="isLoading" >
            <el-alert v-if="isError" :title="apiErrorMsg" type="error" show-icon class="errorMsg"/>

            <el-form-item label="Name: " v-show="isInEditMode" :prop=" 'name' " >
              <div v-if="!isInEditMode">{{ presentationForm.name }}</div>
              <el-input v-model="presentationFormName" v-if="isInEditMode"/>
            </el-form-item>
            <el-form-item label="Name: " v-show="!isInEditMode" >
              <div v-if="!isInEditMode">{{ presentationForm.name }}</div>
              <el-input v-model="presentationFormName" v-if="isInEditMode"/>
            </el-form-item>
            <el-form-item label="Access Control: " v-if="!isNewPresentation">
              <el-tag>Created by {{ presentationForm.creatorIdentifier }}</el-tag>
            </el-form-item>
            <el-form-item label="Description: ">
              <div v-if="!isInEditMode" id="presentation-description">{{ presentationForm.description }}</div>
              <el-input type="textarea" autosize v-model="presentationFormDescription" v-if="isInEditMode"/>
            </el-form-item>
            <el-form-item v-if="isInEditMode">
              <el-button-group>
                <el-button type="primary" icon="el-icon-check" @click="addPresentation()" v-if="isInEditMode">Save</el-button>
                <el-button type="info" icon="el-icon-close" @click="changeEditMode(false)" v-if="isInEditMode && !isNewPresentation">Cancel</el-button>
              </el-button-group>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <el-divider v-if="!isInEditMode"></el-divider>
      <el-row v-if="!isInEditMode">
        <el-col class="download-section" :span="12">
          <el-button-group>
            <el-button type="primary" @click="downloadPDF()" v-if="!isInEditMode && !isNewPresentation"
              icon="el-icon-document">
                PDF
            </el-button>
            <el-button type="danger" @click="downloadPPTX()" v-if="!isInEditMode && !isNewPresentation"
              icon="el-icon-data-board">
                Powerpoint
            </el-button>
          </el-button-group>
        </el-col>
        <el-col class="options-section" :span="12">
          <el-divider direction="vertical" class="v-divide"></el-divider>
          <el-button-group>
            <el-button type="success" class="share_button_left_margin" icon="el-icon-share"
                    @click="openAccessControlPanel()" v-if="isLogin && isPresentationEditable">
              Share
            </el-button>
            <el-button type="primary" @click="changeEditMode(true)" icon="el-icon-edit"
                    v-if="!isInEditMode && isPresentationEditable">
              Edit
            </el-button>
            <el-button type="danger" v-if="!isNewPresentation && isLogin && isPresentationEditable"
                    icon="el-icon-delete" @click="deletePresentation()">
              Delete
            </el-button>
          </el-button-group>
        </el-col>
      </el-row>
    </el-card>

    <el-dialog title="Share with other users:" :visible.sync="isAccessControlDialogVisible" width="70%"
            :close-on-click-modal="false">
      <access-control-panel :presentationId="id"></access-control-panel>
    </el-dialog>
  </div>
</template>

<script>
  import AccessControlPanel from '@/components/AccessControlPanel'
  import {downloadPDF} from "@/store/helpers/pdfDownloader"
  import {downloadPPTX} from "@/store/helpers/pptxDownloader"
  import {AccessLevel, ID_NEW_PRESENTATION, SPECIAL_IDENTIFIER_PUBLIC} from "@/common/const";
  import {deepCopy} from "@/common/utility";

  export default {
    name: 'PresentationBrief',
    props: {
      id: String
    },
    mounted() {
      this.updatePresentationForm();
    },
    watch: {
      'id'() {
        this.updatePresentationForm()
      },
    },
    computed: {
      isLogin() {
        return this.$store.state.userInfo.isLogin
      },
      isPresentationEditable() {
        return this.$store.state.presentation.isPresentationEditable;
      },

      presentationForm() {
        return {
          name: this.presentationFormName,
          creatorIdentifier: this.presentationFormCreatorIdentifier,
          description: this.presentationFormDescription,
        }
      },
      presentationFormName: {
        get() {
          return this.$store.state.presentation.presentationForm.name
        },
        set(value) {
          this.$store.commit('setPresentationFormField', {
            field: 'name',
            value
          })
        },
      },
      presentationFormCreatorIdentifier() {
        return this.$store.state.presentation.presentationForm.creatorIdentifier
      },
      presentationFormDescription: {
        get() {
          return this.$store.state.presentation.presentationForm.description
        },
        set(value) {
          this.$store.commit('setPresentationFormField', {
            field: 'description',
            value
          })
        },
      },
      isNewPresentation() {
        return this.id === ID_NEW_PRESENTATION
      },
      isInEditMode() {
        return this.isEditing || this.isNewPresentation
      },
      isLoading() {
        return this.$store.state.presentation.presentationFormStatus.isLoading
      },
      isError() {
        return this.$store.state.presentation.presentationFormStatus.isApiError
      },
      apiErrorMsg() {
        return this.$store.state.presentation.presentationFormStatus.apiErrorMsg
      }
    },
    data() {
      return {
        isEditing: false,
        isAccessControlDialogVisible: false,
        rules: {
          name: [
            {required: true, message: 'Please enter presentation name', trigger: 'blur'},
            {min: 3, message: 'The length should be more than 3 character', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      changeEditMode(isEditing) {
        if (isEditing === false) {
          this.updatePresentationForm();
        }
        this.isEditing = isEditing;
      },

      openAccessControlPanel() {
        this.isAccessControlDialogVisible = true;
      },

      addPresentation() {
        this.$refs['presentationForm'].validate((valid, object) => {
          if (!valid) {

            if('name' in object) {
              this.$notify.error({
                title: 'Error',
                message: object.name[0].message
              });
            }
            return
          }
          this.$refs['presentationForm'].clearValidate();
          if (this.isNewPresentation) {
            // add
            this.$store.dispatch('savePresentation')
                    .then(() => {
                      if (this.isError) {
                        return
                      }
                      // redirect to the newly added presentation
                      this.$router.push({
                        name: 'analyze',
                        params: {
                          id: this.$store.state.presentation.presentationForm.id
                        }
                      });
                    });
          } else {
            // edit
            this.$store.dispatch('updatePresentation')
                    .then(() => {
                      if (this.isError) {
                        return
                      }
                      this.isEditing = false
                    })
          }
        });
      },
      deletePresentation() {
        this.$store.dispatch('deletePresentation', this.id)
                .then(() => {
                  if (this.isError) {
                    return
                  }
                  this.$router.replace({
                    name: 'analyze',
                    params: {
                      id: ID_NEW_PRESENTATION
                    }
                  });
                  this.isEditing = false;
                })
      },
      updatePresentationForm() {
        if (this.$refs['presentationForm']) {
          this.$refs['presentationForm'].clearValidate();
        }
        this.$store.commit('resetPresentationForm');
        if (this.id !== ID_NEW_PRESENTATION) {
          this.$store.dispatch('getPresentation', this.id)
                  .then(() => {
                    // check writable or not
                    this.$store.dispatch('fetchAccessControlList', this.id)
                            .then(() => {
                              let currentUser = this.$store.state.userInfo.userEmail;
                              let accessControlList = this.$store.state.accessControl.accessControlList;
                              let isPresentationEditable =
                                      currentUser === this.presentationFormCreatorIdentifier
                                      || accessControlList.some(acl => acl.userIdentifier === currentUser && acl.accessLevel === AccessLevel.CAN_WRITE)
                                      || accessControlList.some(acl => acl.userIdentifier === SPECIAL_IDENTIFIER_PUBLIC && acl.accessLevel === AccessLevel.CAN_WRITE);
                              this.$store.commit('setIsPresentationEditable', isPresentationEditable)
                            })
                  })
        }
      },
      downloadPDF() {
        window.scrollTo(0, 0)
        let vm = this;
        let wasPresentationEditable = deepCopy(vm.isPresentationEditable);
        vm.$store.commit('setIsPresentationEditable', false);
        vm.$store.commit('setPageLoadingStatus', true);

        this.$nextTick(() => {
          downloadPDF(vm.presentationFormName).then(() => {
            vm.$store.commit('setIsPresentationEditable', wasPresentationEditable);
            vm.$store.commit('setPageLoadingStatus', false);
          });
        });
      },
      downloadPPTX() {
        window.scrollTo(0, 0)
        let vm = this;
        let wasPresentationEditable = deepCopy(vm.isPresentationEditable);
        vm.$store.commit('setIsPresentationEditable', false);
        vm.$store.commit('setPageLoadingStatus', true);

        this.$nextTick(() => {
          downloadPPTX(vm.presentationFormName).then(() => {
            vm.$store.commit('setIsPresentationEditable', wasPresentationEditable);
            vm.$store.commit('setPageLoadingStatus', false);
          });
        });
      }
    },

    components: {
      AccessControlPanel
    },
  }
</script>

<style scoped>
  .formStyle {
    display: inline-block;
    text-align: left;
    margin-right: 8px;
  }ß
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
    text-align:center;
    vertical-align:middle;
    margin-top: 1.5rem;
  }
  .options-section {
    text-align:center;
    vertical-align:middle;
    margin-top: 1.5rem;
  }
  .v-divide {
    float: left;
    height: 6rem;
    margin-top: -1.5rem;
  }
</style>
