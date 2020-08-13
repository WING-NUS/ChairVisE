<template>
    <el-main>
        <el-card>
            <div slot="header" class="clearfix">
                <span> Add New Conference  </span>
            </div>
            <el-alert v-if="isNewConference && !isLogin" title="Please login to create new conference" type="error" show-icon
                      class="errorMsg"/>
            <el-form v-else :rules="rules" ref="conferenceForm"
                     :model="conferenceForm" v-loading="isLoading">
                <el-form-item label="Name" :prop="'name'" >
                    <el-col>
                        <el-input v-model="conferenceFormName" placeholder="Enter name"/>
                    </el-col>
                </el-form-item>
                <el-form-item label="Description">
                    <el-col>
                        <el-input v-model="conferenceFormDescription" placeholder="Enter description"/>
                    </el-col>
                </el-form-item>
                <el-form-item label="Conference Date" :prop="'date'">
                    <el-col>
                        <el-date-picker
                                v-model="conferenceFormDate"
                                type="datetime"
                                placeholder="Select date and time">
                        </el-date-picker>
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
            <span> Are you sure that the conference details are correct?</span>
            <span slot="footer" class="dialog-footer">
                <el-button v-on:click="hasSubmitted = false">Cancel</el-button>
                <el-button type="primary" v-on:click="addConference">Confirm</el-button>
            </span>
        </el-dialog>
        <el-dialog
        title="Success"
        :visible.sync="saveSuccess"
        width="30%" center>
            <span>You have successfully added a new conference</span>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" v-on:click="closeSuccess">Sure</el-button>
            </span>
        </el-dialog>
        <!-- end of dialogs -->
    </el-main>
</template>

<script>
    import Datepicker from 'vuejs-datepicker';
    import {ID_NEW_CONFERENCE} from "../common/const";
    export default {
        name: "AddConference",
        props: {
            id: String
        },
        watch: {
            'id'() {
                this.updateConferenceForm()
            },
        },
        mounted() {
            this.updateConferenceForm();
        },
        computed: {
            isLogin() {
                return this.$store.state.userInfo.isLogin
            },
            conferenceForm() {
                return {
                    name: this.conferenceFormName,
                    description: this.conferenceFormDescription,
                    date: this.conferenceFormDate,
                }
            },
            conferenceFormName: {
                get() {
                    return this.$store.state.conference.conferenceForm.name
                },
                set(value) {
                    this.$store.commit('setConferenceFormField', {
                        field: 'name',
                        value
                    })
                },
            },
            conferenceFormDescription: {
                get() {
                    return this.$store.state.conference.conferenceForm.description
                },
                set(value) {
                    this.$store.commit('setConferenceFormField', {
                        field: 'description',
                        value
                    })
                },
            },
            conferenceFormDate: {
                get() {
                    return this.$store.state.conference.conferenceForm.date
                },
                set(value) {
                    this.$store.commit('setConferenceFormField', {
                        field: 'date',
                        value
                    })
                },
            },
            isNewConference() {
                return this.id === ID_NEW_CONFERENCE
            },
            isInEditMode() {
                return this.isEditing || this.isNewPresentation
            },
            saveSuccess() {
                return this.$store.state.conference.isSaveSuccess
            },
            isLoading() {
                return this.$store.state.conference.conferenceFormStatus.isLoading
            },
            isError() {
                return this.$store.state.conference.conferenceFormStatus.isApiError
            },
            apiErrorMsg() {
                return this.$store.state.conference.conferenceFormStatus.apiErrorMsg
            }
        },
        methods: {
            addConference() {
                this.hasSubmitted = false;
                this.$store.dispatch('saveConference').then(() => {
                        if (this.isNewConference && !this.isLogin) {
                            return
                        }
                    });
            },
            updateConferenceForm() {
                if (this.$refs['conferenceForm']) {
                    this.$refs['conferenceForm'].clearValidate();
                }
                this.$store.commit('resetConferenceForm');
            },
            uploadClicked() {
                this.$refs['conferenceForm'].validate((valid, object) => {
                    if (!valid) {
                        if('name' in object) {
                            this.$notify.error({
                                title: 'Error',
                                message: object.name[0].message
                            });
                        }
                        if ('date' in object) {
                            this.$notify.error({
                                title: 'Error',
                                message: object.date[0].message,
                                offset: 100
                            });
                        }
                        return
                    }
                    this.$refs['conferenceForm'].clearValidate();
                    this.hasSubmitted = true;
                });
            },
            closeSuccess() {
                this.$store.commit("setSaveSuccess", false);
                this.$router.push({
                    name: 'conference'
                });
            }
        },
        components: {
            Datepicker,
        },
        data() {
            return {
                hasSubmitted: false,
                rules: {
                    name: [
                        {required: true, message: 'Please enter conference name', trigger: 'blur'},
                        {min: 3, message: 'The length should be more than 3 character', trigger: 'blur'}
                    ],
                    date: [
                        {required: true, message: 'Please select a conference date and time.', trigger: 'blur'},
                    ]
                }
            }
        },
    }
</script>

<style scoped>

</style>