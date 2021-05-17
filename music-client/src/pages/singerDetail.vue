<template>
    <div class="singer-album">
        <div class="album-slide">
            <div class="singer-img">
                <img :src="getImageUrl(singer.pic)">
            </div>
            <ul class="info">
                <li>{{attachSex(singer.sex)}}</li>
                <li>故乡：{{singer.location}}</li>
            </ul>
        </div>
        <div class="album-content">
            <div class="intro">
                <h2>{{singer.name}}</h2>
                <span>{{singer.introduction}}</span>
            </div>
            <div class="content">
                <!-- 歌单 -->
          <song-list :songList="songs"></song-list>
            </div>
        </div>
    </div>
</template>
<script>
import {tool} from '../mixins/tool';
import {mapGetters} from 'vuex';
import {searchSongBySingerId} from '../api/index';
import SongList from "../components/songlist"
export default {
    mixins: [tool],
    components: {
      SongList
    },
    data(){
        return {
            singerId: '',       //前面传来的歌手id
            singer: {},         //当前歌手信息
            songs: [],         //   歌曲列表
        }
    },
    computed:{
        ...mapGetters([
            'listOfSongs',      //当前播放列表
            'tempList',         //当前歌手对象
            'isLogin',          //用户是否已登录
            'userID',           //当前登录用户id
        ])
    },
    created(){
        this.singerId = this.$route.query.singerID;
        this.singer = this.tempList;
        this.getSongOfSingerId();
    },
    methods:{
        //根据歌手id查询歌曲
        getSongOfSingerId(){
            searchSongBySingerId(this.singerId)
                .then(res => {
                  this.songs = res;
                    this.$store.commit('setListOfSongs',res);
                })
                .catch(err =>{
                    console.log(err)
                })
        },
        //获取性别
        attachSex(value){
            if(value==0){
                return '女'
            }else if(value==1){
                return '男'
            }
            return '组合'
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/singer-album.scss';
</style>

