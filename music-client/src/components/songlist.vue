<template>
    <div class="content">
      <ul>
        <li>
          <div class="song-item">
            <span class="item-index"></span>
            <span class="item-name" style="padding-left:30px;">歌曲</span>
            <span class="item-title">歌手</span>
            <span class="item-intro">专辑</span>
          </div>
        </li>
        <li v-for="(item,index) in songList" :key="index">
          <div class="song-item">
            <span class="item-index">{{index+1}}</span>
            <span class="item-name" @click="play(item.id,item.url,item.pic,item.name,index,item.lyric)">
              <i class="el-icon-video-play"></i>
              {{SongName(item.name)}}
              </span>
            <span class="item-title">{{SingerName(item.name)}}</span>
            <span class="item-intro">{{item.introduction}}</span>
          </div>
        </li>
      </ul>
    </div>
</template>
<script>
import { tool } from "../mixins/tool"
export default {
  name: 'song-list',
  mixins: [tool],
  props: [
    "songList"
  ],
  methods: {
    // 搜索出来的歌曲播放的方法
    play(id,url,pic,name,index,lyric){
      this.$store.commit('setId',id);
      this.$store.commit('setUrl',this.$store.state.Config.HOST+url);
      this.$store.commit('setPicUrl',this.$store.state.Config.HOST+pic);
      this.$store.commit('setTitle',name);
      this.$store.commit('setListIndex',index);
      this.$store.commit('setLyric',lyric);
    },
  },
}
</script>
  </<style lang="scss" scoped>
  @import "../assets/css/album-content.scss"
  </style>
