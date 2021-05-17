<template>
  <div class="song-audio">
    <audio
      id="player"
      :src="url"
      controls="controls"
      preload="true"
      @canplay="togglePlay"
      @ended="ended"
    ></audio>
  </div>
</template>
<script>
import { mapState, mapGetters } from "vuex";
export default {
  name: "song-audio",
  computed: {
    ...mapState(["url", "isPlay"])
  },
  watch: {
    //监听播放还是暂停
    isPlay: function() {
      this.togglePlay();
    }
  },
  methods: {
    //获取链接后准备播放
    // startPlay(){
    //     let player = document.querySelector('#player');
    //     //开始播放
    //     player.play();
    // },
    //播放完成之后触发
    ended() {
      this.isPlay = false;
    },
    //开始、暂停
    togglePlay() {
      let player = document.querySelector("#player");
      if (this.isPlay) {
        player.play();
      } else {
        player.pause();
      }
    }
  }
};
</script>

<style>
.song-audio {
  /* 设置audio样式隐藏掉 */
  display: none;
}
</style>
