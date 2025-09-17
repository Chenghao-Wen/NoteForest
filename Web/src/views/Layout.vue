<script setup>
import {
  CaretBottom,
  Crop,
  EditPen,
  Management,
  Promotion,
  SwitchButton,
  User,
  UserFilled,
} from "@element-plus/icons-vue";
import avatar from "@/assets/default.png";
import { userInfoService } from "@/api/user";
import { useUserInfoStore } from "@/stores/userInfo";
import { useRouter } from "vue-router";
import { ElMessage, ElMessageBox } from "element-plus";
import { useTokenStore } from "@/stores/token";

const userInfoStore = useUserInfoStore();
const getUserInfo = async () => {
  const result = await userInfoService();
  userInfoStore.setUserInfo(result.data);
};
getUserInfo();

const router = useRouter();
const tokenStore = useTokenStore();
const handleCommand = (command) => {
  if (command === "logout") {
    ElMessageBox.confirm(
      "Are you sure you want to log out?",
      "Friendly Reminder",
      {
        confirmButtonText: "Confirm",
        cancelButtonText: "Cancel",
        type: "warning",
      }
    ).then(async () => {
      // clear data in pinia
      userInfoStore.removeUserInfo();
      tokenStore.removeToken();
      ElMessage.success("Logout successful");
      await router.push("/login");
    });
  } else {
    router.push("/user/" + command);
  }
};
</script>

<template>
  <el-container class="layout-container">
    <!-- Left Menu -->
    <el-aside width="200px">
      <div class="el-aside__logo"></div>
      <el-menu
        active-text-color="#ffd04b"
        background-color="#232323"
        text-color="#fff"
        router
      >
        <el-menu-item index="/note/category">
          <el-icon>
            <Management />
          </el-icon>
          <span>note Categories</span>
        </el-menu-item>
        <el-menu-item index="/note/manage">
          <el-icon>
            <Promotion />
          </el-icon>
          <span>note Management</span>
        </el-menu-item>
        <el-sub-menu>
          <template #title>
            <el-icon>
              <UserFilled />
            </el-icon>
            <span>Personal Center</span>
          </template>
          <el-menu-item index="/user/info">
            <el-icon>
              <User />
            </el-icon>
            <span>Basic Information</span>
          </el-menu-item>
          <el-menu-item index="/user/avatar">
            <el-icon>
              <Crop />
            </el-icon>
            <span>Change Avatar</span>
          </el-menu-item>
          <el-menu-item index="/user/resetPassword">
            <el-icon>
              <EditPen />
            </el-icon>
            <span>Reset Password</span>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <!-- Right Area -->
    <el-container>
      <!-- Top Area -->
      <el-header>
        <div>
          NoteForest:
          <strong>{{ userInfoStore.userInfo.nickname }}</strong>
        </div>
        <el-dropdown placement="bottom-end" @command="handleCommand">
          <span class="el-dropdown__box">
            <el-avatar
              :src="
                userInfoStore.userInfo.userPic
                  ? userInfoStore.userInfo.userPic
                  : avatar
              "
            />
            <el-icon>
              <CaretBottom />
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="info" :icon="User"
                >Basic Information</el-dropdown-item
              >
              <el-dropdown-item command="avatar" :icon="Crop"
                >Change Avatar</el-dropdown-item
              >
              <el-dropdown-item command="resetPassword" :icon="EditPen"
                >Reset Password</el-dropdown-item
              >
              <el-dropdown-item command="logout" :icon="SwitchButton"
                >Log Out</el-dropdown-item
              >
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <!-- middle Area -->
      <el-main>
        <router-view />
      </el-main>
      <!-- Font Area -->
      <el-footer>NoteForest: NoteTree sharing</el-footer>
    </el-container>
  </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
  height: 100vh;

  .el-aside {
    background-color: #232323;

    &__logo {
      height: 120px;
      background: url("@/assets/logo.png") no-repeat center / 120px auto;
    }

    .el-menu {
      border-right: none;
    }
  }

  .el-header {
    background-color: #fff;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .el-dropdown__box {
      display: flex;
      align-items: center;

      .el-icon {
        color: #999;
        margin-left: 10px;
      }

      &:active,
      &:focus {
        outline: none;
      }
    }
  }

  .el-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    color: #666;
  }
}
</style>
