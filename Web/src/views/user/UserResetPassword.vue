<script setup>
import {ref} from 'vue'
import {userPwdUpdateService} from "@/api/user";
import {ElMessage} from "element-plus";


const pwdData = ref({
  old_pwd: '',
  new_pwd: '',
  re_pwd: ''
})

const updatePwdData = async () => {
  await userPwdUpdateService(pwdData.value);
  ElMessage.success('Password updated successfully')
  pwdData.value = {
    old_pwd: '',
    new_pwd: '',
    re_pwd: ''
  }
}
const checkRePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('Please confirm the password again'))
  } else if (value !== pwdData.value.new_pwd) {
    callback(new Error('The two passwords do not match'))
  } else {
    callback()
  }
}
const rules = {
  old_pwd: [
    {required: true, message: 'Please enter the old password', trigger: 'blur'},
    {min: 5, max: 16, message: 'Length must be 5-16 characters', trigger: 'blur'},
  ],
  new_pwd: [
    {required: true, message: 'Please enter the new password', trigger: 'blur'},
    {min: 5, max: 16, message: 'Length must be 5-16 characters', trigger: 'blur'},
  ],
  re_pwd: [
    {required: true, message: 'Please enter the confirmation password', trigger: 'blur'},
    {validator: checkRePassword, trigger: 'blur'},
  ]
}
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>Reset Password</span>
      </div>
    </template>
    <el-row>
      <el-col :span="12">
        <el-form :model="pwdData" :rules="rules" label-width="100px" size="large">
          <el-form-item label="Old Password" prop="old_pwd">
            <el-input v-model="pwdData.old_pwd" type="password" placeholder="Please enter the old password"></el-input>
          </el-form-item>
          <el-form-item label="New Password" prop="new_pwd">
            <el-input v-model="pwdData.new_pwd" type="password" placeholder="Please enter the new password"></el-input>
          </el-form-item>
          <el-form-item label="Confirm New Password" prop="re_pwd">
            <el-input v-model="pwdData.re_pwd" type="password" placeholder="Please enter the new password again"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updatePwdData">Submit Changes</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
</template>
