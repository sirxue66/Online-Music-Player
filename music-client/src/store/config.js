const config = {
  state:{
      HOST: 'http://127.0.0.1:8888',  //后台访问地址根目录
      showAside: false,               //是否显示播放中的歌曲列表
      isLogin: false,                // 用户是否登录
      isSearch: false                    //搜索使用的boolean变量
  },
  getters: {
    showAside: state => {
      let showAside = state.showAside
      if(!showAside){
          showAside = JSON.parse(window.sessionStorage.getItem('showAside'))
      }
      return showAside
  },
  isLogin: state => {
    let isLogin = state.isLogin
    if(!isLogin){
      isLogin = JSON.parse(window.sessionStorage.getItem('isLogin'))
    }
    return isLogin
    },
    isSearch: state => {
      let isSearch = state.isSearch;
      if(!isSearch){
        isSearch = JSON.parse(window.sessionStorage.getItem('isSearch'));
    }
    return isSearch;
    }

  },
  mutations: {
    setShowAside: (state,showAside) => {
      state.showAside = showAside
      window.sessionStorage.setItem('showAside',JSON.stringify(showAside))
  },
  setIsLogin: (state,isLogin) => {
    state.isLogin = isLogin
    window.sessionStorage.setItem('isLogin',JSON.stringify(isLogin))
},
  setIsSearch: (state,isSearch) => {
    state.isSearch = isSearch;
    window.sessionStorage.setItem('isSearch',JSON.stringify(isSearch));
  },

  }
}

export default config
