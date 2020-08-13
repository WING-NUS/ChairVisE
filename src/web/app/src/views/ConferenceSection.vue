<template>
    <el-main>
        <el-container>
            <el-main>
                <conference-brief :id="id"/>
            </el-main>
        </el-container>
    </el-main>
</template>

<script>
    import {ID_NEW_CONFERENCE} from "@/common/const";
    import ConferenceBrief from "@/components/ConferenceBrief.vue";

    export default {
        props: {
            id: String,
        },
        data() {
            return {
                selectedNewSection: '',
                conferenceId: '',
            }
        },
        computed: {
            isLogin() {
                return this.$store.state.userInfo.isLogin
            },


            isNewConference() {
                return this.conferenceId === ID_NEW_CONFERENCE
            },

        },
        components: {
            ConferenceBrief
        },
        mounted() {
            this.conferenceId = this.$route.params.id;
        },
        methods: {
        }
    }
</script>

<style scoped>
    .el-main {
        padding: 0px 20px;
    }
</style>