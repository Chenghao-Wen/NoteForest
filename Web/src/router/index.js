import { createRouter, createWebHistory } from "vue-router";
import Login from "@/views/Login.vue";
import Layout from "@/views/Layout.vue";
import noteCategory from "@/views/note/noteCategory.vue";
import noteManage from "@/views/note/noteManage.vue";
import UserInfo from "@/views/user/UserInfo.vue";
import UserAvatar from "@/views/user/UserAvatar.vue";
import UserResetPassword from "@/views/user/UserResetPassword.vue";

const routes = [
  { path: "/login", component: Login },
  {
    path: "/",
    redirect: "/note/manage",
    component: Layout,
    children: [
      { path: "/note/category", component: noteCategory },
      { path: "/note/manage", component: noteManage },
      { path: "/user/info", component: UserInfo },
      { path: "/user/avatar", component: UserAvatar },
      { path: "/user/resetPassword", component: UserResetPassword },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes: routes,
});

export default router;
