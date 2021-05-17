<template>
  <div class="song-audio">
    <audio
      class="playAudio"
      ref="player"
      :src="url"
      controls="controls"
      preload="true"
      @canplay="startPlay"
      @ended="ended"
      @timeupdate="timeUpdate"
    ></audio>
  </div>
</template>
<script>
import {mapGetters} from 'vuex';
export default {
  computed: {
    ...mapGetters([
        'id',               //歌曲id
        'url',              //歌曲地址
        'autoNext',        //用于自动触发播放下一首
    ])
  },
  watch:{

  },
  methods:{
      //获取链接后准备播放
      startPlay(){
          let player = this.$refs.player;
          //开始播放
          player.play();
      },
      //播放完成之后触发
      ended(){
        // 传入autoNext的相反值，目的是让这个值有变化
         this.$store.commit('setAutoNext',!this.autoNext);
      },
       //音乐播放时记录音乐的播放位置
        timeUpdate(){
            this.$store.commit('setCurTime',this.$refs.player.currentTime);
        },
  }
};
</script>

<style>
.song-audio {
  display: inline;
}
.playAudio{
  width: 600px;
  height: 50px;
}
</style>
