<template>
    <div class="emptySection">
        <el-row>
            <el-col>
                <div class="icon">
                    <img src="@/assets/businessman.png" />
                </div>
            </el-col>
        </el-row>
        <el-row>
            <el-col>
                <p> 
                    <i> No presentation found. Want to generate presentation for a conference? </i>
                </p>
                <p>
                    Let's get started by adding a new presentation.
                </p>
                <el-button type="primary" icon="el-icon-plus" @click="createPresentation">Add New Presentation</el-button>
            </el-col>
        </el-row>
    </div>
</template>

<script>
export default {
    methods: {
        createPresentation() {
            this.$router.push("/analyze/create");
        },
    }    
}
</script>

<style scoped>
  .icon {
    margin-left: 04.5rem;
    margin-bottom: 0.25rem;
  }
  .emptySection {
    text-align: center;
    vertical-align: middle;
  }
</style>