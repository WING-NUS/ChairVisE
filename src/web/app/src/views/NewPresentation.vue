<template>
    <el-main>
        <el-card>
            <div slot="header" class="clearfix">
                <span> Create New Presentation </span>
            </div>
            <el-alert v-if="isNewPresentation && !isLogin" title="Please login to create new presentation" type="error" show-icon
                    class="errorMsg"/>
            <el-form v-else :rules="rules" ref="presentationForm"
                    :model="presentationForm" v-loading="isLoading">

                <el-form-item label="Name" :prop="'name'" >
                    <el-col>
                        <el-input v-model="presentationFormName" placeholder="Enter name"/>
                    </el-col>
                </el-form-item>
                <el-form-item label="Description">
                    <el-col>
                        <el-input type="textarea" v-model="presentationFormDescription" placeholder="Enter description"/>
                    </el-col>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" icon="el-icon-check" @click="uploadClicked()">Save</el-button>
                </el-form-item>
            </el-form>
        </el-card>

        <!-- dialogs -->
        <el-dialog
        title="Confirm"
        :visible.sync="hasSubmitted"
        width="30%" center>
            <span> Are you sure that the presentation details are correct?</span>
            <span slot="footer" class="dialog-footer">
                <el-button v-on:click="hasSubmitted = false">Cancel</el-button>
                <el-button type="primary" v-on:click="addPresentation">Confirm</el-button>
            </span>
        </el-dialog>
        <el-dialog
        title="Success"
        :visible.sync="saveSuccess"
        width="30%" center>
            <span>You have successfully added a new presentation</span>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" v-on:click="closeSuccess">Sure</el-button>
            </span>
        </el-dialog>
        <!-- end of dialogs -->
    </el-main>
</template>

<script>
    import {AccessLevel, ID_NEW_PRESENTATION, SPECIAL_IDENTIFIER_PUBLIC} from "@/common/const";

    export default {
        name: 'PresentationBrief',
        props: {
            id: String
        },
        watch: {
            'id'() {
                this.updatePresentationForm()
            },
        },
        mounted() {
            this.updatePresentationForm();
            this.$store.dispatch('getVersionList')
        },
        computed: {
            isLogin() {
                return this.$store.state.userInfo.isLogin
            },
            presentationForm() {
                return {
                    name: this.presentationFormName,
                    creatorIdentifier: this.presentationFormCreatorIdentifier,
                    description: this.presentationFormDescription,
                }
            },
            presentationFormCreatorIdentifier() {
                return this.$store.state.presentation.presentationForm.creatorIdentifier
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
            saveSuccess() {
                return this.$store.state.presentation.isSaveSuccess
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
                hasSubmitted: false,
                rules: {
                    name: [
                        {required: true, message: 'Please enter presentation name', trigger: 'blur'},
                        {min: 3, message: 'The length should be more than 3 character', trigger: 'blur'}
                    ],
                }
            }
        },
        methods: {
            addPresentation() {
                this.hasSubmitted = false;
                this.$store.dispatch('savePresentation').then(() => {
                    if (this.isNewPresentation && !this.isLogin) {
                        return
                    }
                });
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
                } else {
                    this.$store.dispatch("getVersionList")
                }
            },
            uploadClicked() {
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
                    this.hasSubmitted = true;
                });
            },
            closeSuccess() {
                this.$store.commit("setSaveSuccess", false);
                this.$router.push({
                    name: 'analyze'
                });
            }
        },
        components: {
        },
    }
</script>

<style scoped>
    .errorMsg {
        margin-bottom: 18px;
    }
</style>
