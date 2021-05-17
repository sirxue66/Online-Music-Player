const song = {
  state:{
      listOfSongs: [],     //当前歌曲列表
      url: '',                //歌曲地址
      id: '',                  //歌曲id
      title: '',                  //歌名
      picUrl: '',                 //歌曲图片
      autoNext: true,             //用于自动触发播放下一首
      lyric: [],                  //未处理的歌词数据
      tempList: {},               //单个歌单信息 或歌手信息
      listIndex: null,            //当前歌曲在歌单中的位置
      curTime: ''                //当前歌曲的播放时间
  },
  getters: {
      listOfSongs: state => {
          let listOfSongs = state.listOfSongs;
          if(!listOfSongs.length){
              listOfSongs = JSON.parse(window.sessionStorage.getItem('listOfSongs')||null);
          }
          return listOfSongs;
      },
      url: state => {
          let url = state.url;
          if(!url){
              url = JSON.parse(window.sessionStorage.getItem('url')||null);
          }
          return url;
      },
      id: state => {
          let id = state.id;
          if(!id){
              id = JSON.parse(window.sessionStorage.getItem('id')||null);
          }
          return id;
      },
      title: state => {
          let title = state.title;
          if(!title){
              title = JSON.parse(window.sessionStorage.getItem('title')||null);
          }
          return title;
      },
      picUrl: state => {
          let picUrl = state.picUrl;
          if(!picUrl){
              picUrl = JSON.parse(window.sessionStorage.getItem('picUrl')||null);
          }
          return picUrl;
      },
      autoNext: state => {
          let autoNext = state.autoNext;
          if(!autoNext){
              autoNext = JSON.parse(window.sessionStorage.getItem('autoNext')||null);
          }
          return autoNext;
      },
      lyric: state => {
          let lyric = state.lyric;
          if(!lyric){
              lyric = JSON.parse(window.sessionStorage.getItem('lyric')||null);
          }
          return lyric;
      },
      tempList: state => {
          let tempList = state.tempList;
          if(!tempList){
              tempList = JSON.parse(window.sessionStorage.getItem('tempList')||null);
          }
          return tempList;
      },
      listIndex: state => {
          let listIndex = state.listIndex;
          if(!listIndex){
              listIndex = JSON.parse(window.sessionStorage.getItem('listIndex')||null);
          }
          return listIndex;
      },
      curTime: state => {
        let curTime = state.curTime;
        if(!curTime){
          curTime = JSON.parse(window.sessionStorage.getItem('curTime')||null);
        }
        return curTime;
    },

  },
  mutations: {
      setListOfSongs: (state,listOfSongs) => {
          state.listOfSongs = listOfSongs;
          window.sessionStorage.setItem('listOfSongs',JSON.stringify(listOfSongs));
      },
      setUrl: (state,url) => {
          state.url = url;
          window.sessionStorage.setItem('url',JSON.stringify(url));
      },
      setId: (state,id) => {
          state.id = id;
          window.sessionStorage.setItem('id',JSON.stringify(id));
      },
      setTitle: (state,title) => {
          state.title = title;
          window.sessionStorage.setItem('title',JSON.stringify(title));
      },
      setPicUrl: (state,picUrl) => {
          state.picUrl = picUrl;
          window.sessionStorage.setItem('picUrl',JSON.stringify(picUrl));
      },
      setAutoNext: (state,autoNext) => {
          state.autoNext = autoNext;
          window.sessionStorage.setItem('autoNext',JSON.stringify(autoNext));
      },
      setLyric: (state,lyric) => {
          state.lyric = lyric;
          window.sessionStorage.setItem('lyric',JSON.stringify(lyric));
      },
      setTempList: (state,tempList) => {
          state.tempList = tempList;
          window.sessionStorage.setItem('tempList',JSON.stringify(tempList));
      },
      setListIndex: (state,listIndex) => {
          state.listIndex = listIndex;
          window.sessionStorage.setItem('listIndex',JSON.stringify(listIndex));
      },
      setCurTime: (state,curTime) => {
        state.curTime = curTime;
        window.sessionStorage.setItem('curTime',JSON.stringify(curTime));
    },
  }
}

export default song
