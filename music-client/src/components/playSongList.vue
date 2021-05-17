<template>
<!-- transition  给vue中任何组件添加进入或离开的过渡，也就是显示与不显示  ，通过v-if就可以操作 -->
    <transition name="slide-fade">
        <div class="the-aside" v-if="showAside">
            <h2 class="title">播放列表</h2>
            <ul class="menus">
                <li v-for="(item,index) in listOfSongs" :key="index" :class="{'is-play':playID === item.id}"
                @click="toplay(item.id,item.url,item.pic,item.index,item.name,item.lyric)">
                    {{item.name}}
                </li>
            </ul>
        </div>
    </transition>
</template>
<script>
import {mapGetters} from 'vuex';

export default {
  data() {
    return {
      playID: ""
    }
  },
    computed: {
        ...mapGetters([
            'showAside',         //是否显示播放中的歌曲列表
            'listOfSongs',       //当前歌曲列表
            'id'                //播放中的音乐id
        ])
    },
    mounted(){
        let _this = this;
        document.addEventListener('click',function(){
            _this.$store.commit('setShowAside',false)
        },true);
    },
    methods:{
        //播放列表歌曲播放
        toplay: function(id,url,pic,index,name,lyric){
            this.playID = id;
            this.$store.commit('setId',id);
            this.$store.commit('setUrl',this.$store.state.Config.HOST+url);
            this.$store.commit('setPicUrl',this.$store.state.Config.HOST+pic);
            this.$store.commit('setListIndex',index);
            this.$store.commit('setTitle',name);
            this.$store.commit('setLyric',lyric);
        },

    }
}
</script>
<style  lang="scss" scoped>
@import '../assets/css/the-aside.scss';
</style>
