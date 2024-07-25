import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue';
import SpreadView from '@/views/SpreadView.vue';
import UserLogin from "@/views/UserLogin.vue";
import UserSignUp from "@/views/UserSignUp.vue";
import UserMypage from "@/views/UserMypage.vue";
import recordviewkjk from "@/views/RecordViewKJK.vue";
import AudioPlayView from '@/views/AudioPlayView.vue';
import PlayListView from '@/views/PlayListView.vue';
import RecordCHJView from '@/views/RecordCHJView.vue';
import ChangeVoiceView from '@/views/ChangeVoiceView.vue';
import PickItUpView from '@/views/PickItUpView.vue';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            redirect: '/home',
        },
        {
            path: '/home',
            name: 'home',
            component: HomeView
        },
        {
            path: '/spread',
            name: 'spread',
            component: SpreadView
        },
        {
            path: "/login",
            name: "login",
            component: UserLogin,
        },
        {
            path: "/signup",
            name: "signup",
            component: UserSignUp,
        },
        {
            path: '/search',
            name: 'search',
            component: PickItUpView
        },
        {
            path: "/playlist",
            name: "playlist",
            component: PlayListView,
        },
        {
            path: "/audioplayer",
            name: "audioplayer",
            component: AudioPlayView,
        },
        {
            path: '/recordkjk',
            name: 'recordkjk',
            component: recordviewkjk,
        },
        {
            path: "/mypage",
            name: "mypage",
            component: UserMypage,
        },
        {
            path: "/record",
            name: "record",
            component: RecordCHJView,
        },
        {
            path: "/change",
            name: "change",
            component: ChangeVoiceView,
        },
    ]
})

export default router
